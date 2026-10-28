# -*- coding: utf-8 -*-
"""用 cli-anything-wps 的 WPS 后端（KWPP.Application）生成答辩 PPT。

链路：cli_anything.wps.utils.wps_backend.find_wps('impress') → WPS 演示 COM
      → create_document(app, 'impress') → 逐页画形状 → save_as(pres, ...)
画布 960 x 540 pt，蓝白配色，共 10 页，每页写入口播备注。
"""
import os
import sys

HARNESS = r"C:\Users\dxh50\WorkBuddy\2026-09-12-18-19-44\harness-anything"
sys.path.insert(0, HARNESS)

from cli_anything.wps.utils.wps_backend import (  # noqa: E402  ← 技能自带后端
    find_wps,
    create_document,
    save_as,
    export_pdf,
)

OUT_DIR = r"D:\haiErp\haiERP\docs\答辩PPT"
OUT_PPTX = os.path.join(OUT_DIR, "海岚ERP-答辩汇报-WPS.pptx")
OUT_PDF = os.path.join(OUT_DIR, "海岚ERP-答辩汇报-WPS.pdf")

W, H = 960, 540
FONT = "微软雅黑"


def bgr(hex_color):
    h = hex_color.lstrip("#")
    r, g, b = int(h[0:2], 16), int(h[2:4], 16), int(h[4:6], 16)
    return (b << 16) | (g << 8) | r


# 蓝白配色
P900 = "#0E2E5C"
P700 = "#1B4F9C"
P500 = "#2E7BD6"
P200 = "#B9D1F2"
P100 = "#E8F1FC"
P050 = "#F4F8FE"
WHITE = "#FFFFFF"
TEXT = "#1A2433"
MUTED = "#5B6B7F"
LINE = "#D8E4F3"

# msoShape 常量
SH_RECT = 1
SH_ROUND = 5
SH_OVAL = 9
SH_RIGHT_ARROW = 33
SH_DOWN_ARROW = 36

app = find_wps("impress")
try:
    app.Visible = True
except Exception:
    pass

pres = create_document(app, "impress")
pres.PageSetup.SlideWidth = W
pres.PageSetup.SlideHeight = H

_idx = [1]


def slide(notes=""):
    s = pres.Slides.Add(_idx[0], 12)   # 12 = 空白版式
    _idx[0] += 1
    try:
        s.FollowMasterBackground = False
    except Exception:
        pass
    try:
        s.Background.Fill.Solid()
        s.Background.Fill.ForeColor.RGB = bgr(WHITE)
    except Exception:
        pass
    if notes:
        try:
            s.NotesPage.Shapes(2).TextFrame.TextRange.Text = notes
        except Exception:
            pass
    return s


def _fit(tf):
    try:
        tf.WordWrap = True
        tf.AutoSize = 0
    except Exception:
        pass


def txt(s, x, y, w, h, text, size=14, color=TEXT, bold=False,
        align=1, anchor=1, spacing=1.25):
    box = s.Shapes.AddTextbox(1, x, y, w, h)
    tf = box.TextFrame
    _fit(tf)
    try:
        tf.MarginLeft = 0
        tf.MarginRight = 0
        tf.MarginTop = 0
        tf.MarginBottom = 0
        tf.VerticalAnchor = anchor
    except Exception:
        pass
    tr = tf.TextRange
    tr.Text = text
    tr.Font.Size = size
    tr.Font.Bold = -1 if bold else 0
    tr.Font.Color = bgr(color)
    tr.Font.Name = FONT
    try:
        tr.Font.NameFarEast = FONT
    except Exception:
        pass
    tr.ParagraphFormat.Alignment = align
    try:
        tr.ParagraphFormat.SpaceWithin = spacing
    except Exception:
        pass
    return box


