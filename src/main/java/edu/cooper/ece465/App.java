package edu.cooper.ece465;

import edu.cooper.ece465.utils.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import lombok.AccessLevel;
import lombok.Getter;

import java.io.IOException;

@SpringBootApplication(scanBasePackages={"edu.cooper.ece465"})
@EnableConfigurationProperties()
public class App {
    static String PID_FILE_NAME = "app.pid";

    @Getter(AccessLevel.PUBLIC)
    static final long pid = Utils.fetchPid();

    static {
        try {
            Utils.writePidToLocalFile(PID_FILE_NAME + "." + pid+".pid", pid);
        } catch (IOException ex) {
            System.err.println("Cannot write pid file for this pid = " + pid);
        }
    }

    // used to see which Beans are running within the application on the node
//	@Bean
//	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//		return args -> {
//
//			System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//			String[] beanNames = ctx.getBeanDefinitionNames();
//			Arrays.sort(beanNames);
//			for (String beanName : beanNames) {
//				System.out.println(beanName);
//			}
//
//		};
//	}

    public static void main(String[] args) {
        System.out.println("hostname: " + Utils.getMyHostname());
//		SpringApplication.run(DemoApplication.class, args);
        SpringApplication application = new SpringApplication(App.class);
//		application.addListeners(new SpringAppEventsListener());
        application.run(args);
    }


}