package com.swa.properSpring;

import com.swa.properSpring.user.User;
import com.swa.properSpring.user.UserRespository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserRespository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new User("Bilbo Baggins", "burglar","frodo@middle-earth.fm","Hobbits.inc","Frodo","Baggins","13376969555","")));
        };
    }
}