def shape(s, kind, x, y, w, h, fill=None, line=None, line_w=0.75, radius=None, text="",
          size=12, color=WHITE, bold=True, align=2):
    sp = s.Shapes.AddShape(kind, x, y, w, h)
    try:
        sp.Shadow.Visible = 0
    except Exception:
        pass
    if fill is None:
        try:
            sp.Fill.Visible = 0
        except Exception:
            pass
    else:
        sp.Fill.Visible = -1
        sp.Fill.Solid()
        sp.Fill.ForeColor.RGB = bgr(fill)
    if line is None:
        try:
            sp.Line.Visible = 0
        except Exception:
            pass
    else:
        sp.Line.Visible = -1
        sp.Line.ForeColor.RGB = bgr(line)
        sp.Line.Weight = line_w
    # 圆角半径：WPS 的 Adjustments 是参数化属性，pywin32 晚绑定不支持赋值，
    # 这里沿用形状默认圆角（约 0.17），视觉差异可忽略。
    if text:
        tf = sp.TextFrame
        _fit(tf)
        try:
            tf.VerticalAnchor = 3
            tf.MarginLeft = 4
            tf.MarginRight = 4
            tf.MarginTop = 2
            tf.MarginBottom = 2
        except Exception:
            pass
        tr = tf.TextRange
        tr.Text = text
        tr.Font.Size = size
        tr.Font.Bold = -1 if bold else 0
        tr.Font.Color = bgr(color)
        tr.Font.Name = FONT
        try:
            tr.Font.NameFarEast = FONT
        except Exception:
            pass
        tr.ParagraphFormat.Alignment = align
    return sp


def rrect(s, x, y, w, h, fill, line=None, radius=0.10, line_w=0.75, text="", size=12,
          color=WHITE, bold=True, align=2):
    return shape(s, SH_ROUND, x, y, w, h, fill, line, line_w, radius, text, size, color, bold, align)


def header(s, kicker, title, page):
    txt(s, 48, 30, 600, 16, kicker, 10.5, P500, True, spacing=1.0)
    txt(s, 48, 48, 820, 32, title, 23, P900, True, spacing=1.0)
    shape(s, SH_RECT, 48, 84, 44, 3, P500)
    shape(s, SH_RECT, 92, 85.4, W - 140, 0.8, LINE)
    txt(s, 48, 512, 420, 14, "海岚 ERP · 服装制造企业资源计划", 8, MUTED, spacing=1.0)
    txt(s, W - 78, 512, 30, 14, str(page), 9, P500, True, align=3, spacing=1.0)


def arrow(s, x, y, w=18, h=13, color=P200):
    shape(s, SH_RIGHT_ARROW, x, y, w, h, color)


# =============================================================== 1 封面
s = slide("开场 20 秒：我们做的是服装制造企业的 ERP，覆盖报价、订单、采购、生产、库存、出货、"
          "应收收款到标准成本结算的整条链路。今天按「业务怎么转、系统怎么接、数据怎么对」三条线讲。")
shape(s, SH_RECT, 0, 0, W, 8, P700)
shape(s, SH_RECT, 0, 8, W, 242, P050)
shape(s, SH_RECT, 0, 250, W, 3, P700)

txt(s, 60, 74, 520, 18, "课程答辩 · 实训项目", 12, P500, True, spacing=1.0)
txt(s, 60, 100, 620, 62, "海岚 ERP", 52, P900, True, spacing=1.0)
txt(s, 60, 168, 680, 30, "服装制造企业资源计划 · 从报价到成本的一条完整链路", 17, P700, spacing=1.2)
txt(s, 60, 206, 680, 20, "Vue 3 + TypeScript  /  Java 21 + Spring Boot 3.5  /  MySQL 8 + Redis", 11.5, MUTED, spacing=1.0)

for i, (n, lab) in enumerate([("19", "步业务主流程"), ("8", "个岗位角色"), ("60", "项数据自检")]):
    x = 640 + i * 100
    txt(s, x, 92, 90, 34, n, 30, P500, True, align=2, spacing=1.0)
    txt(s, x, 130, 90, 16, lab, 9, MUTED, align=2, spacing=1.0)

for i, (k, v) in enumerate([("答辩人", "（填写姓名）"), ("团队", "7 人 · 前后端分离"), ("日期", "2026 年 10 月")]):
    x = 60 + i * 220
    txt(s, x, 300, 90, 14, k, 9, MUTED, True, spacing=1.0)
    txt(s, x, 318, 200, 18, v, 12, TEXT, spacing=1.0)

for i, b in enumerate([
    "一条订单跑通 19 步：报价 → 订单 → 采购 → 生产 → 出货 → 应收 → 成本",
    "单据只走动作、库存只认流水、金额只由服务端计算",
    "60 项 SQL 自检保证「账能对上」，8 个岗位按钮级权限可现场对比",
]):
    y = 368 + i * 34
    shape(s, SH_RECT, 60, y + 7, 6, 6, P500)
    txt(s, 78, y, 820, 18, b, 12.5, TEXT, spacing=1.0)

