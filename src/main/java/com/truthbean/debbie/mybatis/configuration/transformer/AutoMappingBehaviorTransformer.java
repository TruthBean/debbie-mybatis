package com.truthbean.debbie.mybatis.configuration.transformer;

import com.truthbean.debbie.core.data.transformer.DataTransformer;
import org.apache.ibatis.session.AutoMappingBehavior;

public class AutoMappingBehaviorTransformer implements DataTransformer<AutoMappingBehavior, String> {
    @Override
    public String transform(AutoMappingBehavior autoMappingBehavior) {
        return autoMappingBehavior.name();
    }

    @Override
    public AutoMappingBehavior reverse(String s) {
        return AutoMappingBehavior.valueOf(s);
    }
}
