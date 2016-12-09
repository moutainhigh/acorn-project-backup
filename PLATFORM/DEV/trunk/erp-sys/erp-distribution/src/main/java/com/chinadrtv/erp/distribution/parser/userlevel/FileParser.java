package com.chinadrtv.erp.distribution.parser.userlevel;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.chinadrtv.erp.model.agent.UserLevel;

/**
 * 文件解析器接口
 * 
 * @author dengqianyong
 *
 */
public interface FileParser {
	/**
	 * 是否支持该后缀
	 * 
	 * @param file
	 * @return
	 */
	boolean isSupport(MultipartFile file);
	
	/**
	 * 解析方法
	 * 
	 * @param file
	 * @return
	 */
	List<UserLevel> parse(MultipartFile file) throws IOException;
}
