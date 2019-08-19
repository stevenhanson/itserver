package com.example.itserver.utils;

/**
 * <p>
 * 时间工具类
 * </p>
 *
 * @since 2018/10/29
 */

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtils {
	public static final String EMPTY = "";

	/**
	 * Represents a failed index search.
	 * @since 2.1
	 */
	public static final int INDEX_NOT_FOUND = -1;

	/**
	 * 是否相等
	 * @param c1 字符串1
	 * @param c2 字符串2
	 * @return 是否
	 */
	public static boolean isEquals(String c1, String c2){
		if (c1 == null && c2 == null)
			return true;
		else if (c1 == null && c2 != null)
			return c2.equalsIgnoreCase(c1);
		else
			return c1.equalsIgnoreCase(c2);
	}

	/**
	 * 获取内容，如果内容为空，则使用默认值
	 *
	 * @param c 内容
	 * @param def 字符串
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(Object c, T def) {
		if (isEmpty(c)) {
			return def;
		} else {
	        if (def != null && !c.getClass().equals(def.getClass())){
	            if (def instanceof Integer)
	                c = (new Double(c.toString())).intValue();
	            else if (def instanceof Double)
	                c = Double.parseDouble(c.toString());
	            else if (def instanceof Long)
	                c = (new Double(c.toString())).longValue();
	            else if (def instanceof Float)
	                c = Float.parseFloat(c.toString());
	            else if (def instanceof Date){
	            	String v = rightPadding(c.toString(),13,"0");
	            	c = new Date(Long.parseLong(v));
	            } else if (def instanceof String)
	            	c = c.toString();
	        }
			return (T)c;
		}
	}

	/**
	 * 字符串右侧填充
	 * @param c 字符串
	 * @param length 填充后最长度
	 * @param padding 填充物
	 * @return
	 */
	public static String rightPadding(String c, int length, String padding){
		if (c == null)
			return c;
		int csize = c.length();
		int psize = padding.length();
		while (csize < length){
			c += padding;
			csize += psize;
		}
		return csize > length ? c.substring(0, length-1) : c;
	}

	public static String leftPadding(String c, int length, String padding) {
		if (c == null)
			return c;
		int csize = c.length();
		int psize = padding.length();
		while (csize < length){
			c = padding + c;
			csize += psize;
		}
		return csize > length ? c.substring(0, length-1) : c;
	}

	private static final String REX_IMAGE = ".*\\.(jpg|gif|png|bmp|jpge)$";
	/**
	 *
	 * 文件名判断是否为图片
	 * @param filename 文件名
	 * @return
	 */
	public static boolean isValidImage(String filename){
		if (isEmpty(filename)) return false;
		String s = filename.trim().toLowerCase();
		Pattern p = Pattern.compile(REX_IMAGE);
		return p.matcher(s).matches();
	}

	/**
	 * 是否为数字
	 * @param str 字符串
	 * @return
	 */
    public static boolean isNumeric(String str) {
        // 该正则表达式可以匹配所有的数字 包括负数
        Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        Matcher isNum = pattern.matcher(str); // matcher是全匹配
        if (!isNum.matches())
            return false;
        return true;
    }

	/**
	 *
	 * 获取文件后缀名
	 * @param filename 文件名
	 * @return
	 */
	public static String getFileExtName(String filename){
		String s = filename.trim().toLowerCase();
		int pos = s.lastIndexOf('.');
		if (pos != -1 && pos < s.length())
			return s.substring(pos+1);
		else
			return null;
	}

	/**
	 *
	 * 获取不含路径的文件名
	 * @param filename 文件名
	 * @return String
	 */
	public static String getFilename(String filename){
		String s = filename.trim().replaceAll("\\\\","\\/");
		int pos = s.lastIndexOf('/');
		if (pos != -1 && pos < s.length())
			return s.substring(pos+1);
		else
			return filename;
	}

	public static boolean isMatchQR(String s){
		s = trim(s);
		String singleWordRegex = "[\\w\\?\\.\\&\\,\\\"\\:\\s\\?\\=\\+\\-\\{\\}\\(\\)\\[\\]\\/\\u4e00-\\u9fa5\\&]+";
		Pattern r = Pattern.compile(singleWordRegex);
		Matcher m = r.matcher(s);
		return m.matches();
	}
	/**
	 * 去掉两头的换行符、空格
	 *
	 * @param c 字符串
	 * @return String
	 */
	public static String trim(String c) {
		if (isEmpty(c)) {
			return c;
		} else {
			return c.replaceAll("^[\\s\\t\\n\\r\\f　]+|[\\s\\t\\n\\r\\f　]+$", "");
		}
	}
	
	/**
	 * 转换成安全的名称字符串
	 *
	 * @param c 仅允许英文，数字，中文，及标点-_+.@[](), 其它的都去掉
	 * @return String
	 */
	public static String toSafeName(String c) {
		if (c == null) {
			return null;
		} else {
			c = c.replaceAll("^[\\s\\t\\n\\r\\f]+|[\\s\\t\\n\\r\\f]+$", "")
					.replaceAll("[\\s\\t\\n\\r\\f]+", " ")
					.replaceAll("[\\\\~!#$%^&\\*<>,/\\?';:\"{}=]+", "");
			return c;
		}
	}

	/**
	 * l转换为安全的字符串（将所有可能为正则的符号安全处理）
	 * @param c 字符串
	 * @return boolean
	 */
	public static String toSafePatten(String c){
		if (c == null)
			return c;
		return c
				.replaceAll("\\*", "\\\\*")
				.replaceAll("\\?", "\\\\?")
				.replaceAll("\\.", "\\\\.")
				.replaceAll("\\(", "\\\\(")
				.replaceAll("\\)", "\\\\)")
				.replaceAll("\\|", "\\\\|")
				.replaceAll("\\[", "\\\\[")
				.replaceAll("\\]", "\\\\]")
				.replaceAll("\\|", "\\\\|")
				.replaceAll("\\-", "\\\\-")
				.replaceAll("\\$", "\\\\$")
				.replaceAll("\\^", "\\\\^")
				.replaceAll("[\\s\\t\\f\\n\\r]+", ".+");
	}

	/**
	 * 是否为安全的名称字符串
	 *
	 * @param c  不能为空，且仅允许英文，数字，中文，及标点-_+.@[]()
	 * @return boolean
	 */
	public static boolean isSafeName(String c) {
		if (c.contains(" ")) {
			return false;
		}
		if (c == null || c.equals("")) {
			return false;
		} else {
			c = c.replaceAll("^[\\s\\t\\n\\r\\f]+|[\\s\\t\\n\\r\\f]+$", "");
			if (c.equals(""))
				return false;
			else
				return !c.matches(".*[\\\\~!#$%^&\\*<>,/\\?';:\"{}=]+.*");
		}
	}

	/**
	 * 是否为空字符，为NULL或为空均为TURE
	 *
	 * @param c 字符
	 * @return boolean
	 */
	public static boolean isEmpty(Object c) {
		return c == null || c.toString().equals("");
	}

	/**
	 * 是否相等
	 * @param a 可为NULL或其它
	 * @param b 可为NULL或其它
	 * @return boolean
	 */
	public static boolean equals(String a, String b){
		if (a == null && b == null)
			return true;
		else if (a == null && b != null)
			return false;
		else if (a != null && b == null)
			return false;
		else
			return a.equals(b);
	}
	
	/**
	 * 是否为空白，在isEmpty基础上先trim再判断
	 * @param c 字符串
	 * @return boolean
	 */
	public static boolean isBlank(Object c) {
		return c == null || c.toString().trim().equals("");
	}
	/**
	 * 是否为有效的移动电话号码
	 *
	 * @param c 移动电话号码
	 * @return boolean
	 */
	public static boolean isValidMPN(String c) {
		if (!isEmpty(c)) {
			Pattern p = Pattern.compile("^1\\d{10}$");
			if (p.matcher(c).matches())
				return true;
		}
		return false;
	}

	/**
	 * 是否为有效的M1卡号
	 *
	 * @param c M1卡号
	 * @return boolean
	 */
	public static boolean isValidM1Card(String c) {
		if (!isEmpty(c)) {
			Pattern p = Pattern.compile("^\\d{10}$");
			if (p.matcher(c).matches())
				return true;
		}
		return false;
	}


	private static final String REX_EMAIL = "^[0-9a-zA-Z]+([\\-_\\.][0-9a-zA-Z]+)*@[0-9a-zA-Z]+(\\-[0-9a-zA-Z]+)*(\\.[0-9a-zA-Z]+(\\-[0-9a-zA-Z]+)*)+$";
	/**
	 * 是否为有效的邮箱
	 *
	 * @param c 邮箱
	 * @return boolean
	 */
	public static boolean isValidEmail(String c) {
		if (!isEmpty(c)) {
			Pattern p = Pattern.compile(REX_EMAIL);
			if (p.matcher(c).matches())
				return true;
		}
		return false;
	}

	private static final String REX_MAC = "[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]";
	/**
	 * 是否是有效的MAC地址
	 * @param c 字符串
	 * @return boolean
	 */
	public static boolean isValidMAC(String c){
		if (!isEmpty(c)){
			Pattern p = Pattern.compile(REX_MAC);
			if(p.matcher(c).matches())
				return true;
		}
		return false;
	}


	private static final String REX_IPV4 = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$";
	/**
	 * 是否为有效的IPV4格式
	 * @param c 字符串
	 * @return
	 */
	public static boolean isValidIPv4(String c){
		if (StringUtils.isEmpty(c))
			return false;
		Matcher match = Pattern.compile(REX_IPV4).matcher(c);
		if (match.matches()) {
			int ip1 = Integer.parseInt(match.group(1));
			int ip2 = Integer.parseInt(match.group(2));
			int ip3 = Integer.parseInt(match.group(3));
			int ip4 = Integer.parseInt(match.group(4));
			if (ip1 < 0 || ip1 > 255
					|| ip2 < 0 || ip2 > 255
					|| ip3 < 0 || ip3 > 255
					|| ip4 < 0 || ip4 > 255)
				return false;
		}else{
			return false;
		}
		return true;
	}
	/**
	 * 将IPV4转成数字数组
	 * @param c : IPv4 string
	 * @return int[]
	 */
	public static int[] ipv4ToIntArray(String c) {
		if (StringUtils.isEmpty(c))
			return null;
		Matcher match = Pattern.compile(REX_IPV4).matcher(c);
		if (match.matches()) {
			int ip1 = Integer.parseInt(match.group(1));
			int ip2 = Integer.parseInt(match.group(2));
			int ip3 = Integer.parseInt(match.group(3));
			int ip4 = Integer.parseInt(match.group(4));
			if (ip1 < 0 || ip1 > 255
					|| ip2 < 0 || ip2 > 255
					|| ip3 < 0 || ip3 > 255
					|| ip4 < 0 || ip4 > 255)
				return null;
			else
				return new int[] { ip1, ip2, ip3, ip4 };
		} else {
			return null;
		}
	}

	/**
	 * 获取序列网卡上第一个IP地址, 外网地址优先返回
	 * @return String
	 */
	public static String getFristIPv4() {
		String localip = null; // 本地IP，如果没有配置外网IP则返回它
		String netip = null; // 外网IP
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			boolean finded = false;// 是否找到外网IP
			while (netInterfaces.hasMoreElements() && !finded) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> address = ni.getInetAddresses();
				while (address.hasMoreElements()) {
					ip = address.nextElement();
					if(!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1){
						if (!ip.isSiteLocalAddress()) {// 外网IP
							netip = ip.getHostAddress();
							finded = true;
							break;
						} else if (localip == null && ip.isSiteLocalAddress()) {// 内网IP
							localip = ip.getHostAddress();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (netip != null && !"".equals(netip)) {
			return netip;
		} else {
			return localip;
		}
	}

	/**
	 * 获取本地IP，以eth最小的为主
	 * @return String
     */
	public static String getLocalIPv4() {
		String localIp = null; // 本地IP，如果没有配置外网IP则返回它
		String localName = null;
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> address = ni.getInetAddresses();
				while (address.hasMoreElements()) {
					ip = address.nextElement();
					if (!ip.isLoopbackAddress()
							&& !ip.getHostAddress().contains(":")
							&& ip.isSiteLocalAddress()){
						if (StringUtils.isEmpty(localName) && StringUtils.isEmpty(localIp)){
							localName = ni.getName().toLowerCase();
							localIp = ip.getHostAddress();
						}else {
							String niName = ni.getName().toLowerCase();
							if (localName.startsWith("eth") && niName.startsWith("eth")
									&& ((localName.length() == niName.length() && niName.compareTo(localName)<0 )||
									niName.length()<localName.length())){
								//这里是为了保证，同为eth开头时，取eth小的，比如eth5和eth15,取eth5
								localName = niName;
								localIp = ip.getHostAddress();
							}else if(!localName.startsWith("eth") && niName.startsWith("eth")){
								//以eth为主
								localName = niName;
								localIp = ip.getHostAddress();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return localIp;
	}

	/**
	 * 匿名字符串（如：aaa 转为 a**）
	 * @param c 原字符串
	 * @return String
	 */
	public static String anonymous(String c){
		return anonymous(c, '*');
	}
	/**
	 * 匿名字符串（如：aaa 转为 a**）
	 * @param c 原字符串
	 * @param anonym 匿名字符
	 * @return String
	 */
	public static String anonymous(String c, char anonym){
		if (StringUtils.isEmpty(c))
			return c;
		int count = c.length();
		if (count == 1)
			return String.valueOf(anonym);
		int start = (int)Math.floor((double)count / (double)4);
		if (start == 0)
			start = 1;
		else if (start > 4)
			start = 4;
		int end = count - start;
		if (end <= start)
			end = start + 1;
		StringBuffer result = new StringBuffer();
		result.append(c.substring(0, start));
		for (int i = start; i < end; i++)
			result.append(anonym);
		if (end < count)
			result.append(c.substring(end));
		return result.toString();
	}
	/**
	   * 将字符串截短，取前n个字节的字符，英文算半个字符。
	   * @param orignalString 原字符串
	   * @param byteLength 字节长度
	   * @param chopedString 超过部分的表示字符串
	   * @return 截取的字符串
	   */
	public static String chop(String orignalString, int byteLength, String chopedString) {
		if (orignalString == null || orignalString.length() == 0)
			return orignalString;
		StringBuffer buffer = new StringBuffer();
		int stringLength = orignalString.length();
		int i = 0;
		for (;byteLength > 0 && i < stringLength; i++) {
			char c = orignalString.charAt(i);
			if (c < '\u00ff') {
				byteLength--;
				buffer.append(c);
			} else {
				byteLength -= 2;
				if (byteLength >= 0)
					buffer.append(c);
			}
		}
		if (chopedString != null && buffer.length() < stringLength)
			buffer.append(chopedString);
		return buffer.toString();
	}
	
	/**
	 * 将数组转换为字符串
	 * @param delimiter 分割符
	 * @param elements 数组
	 * @return String
	 */
    public static String join(String delimiter, Object... elements) {
        StringBuffer sb = new StringBuffer();
        for (Object element : elements)
            sb.append(element.toString()).append(delimiter);
        return sb.toString().substring(0, sb.toString().length() - 1);
    }
    
	/**
	 * 将数组转换为字符串
	 * @param delimiter 分割符
	 * @param elements 数组
	 * @return String
	 */
    public static String join(String delimiter, Collection<Object> elements) {
        StringBuffer sb = new StringBuffer();
        for (Object element : elements)
            sb.append(element.toString()).append(delimiter);
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

	public static byte[] hexStrToBinaryStr(String hexString) {


		if (null == hexString || hexString.isEmpty()) {
			return null;
		}

		hexString = hexString.replaceAll(" ", "");

		int len = hexString.length();
		int index = 0;


		byte[] bytes = new byte[len / 2];

		while (index < len) {

			String sub = hexString.substring(index, index + 2);

			bytes[index/2] = (byte)Integer.parseInt(sub,16);

			index += 2;
		}


		return bytes;
	}

	public static Float bytes2Float (byte high, byte low) {
		return Float.parseFloat(new BigInteger(Integer.toHexString(high&0xff) + Integer.toHexString(low&0xff), 16).toString(10));
	}
}
