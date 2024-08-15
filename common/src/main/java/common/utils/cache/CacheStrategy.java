package common.utils.cache;

import cn.hutool.core.lang.TypeReference;


public interface CacheStrategy {

    /**
     * 缓存添加
     * @param key 键
     * @param value 值
     * @param <T> 添加的类型
     */
    <T> void setCacheObject(final String key, final T value);

    /**
     * 添加或更新缓存
     *
     * @param key            键
     * @param value          值
     * @param expiryDuration 到期时间 单位(秒)
     */
    <T> void setCacheObject(final String key, final T value, final Long expiryDuration);

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    <T> T getCacheObject(final String key, TypeReference<T> type);

    void del(String... keys);


}
