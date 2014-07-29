package com.ebj.analysiser.process;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public <T> MappingStrategy<T> getStrategy(T t) {
		HeaderColumnNameTranslateMappingStrategy<T> strategy = new HeaderColumnNameTranslateMappingStrategy<T>();
        strategy.setType((Class<T>) t);
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
	
	public CSVReader getReader(String fileName) {
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
	
	public <T> List<T> parse2List(T t, String fileName) {
		List<T> list = null;
		CsvToBean<T> csvToBean = new CsvToBean<T>();
		list = csvToBean.parse(getStrategy(t), getReader(fileName));
		
		for (T t2 : list) {
			System.out.println(t2.getClass());
		}
		
		return list;
	}
}
