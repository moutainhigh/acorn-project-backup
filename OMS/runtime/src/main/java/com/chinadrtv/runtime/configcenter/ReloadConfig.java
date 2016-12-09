package com.chinadrtv.runtime.configcenter;

public interface ReloadConfig {

	/**
	 * 配置项更改的时候，刷新配置项
	 * 
	 * @throws Exception
	 */
	public void refreshConfig() throws Exception;
}
	