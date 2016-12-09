package com.chinadrtv.web.tag;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


public class JurisdictionTag extends TagSupport {
    private final static long serialVersionUID = 1376549517702L;
    private String            tagCode;

    public String getTagCode() {
        return tagCode;
    }

    public void setTagCode(String tagCode) {
        this.tagCode = tagCode;
    }

    public JurisdictionTag() {
    }

    @SuppressWarnings("unchecked")
    public int doStartTag() throws JspException {
        HttpSession httpSession = pageContext.getSession();
        Object object = httpSession.getAttribute(TagConstant.JURISDICTION_CONSTANT);
        if (object != null) {
            List<String> JurisdictionList = (List<String>) object;
            if (JurisdictionList.contains(tagCode)) {
                return (EVAL_BODY_INCLUDE);
            }
        }

        return (SKIP_BODY);
    }

    public int doEndTag() throws JspException {
        return (EVAL_PAGE);
    }
}
