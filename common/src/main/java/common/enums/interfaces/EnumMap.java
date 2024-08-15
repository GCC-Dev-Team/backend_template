package common.enums.interfaces;

import cn.hutool.core.map.MapUtil;
import common.exception.CommonException;


import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * 通过枚举值查找枚举
 * @author I Nhrl
 */
public class EnumMap<T extends Enum<T>,K> {
    protected Map<K, T> map = MapUtil.newHashMap();

    public EnumMap(Class<T> clazz,EnumKey<T,K> enumKey){
        try {
            for (T value : EnumSet.allOf(clazz)) {
                map.put(enumKey.getKey(value),value);
            }
        } catch (Exception e) {
            throw new CommonException(500, e.getMessage());
        }
    }

    /**
     * 查找对应枚举
     * @param key 枚举值
     * @param defaultEnum 未找到时的默认枚举
     * @return 枚举
     */
    public T find(K key, T defaultEnum){
        T value = map.get(key);
        if (value==null) {
            value=defaultEnum;
        }
        return value;
    }

    public Set<K> getEnums() {
        return map.keySet();
    }
}