# =============================================================== 2 定位
s = slide("这一页给规模和定位。数字都实测过，老师追问可以当场 find/wc 验证。"
          "重点强调：九个域不是九个模块菜单，是一条链路上的九个环节。")
header(s, "PROJECT OVERVIEW", "项目定位：一条链、九个域、三件事", 2)
txt(s, 48, 96, 430, 20, "我们做的不是通用 ERP，而是服装代工厂的「订单到成本」那一段。", 13, P700, True, spacing=1.3)
txt(s, 48, 124, 430, 92,
    "上游接客户报价与订单，中间管物料、工单与库存，下游接出货、应收与成本。\n"
    "九个业务域：主数据、销售、采购、生产、库存、出货、成本、人事、财务。", 11.5, TEXT, spacing=1.55)
txt(s, 48, 226, 200, 16, "解决三个具体问题", 11, P500, True, spacing=1.0)
for i, (t1, t2) in enumerate([
    ("订单与物料脱节", "接了单不知道缺多少料 → 净缺料视图按 BOM 算到物料"),
    ("库存账实不符", "有台账没流水 → 全部出入库记流水，结存由流水累加"),
    ("成本算不清", "报价靠拍脑袋 → 标准成本试算并冻结，可被下一轮报价引用"),
]):
    y = 250 + i * 60
    rrect(s, 48, y, 430, 50, P100, None, 0.14)
    txt(s, 60, y + 8, 400, 14, t1, 11, P900, True, spacing=1.0)
    txt(s, 60, y + 26, 405, 16, t2, 9.5, MUTED, spacing=1.0)

txt(s, 520, 96, 200, 16, "一组实数（可现场验证）", 10.5, P500, True, spacing=1.0)
stats = [("325", "后端 Java 文件"), ("43", "REST 控制器"), ("260", "方法级接口"),
         ("228", "生效权限注解"), ("66", "数据库表"), ("60", "SQL 自检项")]
for i, (n, lab) in enumerate(stats):
    r, c = i // 3, i % 3
    x, y = 520 + c * 140, 122 + r * 92
    rrect(s, x, y, 128, 80, WHITE, P200, 0.12)
    txt(s, x, y + 14, 128, 30, n, 24, P700, True, align=2, spacing=1.0)
    txt(s, x, y + 50, 128, 14, lab, 9, MUTED, align=2, spacing=1.0)
txt(s, 520, 306, 400, 44, "另有 9 个视图、186 项菜单（ERP 132 项：1 目录 + 32 页面 + 99 按钮）、"
                          "8 个岗位、7 个部门与 7 个岗位定义。", 9.5, MUTED, spacing=1.4)
rrect(s, 48, 436, 864, 50, P050, None, 0.12)
shape(s, SH_RECT, 48, 436, 3, 50, P500)
txt(s, 66, 448, 830, 26, "一句话：我们只做服装代工这一段，但这一段的状态机、动作幂等与库存账实一致，是按能上线的标准做的。",
    10, P900, True, spacing=1.2)

# =============================================================== 3 主流程总览
s = slide("全场最重要的一页，讲 90 秒。五个阶段不是五个模块，是一条链路的五个环节："
          "接单决定能不能做，采购决定料有没有，生产决定做出来没有，交付决定钱回没回来，"
          "核算决定下一单报多少。底部三条纪律是整套系统的设计前提。")
header(s, "CORE · BUSINESS FLOW", "业务主流程：一条订单跑完 19 步", 3)

stages = [
    ("01", "接单与备料", ["报价单成交 → 转订单", "订单确认冻结色码/价/期", "净缺料 = 需求 − 现存 − 在途"], "产出：订单、缺料清单"),
    ("02", "采购与到货", ["采购单 0→1→2→3", "下单即计入在途", "分批收货写入库流水"], "产出：入库流水、应付"),
    ("03", "生产执行", ["按订单款式建工单（冻结）", "齐套后领料、按工序报工", "完工按 SKU 入库"], "产出：工单、计件、成品"),
    ("04", "交付与结算", ["分批发货 → 签收", "发货即生成应收", "收款核销、账龄"], "产出：出货单、应收"),
    ("05", "核算与闭环", ["标准成本试算 → 确认", "成本快照可复算", "生成下一轮报价"], "产出：结算单、新报价"),
]
CW, STEP = 160, 180
for i, (no, name, lines, out) in enumerate(stages):
    x = 48 + i * STEP
    rrect(s, x, 108, CW, 236, WHITE, LINE, 0.08)
    rrect(s, x, 108, CW, 40, P700, None, 0.08, text=no + "  " + name, size=11)
    shape(s, SH_RECT, x + 0.6, 132, CW - 1.2, 16, P700)
    for j, ln in enumerate(lines):
        y = 156 + j * 44
        shape(s, SH_RECT, x + 11, y + 5, 4, 4, P500)
        txt(s, x + 21, y, CW - 30, 40, ln, 9, TEXT, spacing=1.3)
    shape(s, SH_RECT, x + 11, 300, CW - 22, 0.8, LINE)
    txt(s, x + 11, 310, CW - 22, 24, out, 8, P500, True, spacing=1.2)
    if i < 4:
        arrow(s, x + CW + 4, 220, 12, 13)

