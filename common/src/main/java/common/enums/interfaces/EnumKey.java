package common.enums.interfaces;

/**
 * @author I Nhrl
 */
public interface EnumKey<T extends Enum<T>,K> {
    /**
     * 获取枚举值
     * @param enumType 枚举
     * @return 枚举值
     */
    K getKey(T enumType);
}
