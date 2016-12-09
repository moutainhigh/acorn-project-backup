package com.chinadrtv.uam.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Utility for collection.
 * 
 * @author Qianyong,Deng
 * @since Oct 8, 2012
 *
 */
@SuppressWarnings("rawtypes")
public final class CollectionUtils {

	/**
	 * Return true if the supplied Collection is null or empty. Otherwise, return false.
	 * 
	 * @param collection
	 *            the Collection to check
	 * @return whether the given Collection is empty
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return org.springframework.util.CollectionUtils.isEmpty(collection);
	}
	
	/**
	 * Return true if the supplied Map is null or empty. Otherwise, return false.
	 * 
	 * @param map
	 *            the Map to check
	 * @return whether the given Map is empty
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return org.springframework.util.CollectionUtils.isEmpty(map);
	}
	
	/**
	 * Return true if the supplied Collection is not null or not empty. Otherwise, return false.
	 * 
	 * @param collection
	 *            the Collection to check
	 * @return whether the given Collection is not empty
	 */
	public static boolean isNotEmpty(Collection<?> c) {
		return !isEmpty(c);
	}
	
	/**
	 * Return true if the supplied Map is not null or not empty. Otherwise, return false.
	 * 
	 * @param map
	 *            the Map to check
	 * @return whether the given Map is not empty
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}
	
	/**
	 * Convert the supplied array into a List. A primitive array gets converted
	 * into a List of the appropriate wrapper type. A null source value will be
	 * converted to an empty List.
	 * 
	 * @param source
	 *            the (potentially primitive) array
	 * @return the converted List result
	 * @throws IllegalArgumentException
	 *             if argument is not an array
	 */
	public static List toList(Object source) {
		if (!source.getClass().isArray()) {
			throw new IllegalArgumentException(
					"Argument is not an array, can not be converted to List. ");
		}
		return org.springframework.util.CollectionUtils.arrayToList(source);
	}
	
	
}
