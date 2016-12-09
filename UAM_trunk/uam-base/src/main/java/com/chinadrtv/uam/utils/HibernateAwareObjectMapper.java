package com.chinadrtv.uam.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import com.fasterxml.jackson.module.hibernate.HibernateModule;


/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-3-19
 * Time: 下午1:37
 * To change this template use File | Settings | File Templates.
 */
public class HibernateAwareObjectMapper extends ObjectMapper {
    public HibernateAwareObjectMapper() {
        HibernateModule hm = new HibernateModule();
        registerModule(hm);
        configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
    }

    public void setPrettyPrint(boolean prettyPrint) {
        configure(SerializationConfig.Feature.INDENT_OUTPUT, prettyPrint);
    }
}
