package com.truthbean.debbie.mybatis.configuration.transformer;

import com.truthbean.debbie.core.data.transformer.DataTransformer;
import org.apache.ibatis.type.JdbcType;

/**
 * @author truthbean
 * @since 0.0.2
 */
public class JdbcTypeTransformer implements DataTransformer<JdbcType, String> {
    @Override
    public String transform(JdbcType jdbcType) {
        return jdbcType.name();
    }

    @Override
    public JdbcType reverse(String s) {
        return JdbcType.valueOf(s);
    }
}
