package com.chinadrtv.erp.core.controller.Util;

import com.chinadrtv.erp.core.dao.query.*;
import com.chinadrtv.erp.core.model.FieldType;
import com.chinadrtv.erp.core.model.client.Search;
import com.chinadrtv.erp.core.model.client.SearchField;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * User: liuhaidong
 * Date: 12-11-12
 */
public class RequestUtil {
    private static Parameter getParameter(HttpServletRequest request,SearchField searchField){
      switch (searchField.getType()){
          case FieldType.STRING:{
              String value =  request.getParameter(searchField.getName());
              ParameterString parameterString = new ParameterString(searchField.getName(),value) ;
              return parameterString;
          }
          case FieldType.INTEGER:{
              String s = request.getParameter(searchField.getName());
              if (s.isEmpty()){
                  throw new IllegalArgumentException("value not set for: "+searchField.getName());
              }else{
                  Integer value =  Integer.parseInt(s);
                  ParameterInteger parameterString = new ParameterInteger(searchField.getName(),value) ;
                  return parameterString;
              }
          }
          case FieldType.DOUBLE:{
              String s = request.getParameter(searchField.getName());
              if (s.isEmpty()){
                  throw new IllegalArgumentException("value not set for: "+searchField.getName());
              }else{
                  Double value =  Double.valueOf(s);
                  ParameterDouble parameterString = new ParameterDouble(searchField.getName(),value) ;
                  return parameterString;
              }
          }
          case FieldType.DATE:{
              String s = request.getParameter(searchField.getName());
              if (s.isEmpty()){
                  throw new IllegalArgumentException("value not set for: "+searchField.getName());
              }else{
                  SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
                  Date value = null;
                  try {
                      value = sdf.parse(s);
                  } catch (ParseException e) {
                      throw new IllegalArgumentException("value not set for: "+searchField.getName());
                  }
                  ParameterDate parameterString = new ParameterDate(searchField.getName(),value) ;
                  return parameterString;
              }
          }
          default:return null;
      }
    }

    private static String getCriteriaField(SearchField searchField){
        return " and a." + searchField.getName();
    }
    private static Criteria getCriteria(HttpServletRequest request,SearchField searchField){
        switch (searchField.getType()){
            case FieldType.STRING:{
                String value =  getCriteriaField(searchField) + " like :" + searchField.getName();
                Criteria criteria = new Criteria(searchField.getName(),value) ;
                return criteria;
            }
            case FieldType.INTEGER:case FieldType.DOUBLE:case FieldType.DATE:{
                String value =  getCriteriaField(searchField) + " = :" + searchField.getName();
                Criteria criteria = new Criteria(searchField.getName(),value) ;
                return criteria;
            }
            default:return null;
        }
    }

    public static Map<String,Parameter> getQueryParameters(HttpServletRequest request,Search search){
        Map<String,Parameter> parameterMap = new HashMap<String, Parameter>();
        if (search != null){
            for (SearchField sf : search.getSearchFields()){
                Parameter p = getParameter(request,sf);
                if (p != null){
                    parameterMap.put(p.getName(),p);
                }
            }
        }
        String paramPage = request.getParameter(Page.PARAM_PAGE);
        if (paramPage != null && !paramPage.isEmpty()){
            Parameter p = new ParameterInteger(Page.PARAM_PAGE,Integer.valueOf(paramPage)-1);
            p.setForPageQuery(true);
            parameterMap.put(p.getName(),p);
        }

        String paramPageSize = request.getParameter(Page.PARAM_PAGE_SIZE);
        if (paramPageSize != null && !paramPageSize.isEmpty()){
            Parameter p = new ParameterInteger(Page.PARAM_PAGE_SIZE,Integer.valueOf(paramPageSize));
            p.setForPageQuery(true);
            parameterMap.put(p.getName(),p);
        }
        return parameterMap;
    }

    public static Map<String,Criteria> getQueryCriterias(HttpServletRequest request,Search search){
        Map<String,Criteria> parameterMap = new HashMap<String, Criteria>();
        if (search != null){
            for (SearchField sf : search.getSearchFields()){
                Criteria p = getCriteria(request,sf);
                if (p != null){
                    parameterMap.put(p.getParameterName(),p);
                }
            }
        }
        return parameterMap;
    }
}
