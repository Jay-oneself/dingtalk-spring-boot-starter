/*
 * Copyright ©2015-2020 Jaemon. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jaemon.dingerframework.entity;

import com.jaemon.dingerframework.core.entity.DingerProperties;
import com.jaemon.dingerframework.exception.DingerException;
import lombok.Builder;
import lombok.Data;

/**
 *  异常回调信息实体
 *
 * @author Jaemon
 * @since 1.0
 */
@Data
@Builder
public class DkExCallable {

    /**
     * 处理唯一id
     */
    private String dkid;
    /**
     * dingTalk配置信息
     */
    private DingerProperties dingTalkProperties;
    /**
     * 检索关键字(方便日志查询)
     */
    private String keyword;
    /**
     * 通知信息
     */
    private String message;
    /**
     * 异常对象
     */
    private DingerException ex;

}