package com.truthbean.debbie.mybatis.configuration.transformer;

import com.truthbean.debbie.data.transformer.DataTransformer;
import org.apache.ibatis.session.AutoMappingBehavior;

/**
 * @author truthbean
 * @since 0.0.2
 */
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
