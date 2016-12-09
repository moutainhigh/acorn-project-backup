package com.chinadrtv.erp.distribution.parser.userlevel;

import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.chinadrtv.erp.model.agent.UserLevel;

/**
 * xls 解析器
 * 
 * @author dengqianyong
 *
 */
public class HSSFWorkbookFileParser extends AbstractExcelWorkbookParser {

	public HSSFWorkbookFileParser(String suffix) {
		super(suffix);
	}

	@Override
	protected List<UserLevel> doParse(MultipartFile file) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
		HSSFSheet sheet = workbook.getSheetAt(0);
		return doParse(sheet);
	}

}
