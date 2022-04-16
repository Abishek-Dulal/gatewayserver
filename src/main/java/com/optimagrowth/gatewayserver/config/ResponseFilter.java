package com.optimagrowth.gatewayserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

/**
 * @author abishek on 2022-04-10
 */
@Configuration
public class ResponseFilter {

    final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);


    @Bean
    public GlobalFilter postGlobalFilter(FilterUtils filterUtils){
        return ((exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(()->{
                HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                String correlationId = filterUtils.getCorrelationId(requestHeaders);
                logger.debug("Adding the correlation id  to the outbound header {}", correlationId);
                exchange.getResponse().getHeaders().add(FilterUtils.CORRELATION_ID,correlationId);
                logger.debug("Completing  outgoing  request {}", exchange.getRequest().getURI());
            }))
        );
    }

}
