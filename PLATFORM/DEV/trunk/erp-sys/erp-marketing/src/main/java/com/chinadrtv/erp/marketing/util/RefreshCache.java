package com.chinadrtv.erp.marketing.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StringReader;
import java.rmi.RemoteException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.smsapi.dto.Result;
import com.chinadrtv.erp.smsapi.util.PropertiesUtil;
import com.ctc.ctcoss.webservice.service.IKeywordServiceProxy;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Servlet implementation class RefreshCache
 */
public class RefreshCache extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RefreshCache() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		XStream xstream = new XStream(new DomDriver());
		xstream.autodetectAnnotations(true);
		xstream.alias("result", Result.class);
		try {
			String endpoint = PropertiesUtil.getWordFilterUrl();
			IKeywordServiceProxy ywsnp = new IKeywordServiceProxy();
			String xml = ywsnp.getIKeywordService().getKeywords("");
			ObjectInputStream in = null;
			xstream.addImplicitCollection(Result.class, "keywordSegs");
			StringReader reader = new StringReader(xml);
			Constants.result = (Result) xstream.fromXML(xml);
			response.getWriter().print("refresh  success!");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
