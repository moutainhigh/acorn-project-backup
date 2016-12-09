package com.chinadrtv.web.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinadrtv.common.log.LOG_TYPE;


public class ParamUtils {
	public ParamUtils() {
	}

	public static String getParameter(HttpServletRequest httpservletrequest,
			String s) {
		return getParameter(httpservletrequest, s, false);
	}

	public static String getParameter(Object object, String s) {
		if (object == null) {
			return s;
		}
		String s1 = null;
		if (object instanceof String[]) {
			s1 = ((String[]) object)[0];
		} else if (object instanceof String) {
			s1 = (String) object;
		}

		if (s1.equals(""))
			return null;
		else
			return s1;

	}

	public static String getParameter(HttpServletRequest httpservletrequest,
			String s, boolean flag) {
		String s1 = httpservletrequest.getParameter(s);
		if (s1 != null) {
			if (s1.equals("") && !flag)
				return null;
			else
				return s1;
		} else {
			return null;
		}
	}

	public static String getParameter(HttpServletRequest httpservletrequest,
			String s, String s1) {
		String s2 = httpservletrequest.getParameter(s);
		if (s2 != null) {
			if (s2.equals(""))
				return s1;
			else
				return s2;
		} else {
			return s1;
		}
	}

	public static int getIntParameter(HttpServletRequest httpservletrequest,
			String s, int i) {
		String s1 = httpservletrequest.getParameter(s);
		if (s1 != null && !s1.equals("")) {
			int j = i;
			try {
				j = Integer.parseInt(s1);
			} catch (Exception exception) {
			}
			return j;
		} else {
			return i;
		}
	}

	public static int getIntParameter(Object object, int i) {
		if (object == null) {
			return i;
		}
		try {
			String s1 = null;
			if (object instanceof String[]) {
				s1 = ((String[]) object)[0];
			} else if (object instanceof String) {
				s1 = (String) object;
			} else {
				s1 = String.valueOf(object);
			}

			return Integer.valueOf(s1);
		} catch (Exception e) {
			// LOG.error("转换类型错误===" + e.getMessage());
			try {
				return Integer.parseInt((String) object);
			} catch (Exception e1) {
				return (Integer) object;
			}
		}
	}

	public static double getDoubleParameter(
			HttpServletRequest httpservletrequest, String s, double d) {
		String s1 = httpservletrequest.getParameter(s);
		if (s1 != null && !s1.equals("")) {
			double d1 = d;
			try {
				d1 = Double.parseDouble(s1);
			} catch (Exception exception) {
				double d2 = d;
				return d2;
			}
			return d1;
		} else {
			return d;
		}
	}

	public static double getDoubleParameter(Object object, double d) {
		if (object == null) {
			return d;
		}
		try {
			String s1 = ((String[]) object)[0];
			return Double.parseDouble(s1);
		} catch (Exception e) {
			LOG.error("转换类型错误===" + e.getMessage());
			return d;
		}
	}

	public static boolean getBooleanParameter(
			HttpServletRequest httpservletrequest, String s, boolean flag) {
		String s1 = httpservletrequest.getParameter(s);
		if (s1 == null)
			return flag;
		else
			return s1.equals("true");
	}

	public static boolean getBooleanParameter(Object object, boolean flag) {
		if (object == null) {
			return flag;
		}
		String s1 = ((String[]) object)[0];
		return s1.equals("true");
	}

	public static boolean getCheckboxParameter(
			HttpServletRequest httpservletrequest, String s) {
		String s1 = httpservletrequest.getParameter(s);
		return s1 != null && s1.equals("on");
	}

	public static String getAttribute(HttpServletRequest httpservletrequest,
			String s) {
		return getAttribute(httpservletrequest, s, false);
	}

	public static String getAttribute(HttpServletRequest httpservletrequest,
			String s, boolean flag) {
		String s1 = (String) httpservletrequest.getAttribute(s);
		if (s1 != null) {
			if (s1.equals("") && !flag)
				return null;
			else
				return s1;
		} else {
			return null;
		}
	}

	public static boolean getBooleanAttribute(
			HttpServletRequest httpservletrequest, String s) {
		String s1 = (String) httpservletrequest.getAttribute(s);
		return s1 != null && s1.equals("true");
	}

	public static int getIntAttribute(HttpServletRequest httpservletrequest,
			String s, int i) {
		String s1 = (String) httpservletrequest.getAttribute(s);
		if (s1 != null && !s1.equals("")) {
			int j = i;
			try {
				j = Integer.parseInt(s1);
			} catch (Exception exception) {
			}
			return j;
		} else {
			return i;
		}
	}

	public static String[] getStringArrayParameter(
			HttpServletRequest httpservletrequest, String s) {
		return httpservletrequest.getParameterValues(s);
	}

	private static final Logger LOG = LoggerFactory
			.getLogger("LOG_TYPE.PAFF_COMMON.val");

}
