package com.chinadrtv.uam.dao.listener;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.def.DefaultSaveOrUpdateEventListener;

import com.chinadrtv.uam.model.BaseEntity;

/**
 * 监听 SaveOrUpdate 事件
 * 在保存或者更新对象之前，自动加入当前时间
 * 
 * @author dengqianyong
 *
 */
@SuppressWarnings("serial")
public class CreationDateSaveOrUpdateEventListener extends DefaultSaveOrUpdateEventListener {

	@Override
	public void onSaveOrUpdate(SaveOrUpdateEvent event)
			throws HibernateException {
		if (event.getObject() instanceof BaseEntity) {
			BaseEntity be = (BaseEntity) event.getObject();
			Date date = new Date();
			be.setCreateDate(date);
			be.setUpdateDate(date);
		}
		super.onSaveOrUpdate(event);
	}


}
