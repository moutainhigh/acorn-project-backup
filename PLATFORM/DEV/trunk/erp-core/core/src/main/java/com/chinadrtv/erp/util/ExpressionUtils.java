package com.chinadrtv.erp.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.mvel2.MVEL;
import org.mvel2.compiler.CompiledExpression;
import org.mvel2.compiler.ExpressionCompiler;
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRuntime;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-4-12 下午3:40:17
 * 
 */
public class ExpressionUtils {

	private static Map<String, CompiledTemplate> templateCache = new ConcurrentHashMap<String, CompiledTemplate>();

	private static Map<String, CompiledExpression> expressionCache = new ConcurrentHashMap<String, CompiledExpression>();

	public static Object evalExpression(String expression,
			Map<String, ?> context) {
		if (StringUtils.isBlank(expression))
			return expression;

		CompiledExpression ce = expressionCache.get(expression);
		if (ce == null) {
			ce = new ExpressionCompiler(expression).compile();
			expressionCache.put(expression, ce);
		}
		return MVEL.executeExpression(ce, context);
	}

	public static Object eval(String template, Map<String, ?> context) {
		if (StringUtils.isBlank(template))
			return template;
		CompiledTemplate ct = templateCache.get(template);
		if (ct == null) {
			ct = new TemplateCompiler(template).compile();
			templateCache.put(template, ct);
		}
		return TemplateRuntime.execute(ct, context);
	}

	public static String evalString(String template, Map<String, ?> context) {
		Object obj = eval(template, context);
		if (obj == null)
			return null;
		return obj.toString();
	}

	public static boolean evalBoolean(String template, Map<String, ?> context,
			boolean defaultValue) {
		if (StringUtils.isBlank(template))
			return defaultValue;
		Object obj = eval(template, context);
		if (obj == null)
			return defaultValue;
		if (obj instanceof Boolean)
			return (Boolean) obj;
		return Boolean.parseBoolean(obj.toString());
	}

	public static int evalInt(String template, Map<String, ?> context,
			int defaultValue) {
		if (StringUtils.isBlank(template))
			return defaultValue;
		Object obj = eval(template, context);
		if (obj == null)
			return defaultValue;
		if (obj instanceof Integer)
			return (Integer) obj;
		return Integer.parseInt(obj.toString());
	}

	public static long evalLong(String template, Map<String, ?> context,
			long defaultValue) {
		if (StringUtils.isBlank(template))
			return defaultValue;
		Object obj = eval(template, context);
		if (obj == null)
			return defaultValue;
		if (obj instanceof Long)
			return (Long) obj;
		return Long.parseLong(obj.toString());
	}

	public static double evalDouble(String template, Map<String, ?> context,
			double defaultValue) {
		if (StringUtils.isBlank(template))
			return defaultValue;
		Object obj = eval(template, context);
		if (obj == null)
			return defaultValue;
		if (obj instanceof Double)
			return (Double) obj;
		return Double.parseDouble(obj.toString());
	}

	@SuppressWarnings("rawtypes")
	public static List evalList(String template, Map<String, ?> context) {
		Object obj = eval(template, context);
		if (obj == null)
			return null;
		if (obj instanceof List)
			return (List) obj;
		return Arrays.asList(obj.toString().split("\\s*,\\s*"));
	}

}