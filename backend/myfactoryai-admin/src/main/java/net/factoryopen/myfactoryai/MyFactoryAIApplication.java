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


            System.out.println(YELLOW + BOLD + "═════════════════════════════════════════════════════════════════════════════════════════════════════════" + RESET);
            System.out.println();
        };
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}