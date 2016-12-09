package com.chinadrtv.uam.dao.listener;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.event.MergeEvent;
import org.hibernate.event.def.DefaultMergeEventListener;

import com.chinadrtv.uam.model.BaseEntity;

/**
 * 监听Hibernate merge事件
 * 在合并对象之前，自动加入当前时间
 * 
 * @author dengqianyong
 *
 */
@SuppressWarnings("serial")
public class CreationDateMergeEventListener extends DefaultMergeEventListener {

	@Override
	public void onMerge(MergeEvent event) throws HibernateException {
		if (event.getOriginal() instanceof BaseEntity) {
			BaseEntity be = (BaseEntity) event.getOriginal();
			Date date = new Date();
			be.setCreateDate(date);
			be.setUpdateDate(date);
		}
		super.onMerge(event);
	}

}
