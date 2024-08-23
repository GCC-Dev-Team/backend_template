package miniapp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScan(value = {"common","miniapp"})//"common.*"
public class MiniAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(MiniAppApplication.class, args);
    }
}