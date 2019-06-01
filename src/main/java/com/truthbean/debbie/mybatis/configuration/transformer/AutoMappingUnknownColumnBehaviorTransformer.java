package com.truthbean.debbie.mybatis.configuration.transformer;

import com.truthbean.debbie.core.data.transformer.DataTransformer;
import org.apache.ibatis.session.AutoMappingUnknownColumnBehavior;

public class AutoMappingUnknownColumnBehaviorTransformer implements DataTransformer<AutoMappingUnknownColumnBehavior, String> {
    @Override
    public String transform(AutoMappingUnknownColumnBehavior autoMappingUnknownColumnBehavior) {
        return autoMappingUnknownColumnBehavior.name();
    }

    @Override
    public AutoMappingUnknownColumnBehavior reverse(String s) {
        return AutoMappingUnknownColumnBehavior.valueOf(s);
    }
}
