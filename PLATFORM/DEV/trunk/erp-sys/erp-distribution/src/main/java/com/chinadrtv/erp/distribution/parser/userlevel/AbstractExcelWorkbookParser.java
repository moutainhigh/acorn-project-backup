package com.chinadrtv.erp.distribution.parser.userlevel;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.chinadrtv.erp.model.agent.UserLevel;

/**
 * 抽象解析excel文件
 * 
 * @author dengqianyong
 *
 */
public abstract class AbstractExcelWorkbookParser extends AbstractFileParser {

	public AbstractExcelWorkbookParser(String suffix) {
		super(suffix);
	}

	protected List<UserLevel> doParse(Sheet sheet) {
		List<UserLevel> result = new ArrayList<UserLevel>();
		for (int idx = 1; idx <= sheet.getLastRowNum(); idx++) {
			Row row = sheet.getRow(idx);
			if (row != null) {
				String usrId;
				if (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING) {
					usrId = Long.valueOf(row.getCell(0).getStringCellValue()).toString();
				} else {
					usrId = String.valueOf(Double.valueOf(row.getCell(0).getNumericCellValue()).longValue());
				}
				String level = row.getCell(1) != null ? row.getCell(1).getStringCellValue() : null;
				String level2 = row.getCell(2) != null ? row.getCell(2).getStringCellValue() : null;
				String level3 = row.getCell(3) != null ? row.getCell(3).getStringCellValue() : null;
				result.add(create(usrId, level, level2, level3));
			}
		}
		return result;
	}
}