rrect(s, 48, 366, 864, 54, P050, None, 0.10)
txt(s, 66, 376, 830, 18, "贯穿全流程的三条纪律", 10, P700, True, spacing=1.0)
txt(s, 66, 394, 830, 20,
    "① 状态只走动作端点（没有「直接改状态」的接口）   ② 库存只认流水，结存由流水累加   ③ 金额只由服务端算，前端不参与结算",
    9.5, TEXT, spacing=1.2)
shape(s, SH_RECT, 48, 440, 864, 1, P200)
for i, m in enumerate(["客户下单", "物料到位", "成品入库", "客户签收", "成本确认"]):
    x = 48 + i * 172.8 + 86
    shape(s, SH_RECT, x, 437, 2, 7, P500)
    txt(s, x - 50, 450, 100, 14, m, 8.5, MUTED, align=2, spacing=1.0)

# =============================================================== 4 接单备料
s = slide("接单段两个关键点：报价转订单只能一次（防止一张报价下两次单）；草稿不计在途，"
          "否则下了单还没到货就会重复采购。收货强调流水只增不改，这是后面能对账的基础。")
header(s, "STAGE 01—02", "接单与备料：从一张报价单到料进仓库", 4)
steps = [
    ("报价单", "0 待确认\n1 已接受\n3 已转单", P500),
    ("销售订单", "0 草稿\n1 已确认\n2 生产中", P500),
    ("净缺料", "BOM 需求\n− 已领 − 现存\n− 在途", P700),
    ("采购单", "0 草稿\n1 已下单\n2 部分/3 已到货", P500),
    ("收货入库", "写库存流水\n结存累加\n形成应付", P700),
]
for i, (t1, t2, col) in enumerate(steps):
    x = 48 + i * 178
    rrect(s, x, 108, 152, 84, WHITE, LINE, 0.10)
    shape(s, SH_RECT, x, 108, 4, 84, col)
    txt(s, x + 14, 116, 130, 18, t1, 12, P900, True, spacing=1.0)
    txt(s, x + 14, 140, 128, 46, t2, 8.5, MUTED, spacing=1.35)
    if i < 4:
        arrow(s, x + 156, 146, 16, 12)

rules = [
    ("报价只能转一次", "转单靠「条件更新」：只有已接受的报价能改成已转单，并发下只有一个请求成功；重复转单返回 2006，整张订单回滚 —— 防止重复生产、重复记账。"),
    ("草稿不计在途", "采购单确认（或审批通过）那一刻才计入在途；收货后已收数量不再算在途，所以净缺料 = 需求 − 已领 − 现存 − 在途，不会因为「下了单还没到」而重复采购。"),
    ("收货即入账", "收货写库存流水（只增不改）、结存同步累加、按收货金额挂应付；若下单时已付款，收货不再重复挂应付 —— 避免同一批料两次计账。"),
]
for i, (t, d) in enumerate(rules):
    y = 216 + i * 84
    rrect(s, 48, y, 864, 74, P050, None, 0.10)
    shape(s, SH_RECT, 48, y, 3, 74, P500)
    txt(s, 66, y + 10, 200, 16, t, 10.5, P700, True, spacing=1.0)
    txt(s, 66, y + 30, 820, 40, d, 9.5, TEXT, spacing=1.35)

# =============================================================== 5 生产
s = slide("生产段核心是「冻结」：工单建单时把色码计划量、BOM 用量、工序工价抄一份到工单里，"
          "之后改主数据不影响在产工单，成本也能复算。报工的四道约束是服务端校验，不是前端提示。")
