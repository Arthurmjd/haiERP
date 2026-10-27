package net.factoryopen.myfactoryai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 启动程序
 *
 * @author factoryOpen
 */
@EnableAsync
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class},
        scanBasePackages = {"net.factoryopen.myfactoryai","com.ruoyi","org.jeecg.modules.jmreport"})
@MapperScan({"com.ruoyi.**.mapper","net.factoryopen.myfactoryai.**.mapper"})
public class MyFactoryAIApplication {
    private static final Logger log = LoggerFactory.getLogger(MyFactoryAIApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MyFactoryAIApplication.class, args);
    }

    /**
     * 启动后打印酷炫欢迎词（控制台或日志）
     */
    @Bean
    public CommandLineRunner printWelcome() {
        return args -> {
            // ANSI 颜色代码
            String RESET = "\u001B[0m";
            String GREEN = "\u001B[32m";
            String CYAN = "\u001B[36m";
            String YELLOW = "\u001B[33m";
            String RED = "\u001B[31m";
            String BOLD = "\u001B[1m";

            //输出到日志
            String logContent = """
        
           ███╗   ███╗██╗   ██╗    ███████╗ █████╗  ██████╗████████╗ ██████╗ ██████╗ ██╗   ██╗    █████╗ ██╗
           ████╗ ████║╚██╗ ██╔╝    ██╔════╝██╔══██╗██╔════╝╚══██╔══╝██╔═══██╗██╔══██╗╚██╗ ██╔╝   ██╔══██╗██║
           ██╔████╔██║ ╚████╔╝     █████╗  ███████║██║        ██║   ██║   ██║██████╔╝ ╚████╔╝    ███████║██║
           ██║╚██╔╝██║  ╚██╔╝      ██╔══╝  ██╔══██║██║        ██║   ██║   ██║██╔══██╗  ╚██╔╝     ██╔══██║██║
           ██║ ╚═╝ ██║   ██║       ██║     ██║  ██║╚██████╗   ██║   ╚██████╔╝██║  ██║   ██║      ██║  ██║██║
           ╚═╝     ╚═╝   ╚═╝       ╚═╝     ╚═╝  ╚═╝ ╚═════╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝   ╚═╝      ╚═╝  ╚═╝╚═╝
        
                                                        海岚 ERP ｜ 服装制造 · 订单到成本 ⚡
        
        ═════════════════════════════════════════════════════════════════════════════════════════════════════════
          🚀 海岚 ERP 系统已成功启动
          📘 接入文档: docs/AI问答接入DeepSeek.md
          🌐 API: http://localhost:8080/swagger-ui/index.html  |  Druid: http://localhost:8080/druid/index.html
          🌐 微信: factoryopen
        
          ✨ 核心能力已加载：
             • 💬 智能对话（DeepSeek / 通义千问）
             • 📊 自然语言图表（NL2SQL + ECharts）
             • 📈 11维数据洞察报告 + 智能总结
             • 🛡️ SQL安全校验 + 权限拦截
             • 🧠 元数据增强（字典映射 + 字段中文化）
             • 📄 多模态PPT生成（Apache POI）
             • 🎤 持续语音输入 + 实时语音应答
             • 🏭 业务一体化：PLM + ERP(MRPII) + MES + IIoT适配 + 车间数字孪生
        
          💡 提示：语音识别需 HTTPS / localhost 环境，若遇 network 错误请检查网络或使用安全域名。
          🎉 欢迎使用 factoryOpen.aifactory | 让工厂插上 AI 的翅膀！
        ═════════════════════════════════════════════════════════════════════════════════════════════════════════
        """;
            log.info(logContent);

            String asciiArt =
                    "   ███╗   ███╗██╗   ██╗    ███████╗ █████╗  ██████╗████████╗ ██████╗ ██████╗ ██╗   ██╗    █████╗ ██╗\n" +
                            "   ████╗ ████║╚██╗ ██╔╝    ██╔════╝██╔══██╗██╔════╝╚══██╔══╝██╔═══██╗██╔══██╗╚██╗ ██╔╝   ██╔══██╗██║\n" +
                            "   ██╔████╔██║ ╚████╔╝     █████╗  ███████║██║        ██║   ██║   ██║██████╔╝ ╚████╔╝    ███████║██║\n" +
                            "   ██║╚██╔╝██║  ╚██╔╝      ██╔══╝  ██╔══██║██║        ██║   ██║   ██║██╔══██╗  ╚██╔╝     ██╔══██║██║\n" +
                            "   ██║ ╚═╝ ██║   ██║       ██║     ██║  ██║╚██████╗   ██║   ╚██████╔╝██║  ██║   ██║      ██║  ██║██║\n" +
                            "   ╚═╝     ╚═╝   ╚═╝       ╚═╝     ╚═╝  ╚═╝ ╚═════╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝   ╚═╝      ╚═╝  ╚═╝╚═╝\n";

            String slogan = "                                                        海岚 ERP ｜ 服装制造 · 订单到成本 ⚡\n";

            //输出到控制台
            System.out.println();
            System.out.println(CYAN + BOLD + asciiArt + RESET);
            System.out.println(CYAN + BOLD + slogan + RESET);
            System.out.println(YELLOW + BOLD + "═════════════════════════════════════════════════════════════════════════════════════════════════════════" + RESET);
            System.out.println(GREEN + BOLD + "  🚀 海岚 ERP 系统已成功启动" + RESET);
            System.out.println(CYAN + "  🌐 文档: " + BOLD + "docs/AI问答接入DeepSeek.md" + RESET + CYAN + "  |  API: http://localhost:8080/swagger-ui/index.html" + RESET);
            System.out.println(CYAN + "  🌐 文档: " + BOLD + "http://localhost:8080/swagger-ui/index.html" + RESET + CYAN + "  |  Druid: http://localhost:8080/druid/index.html" + RESET);
            System.out.println(CYAN + "  🌐 微信: " + BOLD + "factoryopen" + RESET);
            System.out.println();
            System.out.println(YELLOW + "  ✨ 核心能力已加载：" + RESET);
            System.out.println("     • 💬 智能对话（DeepSeek / 通义千问）");
            System.out.println("     • 📊 自然语言图表（NL2SQL + ECharts）");
            System.out.println("     • 📈 11维数据洞察报告 + 智能总结");
            System.out.println("     • 🛡️ SQL安全校验 + 权限拦截");
            System.out.println("     • 🧠 元数据增强（字典映射 + 字段中文化）");
            System.out.println("     • 📄 多模态PPT生成（Apache POI）");
            System.out.println("     • 🎤 持续语音输入 + 实时语音应答");
            //System.out.println("     • 🧩 四层Skill体系（57种AI技能）");
            System.out.println("     • 🏭 业务一体化：PLM + ERP(MRPII) + MES + IIoT适配 + 车间数字孪生");
            System.out.println();
            System.out.println(RED + "  💡 提示：语音识别需 HTTPS / localhost 环境，若遇 network 错误请检查网络或使用安全域名。" + RESET);
            System.out.println(GREEN + "  🎉 欢迎使用 factoryOpen.aifactory | 让工厂插上 AI 的翅膀！" + RESET);
            System.out.println(YELLOW + BOLD + "═════════════════════════════════════════════════════════════════════════════════════════════════════════" + RESET);
            System.out.println();
        };
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}