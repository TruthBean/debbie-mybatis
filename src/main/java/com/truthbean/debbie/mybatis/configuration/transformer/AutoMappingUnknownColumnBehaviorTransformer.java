package com.truthbean.debbie.mybatis.configuration.transformer;

import com.truthbean.debbie.core.data.transformer.DataTransformer;
import org.apache.ibatis.session.AutoMappingUnknownColumnBehavior;

/**
 * @author truthbean
 * @since 0.0.2
 */
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
