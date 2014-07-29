package com.ebj.analysiser;

import java.io.File;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.ebj.analysiser.loader.CSVFileResolve;

public class CSVFileResolveTest {

	Logger log = Logger.getLogger(CSVFileResolveTest.class);
	CSVFileResolve csvFileResolve = new CSVFileResolve();

	@Test
	public void testWriteCSVFile() {

		Vector<String> vector = new Vector<String>();
		vector.add("中国百姓");
		vector.add("11");
		vector.add("22");
		csvFileResolve.writeFile("a.csv", vector);
	}

	@Test
	public void testwriteCSVFile() {
		File file = new File("c.csv");
		Vector<String> vector = new Vector<String>();
		vector.add("中国百姓");
		vector.add("11");
		vector.add("22");
		csvFileResolve.writeFile(file, vector);
	}

	@Test
	public void testreadFile() {
		File file = new File("c.csv");
		Vector<String> vector = csvFileResolve.readFile(file);
		for (String s : vector) {
			System.out.println(s);
			log.debug(s);
		}
	}

}
