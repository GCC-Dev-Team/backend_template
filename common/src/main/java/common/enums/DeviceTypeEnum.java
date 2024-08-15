package common.enums;


import common.enums.interfaces.EnumMap;

import java.util.Set;

/**
 * 登录设备
 *
 * @author I Nhrl
 */
public enum DeviceTypeEnum {
    //ME(mobile equipment)
    /**
     * 移动-安卓
     */
    ME_ANDROID("meAndroid"),
    /**
     * 移动-ios
     */
    ME_IOS("meIos"),
    /**
     * 移动-浏览器
     */
    ME_BROWSER("meBrowser"),
    /**
     * 浏览器
     */
    BROWSER("browser"),
    /**
     * 客户端(Unknown,目前Unknown浏览器视为客户端)
     */
    CLIENT("client"),
    /**
     * 未知(不在定义中的设备类型,[错误])
     */
    UNKNOWN("**");

    private final String key;

    @Override
    public String toString() {
        return key;
    }

    DeviceTypeEnum(String key) {
        this.key = key;
    }

    static final EnumMap<DeviceTypeEnum, String> ENUM_MAP = new EnumMap<>(DeviceTypeEnum.class,
            enumType -> enumType.key);

    public static DeviceTypeEnum find(String key) {
        return ENUM_MAP.find(key, UNKNOWN);
    }

    public static Set<String> getDeviceTypes() {
        return ENUM_MAP.getEnums();
    }
}
