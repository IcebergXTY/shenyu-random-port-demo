package com.iceberg.rh.rhf.yqs.saas.shenyu.config;

import com.iceberg.rh.rhf.yqs.saas.shenyu.websocket.HttpAuthHandler;
import com.iceberg.rh.rhf.yqs.saas.shenyu.websocket.WebSocketInterceptor;
import org.apache.shenyu.client.spring.websocket.annotation.ShenyuSpringWebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@ShenyuSpringWebSocketClient("/myWebSocket")
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Autowired
    private HttpAuthHandler httpAuthHandler;

    @Autowired
    private WebSocketInterceptor myInterceptor;

    @Override
    public void registerWebSocketHandlers(final WebSocketHandlerRegistry registry) {
        registry.addHandler(httpAuthHandler, "myWebSocket")
                .addInterceptors(myInterceptor)
                .setAllowedOrigins("*");
    }
}
