package com.iceberg.rh.rhf.yqs.saas.shenyu.websocket;

import org.apache.shenyu.client.springmvc.annotation.ShenyuSpringMvcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Controller
@RequestMapping("/ws")
@ShenyuSpringMvcClient("/ws/**")
public class TestHttpController {

    private static final Logger LOG = LoggerFactory.getLogger(TestHttpController.class);

    /**
     * Send message.
     * @param token session key
     * @param msg text-type message
     * @return response
     */
    @RequestMapping("/sendMsg")
    @ResponseBody
    public String sendMsg(final String token, final String msg) {
        WebSocketSession webSocketSession = WsSessionManager.get(token);
        if (webSocketSession == null) {
            return "User login has expired";
        }
        try {
            webSocketSession.sendMessage(new TextMessage(msg));
        } catch (IOException e) {
            LOG.error("throw exception when sending message.", e);
        }
        return "Message sent successfully";
    }

    /**
     * Upload files.
     * @param token session key
     * @param file file
     * @return response
     */
    @PostMapping(value = "/upload")
    @ResponseBody
    public String file(final String token, @RequestParam("file") final MultipartFile file) {
        try {
            WebSocketSession webSocketSession = WsSessionManager.get(token);
            if (webSocketSession == null) {
                return "User login has expired";
            }
            if (file.getOriginalFilename().endsWith(".bin")) {
                webSocketSession.sendMessage(new BinaryMessage(file.getBytes()));
            }
        } catch (Exception e) {
            // ignore exception
        }
        return "ok";
    }
}
