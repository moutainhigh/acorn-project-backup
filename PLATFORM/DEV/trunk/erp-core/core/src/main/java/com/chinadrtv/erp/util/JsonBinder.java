/*
 * Copyright (c) 2012 Acorn International.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or  implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.chinadrtv.erp.util;


import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * User: ping.chen
 * Date: 8/6/12
 */
public class JsonBinder {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(JsonBinder.class);

    private ObjectMapper mapper;

    public JsonBinder(Inclusion inclusion) {
        mapper = new ObjectMapper();
        //set output inclusion
        mapper.getSerializationConfig().setSerializationInclusion(inclusion);
        //not fail on unknown java properties
        mapper.getDeserializationConfig().set(
                org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * create binder for all properties.
     */
    public static JsonBinder buildNormalBinder() {
        return new JsonBinder(Inclusion.ALWAYS);
    }

    /**
     * create bind for not null properties.
     */
    public static JsonBinder buildNonNullBinder() {
        return new JsonBinder(Inclusion.NON_NULL);
    }

    /**
     * create bind for changed properties.
     */
    public static JsonBinder buildNonDefaultBinder() {
        return new JsonBinder(Inclusion.NON_DEFAULT);
    }

    /**
     *  if JSON is empty, then return null
     *  if JSON is "[]", return zero size set.
     * <p/>
     * if want to transfer object List/Map, but not List<String> use following code:
     * List<MyBean> beanList = binder.getMapper().readValue(listString, new TypeReference<List<MyBean>>() {});
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.warn( "parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * if object is null, then return "null".
     * if set size is zero, then return "[]".
     */
    public String toJson(Object object) {

        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            logger.warn("write to json string error:" + object, e);
            return null;
        }
    }

    /**
     * set date formatter. if not set, use timestamp as default value.
     */
    public void setDateFormat(String pattern) {
        if (StringUtils.isNotBlank(pattern)) {
            DateFormat df = new SimpleDateFormat(pattern);
            mapper.getSerializationConfig().setDateFormat(df);
            mapper.getDeserializationConfig().setDateFormat(df);
        }
    }

    /**
     * get mapper object for setting more properties
     */
    public ObjectMapper getMapper() {
        return mapper;
    }
}