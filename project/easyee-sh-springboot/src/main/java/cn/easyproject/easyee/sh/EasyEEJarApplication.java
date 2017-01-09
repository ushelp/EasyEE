package cn.easyproject.easyee.sh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Run with JAR
 * 
 * <pre>
 * 1. Use `pom_jar.xml` to `pom.xml`
 * 
 * 2. Uncomment the comment, Run `EasyEEJarApplication.java` 
 * </pre>
 * 
 * @author easyproject.cn
 *
 */

@SpringBootApplication(
		scanBasePackages = { "cn.easyproject.easyee.sh" }, 
		exclude = { SecurityAutoConfiguration.class })
@ImportResource({ 
	"classpath*:/spring/spring_shiro.xml"
})
@EnableTransactionManagement
public class EasyEEJarApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(EasyEEJarApplication.class);
		app.run();
	
	}

}
