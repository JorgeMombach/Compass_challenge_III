package jorge.mombach.mshistory.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue raceResultQueue() {
        return new Queue("race-result-queue");
    }
}