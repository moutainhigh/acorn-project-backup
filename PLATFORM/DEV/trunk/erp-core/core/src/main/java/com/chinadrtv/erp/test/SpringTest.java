package com.chinadrtv.erp.test;

import java.util.Iterator;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: liuhaidong
 * Date: 12-12-19
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/applicationContext*.xml")
@TransactionConfiguration
@Transactional
public class SpringTest extends AbstractTransactionalJUnit4SpringContextTests {

	/**
	 * <p>对比两个java.util.List是否equals非same 若要比较对象需要重写对象的hashCode与equals方法</p>
	 * @param list1
	 * @param list2
	 * @return java.util.Boolean
	 */
	public boolean compareWithoutOrder(List<?> list1, List<?> list2) {
		return this.compareWithoutOrder(list1, list2, true);
	}

	/**
	 * <p></p>
	 * @param list1
	 * @param list2
	 * @param iterateCompare	是交换比较对象对比
	 * @return java.util.Boolean
	 */
	private boolean compareWithoutOrder(List<?> list1, List<?> list2, boolean iterateCompare) {
		if(null == list1 || null == list2) {
			return false;
		}
		
		if(list1.size() != list2.size()) {
			return false;
		}
		
		Boolean[] assertRs = new Boolean[list1.size()];
		Integer idx = 0;
		boolean isNotMatch = false;
		for(Iterator<?> iter = list1.iterator(); iter.hasNext();) {
			Object obj1 = iter.next();
			
			for(Iterator<?> iter2 = list2.iterator(); iter2.hasNext();) {
				Object obj2 = iter2.next();
				if(obj1.equals(obj2) && (obj1.hashCode() == obj2.hashCode())) {
					assertRs[idx] = true;
					idx ++;
					isNotMatch = true;
					break;
				}else{
					isNotMatch = false;
				}
			}
			
			if(!isNotMatch) {
				assertRs[idx] = false;
				idx ++;
			}
		}
		
		boolean result = true;
		for(int i = 0; i < assertRs.length; i++) {
			result &= assertRs[i];
		}
		
		if(iterateCompare) {
			//list1里有重复数据
			result &= this.compareWithoutOrder(list2, list1, false);
		}
		
		return result;
	}
}
