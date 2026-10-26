# AI 问答接入 DeepSeek

业务问答（`/ai/erp/ask`）和预警解读（`/ai/alerts/explain`）都走 DeepSeek。
DeepSeek 用的是 OpenAI 兼容协议，所以配置项前缀是 `spring.ai.openai.*`，
**不要被 "openai" 这个名字误导**，它不是只能连 OpenAI。

## 一、需要配三个值

| 配置项 | 环境变量 | 默认值 | 说明 |
|---|---|---|---|
| `spring.ai.openai.api-key` | `OPENAI_API_KEY` | `unset` | DeepSeek 密钥，**唯一必填项** |
| `spring.ai.openai.base-url` | `OPENAI_BASE_URL` | `https://api.deepseek.com/v1` | 官方地址带不带 `/v1` 都可用 |
| `spring.ai.openai.chat.options.model` | `OPENAI_MODEL` | `deepseek-v4-flash` | 见下方模型选择 |

### 模型怎么选

> `deepseek-chat` 和 `deepseek-reasoner` 已于 **2026-07-24 弃用**，调用会直接报错。

| 模型 | 适合 |
|---|---|
| `deepseek-v4-flash` | 默认。问答、摘要、分类这类任务，快且便宜 |
| `deepseek-v4-pro` | 复杂推理、长上下文。约 3 倍价格 |

这个问答场景用 `deepseek-v4-flash` 足够。

## 二、配置步骤

### 1. 拿密钥

打开 https://platform.deepseek.com/api_keys ，创建 API Key（形如 `sk-xxxxxxxx`）。
注意密钥只在创建时完整显示一次。

### 2. 写进 `scripts/env.local.ps1`

`start-all.bat` 会自动 dot-source 这个文件，环境变量直接传给后端子进程，
所以这是本地开发最省事的方式：

```powershell
$env:OPENAI_API_KEY  = 'sk-你的密钥'
$env:OPENAI_BASE_URL = 'https://api.deepseek.com/v1'
$env:OPENAI_MODEL    = 'deepseek-v4-flash'
```

> `env.local.ps1` 已在 `.gitignore` 里，不会被提交。
> 首次运行 `start-all.bat` 会自动生成这个文件，新生成的模板里已包含这三行（密钥留空）。

### 3. 重启后端

双击 `scripts/start-all.bat`。环境变量只在进程启动时读取，改完必须重启。

## 三、怎么确认生效

### 看启动日志

配置成功时后端起日志会打印：

```
[AI] DeepSeek 已就绪 model=deepseek-v4-flash endpoint=https://api.deepseek.com/v1/chat/completions
```

没配时是：

```
[AI] 未检测到 DeepSeek API Key，AI 问答与预警解读会降级为数据摘要。
```

### 看页面

进「业务问答」页，选一个范围（订单/库存/缺料/成本/交付）提问，结果区右上角会显示：

- **模型分析** → 调通了，`answer` 是大模型写的
- **数据摘要** → 没调通，只返回了查询到的原始数据（下方表格仍然可用）

## 四、排查

| 现象 | 原因 | 处理 |
|---|---|---|
| 一直显示「数据摘要」 | 密钥没配或没重启 | 先看启动日志那两行 |
| 日志显示已就绪但仍是「数据摘要」 | 看 `logs/sys-info.log` 里的 `[AI]` 告警，会写明是空内容还是调用失败 | 按下一行排查 |
| 告警「模型返回空内容 … 是否含推理=true」 | v4 默认开思考，推理 token 吃光 `max_tokens` | 已默认关闭 `thinking`，别把这个参数删掉 |
| 报 `Authentication Fails` | 密钥错或已删除 | 重新建一个 |
| 报 `Model Not Exist` | 用了已弃用的 `deepseek-chat` | 改成 `deepseek-v4-flash` |
| 报 402 / `Insufficient Balance` | 账户余额不足 | 充值 |
| 回答里出现 `1791820800000` 这类数字 | 日期被序列化成时间戳 | 已给 ObjectMapper 注册 JavaTimeModule，别回退成裸 `new ObjectMapper()` |

绕过应用直接验证接口：

```bash
curl https://api.deepseek.com/chat/completions \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer sk-你的密钥" \
  -d '{"model":"deepseek-v4-flash","messages":[{"role":"user","content":"你好"}],"stream":false}'
```

能返回 `choices[0].message.content` 就说明密钥和额度没问题，剩下的是应用侧配置。

## 五、换成中转站 / 其它兼容供应商

只改 `OPENAI_BASE_URL` 和 `OPENAI_MODEL` 即可，代码不用动。
例如接某个 OpenAI 兼容中转站：

```powershell
$env:OPENAI_API_KEY  = '中转站给的 key'
$env:OPENAI_BASE_URL = 'https://your-relay.example.com/v1'
$env:OPENAI_MODEL    = 'deepseek-v4-flash'   # 按中转站支持的模型名填
```

`base-url` 只写到根路径或 `/v1`，程序会自动补 `/chat/completions`；
如果你写的就是完整地址（以 `/chat/completions` 结尾）则原样使用。

## 六、实现说明（改代码时看这里）

- 提示词：`myfactoryai-manufacturing/.../erp/support/ErpAiPrompts.java`
  - `QA_SYSTEM` / `ALERT_SYSTEM` 是两段系统提示词，角色设定为服装代工厂的业务分析助手
  - `SCOPES` 是每个查询范围的**字段字典与判断要点**，必须与
    `ErpAiReadMapper` 的 SQL 别名保持一致（例如 `gapQty=净缺口`、`status=9 已取消`）。
    模型看不懂字段含义就会把数字解释错，这是最容易出问题的地方
  - `userMessage()` 会把**今天日期**拼进去，否则"哪些订单交期最紧"这类问题模型无法判断
  - 记录按 JSON 传给模型，不是 Java 的 `Map.toString`（后者是 `key=value` 形式，容易串行）
  - 改提示词后跑 `mvn -pl myfactoryai-admin -am test -Dtest=ErpAiPromptsTest`，
    该测试钉住了关键约束：日期、字段字典、JSON 格式、只读护栏
- 配置类：`myfactoryai-ai/.../ai/config/LLMConfig.java`
  - `isDeepseekConfigured()` 统一判断密钥是否可用（把 `unset`、占位值都算作未配置，
    避免每次提问白打一次 401 请求）
  - `deepseekEndpoint()` 负责拼 `chat/completions`
  - 连不上/超时：连接 5s（`spring.ai.openai.timeout.connect-ms`），
    读取 30s（`read-ms`）
- 调用方：`ErpAiQuestionController`（业务问答）、`ErpAlertController`（预警解读）
  - 请求带 `temperature=0.3`（业务分析要稳定复现）和 `max_tokens`（控成本）
  - 模型调用被 `try/catch` 包住，失败时**降级为数据摘要**，不会把整个请求打挂
  - 返回体里的 `modelUsed` 就是给前端区分「模型分析 / 数据摘要」用的
- 前端：`AiAssistantView.vue`
  - 结果右上角 `modelUsed ? '模型分析' : '数据摘要'`
  - 每个查询范围内置几条示例问题（`examples`），点击直接填入输入框
- 前端 axios 超时是 15s，所以后端等待模型的时间不要超过这个值，否则页面会先超时。

## 七、安全提醒

- 密钥只放在 `env.local.ps1` 或部署机的环境变量里，**不要写进 `application.yml` 提交**。
- 问答会把查询结果（最多 20 条）作为上下文发给模型，本身就是发给第三方；
  如果数据敏感，需要评估后再开。
- 该接口是只读的：不提供任何写库能力，模型输出也不会被执行。
