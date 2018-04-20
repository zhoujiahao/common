package cn.zhou.utils;

import java.util.ArrayList;
import java.util.List;

import com.joysuch.facade.system.pojo.DocumentCodePojo;

public final class StringUtil {

	public static String leftPad(Object obj, int len, char appendChar) {
		String result = null;
		if (obj != null) {
			String str = String.valueOf(obj);
			if (str.length() < len) {
				result = "";
				for (int i = 0; i < len - str.length(); i++) {
					result += appendChar;
				}
				result += str;
			} else {
				result = str;
			}
		}
		return result;
	}

	public static String substringAfterLast(String str, String separator) {
		if (isEmpty(str))
			return str;
		if (isEmpty(separator))
			return "";
		int pos = str.lastIndexOf(separator);
		if (pos == -1 || pos == str.length() - separator.length())
			return "";
		else
			return str.substring(pos + separator.length());
	}

	public static String viewValue(Object o) {
		if (o == null)
			return "-";
		if (isEmpty(o.toString()))
			return "-";
		return o.toString();
	}

	/**
	 * returns true if null or empty, otherwise false.
	 */
	public static boolean isEmpty(String string) {
		return (string == null) || (string.trim().length() == 0);
	}

	public static boolean notEmpty(String string) {
		return !isEmpty(string);
	}

	/**
	 * encode the HTML tags
	 * 
	 * @param str
	 * @return
	 */
	public static String encodeHtml(String str) {
		String eh = str;
		if (null != eh) {
			eh = eh.replaceAll("&", "&amp;");
			eh = eh.replaceAll(">", "&gt;");
			eh = eh.replaceAll("<", "&lt;");
			eh = eh.replaceAll("\"", "&quot;");
			eh = eh.replaceAll("'", "&#39;");
			eh = eh.replaceAll(" ", "&nbsp;");
			eh = eh.replaceAll("\r\n", "<br/>");
			eh = eh.replaceAll("\n", "<br/>");
			eh = eh.replaceAll("\r", "");
		}

		return eh;
	}

	/**
	 * encode the HTML tags except character '&nbsp;'
	 * 
	 * @param str
	 * @return
	 */
	public static String encodeHtmlExceptNbsp(String str) {
		String eh = str;
		if (null != eh) {
			eh = eh.replaceAll("&", "&amp;");
			eh = eh.replaceAll(">", "&gt;");
			eh = eh.replaceAll("<", "&lt;");
			eh = eh.replaceAll("\"", "&quot;");
			eh = eh.replaceAll("'", "&#39;");
			eh = eh.replaceAll("\r\n", "<br/>");
			eh = eh.replaceAll("\n", "<br/>");
			eh = eh.replaceAll("\r", "");
		}
		return eh;
	}

	public static String replaceNbspWithSpace(String str) {
		if (str != null) {
			str = str.replaceAll("&nbsp;", " ");
		}

		return str;
	}

	private static final byte[] val = { 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,
			0x09, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F };

	/**
	 * unescape the String variable which comes from javascript escape() method.
	 */
	public static String unescape(String param) {
		if (null == param || "".equals(param.trim()))
			return "";

		StringBuffer sbuf = new StringBuffer();
		int i = 0;
		int len = param.length();
		while (i < len) {
			int ch = param.charAt(i);
			if ('A' <= ch && ch <= 'Z') {
				sbuf.append((char) ch);
			} else if ('a' <= ch && ch <= 'z') {
				sbuf.append((char) ch);
			} else if ('0' <= ch && ch <= '9') {
				sbuf.append((char) ch);
			} else if (ch == '-' || ch == '_' || ch == '.' || ch == '!' || ch == '~' || ch == '*' || ch == '\'' || ch == '(' || ch == ')') {
				sbuf.append((char) ch);
			} else if (ch == '%') {
				int cint = 0;
				if ('u' != param.charAt(i + 1)) {
					cint = (cint << 4) | val[param.charAt(i + 1)];
					cint = (cint << 4) | val[param.charAt(i + 2)];
					i += 2;
				} else {
					cint = (cint << 4) | val[param.charAt(i + 2)];
					cint = (cint << 4) | val[param.charAt(i + 3)];
					cint = (cint << 4) | val[param.charAt(i + 4)];
					cint = (cint << 4) | val[param.charAt(i + 5)];
					i += 5;
				}
				sbuf.append((char) cint);
			} else {
				sbuf.append((char) ch);
			}
			i++;
		}
		return sbuf.toString();
	}

