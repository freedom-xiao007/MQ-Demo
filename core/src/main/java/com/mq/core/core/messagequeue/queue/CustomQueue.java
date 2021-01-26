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

package com.mq.core.core.messagequeue.queue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lw1243925457
 */
public class CustomQueue {

    /**
     * 各个分组的读取位置记录
     *
     * group --> index
     */
    private Map<String, AtomicInteger> offset = new HashMap<>();

    /**
     * 写位置记录
     */
    private int writeIndex = 0;

    /**
     * 存储队列
     */
    private List<String> queue = new ArrayList<>();

    private final String defaultGroup = "default";

    private final String writeLock = "WRITE_LOCK";

    private final String readLock = "READ_LOCK";

    public boolean put(String message) {
        // TODO
        // 为啥函数锁没有用？
        synchronized (writeLock) {
            queue.add(message);
            writeIndex += 1;
            return true;
        }
    }

    public String get(String group) {
        synchronized (readLock) {
            return queue.get(offset.computeIfAbsent(group, k -> new AtomicInteger(-1)).incrementAndGet());
        }
    }

    synchronized public String get() {
        synchronized (readLock) {
            int index = offset.getOrDefault(defaultGroup, new AtomicInteger(-1)).incrementAndGet();
            if (writeIndex == 0 || index >= queue.size()) {
                return null;
            }
            return queue.get(index);
        }
    }

    public boolean isEmpty() {
        return writeIndex == 0 || writeIndex >= queue.size();
    }

    public int size() {
        return writeIndex;
    }
}
