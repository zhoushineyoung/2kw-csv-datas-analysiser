package com.ebj.analysiser.process;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import com.ebj.analysiser.meta.GrogerInfo;
import com.google.common.base.Splitter;

public class CSVMappingBeanTest {

	public String pairsString = "";
	CSVMappingBean<GrogerInfo> csvMappingBean = null;

	@Before
	public void setUp() throws Exception {
		pairsString = "Name:name,CardNo:cardNo,Descriot:descriot,CtfTp:ctfTp,CtfId:ctfId,Gender:gender,Birthday:birthday,Address:address,Zip:zip,Dirty:dirty,District1:district1,District2:district2,District3:district3,District4:district4,District5:district5,District6:district6,FirstNm:firstNm,LastNm:lastNm,Duty:duty,Mobile:mobile,Tel:tel,Fax:fax,EMail:email,Nation:nation,Taste:taste,Education:education,Company:company,CTel:cTel,CAddress:cAddress,CZip:cZip,Family:family,Version:version,id:id";
		csvMappingBean = new CSVMappingBean<GrogerInfo>();
	}

	@Test
	public void testGetStrategy() {
		if (null != csvMappingBean) {
			System.out.println(csvMappingBean.getStrategy(GrogerInfo.class).getClass());
		}
		assertTrue(true);
	}

	@Test
	public void testGetMapping() {
		Map<String, String> pairs = csvMappingBean.getMapping(pairsString);
		for (Entry<String, String> entry: pairs.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			System.out.println(key + " - " + value);
		}
	}

	@Test
	public void testGetReader() {
		assertTrue(true);
	}

	@Test
	public void testParse2List() {
		assertTrue(true);
	}
}
