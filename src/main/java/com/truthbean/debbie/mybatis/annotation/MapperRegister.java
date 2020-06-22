/**
 * Copyright (c) 2020 TruthBean(Rogar·Q)
 * Debbie is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *         http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */
package com.truthbean.debbie.mybatis.annotation;

import com.truthbean.debbie.bean.AnnotationRegister;
import com.truthbean.debbie.bean.BeanInitialization;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author truthbean
 * @since 0.0.2
 */
public class MapperRegister implements AnnotationRegister<Mapper> {
    private final BeanInitialization initialization;

    public MapperRegister(BeanInitialization beanInitialization) {
        this.initialization = beanInitialization;
    }

    @Override
    public void register() {
        register(Mapper.class);
    }

    @Override
    public BeanInitialization getBeanInitialization() {
        return initialization;
    }
}
