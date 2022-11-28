package com.github.newcodercircle.saassample.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

/**
 * ip相关工具
 *
 * @author xiaobao
 * @since 2022-11-28
 */
public class IpUtils {
    private static final Pattern IPV4_PATTERN = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
    private static final int IPV6_ADDRESS_LENGTH = 16;


    /**
     * 判断地址是否是IP
     *
     * @param addr
     * @return
     */
    public static boolean isIP(String addr) {
        return isIPv4(addr) || isIPv6(addr);
    }

    public static boolean isIPv4(String addr) {
        return IPV4_PATTERN.matcher(addr).matches();
    }

    public static boolean isIPv6(String addr) {
        try {
            return InetAddress.getByName(addr).getAddress().length == IPV6_ADDRESS_LENGTH;
        } catch (UnknownHostException e) {
            return false;
        }
    }
}
