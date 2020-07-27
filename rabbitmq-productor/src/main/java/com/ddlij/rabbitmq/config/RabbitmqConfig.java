package com.ddlij.rabbitmq.config;

import org.springframework.context.annotation.Configuration;

/**
 * @author fyzheng
 * @date 2020/7/12/012 19:13
 */
@Configuration
public class RabbitmqConfig {
    /*@Autowired
    private RabbitmqProperties rabbitmqProperties;

    @Bean
    public ConnectionFactory factory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(rabbitmqProperties.getHost());
        factory.setPort(rabbitmqProperties.getPort());
        factory.setUsername(rabbitmqProperties.getUsername());
        factory.setPassword(rabbitmqProperties.getPassword());

        return factory;
    }

    @Bean
    public RabbitTemplate getRabbitTrmplate(ConnectionFactory factory){
        RabbitTemplate template = new RabbitTemplate(factory);
        return template;
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory factory){
        SimpleMessageListenerContainer container =  new SimpleMessageListenerContainer(factory);
        container.setConsumerTagStrategy(new ConsumerTagStrategy() {
            @Override
            public String createConsumerTag(String s) {
                return null;
            }
        });

        return container;
    }*/
}
