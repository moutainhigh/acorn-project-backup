package com.chinadrtv.erp.distribution.parser.userlevel;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.chinadrtv.erp.model.agent.UserLevel;

/**
 * CVS 解析器
 * 
 * @author dengqianyong
 *
 */
public class CVSFileParser extends AbstractFileParser {

	public CVSFileParser(String suffix) {
		super(suffix);
	}

	@Override
	protected List<UserLevel> doParse(MultipartFile file) throws IOException {
		return null;
	}

}
