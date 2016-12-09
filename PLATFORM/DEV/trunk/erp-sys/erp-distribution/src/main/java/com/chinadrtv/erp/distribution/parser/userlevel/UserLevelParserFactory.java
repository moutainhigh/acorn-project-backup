package com.chinadrtv.erp.distribution.parser.userlevel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.resource.NotSupportedException;

import org.springframework.web.multipart.MultipartFile;

import com.chinadrtv.erp.model.agent.UserLevel;

/**
 * 解析用户等级文件的工厂
 * 
 * @author dengqianyong
 *
 */
public abstract class UserLevelParserFactory {
	
	private static final List<FileParser> parsers = new ArrayList<FileParser>();
	
	/**
	 * xls后缀的文件
	 */
	private static final String SUFFIX_XLS = ".xls";
	
	/**
	 * xlsx后缀的文件
	 */
	private static final String SUFFIX_XLSX = ".xlsx";
	
	/**
	 * cvs后缀的文件
	 */
	private static final String SUFFIX_CVS = ".cvs";
	
	static {
		parsers.add(new HSSFWorkbookFileParser(SUFFIX_XLS));
		parsers.add(new XSSFWorkbookFileParser(SUFFIX_XLSX));
		parsers.add(new CVSFileParser(SUFFIX_CVS));
	}

	/**
	 * 解析文件, 根据文件后缀找对应的解析器 
	 * 
	 * @param file
	 * @return
	 * @throws NotSupportedException 找不到解析器，抛出该异常
	 * @throws IOException 解析错误时，抛出该异常 
	 */
	public static List<UserLevel> parse(MultipartFile file)
			throws IOException {
		for (FileParser parser : parsers) {
			if (parser.isSupport(file)) {
				return parser.parse(file);
			}
		}
		throw new RuntimeException("不能解析该文件: " + file.getOriginalFilename());
	}
}
