/*
 * Copyright ©2015-2021 Jaemon. All Rights Reserved.
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
package com.github.jaemon.dinger.core.spring;

import com.github.jaemon.dinger.core.DingerRobot;
import com.github.jaemon.dinger.core.DingerSession;
import com.github.jaemon.dinger.core.entity.DingerProperties;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * DingerFactoryBean
 *
 * @author Jaemon
 * @since 1.0
 */
public class DingerFactoryBean<T> implements FactoryBean<T> {
    private Class<T> dingerInterface;

    @Autowired
    private DingerRobot dingerRobot;
    @Autowired
    private DingerProperties dingerProperties;

    public DingerFactoryBean(Class dingerInterface) {
        this.dingerInterface = dingerInterface;
    }

    @Override
    public T getObject() throws Exception {
        return new DingerSession(dingerRobot, dingerProperties).getDinger(this.dingerInterface);
    }

    @Override
    public Class<?> getObjectType() {
        return dingerInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}