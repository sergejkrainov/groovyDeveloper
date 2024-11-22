package todolist

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StartApplication {

    public static void main(String... args) {

        System.setProperty("server.port", "8090")
        SpringApplication.run(StartApplication.class, args);
    }
}
