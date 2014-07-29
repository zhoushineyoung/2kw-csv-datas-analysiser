package com.ebj.analysiser.loader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

import org.apache.log4j.Logger;

public class CSVFileResolve {
	
	Logger log=Logger.getLogger(CSVFileResolve.class);
	
	
	/**
	 * 写一行数据到文件中，支持中文
	 * @param filePath  文件路径
	 * @param vector 数据
	 * @return
	 */
	public boolean writeFile(String filePath,Vector<String> vector){
		  try {
		  

		      BufferedWriter bw =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "GBK")) ;// 附加
		      // 添加新的数据行
		      for (String s :vector) {
		    	  bw.write(s);
			      bw.newLine();
			  }
	
		      bw.close();
                log.debug("\n wirite file path:"+filePath);
               
		    } catch (FileNotFoundException e) {
		    	log.error(e.toString(),e.fillInStackTrace());
		    	return false;
		    } catch (IOException e) {
		    	log.error(e.toString(),e.fillInStackTrace());
		    	return false;
		    }
		  
		return true;
	}
	
	
	/**
	 * 写一行数据到文件中，支持中文
	 * @param file  文件
	 * @param vector 数据
	 * @return
	 */
	public boolean writeFile(File file,Vector<String> vector){
		  try {
		  

		      BufferedWriter bw =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "GBK")) ;// 附加
		      // 添加新的数据行
		      for (String s :vector) {
		    	  bw.write(s);
			      bw.newLine();
			  }
	
		      bw.close();
		      log.debug("\n wirite file:"+file);
		    } catch (FileNotFoundException e) {
		    	log.error(e.toString(),e.fillInStackTrace());
		    	return false;
		    } catch (IOException e) {
		    	log.error(e.toString(),e.fillInStackTrace());
		    	return false;
		    }
		  
		return true;
	}
	
	public Vector<String> readFile(File file){
		Vector<String> vector=new Vector<String>();
		
		if(file !=null && file.exists() && file.isFile() ){
			try {
				BufferedReader bufferdReader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"GBK"));
				String strTem="";
				while((strTem=bufferdReader.readLine())!=null){
					vector.add(strTem);
				}
				log.debug("readfile:"+file);
			} catch (UnsupportedEncodingException e) {
				log.error(e.toString(),e.fillInStackTrace());
			} catch (FileNotFoundException e) {
				log.error(e.toString(),e.fillInStackTrace());
			} catch (IOException e) {
				log.error(e.toString(),e.fillInStackTrace());
			}
		}
		return vector;
	}

}
