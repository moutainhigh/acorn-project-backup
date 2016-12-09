package com.chinadrtv.uam.utils;

import java.lang.reflect.Modifier;

/**
 * Utility for modifier
 * 
 * @author Qianyong,Deng
 * @since Oct 8, 2012
 *
 */
public final class ModifierUtils {

	/**
     * Return <tt>true</tt> if the integer argument includes the
     * <tt>public</tt> modifier, <tt>false</tt> otherwise.
     *
     * @param clazz
     * @return <tt>true</tt> if <code>mod</code> includes the
     * <tt>public</tt> modifier; <tt>false</tt> otherwise.
     */
	public static boolean isPublic(Class<?> clazz) {
		if (clazz == null) return false;
		return Modifier.isPublic(clazz.getModifiers());
	}

	/**
	 * Return <tt>true</tt> if the integer argument includes the
	 * <tt>private</tt> modifier, <tt>false</tt> otherwise.
	 * 
	 * @param clazz
	 * @return <tt>true</tt> if <code>mod</code> includes the <tt>private</tt>
	 *         modifier; <tt>false</tt> otherwise.
	 */
	public static boolean isPrivate(Class<?> clazz) {
		if (clazz == null) return false;
		return Modifier.isPrivate(clazz.getModifiers());
	}

	/**
	 * Return <tt>true</tt> if the integer argument includes the
	 * <tt>protected</tt> modifier, <tt>false</tt> otherwise.
	 * 
	 * @param clazz
	 * @return <tt>true</tt> if <code>mod</code> includes the <tt>protected</tt>
	 *         modifier; <tt>false</tt> otherwise.
	 */
	public static boolean isProtected(Class<?> clazz) {
		if (clazz == null) return false;
		return Modifier.isProtected(clazz.getModifiers());
	}

	/**
	 * Return <tt>true</tt> if the integer argument includes the <tt>static</tt>
	 * modifier, <tt>false</tt> otherwise.
	 * 
	 * @param clazz
	 * @return <tt>true</tt> if <code>mod</code> includes the <tt>static</tt>
	 *         modifier; <tt>false</tt> otherwise.
	 */
	public static boolean isStatic(Class<?> clazz) {
		if (clazz == null) return false;
		return Modifier.isStatic(clazz.getModifiers());
	}

	/**
	 * Return <tt>true</tt> if the integer argument includes the <tt>final</tt>
	 * modifier, <tt>false</tt> otherwise.
	 * 
	 * @param clazz
	 * @return <tt>true</tt> if <code>mod</code> includes the <tt>final</tt>
	 *         modifier; <tt>false</tt> otherwise.
	 */
	public static boolean isFinal(Class<?> clazz) {
		if (clazz == null) return false;
		return Modifier.isFinal(clazz.getModifiers());
	}

	/**
	 * Return <tt>true</tt> if the integer argument includes the
	 * <tt>synchronized</tt> modifier, <tt>false</tt> otherwise.
	 * 
	 * @param clazz
	 * @return <tt>true</tt> if <code>mod</code> includes the
	 *         <tt>synchronized</tt> modifier; <tt>false</tt> otherwise.
	 */
	public static boolean isSynchronized(Class<?> clazz) {
		if (clazz == null) return false;
		return Modifier.isSynchronized(clazz.getModifiers());
	}

	/**
	 * Return <tt>true</tt> if the integer argument includes the
	 * <tt>volatile</tt> modifier, <tt>false</tt> otherwise.
	 * 
	 * @param clazz
	 * @return <tt>true</tt> if <code>mod</code> includes the <tt>volatile</tt>
	 *         modifier; <tt>false</tt> otherwise.
	 */
	public static boolean isVolatile(Class<?> clazz) {
		if (clazz == null) return false;
		return Modifier.isVolatile(clazz.getModifiers());
	}

	/**
	 * Return <tt>true</tt> if the integer argument includes the
	 * <tt>transient</tt> modifier, <tt>false</tt> otherwise.
	 * 
	 * @param clazz
	 * @return <tt>true</tt> if <code>mod</code> includes the <tt>transient</tt>
	 *         modifier; <tt>false</tt> otherwise.
	 */
	public static boolean isTransient(Class<?> clazz) {
		if (clazz == null) return false;
		return Modifier.isTransient(clazz.getModifiers());
	}

	/**
	 * Return <tt>true</tt> if the integer argument includes the <tt>native</tt>
	 * modifier, <tt>false</tt> otherwise.
	 * 
	 * @param clazz
	 * @return <tt>true</tt> if <code>mod</code> includes the <tt>native</tt>
	 *         modifier; <tt>false</tt> otherwise.
	 */
	public static boolean isNative(Class<?> clazz) {
		if (clazz == null) return false;
		return Modifier.isNative(clazz.getModifiers());
	}

	/**
	 * Return <tt>true</tt> if the integer argument includes the
	 * <tt>interface</tt> modifier, <tt>false</tt> otherwise.
	 * 
	 * @param clazz
	 * @return <tt>true</tt> if <code>mod</code> includes the <tt>interface</tt>
	 *         modifier; <tt>false</tt> otherwise.
	 */
	public static boolean isInterface(Class<?> clazz) {
		if (clazz == null) return false;
		return Modifier.isInterface(clazz.getModifiers());
	}

	/**
	 * Return <tt>true</tt> if the integer argument includes the
	 * <tt>abstract</tt> modifier, <tt>false</tt> otherwise.
	 * 
	 * @param clazz
	 * @return <tt>true</tt> if <code>mod</code> includes the <tt>abstract</tt>
	 *         modifier; <tt>false</tt> otherwise.
	 */
	public static boolean isAbstract(Class<?> clazz) {
		if (clazz == null) return false;
		return Modifier.isAbstract(clazz.getModifiers());
	}

	/**
	 * Return <tt>true</tt> if the integer argument includes the
	 * <tt>strictfp</tt> modifier, <tt>false</tt> otherwise.
	 * 
	 * @param clazz
	 * @return <tt>true</tt> if <code>mod</code> includes the <tt>strictfp</tt>
	 *         modifier; <tt>false</tt> otherwise.
	 */
	public static boolean isStrict(Class<?> clazz) {
		if (clazz == null) return false;
		return Modifier.isStrict(clazz.getModifiers());
	}
}
