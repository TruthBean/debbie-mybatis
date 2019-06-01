package com.truthbean.debbie.mybatis.configuration.transformer;

import com.truthbean.debbie.core.data.transformer.DataTransformer;
import org.apache.ibatis.session.ExecutorType;

public class ExecutorTypeTransformer implements DataTransformer<ExecutorType, String> {
    @Override
    public String transform(ExecutorType executorType) {
        return executorType.name();
    }

    @Override
    public ExecutorType reverse(String s) {
        return ExecutorType.valueOf(s);
    }
}
