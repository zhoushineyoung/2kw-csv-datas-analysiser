package com.ebj.analysiser.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import au.com.bytecode.opencsv.bean.MappingStrategy;
/**
 * reference :: http://www.2cto.com/kf/201401/270113.html
 * @author ZhouXY
 *
 * @param <T>
 */
public class CSVMappingBean<T> {
	private static String pairsString = "Name:name,CardNo:cardNo,Descriot:descriot,CtfTp:ctfTp,CtfId:ctfId,Gender:gender,Birthday:birthday,Address:address,Zip:zip,Dirty:dirty,District1:district1,District2:district2,District3:district3,District4:district4,District5:district5,District6:district6,FirstNm:firstNm,LastNm:lastNm,Duty:duty,Mobile:mobile,Tel:tel,Fax:fax,EMail:email,Nation:nation,Taste:taste,Education:education,Company:company,CTel:cTel,CAddress:cAddress,CZip:cZip,Family:family,Version:version,id:id";
	
	public static void setPairsString(String pairsString) {
		CSVMappingBean.pairsString = pairsString;
	}
	public static String getPairsString() {
		return pairsString;
	}

	public <T> MappingStrategy<T> getStrategy(Class<T> t) {
		HeaderColumnNameTranslateMappingStrategy<T> strategy = new HeaderColumnNameTranslateMappingStrategy<T>();
        strategy.setType(t);
        strategy.setColumnMapping(getMapping(pairsString));
        return strategy;
	}
	
	public Map<String, String> getMapping(String string) {
		Map<String, String> pairs = new HashMap<String, String>();
		if (!Strings.isNullOrEmpty(string)) {
			System.out.println(string);
			pairs = Splitter.on(",").withKeyValueSeparator(":").split(string);
		}
		return pairs;
	}
	
	private CSVReader getCSVReader(String fileName) {
		// String csvFilename = "C:\\sample2.csv";
		String csvFilename = fileName;
        CSVReader reader = null;
        try {
			reader = new CSVReader(new FileReader(csvFilename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        return reader;
	}
	
	private CSVReader getCSVReader2(File file) {
		CSVReader reader = null;
		try {
			reader = new CSVReader(new InputStreamReader(new FileInputStream(file), "GBK"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return reader;
	}
	
	public <T> List<T> parse2List(Class<T> t, String fileName) {
		List<T> list = null;
		CsvToBean<T> csvToBean = new CsvToBean<T>();
		list = csvToBean.parse(getStrategy(t), getCSVReader(fileName));
		
		for (T t2 : list) {
			System.out.println(t2.getClass());
		}
		
		return list;
	}
	
	public <T> List<T> parse2List2(Class<T> t, File file) {
		List<T> list = null;
		CsvToBean<T> csvToBean = new CsvToBean<T>();
		list = csvToBean.parse(getStrategy(t), getCSVReader2(file));
		
		for (T t2 : list) {
			System.out.println(t2.getClass());
		}
		
		return list;
	}
}
