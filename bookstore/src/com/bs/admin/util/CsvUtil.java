package com.bs.admin.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.ss.usermodel.Cell; 
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/* * 

* <p>Title: CsvUtil</p>  

* <p>Description: </p>  
	用于将excel文件转换成csv文件
* @author zhengjian  

* <p> @date 2018年12月11日</p>
 */
public class CsvUtil {

	// 输出路径（mysql安全路径）
//	private static String csvDir = "E:/mysql/";			
//	 private static String csvDir = "C:/ProgramData/MySQL/MySQL Server 5.7/Uploads/";
	private static String csvDir = PropertyUtil.CSVDIR.getName();

	/**
	 * 
	 * 
	 * <p>
	 * Title: excelToCsv
	 * </p>
	 * 
	 * <p>
	 * Description:
	 * </p>
	 * 将上传的xlsx表转为csv格式，然后将Excel文件删除
	 * 
	 * @param src
	 *            <p>
	 * @return 
	 * 			@date 2018年12月7日
	 *            </p>
	 */
	public static String xlsxToCsv(String src, Long adminId) {
		XSSFWorkbook workbook = null;
		BufferedWriter bw = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		File file = new File(src);
		FileInputStream fis = null;
		String filename = src.substring(src.lastIndexOf("\\")+1,src.lastIndexOf("."));
		try {
			fis = new FileInputStream(file);
			workbook = new XSSFWorkbook(fis);
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvDir.concat(filename).concat(".csv")), "UTF-8"));
			XSSFSheet sheet = workbook.getSheetAt(0);
//			System.out.println(sheet.getLastRowNum());
			// 第一行表头跳过 int i = 0; i < sheet.getLastRowNum(); i++
			for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
				XSSFRow row = sheet.getRow(i);
				if(row == null){
					System.out.println("空行");
					continue;
				}
				for (Cell cell : row) {
					if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
						bw.write(cell.getStringCellValue());
					}
					else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
						if(DateUtil.isCellDateFormatted(cell)){
							bw.write(sdf.format(cell.getDateCellValue()));
						}else{
							bw.write("" + cell.getNumericCellValue());
						}
					}
					else if (cell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN){
						bw.write("" + cell.getBooleanCellValue());
					}
					bw.write(",");
				}
				// 操作者id
				bw.write(adminId+",");
				bw.newLine();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (null != bw) {
					bw.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (null != fis) {
						fis.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//删除Excel文件，返回csv文件路径
		boolean delete = file.delete();
//		System.out.println(delete);
		return csvDir.concat(filename).concat(".csv");
	}
	
	
	/**
	 * 
	
	 * <p>Title: xlsToCsv</p>  
	
	 * <p>Description: </p>  
		将xls文件转换为csv文件
	 * @param fileName  
	 * <p> @date 2018年12月10日  </p>
	 */
	public static String xlsToCsv(String src, Long adminId) {
		HSSFWorkbook workbook = null;
		BufferedWriter bw = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String filename = src.substring(src.lastIndexOf("\\")+1,src.lastIndexOf("."));
//		String csvPath = csvDir.concat(fileName.substring(0,fileName.indexOf("."))).concat(".csv");
		File file = new File(src);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			workbook = new HSSFWorkbook();
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvDir.concat(filename).concat(".csv")), "UTF-8"));
			HSSFSheet sheet = workbook.getSheetAt(0);
			// 第一行表头跳过 int i = 0; i < sheet.getLastRowNum(); i++
			for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
				HSSFRow row = sheet.getRow(i);
				for (Cell cell : row) {
					if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
						bw.write(cell.getStringCellValue());
					}
					else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
						if(DateUtil.isCellDateFormatted(cell)){
							bw.write(sdf.format(cell.getDateCellValue()));
						}else{
							bw.write("" + cell.getNumericCellValue());
							
						}
					}
					else if (cell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN){
						bw.write("" + cell.getBooleanCellValue());
					}
					bw.write(",");
				}
				// 操作者id
				bw.write(adminId+",");
				bw.newLine();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (null != bw) {
					bw.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (null != fis) {
						fis.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//删除Excel文件，返回csv文件路径
		boolean delete = file.delete();
		return csvDir.concat(filename).concat(".csv");
	}
	
	/**
	 * 
	
	 * <p>Title: excel</p>  
	
	 * <p>Description: </p>  
		传入路径+文件全名（如“c://excel/data.xlsx”）根据文件后缀判断需要执行哪个方法。返回csv文件路径
	 * @param fileName  
	 * <p> @date 2018年12月10日  </p>
	 */
	public static String excel(String src, Long adminId){
		String suffix = src.substring(src.lastIndexOf(".")+1);
		if(suffix.equals("xlsx")){
			return xlsxToCsv(src, adminId);
		}else{
			return xlsToCsv(src, adminId);
		}
	}
}
