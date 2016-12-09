package com.chinadrtv.erp.task.remoting;

import com.chinadrtv.erp.remoting.task.PostPriceRemoteService;

public class PostPriceRemoteServiceImpl implements PostPriceRemoteService {

//	@Autowired
//	PostPriceQuartzJob postPriceQuartzJob;
	
	@Override
	public String run() {
		System.err.println("123");
//		postPriceQuartzJob.execute();
//		return postPriceQuartzJob.getEx();
		return null;
	}

}
