package com.xsqwe.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fangjialiang
 * @desc
 * @date 2020/8/10 14:19
 */
public class AppUtils {

    private static final String ANDROID = "android";
    private static final String IOS = "ios";

    public static boolean isApp(String platform) {
        return ANDROID.equals(platform) || IOS.equals(platform);
    }

    public static boolean isAndroid(String platform) {
        return ANDROID.equals(platform);
    }

    public static boolean isIos(String platform) {
        return IOS.equals(platform);
    }

    public static String getPlatform(String deviceInfo) {
        // X-Fast-DeviceInfo （设备类型@机型@系统版本号@厂商，IOS@Iphone6@12.0.1，Android@EVA-AL10@Android8.0.0@HUAWEI）
        if(StringUtils.isEmpty(deviceInfo)) {
            return "";
        }
        String[] deviceInfos = deviceInfo.split("@");
        if("ios".equalsIgnoreCase(deviceInfos[0])) {
            return "ios";
        } else if("android".equalsIgnoreCase(deviceInfos[0])) {
            return "android";
        }
        return "";
    }

    public static String getPlatform(HttpServletRequest request) {
        return getPlatform(request.getHeader("X-Fast-DeviceInfo"));
    }
}
