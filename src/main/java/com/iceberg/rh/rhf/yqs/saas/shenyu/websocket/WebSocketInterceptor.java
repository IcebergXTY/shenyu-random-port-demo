package com.iceberg.rh.rhf.yqs.saas.shenyu.websocket;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class WebSocketInterceptor implements HandshakeInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketInterceptor.class);

    /**
     * Before handshake.
     * @param request request
     * @param response response
     * @param wsHandler websocketHandler
     * @param attributes  attributes
     * @return enable handshake
     * @throws Exception exception
     */
    @Override
    public boolean beforeHandshake(final ServerHttpRequest request,
                                   final ServerHttpResponse response,
                                   final WebSocketHandler wsHandler,
                                   final Map<String, Object> attributes) throws Exception {
        LOG.info("Shake hands.");
        Map<String, String> paramMap = HttpUtil.decodeParamMap(request.getURI().getQuery(), StandardCharsets.UTF_8);
        String uid = paramMap.get("token");
        if (StrUtil.isNotBlank(uid)) {
            attributes.put("token", uid);
            LOG.info("user token " + uid + " shook hands successfully！");
            return true;
        }
        LOG.info("user login has expired");
        return false;
    }

    /**
     * After shaking hands.
     * @param request  request
     * @param response  response
     * @param wsHandler  websocketHandler
     * @param exception  exception
     */
    @Override
    public void afterHandshake(final ServerHttpRequest request,
                               final ServerHttpResponse response,
                               final WebSocketHandler wsHandler,
                               final Exception exception) {
        LOG.info("Handshake complete");
    }

}
