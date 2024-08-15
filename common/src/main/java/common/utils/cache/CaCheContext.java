package common.utils.cache;

import cn.hutool.core.lang.TypeReference;

/**
 * 缓存策略模式
 * 配置是否使用redis
 */
public class CaCheContext {

    private final CacheStrategy cacheStrategy;

    public CaCheContext(CacheStrategy cacheStrategy) {
        this.cacheStrategy = cacheStrategy;
    }

    public <T> void setCacheObject(final String key, final T value){
        cacheStrategy.setCacheObject(key,value);
    }

    public <T> void setCacheObject(final String key, final T value, final Long expiryDuration){
        cacheStrategy.setCacheObject(key,value,expiryDuration);
    }

    public <T> T getCacheObject(final String key, TypeReference<T> type){
        return cacheStrategy.getCacheObject(key,type);
    }

    public void del(String... keys){
        cacheStrategy.del(keys);
    }
}
