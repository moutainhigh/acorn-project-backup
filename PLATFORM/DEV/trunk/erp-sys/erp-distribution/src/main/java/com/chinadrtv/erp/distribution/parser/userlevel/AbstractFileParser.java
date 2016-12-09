package com.chinadrtv.erp.distribution.parser.userlevel;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.chinadrtv.erp.model.agent.UserLevel;

/**
 * 抽象解析器
 * 
 * @author dengqianyong
 *
 */
public abstract class AbstractFileParser implements FileParser {
	
	protected String _suffix;
	
	public AbstractFileParser(String suffix) {
		this._suffix = suffix;
	}
	
	public final boolean isSupport(MultipartFile file) {
		return doSupport(getSuffix(file));
	}
	
	public final List<UserLevel> parse(MultipartFile file) throws IOException {
		return doParse(file);
	}
	
	private String getSuffix(MultipartFile file) {
		String oname = file.getOriginalFilename();
		return oname.substring(oname.lastIndexOf("."));
	}
	
	private boolean doSupport(String suffix) {
		return _suffix.equals(suffix);
	}

	/**
	 * 具体解析内容
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	protected abstract List<UserLevel> doParse(MultipartFile file) throws IOException;
	
	protected UserLevel create(String usrId, String level, String level2, String level3) {
		UserLevel ul = new UserLevel();
		ul.setUserId(usrId);
		ul.setLevelId(level);
		ul.setLevelId2(level2);
		ul.setLevelId3(level3);
		ul.setModifyTime(new Date());
		ul.setValid("-1");
		return ul;
	}
}