header(s, "STAGE 03", "生产执行：工单冻结，领料只认现存", 5)
for i, (t1, t2) in enumerate([("建工单", "冻结色码/ BOM / 工价"), ("领料", "扣库存 + 领料流水"),
                              ("报工", "工序 × SKU 记合格/不良"), ("完工", "按 SKU 入成品库")]):
    y = 108 + i * 74
    rrect(s, 48, y, 200, 58, P100, None, 0.12)
    txt(s, 62, y + 9, 130, 18, t1, 12, P900, True, spacing=1.0)
    txt(s, 62, y + 30, 178, 16, t2, 8.5, MUTED, spacing=1.0)
    if i < 3:
        shape(s, SH_DOWN_ARROW, 140, y + 60, 11, 12, P200)

txt(s, 288, 100, 400, 16, "报工的四道约束（都在服务端校验）", 10.5, P500, True, spacing=1.0)
cons = [
    ("不超过计划量", "同一 SKU 同一工序，累计合格数 ≤ 工单冻结的计划量"),
    ("不超过前道工序", "本工序累计合格数 ≤ 前一道工序的累计合格数（不能凭空多出来）"),
    ("不超工资期", "报工日期所属月份工资单已确认 → 拒绝补录，要先作废工资单"),
    ("外发不计件", "委外工序只记成本，不生成员工计件；内部报工才按「合格数 × 工价快照」"),
]
for i, (t, d) in enumerate(cons):
    y = 122 + i * 60
    rrect(s, 288, y, 620, 52, WHITE, LINE, 0.12)
    shape(s, SH_RECT, 288, y, 3, 52, P700)
    txt(s, 304, y + 8, 170, 16, t, 10, P900, True, spacing=1.0)
    txt(s, 304, y + 26, 590, 20, d, 8.8, MUTED, spacing=1.2)

rrect(s, 48, 400, 864, 86, P050, None, 0.10)
txt(s, 66, 412, 830, 16, "两个容易被忽略的边界", 9.5, P700, True, spacing=1.0)
txt(s, 66, 434, 830, 44,
    "· 领料只认现存库存：采购在途的量只参与缺料计算，不能拿来领料，否则账上会出现负数\n"
    "· 完工入库不超过末道工序累计合格数：没做完的工序不能提前入库，防止「工单说完工、库存却没有」",
    9, TEXT, spacing=1.35)

# =============================================================== 6 交付结算
s = slide("交付段最容易被问的是「发齐」和「完成」的区别，以及成本口径。应收是发货那一刻自动生成的；"
          "成本是标准成本快照，改价不影响历史。最后那句「一个事务」是加分点。")
header(s, "STAGE 04—05", "交付与结算：发货即开票，成本是快照", 6)
steps2 = [
    ("出货单", "按未发量选批次\n0 待发货 → 1 已发货 → 2 已签收"),
    ("应收", "金额 = 出货量 × 订单单价\n到期日 = 发货日 + 客户账期"),
    ("收款", "登记 + 确认 → 写资金流水\n应收余额递减，账龄分档"),
    ("标准成本", "材料 + 人工 + 费用\n确认后冻结，不可重算覆盖"),
    ("新报价", "引用已确认成本\n形成下一轮报价依据"),
]
for i, (t1, t2) in enumerate(steps2):
    x = 48 + i * 182
    rrect(s, x, 108, 160, 92, WHITE, LINE, 0.10)
    txt(s, x + 14, 118, 132, 18, t1, 11.5, P900, True, spacing=1.0)
    txt(s, x + 14, 142, 132, 50, t2, 8.5, MUTED, spacing=1.3)
    if i < 4:
        arrow(s, x + 163, 150, 14, 12)

for i, (t, d) in enumerate([
    ("发齐 ≠ 完成", "发齐看累计发货量是否达到订购量；完成看是否所有出货单都签收。两个状态、两个动作。"),
    ("分批发货", "按 SKU + 批次记录，同一订单可以多次发货；未发齐不进入准交率分母，逾期未发齐单独列。"),
    ("标准成本 ≠ 实际成本", "用冻结 BOM × 物料参考价 + 计划合格量 × 冻结工价算；退货不冲回已确认成本。"),
]):
    x = 48 + i * 292
    rrect(s, x, 224, 280, 96, P100, None, 0.10)
    txt(s, x + 16, 236, 250, 18, t, 11, P700, True, spacing=1.0)
    txt(s, x + 16, 260, 252, 52, d, 9, TEXT, spacing=1.35)

