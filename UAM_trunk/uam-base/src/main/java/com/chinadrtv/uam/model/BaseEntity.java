package com.chinadrtv.uam.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;

import com.chinadrtv.uam.utils.ModifierUtils;

/**
 * Base Entity.
 * 
 * @author Qianyong,Deng
 * @since Oct 8, 2012
 * 
 */
@MappedSuperclass
@SuppressWarnings("serial")
public abstract class BaseEntity implements Serializable, Comparable<BaseEntity> {
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE", insertable = true, updatable = false)
	private Date createDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_DATE", insertable = false, updatable = true)
	private Date updateDate;
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public final int compareTo(BaseEntity o) {
		return compare(o);
	}
	
	/**
	 * 实现该方法进行排序
	 * 
	 * @param t
	 * @return
	 */
	protected <T extends BaseEntity> int compare(T t) {
		return 0;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		builder.append(this.getClass());
		builder.append("@");
		builder.append(this.hashCode());
		builder.append("[");
		String info = toString(this, getClass());
		builder.append(info);
		builder.append("]}");
		return builder.toString();
	}

	public static String toString(Object obj, Class<?> clazz) {
		StringBuilder builder = new StringBuilder();

		Class<?> parentClass = clazz.getSuperclass();
		if (parentClass != null)
			builder.append(toString(obj, parentClass));

		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();

			// 跳过静态变量以及常量
			if (isStatic(field) || isFinal(field))
				continue;

			// 跳过内部类，防止栈溢出
			if (startsWith(name, "this$"))
				continue;

			try {
				field.setAccessible(true);
				Object value = field.get(obj);
				if (value instanceof String)
					builder.append(name + "=\"" + value.toString() + "\";");
				else
					builder.append(name + "=" + value.toString() + ";");
			} catch (Exception e) {
			}
		}
		return builder.toString();
	}

	// 判断是否static修辞
	private static boolean isStatic(Field field) {
		return ModifierUtils.isStatic(field.getClass());
	}

	// 判断是否final修辞
	private static boolean isFinal(Field field) {
		return ModifierUtils.isFinal(field.getClass());
	}

	// 判断是否以prefix开始
	private static boolean startsWith(String str, String prefix) {
		return StringUtils.startsWith(str, prefix);
	}
}
