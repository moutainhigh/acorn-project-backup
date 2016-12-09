package com.chinadrtv.erp.admin;

import static org.junit.Assert.*;

import org.junit.Test;

import com.chinadrtv.erp.util.MvelUtil;

public class MevlUtilTest {
	   @Test
	  public  void getValue() {    
              Double d = MvelUtil.getBigValue(null, new Double(200), new Double(300), new Double(400), new Double(500), new Double(220));
              assertTrue(d==200);
		   }    
}