rrect(s, 48, 340, 864, 76, WHITE, P200, 0.10)
txt(s, 66, 350, 300, 16, "资金闭环（财务页 8 个标签）", 10, P700, True, spacing=1.0)
for i, c in enumerate(["总览", "账户", "资金流水", "月度现金流", "应收", "应付", "账龄汇总", "收付款"]):
    rrect(s, 66 + i * 104, 374, 96, 28, P050, LINE, 0.25, text=c, size=9, color=P700, bold=False)
txt(s, 48, 432, 864, 44,
    "发货是一个事务：校验期间 → 校验状态与未发量 → 扣库存 + 写出库流水 → 改出货状态 → 改订单状态 → 生成应收。\n"
    "任何一步失败全部回滚，所以不会出现「货发了、应收没生成」。", 9, TEXT, spacing=1.4)

# =============================================================== 7 三条纪律
s = slide("这一页把三个技术点翻译成业务语言。如果老师问并发怎么处理，从第 2 条展开："
          "条件更新 + 请求键表 + 行锁。")
header(s, "DESIGN PRINCIPLES", "三条贯穿全系统的纪律", 7)
princ = [
    ("01", "状态只走动作",
     "做法：不给「直接改状态」的接口，\n每个动作的 SQL 自带前置状态\n\n"
     "效果：并发下只有一个请求能改成功，\n其余按失败回滚\n\n"
     "现场可查：ErpOrderService.confirm\n（UPDATE … WHERE status=0）\n\n"
     "被追问「能直接改库吗」：能改，但\n改库等于绕过动作，自检第 28 项会 FAIL"),
    ("02", "动作幂等",
     "做法：请求键 + 参数摘要 + 状态位，\n统一落 biz_action_request 表\n\n"
     "效果：同键重复提交只入账一次；\n同键换参数报 2007；换键正常执行\n\n"
     "现场可查：ErpStockService.begin\nErpStockMapper.reserveAction\n\n"
     "被追问「键谁生成」：前端每次动作\n生成 UUID，网络重试复用同一个"),
    ("03", "账实可对账",
     "做法：结存由流水累加，按「物料 + 批次 +\n仓库」四元组双向比对\n\n"
     "效果：幽灵出入库跑一次自检就暴露，\n不用人工翻账\n\n"
     "现场可查：sql/self_check.sql 第 3 项\n（库存双向对账）\n\n"
     "被追问「怎么发现的」：演示库曾有 5 张\n已签收出货单没有出库流水，就是对出来的"),
]
for i, (no, t, d) in enumerate(princ):
    x = 48 + i * 292
    rrect(s, x, 104, 280, 274, P050, None, 0.08)
    shape(s, SH_RECT, x, 104, 280, 46, P700)
    txt(s, x + 18, 114, 40, 26, no, 16, P200, True, spacing=1.0)
    txt(s, x + 60, 116, 200, 24, t, 13, WHITE, True, spacing=1.0)
    txt(s, x + 18, 160, 246, 212, d, 9, TEXT, spacing=1.35)

rrect(s, 48, 424, 864, 58, P050, None, 0.10)
txt(s, 66, 434, 830, 16, "为什么先讲这三条", 9.5, P700, True, spacing=1.0)
txt(s, 66, 452, 830, 24,
    "因为业务链路会变（加节点、改规则），但这三条是地基：状态不乱、动作不重、账能对上。后面的技术架构与权限设计都是为它们服务的。",
    9, TEXT, spacing=1.2)

# =============================================================== 8 架构
s = slide("架构这页不讲细节，讲两件事：分层职责（Controller 只做鉴权和参数边界，业务规则全在 Service），"
          "以及业务独立成模块，换前端不影响业务。")
