package com.truthbean.debbie.mybatis.configuration.transformer;

import com.truthbean.debbie.core.data.transformer.DataTransformer;
import org.apache.ibatis.session.LocalCacheScope;

public class LocalCacheScopeTransformer implements DataTransformer<LocalCacheScope, String> {
    @Override
    public String transform(LocalCacheScope localCacheScope) {
        return localCacheScope.name();
    }

    @Override
    public LocalCacheScope reverse(String s) {
        return LocalCacheScope.valueOf(s);
    }
}
