package com.chinadrtv.web.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;


public class TagDirective implements TemplateDirectiveModel {

    /*
     * 参考http://www.oschina.net/code/snippet_114990_4438
     */
    
    /** 
     * @see freemarker.template.TemplateDirectiveModel#execute(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateModel[], freemarker.template.TemplateDirectiveBody)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void execute(Environment env, Map params, TemplateModel[] model, TemplateDirectiveBody body)
                                                                                                     throws TemplateException,
                                                                                                     IOException {
        TemplateModel templateModel = (TemplateModel)params.get("tagCode");
        String tagCode = templateModel.toString();
        
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();
        HttpSession session = request.getSession();
        Object object = session.getAttribute(TagConstant.JURISDICTION_CONSTANT);
        
        Writer out = env.getOut();
        
        if(object!=null){
            List<Long> JurisdictionList = (List<Long>)object;
            if(JurisdictionList.contains(Long.parseLong(tagCode))){
                body.render(out);
            }
        }
    }

}