	public static boolean containsInvalidChar(String str) {
		// "<", ">", "'", """, ")", ";", "%", or "_"
		if (isEmpty(str)) {
			return false;
		}

		String[] chars = new String[] { "<", ">", "'", "\"", ")", ";", "%", "_" };

		for (String string : chars) {
			if (str.contains(string)) {
				return true;
			}
		}
		return false;
	}

	public static boolean getBoolean(String str) {
		return "YES".equalsIgnoreCase(str) || "TRUE".equalsIgnoreCase(str);
	}

	/**
	 * lefPad with septial Char
	 * 
	 * @param str
	 * @param fillChar
	 * @param len
	 * @return
	 */
	public static String leftPad(String str, char fillChar, int len) {
		if (str == null) {
			return null;
		}
		if (str.length() >= len) {
			return str;
		}
		StringBuilder buf = new StringBuilder(str);
		while (buf.length() < len) {
			buf.insert(0, fillChar);
		}
		return buf.toString();
	}

	/**
	 * rightPad with septial Char
	 * 
	 * @param str
	 * @param fillChar
	 * @param len
	 * @return
	 */
	public static String rightPad(String str, char fillChar, int len) {
		if (str == null) {
			return null;
		}
		if (str.length() >= len) {
			return str;
		}
		StringBuilder buf = new StringBuilder(str);
		while (buf.length() < len) {
			buf.append(fillChar);
		}
		return buf.toString();
	}

	/**
	 * split 2016年5月30日 上午10:32:48
	 * 
	 * @param strs
	 * @return TODO：分解string
	 */
	public static String[] split(String strs) {
		String[] s = null;
		if (StringUtil.notEmpty(strs)) {
			s = strs.split(",");
		}
		return s;
	}

	/**
	 * split 2016年5月30日 上午10:32:48
	 * 
	 * @param strs
	 * @return TODO：分解string
	 */
	public static String[] splitByPattern(String strs, String pattern) {
		String[] s = null;
		if (StringUtil.notEmpty(strs) && StringUtil.notEmpty(pattern)) {
			s = strs.split(pattern);
		}
		return s;
	}

	/**
	 * strArryToString 2016年6月6日 下午1:24:47
	 * 
	 * @param strs
	 * @return TODO：将字符串数组转换成数组格式的字符串
	 */
	public static String strArryToString(String[] strs) {
		String returnStr = "";
		if (strs != null && strs.length > 0) {
			for (String string : strs) {
				returnStr = returnStr + "," + string;
			}
		}
		return "[" + returnStr.substring(1) + "]";
	}

	public static void main(String[] args) {
	  /*  String str = "zh_CN-en_US";
	    String[] sarr = str.split("-");
	    System.out.println(sarr[0] + " : " + sarr[1]);*/
	    ReturnMsg msg = new ReturnMsg();
        List<DocumentCodePojo> pojoList = new ArrayList<>();
        DocumentCodePojo pojo = new DocumentCodePojo();
        pojo.setCode("abc_zh_CN");
        pojo.setModuleMsg("模拟的假数据-中文测试模块");
        DocumentCodePojo pojo1 = new DocumentCodePojo();
        pojo1.setCode("qqq_en_US");
        pojo1.setModuleMsg("模拟的假数据-英文测试模块");
        pojoList.add(pojo);
        pojoList.add(pojo1);
        msg.setErrorCode(0);
        msg.setData(pojoList);
        System.out.println(JsonUtil.toJson(msg));
	}
}
