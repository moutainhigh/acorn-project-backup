package com.chinadrtv.erp.tags;

import java.io.File;
import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.chinadrtv.erp.util.FileMd5Utils;

/**
 * 自定义Md5标签
 * usage: <p>
 * {@code
 * <acron:md5 path="/static/myorder/orderDetails.js"></acron:md5>
 * }
 * @author dengqianyong
 *
 */
public class Md5Tag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6063639533543795524L;
	
	private String path;
	
	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public int doStartTag() throws JspException {
		if (path == null || "".equals(path)) {
			return EVAL_PAGE;
		}
		
		if (!path.startsWith("/")) {
			path += "/";
		}
		
		try {
			String filePath = pageContext.getServletContext().getRealPath("") + path;
			File file = new File(filePath);
			if (file.exists()) {
				String md5 = FileMd5Utils.md5(file);
				pageContext.getOut().print(md5);
			}
		} catch (IOException e) {
		}
		return EVAL_PAGE;
	}

}
