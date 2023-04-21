package com.iceberg.rh.rhf.yqs.saas.shenyu.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class HttpAuthHandler extends TextWebSocketHandler {

    private static final Logger LOG = LoggerFactory.getLogger(HttpAuthHandler.class);

    /**
     * socket create a success event.
     *
     * @param session session
     * @throws Exception exception
     */
    @Override
    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
        Object token = session.getAttributes().get("token");
        if (token != null) {
            // The user is successfully connected and put into the online user cache.
            WsSessionManager.add(token.toString(), session);
        } else {
            throw new RuntimeException("User login has expired!");
        }
    }

    /**
     * Receive message events.
     *
     * @param session session
     * @param message message
     * @throws Exception exception
     */
    @Override
    protected void handleTextMessage(final WebSocketSession session, final TextMessage message) throws Exception {
        // Get the message from the client.
        String payload = message.getPayload();
        Object token = session.getAttributes().get("token");
        LOG.info("server received " + token + " sent " + payload);
        session.sendMessage(new TextMessage("apache shenyu server send to " + token + " message : -> " + payload));
    }

    /**
     * when socket disconnected.
     *
     * @param session  session
     * @param status  close status
     * @throws Exception exception
     */
    @Override
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) throws Exception {
        Object token = session.getAttributes().get("token");
        if (token != null) {
            // The user exits and removes the cache.
            WsSessionManager.remove(token.toString());
        }
    }

}