header(s, "ARCHITECTURE", "技术架构：分层清楚，业务独立成模块", 8)
layers = [
    ("前端", "Vue 3 + TypeScript + Vite + Element Plus", "28 个页面（10 个手写 + 配置驱动）· 26 条路由 · 按钮级权限", P500),
    ("后端", "Java 21 + Spring Boot 3.5 + MyBatis + Spring Security/JWT", "43 个控制器 · 260 个接口 · 228 处权限注解 · 51 个事务方法", P700),
    ("数据", "MySQL 8（66 张表 / 9 个视图）+ Redis（登录态、AI 限流）", "库存流水只增不改 · 结存由流水累加 · 视图统一口径", P900),
]
for i, (name, tech, desc, col) in enumerate(layers):
    y = 104 + i * 96
    rrect(s, 48, y, 560, 84, WHITE, LINE, 0.08)
    shape(s, SH_RECT, 48, y, 62, 84, col)
    txt(s, 48, y + 30, 62, 20, name, 12, WHITE, True, align=2, spacing=1.0)
    txt(s, 126, y + 12, 460, 16, tech, 10, P900, True, spacing=1.0)
    txt(s, 126, y + 36, 460, 36, desc, 9, MUTED, spacing=1.3)
    if i < 2:
        shape(s, SH_DOWN_ARROW, 320, y + 86, 14, 14, P200)

txt(s, 636, 104, 300, 16, "后端 6 个 Maven 模块", 10.5, P500, True, spacing=1.0)
for i, (m, d) in enumerate([
    ("ruoyi-common", "公共库：统一响应、异常、工具"),
    ("ruoyi-framework", "安全骨架：JWT、权限 Bean"),
    ("ruoyi-quartz", "定时任务（默认关闭）"),
    ("myfactoryai-system", "用户/角色/菜单/部门 + 单据冻结"),
    ("myfactoryai-manufacturing", "业务域：service / mapper / domain"),
    ("myfactoryai-admin", "启动类 + 控制器 + AI 配置"),
]):
    y = 124 + i * 44
    rrect(s, 636, y, 276, 38, P050, None, 0.14)
    txt(s, 648, y + 5, 250, 14, m, 8.8, P700, True, spacing=1.0)
    txt(s, 648, y + 20, 250, 14, d, 7.8, MUTED, spacing=1.0)

txt(s, 48, 392, 864, 16, "一次请求怎么走（以订单列表为例）", 10, P700, True, spacing=1.0)
for i, c in enumerate(["浏览器", "JWT 过滤", "@PreAuthorize", "Controller", "Service（校验/事务）", "Mapper（注解 SQL）", "MySQL"]):
    x = 48 + i * 124
    rrect(s, x, 414, 112, 30, P100, None, 0.18, text=c, size=8.5, color=P900, bold=False)
    if i < 6:
        arrow(s, x + 113, 422, 9, 14)

# =============================================================== 9 验证
s = slide("验证这页把「我们真的测过」讲清楚。挑库存双向对账讲深一点，"
          "它能同时证明「流水驱动」的设计和「自检不是走过场」。不足要主动说。")
header(s, "VERIFICATION", "怎么证明它对：三层验证", 9)
for i, (n, t, d) in enumerate([
    ("60", "项 SQL 自检", "库存双向对账、单据链路、权限完整度"),
    ("32", "个单元测试", "6 个测试类，含权限护栏反射扫描"),
    ("8", "个账号验收", "按岗位逐个登录，检查菜单与 403 边界"),
]):
    x = 48 + i * 292
    rrect(s, x, 104, 280, 96, P050, None, 0.10)
    txt(s, x + 18, 116, 70, 34, n, 26, P700, True, spacing=1.0)
    txt(s, x + 92, 122, 170, 16, t, 10.5, P900, True, spacing=1.0)
    txt(s, x + 92, 144, 172, 40, d, 8.5, MUTED, spacing=1.3)

txt(s, 48, 216, 400, 16, "自检里最能说明问题的一项", 10, P500, True, spacing=1.0)
rrect(s, 48, 238, 420, 130, P050, None, 0.08)
txt(s, 66, 250, 386, 110,
    "库存双向对账：把 st_inventory 的结存与 st_stock_txn 的流水，按\n"
    "（物料类型 + 物料 + 批次 + 仓库）四元组分别求和比较。\n\n"
    "只查一个方向会漏掉「有流水却没有结存行」；双向比对才能发现\n"
    "幽灵出入库。这一项跑不过，说明业务动作有绕过流水的地方。", 9, TEXT, spacing=1.4)

txt(s, 496, 216, 300, 16, "三条已知不足（主动讲）", 10, P500, True, spacing=1.0)
for i, (t, d) in enumerate([
    ("没有工作流引擎", "审批只有通过/驳回，没有多级会签与条件分支"),
    ("报工没有复核", "报工直接生成计件，缺少班长或质检确认环节"),
    ("没有 CI", "32 个用例与 60 项自检是本地手工跑，未接入流水线"),
]):
    y = 238 + i * 44
    rrect(s, 496, y, 416, 38, P050, None, 0.12)
    txt(s, 510, y + 5, 150, 14, t, 9, P700, True, spacing=1.0)
    txt(s, 510, y + 21, 390, 14, d, 8, MUTED, spacing=1.0)

