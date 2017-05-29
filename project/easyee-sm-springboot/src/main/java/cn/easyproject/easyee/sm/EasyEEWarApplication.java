package cn.easyproject.easyee.sm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * Run with WAR
 * 
 * <pre>
 * 1. Use `pom_war.xml` to `pom.xml`
 * 
 * 2. Uncomment the comment, Run `EasyEEWarApplication.java` 
 * </pre>
 * 
 * @author easyproject.cn
 *
 */
@SpringBootApplication(
		scanBasePackages = { "cn.easyproject.easyee.sm" }, 
		exclude = { SecurityAutoConfiguration.class })
@ImportResource({ 
	"classpath*:/spring/spring_shiro.xml"
})
@EnableTransactionManagement
@MapperScan(basePackages={
		"cn.easyproject.easyee.sm.sys.dao",
		"cn.easyproject.easyee.sm.hr.dao"
})
@ServletComponentScan(basePackages={"cn.easyproject.easyee.sm"})
public class EasyEEWarApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EasyEEWarApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(EasyEEWarApplication.class, args);
	}
}
