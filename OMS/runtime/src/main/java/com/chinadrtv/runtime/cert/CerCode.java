package com.chinadrtv.runtime.cert;

import com.chinadrtv.runtime.cert.dto.CertParam;
import com.chinadrtv.runtime.cert.dto.CertResult;


public interface CerCode {

	/**
	 * 远程校验用户提交的信息
	 */
	public boolean cerCode(int id, String submitCode) throws Exception;

	/**
	 * 远程获取校验信息
	 */
	public CertResult getRemoteCrtCode(CertParam certParam) throws Exception;

}