txt(s, 48, 384, 864, 40,
    "补充：前端 npm run build 含 vue-tsc 类型检查，类型不过不出包；后端 mvn test 通过；\n"
    "演示库的 4 个自检脚本可重复执行，每次改完数据都能跑一遍确认账还对得上。", 9, TEXT, spacing=1.35)
rrect(s, 48, 436, 864, 50, P050, None, 0.12)
shape(s, SH_RECT, 48, 436, 3, 50, P500)
txt(s, 66, 448, 830, 26, "结论：三层验证构成「改完还能对账」的最小保障；目前缺的是自动化流水线和报工复核环节。",
    10, P900, True, spacing=1.2)

# =============================================================== 10 结束
s = slide("收尾 20 秒：项目就到这，三条不足我们也清楚。老师想先看哪一块，我可以直接演示或翻代码。")
shape(s, SH_RECT, 0, 0, W, 8, P700)
shape(s, SH_RECT, 0, 8, W, 232, P050)
shape(s, SH_RECT, 0, 240, W, 3, P700)
txt(s, 60, 70, 700, 20, "THANK YOU", 12, P500, True, spacing=1.0)
txt(s, 60, 96, 760, 48, "谢谢，请老师提问", 34, P900, True, spacing=1.0)
txt(s, 60, 158, 760, 44, "现场可演示：报价转订单 · 分批发货 · 财务页两种角色对比 · 业务问答", 12, P700, spacing=1.3)
for i, (k, v) in enumerate([
    ("演示入口", "http://127.0.0.1:5173　账号 admin / sales / finance / boss　密码 Demo@2026!"),
    ("两条对比", "同一个财务页：finance 能登记收付款，boss 只能查看"),
    ("如果要看代码", "ErpOrderService（状态机）· ErpStockMapper（防超卖）· api/catalog.ts（配置驱动）"),
]):
    y = 272 + i * 60
    txt(s, 60, y, 90, 14, k, 9, P500, True, spacing=1.0)
    txt(s, 60, y + 18, 560, 20, v, 10, TEXT, spacing=1.0)
    shape(s, SH_RECT, 60, y + 44, 560, 0.8, LINE)

rrect(s, 648, 268, 264, 190, WHITE, P200, 0.08)
txt(s, 666, 280, 230, 16, "答辩材料在哪", 9.5, P700, True, spacing=1.0)
for i, f in enumerate(["docs/答辩手册/00-总讲稿", "docs/答辩手册/01-后端篇",
                       "docs/答辩手册/02-前端篇", "docs/答辩手册/03-问答速查"]):
    y = 306 + i * 28
    shape(s, SH_RECT, 666, y + 5, 4, 4, P500)
    txt(s, 678, y, 220, 16, f, 8.5, MUTED, spacing=1.0)
txt(s, 666, 420, 230, 28, "每份都带口播稿与代码位置", 8, MUTED, spacing=1.0)
txt(s, 60, 490, 800, 16, "海岚 ERP · 服装制造企业资源计划 · 2026.10", 8.5, MUTED, spacing=1.0)

# 保存：优先走技能后端的 save_as / export_pdf，失败再退回原生接口
try:
    save_as(pres, OUT_PPTX, "impress", "pptx")
except Exception as e:
    print("save_as 失败，退回原生 SaveAs:", e)
    pres.SaveAs(OUT_PPTX, 24)   # ppSaveAsOpenXMLPresentation

print("PPTX:", OUT_PPTX, os.path.getsize(OUT_PPTX), "bytes")

try:
    export_pdf(pres, OUT_PDF, "impress")
except Exception as e:
    print("export_pdf 失败，退回原生 SaveAs:", e)
    try:
        pres.SaveAs(OUT_PDF, 32)  # ppSaveAsPDF
    except Exception as e2:
        print("PDF skip:", e2)

if os.path.exists(OUT_PDF):
    print("PDF:", OUT_PDF, os.path.getsize(OUT_PDF), "bytes")

pres.Close()
try:
    app.Quit()
except Exception:
    pass
print("DONE slides=", _idx[0] - 1)
