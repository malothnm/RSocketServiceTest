package in.nmaloth.rsocketTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(
        basePackages = {"in.nmaloth.rsocketServices"},
        basePackageClasses = {RSocketServiceTest.class}
)
public class RSocketServiceTest {

    public static void main(String[] args) {
        SpringApplication.run(RSocketServiceTest.class,args);
    }
}
