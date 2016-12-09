package com.chinadrtv.erp.distribution.parser.userlevel;

import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.chinadrtv.erp.model.agent.UserLevel;

/**
 * xlsx 解析器
 * 
 * @author dengqianyong
 *
 */
public class XSSFWorkbookFileParser extends AbstractExcelWorkbookParser {

	public XSSFWorkbookFileParser(String suffix) {
		super(suffix);
	}

	@Override
	protected List<UserLevel> doParse(MultipartFile file) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		XSSFSheet sheet = workbook.getSheetAt(0);
		return doParse(sheet);
	}

}
