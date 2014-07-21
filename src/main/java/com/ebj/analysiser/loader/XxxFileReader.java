package com.ebj.analysiser.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.csv.CSVPrinter;
import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;

import com.ebj.analysiser.utils.ArrayUtils;
import com.ebj.analysiser.utils.CommonUtils;
import com.google.common.base.Joiner;


public class XxxFileReader {
	static Logger syslog = Logger.getLogger(XxxFileReader.class);
	public static String csvDatasBaseFolder = "meta.csv.datas";
	private static Map<String, String[][]> csvTestDatasetMap = null; // 存放待测数据，和对应测试值
	// csv文件数据分隔符  
    private static final String SPLIT_TAG  = ",";  
    // 忽略标记
    private static final String IGNORE_TAG = "#"; 
    
    static {
		csvTestDatasetMap = new HashMap<String, String[][]>();
		syslog.info("Init Dataset ...");
		//初始化测试相关数据,包括测试值，期望值
		initCSVTestData();
	}
    
    public static void main(String[] args) throws UnsupportedEncodingException {
		String[][] object = getDatasetByFolderName("grogersinfo");
		for (String[] strings : object) {
			String tmpString = Joiner.on(",").join(strings);
			String str=new String(tmpString.getBytes("UTF-8"),"UTF-8");	
			System.out.println(tmpString);
			
		}
		Arrays.asList(object); 
	}
    
	public static ArrayList<String[]> initCSVTestData() {
		List<String[]> itemList = new ArrayList<String[]>();
		File file = new File(csvDatasBaseFolder);
		File [] fileList = file.listFiles();
		for (File f : fileList) {
			System.out.println(CommonUtils.getFileType(f));
		}
		for (int i = 0; i < fileList.length; i++) {
			File file2 = fileList[i];
			if (file2.isDirectory()) {
				String folderName = file2.getName();
				if (folderName.equals(".svn")) {
					continue;
				}
				File [] tempFileList = file2.listFiles();// 某个测试类(该类的文件名的全小写为目录名)对应的数据文件集合
				Map<String , File> fileMap = new HashMap<String, File>();
				
				for (int j = 0; j < tempFileList.length; j++) {
					File tempFile = tempFileList[j];
					if (tempFile.isFile()) {
						// 去掉包含SVN信息的文件夹
						String fileName = tempFile.getName();
						syslog.info("已成功加载文件： " + tempFile.getPath());
						fileMap.put(fileName, tempFile);
					}
				}
				csvTestDatasetMap.put(folderName, ArrayUtils.merge2DmArray(loadCVSDataset(fileMap)));//加载测试数据，和期望值
				fileMap.clear();//清除当前值
			}
			
		}

		return (ArrayList<String[]>) itemList;
	}
	
	/**
	 * <p>加载所有指定CSV文件内的数据集</p>
	 * @since V1.0
	 * @Author Martin
	 * @createTime 2014年1月15日 下午6:25:03
	 * @modifiedBy name
	 * @modifyOn dateTime
	 * @param fileMap
	 * @return
	 */
	private static List<String[][]> loadCVSDataset(Map<String, File> fileMap) {
		List<String[][]> testDataList = new ArrayList<String[][]>();
		Set<String> keys = fileMap.keySet();
		Iterator<String> it = keys.iterator();

		while (it.hasNext()) {
			String fileName = it.next();
			// csv文件
			if (fileName.indexOf("csv") != -1) {
				String[][] csvDatas = readCSVFile2(fileMap.get(fileName));
				if (csvDatas.length > 0) {
					testDataList.add(csvDatas);
				}
			}
		}
		return testDataList;
	}
	/**
	 * <p>加载CSV文件数据集</p>
	 * @since V1.0
	 * @Author Martin
	 * @createTime 2014年1月15日 下午7:10:35
	 * @modifiedBy name
	 * @modifyOn dateTime
	 * @param file
	 * @return
	 */
	private static String[][] readCSVFile(File file) {
		List<String[]> itemList = new ArrayList<String[]>();
		String[][] dataset = null;
		int _size = 0;
		
		if (file == null || file.isDirectory()) {
			return null;
		}
		
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file),"UTF-8");
		    BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
			//BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.indexOf(IGNORE_TAG) == 0) {
					continue;
				}
				String[] tmpArr = line.split(SPLIT_TAG);
				itemList.add(tmpArr);
				if (_size != tmpArr.length) {
					_size = tmpArr.length;
				}
			}
			// 剔除第一行标题信息 
			itemList.remove(0);
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (itemList.size() > 0) {
			dataset = new String[itemList.size()][_size];
			for (int i = 0; i < itemList.size(); i++) {
				dataset[i] = itemList.get(i);
			}
			// return dataset;
		}
		return dataset;
	}
	
	private static String[][] readCSVFile2(File file) {
		List<String[]> itemList = new ArrayList<String[]>();
		String[][] dataset = null;
		int _size = 0;
		
		if (file == null || file.isDirectory()) {
			return null;
		}
		
		try {
			FileReader reader = new FileReader(file);
			// CSVReader csvReader = new CSVReader(reader);
			// BufferedReader bufferedReader = new BufferedReader(reader);
			InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file),"UTF-8");
		    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			CSVReader csvReader = new CSVReader(bufferedReader);
			String[] csvLine = null;
			while ((csvLine = csvReader.readNext()) != null) {
				if (Joiner.on(",").join(csvLine).startsWith(IGNORE_TAG)) {
					continue;
				}
				itemList.add(csvLine);
				if (_size != csvLine.length) {
					_size = csvLine.length;
				}
			}
			// 剔除第一行标题信息 
			itemList.remove(0);
			reader.close();
			csvReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (itemList.size() > 0) {
			dataset = new String[itemList.size()][_size];
			for (int i = 0; i < itemList.size(); i++) {
				dataset[i] = itemList.get(i);
			}
			// return dataset;
		}
		return dataset;
	}
	/**
	 * <p>根据目录名获取测试数据集</p>
	 * @since V1.0
	 * @Author Martin
	 * @createTime 2014年1月16日 上午11:29:51
	 * @modifiedBy name
	 * @modifyOn dateTime
	 * @param folderName
	 * @return
	 */
	public static String[][] getDatasetByFolderName(String folderName) {
		return csvTestDatasetMap.get(folderName);
	}

	public static String readXxxFile(File file) {
		if (file == null || file.isDirectory()) {
			return "";
		}
		
		return null;
	}
}
