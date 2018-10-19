package com.cleartrip.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.CheckForNull;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExelReaderUtil {
	
	private ExelReaderUtil() {
		
	}

	private static Logger log = Logger.getLogger(ExelReaderUtil.class);

	public static Map<String, Object> readExcel(String filePath, String fileName, String sheetName) throws IOException {
		
		List<String> headerList = new ArrayList<>();
		List<String> valueList = new ArrayList<>();
		Map<String, Object> excelKeyValueDataMap= new HashMap<>();
		
		log.debug("Reading File  :" + filePath);
		File file = new File(filePath + '\\' + fileName);
		try (FileInputStream inputStream = new FileInputStream(file);) {
			Workbook workBook = null;
			String fileExtensionName = fileName.substring(fileName.indexOf('.'));
			if (fileExtensionName.equals(".xlsx")) {
				workBook = new XSSFWorkbook(inputStream);
			} else if (fileExtensionName.equals(".xls")) {
				workBook = new HSSFWorkbook(inputStream);
			}
			if(null != workBook) {
			getDataFromExcel(sheetName, headerList, valueList, excelKeyValueDataMap, workBook);
			}
			else {
				log.error("Workbook is null");
			}
		} catch (Exception e) {
			log.error("exception occured", e);
		}
		return excelKeyValueDataMap;
	}

	private static void getDataFromExcel(String sheetName, List<String> headerList, List<String> valueList,
			Map<String, Object> excelKeyValueDataMap, Workbook workBook) {
		Sheet sheet = workBook.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		log.debug("row count is :" + rowCount);
		for (int i = 0; i <= rowCount; i++) {
			Row row = sheet.getRow(i);
			for (int j = 0; j < row.getLastCellNum(); j++) {
				if (i == 0) {
					log.info("header list value is :" + row.getCell(j).getStringCellValue());
					headerList.add(row.getCell(j).getStringCellValue());
				} else {
					log.info("cell Type is :" + row.getCell(j).getCellType());
					if (row.getCell(j).getCellType() == CellType.NUMERIC) {
						valueList.add(String.valueOf(
								new java.text.DecimalFormat("0").format(row.getCell(j).getNumericCellValue())));
					} else {
						valueList.add(row.getCell(j).getStringCellValue());
					}
				}
			}
		}

		for (int i = 0; i < headerList.size(); i++) {
			excelKeyValueDataMap.put(headerList.get(i), valueList.get(i));
		}

		for (Map.Entry<String, Object> excelKeyValMap : excelKeyValueDataMap.entrySet()) {
			log.info("Key is :" + excelKeyValMap.getKey() + " Value is :" + excelKeyValMap.getValue());

		}
	}

}
