package sn.esmt.eapplication.productmicroservice.config;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Configuration
@Slf4j
public class BaseConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Scheduler jdbcScheduler() {
        log.info("Creates a jdbcScheduler with thread pool size = {}", 10);
        return Schedulers.newBoundedElastic(10, 100, "jdbc-pool");
    }

}
