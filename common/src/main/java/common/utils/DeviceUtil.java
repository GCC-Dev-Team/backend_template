package common.utils;

import cn.hutool.http.useragent.Browser;
import cn.hutool.http.useragent.Platform;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import common.enums.DeviceTypeEnum;


/**
 * 设备获取
 * @author I Nhrl
 */
public class DeviceUtil {

    public static String getDevice(String userAgent){
        try {
            UserAgent parse = UserAgentUtil.parse(userAgent);
            Platform platform = parse.getPlatform();
            Browser browser = parse.getBrowser();
            if (platform.isMobile()) {
                if (browser.isMobile()) {
                    return DeviceTypeEnum.ME_BROWSER.toString();
                }
                if (platform.isAndroid()){
                    return DeviceTypeEnum.ME_ANDROID.toString();
                }else if (platform.isIos()){
                    return DeviceTypeEnum.ME_IOS.toString();
                }
            }
            return browser.isUnknown()?DeviceTypeEnum.CLIENT.toString():
                    DeviceTypeEnum.BROWSER.toString();
        } catch (Exception e) {
            return null;
        }
    }

}
