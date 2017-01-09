package cn.easyproject.easyee.sh;

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
//@SpringBootApplication(
//		scanBasePackages = { "cn.easyproject.easyee.sh" }, 
//		exclude = { SecurityAutoConfiguration.class })
//@ImportResource({ 
//	"classpath*:/spring/spring_shiro.xml"
//})
//@EnableTransactionManagement
//@ServletComponentScan(basePackages={"cn.easyproject.easyee.sh"})
public class EasyEEWarApplication extends SpringBootServletInitializer {

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(EasyEEWarApplication.class);
//    }
//	
//	public static void main(String[] args) {
//		SpringApplication.run(EasyEEWarApplication.class, args);
//	}
}
