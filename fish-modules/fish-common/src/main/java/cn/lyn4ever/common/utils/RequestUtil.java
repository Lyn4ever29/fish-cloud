package cn.lyn4ever.common.utils;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Administrator
 * @date 2023/10/1
 */
@Slf4j
public class RequestUtil {
    private static final String UNKNOWN = "unknown";

    /**
     * 获取ip地址
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String comma = ",";
        String localhost = "127.0.0.1";
        if (ip.contains(comma)) {
            ip = ip.split(",")[0];
        }
        if (localhost.equals(ip)) {
            // 获取本机真正的ip地址
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                log.error(e.getMessage(), e);
            }
        }
        return ip;
    }

    public static String getBrowser(HttpServletRequest request) {
        UserAgent ua = UserAgentUtil.parse(request.getHeader("User-Agent"));
        String browser = ua.getBrowser().toString() + " " + ua.getVersion();
        return browser.replace(".0.0.0", "");
    }
}
