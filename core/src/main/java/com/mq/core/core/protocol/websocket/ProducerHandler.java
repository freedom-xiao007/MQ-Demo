/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mq.core.core.protocol.websocket;

import com.google.gson.Gson;
import com.mq.core.core.common.Constants;
import com.mq.core.core.messagequeue.Broker;
import lombok.SneakyThrows;
import org.springframework.web.socket.*;

import java.util.Map;

/**
 * @author lw1243925457
 */
public class ProducerHandler implements WebSocketHandler {

    private final Broker broker;
    private Gson gson = new Gson();

    ProducerHandler(Broker broker) {
        this.broker = broker;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) {
    }

    @SneakyThrows
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) {
        String topic = Constants.TOPIC;
        String message = Constants.MESSAGE;

        Map map = gson.fromJson(webSocketMessage.getPayload().toString(), Map.class);
        if (broker.send(map.get(topic).toString(), map.get(message).toString())) {
            webSocketSession.sendMessage(new TextMessage(map.get(Constants.SEND_ID).toString()));
        } else {
            webSocketSession.sendMessage(new TextMessage("null"));
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) {
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) {
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
