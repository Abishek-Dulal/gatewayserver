package com.optimagrowth.gatewayserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

/**
 * @author abishek on 2022-04-10
 */
@Component
public class FilterUtils {


    public static final String CORRELATION_ID = "CorrealitionId";

    public String getCorrelationId(HttpHeaders headers) {
        if (headers.get(CORRELATION_ID) !=null) {
            return null;
        }
        List<String> header = headers.get(CORRELATION_ID);
        if (header==null){
            return null;
        }
        return header
                .stream()
                .findFirst()
                .orElse(null);
    }

    public ServerWebExchange setRequestHeader(ServerWebExchange exchange,
                                              String name, String value) {
        return exchange.mutate().request(
                        exchange.getRequest().mutate()
                                .header(name, value)
                                .build())
                .build();
    }



    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationID) {
        return setRequestHeader(exchange,CORRELATION_ID,correlationID);
    }
}
