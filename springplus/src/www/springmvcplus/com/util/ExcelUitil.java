package www.springmvcplus.com.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSON;

import www.springmvcplus.com.common.SpringMVCPlusArgsConfig;
import www.springmvcplus.com.services.service.MyService;
import www.springmvcplus.com.util.system.SqlUtil;

public class ExcelUitil {
	private int currentRow=-1;
	public int getNewRowIndex() {
		return ++currentRow;
	}
	
	public static List<Map<String, Object>> excelToListMap(String excelname) throws FileNotFoundException, IOException {
		SpringMVCPlusArgsConfig springMVCPlusArgsConfig=SpringBeanUtil.getBean(SpringMVCPlusArgsConfig.class); 
		String excelPath=springMVCPlusArgsConfig.getInputFilePath()+excelname;
		System.out.println(excelPath);
		List<String> keys=new ArrayList<String>();
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		XSSFWorkbook wb=new XSSFWorkbook(new FileInputStream(FileUtil.getFile(excelPath)));
		Sheet sheet = wb.getSheetAt(0);
		//获取标题
		Row titleRow = sheet.getRow(0);
		int colums_num = titleRow.getLastCellNum();
		for(int i=0;i<colums_num;i++){
			keys.add(titleRow.getCell(i).getStringCellValue());
		}
		int allrow=sheet.getLastRowNum();
		//循环每一行的值
		for(int j=1;j<allrow;j++){//从第一行开始，应为0行是标题
			Map<String, Object> map=new TreeMap<>();
			Row dataRow = sheet.getRow(j);
			for(int i=0;i<colums_num;i++){
				map.put(keys.get(i), dataRow.getCell(i).getStringCellValue());
			}
			list.add(map);
		}
		return list;
	}
	public static List<Map<String, Object>> excelToListMap1(String excelname) throws FileNotFoundException, IOException {
		String excelPath=excelname;
		System.out.println(excelPath);
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		XSSFWorkbook wb=new XSSFWorkbook(new FileInputStream(FileUtil.getFile(excelPath)));
		Sheet sheet = wb.getSheetAt(0);
		//获取标题
		Row titleRow = sheet.getRow(0);
		int colums_num = titleRow.getLastCellNum();
		int allrow=sheet.getLastRowNum();
		//循环每一行的值
		int ll=1;
		int id=1;
		for(int j=0;j<=allrow;j++){//从第一行开始，应为0行是标题
			Map<String, Object> map=new TreeMap<>();
			Row dataRow = sheet.getRow(j);
			for(int i=0;i<colums_num;i++){
				map.put(j+"_"+i, dataRow.getCell(i));
				if (i==2) {
					String str=dataRow.getCell(i).toString().replace(".0", "");
					System.out.println("update answer set mark="+str.charAt(0)+" where id="+id+";");
					id++;
					System.out.println("update answer set mark="+str.charAt(1)+" where id="+id+";");
					id++;
					System.out.println("update answer set mark="+str.charAt(2)+" where id="+id+";");
					id++;
					ll++;
				}
			}
			list.add(map);
		}
		return list;
	}
	public static void main(String[] args) {
		try {
			List<Map<String, Object>> list = excelToListMap1("C:\\Users\\Administrator\\Desktop\\Book1.xlsx");
			System.out.println("---------------------");
			System.out.println(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String saveExcel(String sql,Object[] paramters,String excelname,int maxrow) throws IOException {
		MyService myService=SpringBeanUtil.getBean(MyService.class);
		
		int allrow=Integer.valueOf(myService.getSingleResult(SqlUtil.makeFenyeCount(sql)));
		int allpage=allrow%maxrow==0?allrow/maxrow:(allrow/maxrow+1);
		ExcelUitil excelUitil=new ExcelUitil();
		SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
		Sheet sh = wb.createSheet(excelname);
		boolean istitle=false;
		for(int page=1;page<=allpage;page++){
			String fenyesql = SqlUtil.makeFenye(sql, page, maxrow);
			List<Map<String, Object>> list = myService.getListMaps(fenyesql,paramters);
			for (Map<String, Object> map : list) {
				if (!istitle) {//如果还没有标题，生成标题
					Row rowTitle = sh.createRow(excelUitil.getNewRowIndex());
					int cellindex=0;
					for (String key : map.keySet()) {
						Cell cell = rowTitle.createCell(cellindex);
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);//文本格式   
						cell.setCellValue(key);
						cellindex++;
					}
					istitle=true;
				}
				Row dataRow=sh.createRow(excelUitil.getNewRowIndex());
				int cellindex=0;
				for (String key : map.keySet()) {
					Cell cell = dataRow.createCell(cellindex);
					cell.setCellType(XSSFCell.CELL_TYPE_STRING);//文本格式   
					cell.setCellValue(StringUtil.valueOf(map.get(key)));
					cellindex++;
				}
			}
		}
		FileOutputStream out = null;
		try {
			excelname=excelname+"_"+DateUtil.dateFormat(new Date(), "yyyyMMddHHmmss")+".xlsx";
			SpringMVCPlusArgsConfig springMVCPlusArgsConfig=SpringBeanUtil.getBean(SpringMVCPlusArgsConfig.class);
			out = new FileOutputStream(FileUtil.getFile(springMVCPlusArgsConfig.getInputFilePath()+excelname));
			wb.write(out);
			// 关闭文件流对象
			out.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return excelname;
	}
	public static String saveExcel(String sql,String excelname,int maxrow) throws IOException {
		MyService myService=SpringBeanUtil.getBean(MyService.class);
		
		int allrow=Integer.valueOf(myService.getSingleResult(SqlUtil.makeFenyeCount(sql)));
		int allpage=allrow%maxrow==0?allrow/maxrow:(allrow/maxrow+1);
		ExcelUitil excelUitil=new ExcelUitil();
		SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
		Sheet sh = wb.createSheet(excelname);
		boolean istitle=false;
		for(int page=1;page<=allpage;page++){
			String fenyesql = SqlUtil.makeFenye(sql, page, maxrow);
			List<Map<String, Object>> list = myService.getListMaps(fenyesql);
			for (Map<String, Object> map : list) {
				if (!istitle) {//如果还没有标题，生成标题
					Row rowTitle = sh.createRow(excelUitil.getNewRowIndex());
					int cellindex=0;
					for (String key : map.keySet()) {
						Cell cell = rowTitle.createCell(cellindex);
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);//文本格式   
						cell.setCellValue(key);
						cellindex++;
					}
					istitle=true;
				}
				Row dataRow=sh.createRow(excelUitil.getNewRowIndex());
				int cellindex=0;
				for (String key : map.keySet()) {
					Cell cell = dataRow.createCell(cellindex);
					cell.setCellType(XSSFCell.CELL_TYPE_STRING);//文本格式   
					cell.setCellValue(StringUtil.valueOf(map.get(key)));
					cellindex++;
				}
			}
		}
		FileOutputStream out = null;
		try {
			excelname=excelname+"_"+DateUtil.dateFormat(new Date(), "yyyyMMddHHmmss")+".xlsx";
			SpringMVCPlusArgsConfig springMVCPlusArgsConfig=SpringBeanUtil.getBean(SpringMVCPlusArgsConfig.class);
			out = new FileOutputStream(FileUtil.getFile(springMVCPlusArgsConfig.getInputFilePath()+excelname));
			wb.write(out);
			// 关闭文件流对象
			out.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return excelname;
	}
}
