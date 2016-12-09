package com.chinadrtv.uam.utils;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * Utility for IP address
 * 
 * @author Qianyong,Deng
 * @since Sep 25, 2012
 *
 */
public final class IpUtils {
	
	private static final String DOT = ".";

	/**
	 * 比较ip是否在ip段中
	 * 
	 * @param ip
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean isIpIn(String ip, String start, String end) {
		if (!RegexValidateUtils.isIp(ip) || !RegexValidateUtils.isIp(start)
				|| !RegexValidateUtils.isIp(end)) {
			return false;
		}
		return isIpIn(ip, parseIp(start), parseIp(end));
	}
	
	/**
	 * 比较ip是否在ip段中
	 * 
	 * @param ip
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean isIpIn(String ip, long start, long end) {
		if (!RegexValidateUtils.isIp(ip))
			return false;
		return parseIp(ip) >= start && parseIp(ip) <= end;
	}
	
	/**
	 * 将ip转换成数字 <br>
	 * example: <br>
	 * ip = 192.168.0.128 <br>
	 * value = 192 * <code>256<sup>3</sup></code> + 168 *
	 * <code>256<sup>2</sup></code> + 0 * <code>256<sup>1</sup></code> + 128
	 * 
	 * @param ip
	 * @return 如果参数不是正常ip则返回0
	 */
	public static long parseIp(String ip) {
		long value = 0L;
		if (!RegexValidateUtils.isIp(ip))
			return value;

		String[] ips = StringUtils.split(ip, DOT);
		for (int pos = 0; pos < ips.length; pos++) {
			value += (long) Math.pow(256L, (ips.length - 1 - pos))
					* Long.parseLong(ips[pos]);
		}
		return value;
	}
	
	/**
	 * 获取本机所有IP
	 * 
	 * @return
	 * @throws SocketException
	 */
	public static List<String> getServerIps() {
		List<String> ips = new ArrayList<String>();
		// 根据网卡取本机配置的IP
		try {
			Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
			while (nis.hasMoreElements()) {
				NetworkInterface ni = nis.nextElement();
				Enumeration<InetAddress> ias = ni.getInetAddresses();
				while (ias.hasMoreElements()) {
					InetAddress ia = (InetAddress) ias.nextElement();
					if (ia instanceof Inet6Address) continue;
					ips.add(ia.getHostAddress());
				}
			}
		} catch (SocketException e) {
		}
		return ips;
	}

	/**
	 * 获取本机除127.0.0.1外的所有IP
	 * 
	 * @return
	 * @throws SocketException
	 */
	public static List<String> getServerIpsExceptLocal() {
		List<String> allIps = getServerIps();
		List<String> ips = new ArrayList<String>();
		for (String ip : allIps) {
			if (!StringUtils.equalsIgnoreCase(ip, "127.0.0.1")) {
				ips.add(ip);
			}
		}
		return ips;
	}

	/**
	 * 是否是本机IP
	 * 
	 * @param compareIp
	 * @return
	 * @throws SocketException
	 */
	public static boolean isLocal(String compareIp) {
		List<String> ips = getServerIps();
		for (String ip : ips) {
			if (StringUtils.equalsIgnoreCase(ip, compareIp)) return true;
		}
		return false;
	}

	/**
	 * 得到请求真实IP地址。<br>
	 * 若存在反向代理header: http_x_forwarded_for，则取其值.<br>
	 * 否则，取header: remote_addr<br>
	 * 如果长度超过50个字符，只取50个字符。
	 * @return
	 */
	public static String getRequestIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (StringUtils.isEmpty(ip)
				|| StringUtils.equalsIgnoreCase(ip, "unknown")) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip)
				|| StringUtils.equalsIgnoreCase(ip, "unknown")) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip)
				|| StringUtils.equalsIgnoreCase(ip, "unknown")) {
			ip = request.getRemoteAddr();
		}
		if (StringUtils.isNotEmpty(ip)) {
			if (isLocal(ip) || isIpv6Localhost(ip))
				return "127.0.0.1";

			if (RegexValidateUtils.isIp(ip))
				return ip;

			String[] ips = StringUtils.split(ip, ",");
			if (ips.length == 1) {
				ip = StringUtils.length(ips[0]) > 50 ? StringUtils.substring(
						ips[0], 50) : ips[0];
			} else {
				for (String str : ips) {
					if (RegexValidateUtils.isIp(str)) {
						return str;
					}
				}
			}
		}
		return ip;
	}
	
	public static boolean isIpv6Localhost(String ip) {
		return StringUtils.equals(ip, "0:0:0:0:0:0:0:1");
	}

	/**
	 * 返回服务器IP字符串，以逗号分割
	 * 
	 * @return
	 * @throws SocketException
	 */
	public static String getServerIPsStr() {
		StringBuilder sb = new StringBuilder();
		List<String> ips = getServerIpsExceptLocal();
		for (String ip : ips) {
			sb.append(ip);
			sb.append(",");
		}
		return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : sb.toString();
	}
	
}
