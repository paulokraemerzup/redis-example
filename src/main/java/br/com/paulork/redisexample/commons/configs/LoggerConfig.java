package br.com.paulork.redisexample.commons.configs;

import br.com.paulork.redisexample.utils.LoggerUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class LoggerConfig {

    @Bean
    @Scope("prototype")
    public LoggerUtils produceLogger(InjectionPoint injectionPoint) {
        Class<?> clazz = injectionPoint.getMember().getDeclaringClass();
        return new LoggerUtils(LogFactory.getLog(clazz));
    }

}