package com.chinadrtv.erp.task.core.orm.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Long主键实体类
 * @author zhangguosheng
 */
@MappedSuperclass
public class IdEntity extends BaseEntity{

	private static final long serialVersionUID = 4275566650516425892L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
