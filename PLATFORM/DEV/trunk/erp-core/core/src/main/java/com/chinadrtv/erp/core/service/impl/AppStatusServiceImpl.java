package com.chinadrtv.erp.core.service.impl;

import com.chinadrtv.erp.core.dao.AppStatusDao;
import com.chinadrtv.erp.core.service.AppStatusService;
import com.chinadrtv.erp.exception.AppStatusException;
import com.chinadrtv.erp.util.AppValidationResource;
import com.chinadrtv.erp.util.SpringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * User: liuhaidong
 * Date: 12-8-10
 */
@Service("appStatusService")
public class AppStatusServiceImpl implements AppStatusService {

	@Autowired
	private AppStatusDao appStatusDao;
	
	/**
	* <p>Title: checkDbConnection</p>
	* <p>Description: </p>
	* @return
	* @throws AppStatusException
	* @see com.chinadrtv.erp.core.service.AppStatusService#checkDbConnection()
	*/ 
	public boolean checkDbConnection() throws AppStatusException {
		boolean result = false;
		String dburl = null;
		StringBuffer strs = new StringBuffer("");
		String dbSourceName = null;
		Connection conn = null;
		if(AppValidationResource.dbs!=null){
			DataSource db = null;
			for(String datasorce:AppValidationResource.dbs){
				dbSourceName = datasorce;
				try{
					db = (DataSource)SpringUtil.getBean(datasorce);
					conn = db.getConnection();
					dburl = conn.getMetaData().getURL();
					result = appStatusDao.testConStatus(conn);
					if(!result){
						throw new AppStatusException("数据库访问异常:"+conn.getMetaData().getURL());
					}
				}catch (Exception e) {
					e.printStackTrace();
					strs.append(dburl!=null?dburl:dbSourceName);
				}finally{
					if(conn!=null){
						try {
							conn.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		if(!strs.toString().equals("")){
			throw new AppStatusException("数据库访问异常:"+strs.toString());
		}
		return result;
	}

	/**
	* <p>Title: checkFilePath</p>
	* <p>Description: </p>
	* @return
	* @throws AppStatusException
	* @see com.chinadrtv.erp.core.service.AppStatusService#checkFilePath()
	*/ 
	public boolean checkFilePath() throws AppStatusException {
		boolean result = false;
		StringBuffer files = new StringBuffer("");
		if(AppValidationResource.files!=null){
			File file = null;
			int i=0;
			for(String filePath:AppValidationResource.files){
					file = new File(filePath);
					if(!file.exists()){
						if(i>0)files.append("|");
						files.append(filePath);
					}else{
						result = true;
					}
					i++;
			}
			
			if(!files.toString().equals("")){
				result = false;
				throw new AppStatusException("文件或目录不存在:"+files.toString());
			}
			
		}else{
			result = true;
		}
		
		return result;
	}

	/**
	* <p>Title: checkHttpConnection</p>
	* <p>Description: </p>
	* @return
	* @throws AppStatusException
	* @see com.chinadrtv.erp.core.service.AppStatusService#checkHttpConnection()
	*/ 
	public boolean checkHttpConnection() throws AppStatusException {
		
		boolean result = false;
		
		StringBuffer https = new StringBuffer("");
		if(AppValidationResource.https!=null){
			URL url = null;
			int i=0;
			
			try {
				for(String http:AppValidationResource.https){

					url = new URL(http);
					HttpURLConnection con = (HttpURLConnection)url.openConnection();
					//System.out.println(con.getResponseCode());
					
						if( 200 != con.getResponseCode()){
							if(i>0)https.append("|");
							https.append(http);
						}else{
							result = true;
						}
						i++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(!https.toString().equals("")){
				throw new AppStatusException("http连接异常:"+https.toString());
			}
			
		}else{
			result = true;
		}
		

		return result;
		
	}
  
}
