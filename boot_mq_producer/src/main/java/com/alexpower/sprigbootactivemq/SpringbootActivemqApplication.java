package com.alexpower.sprigbootactivemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/*@SpringBootApplication
 disabled here since Intellij Compile Error: no bean Queue_produce found in test code,
 use three annotation below to fix
* */
@Configuration
@EnableAutoConfiguration
@ComponentScan(value = "com.alexpower")
// add scheduling for scheduled message sending
@EnableScheduling
public class SpringbootActivemqApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootActivemqApplication.class, args);
    }

}
