package com.chinadrtv.uam.validate;

import com.chinadrtv.uam.model.auth.User;
import com.chinadrtv.uam.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-4-14
 * Time: 上午10:20
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UserValidate {
     @Autowired
    public UserService userService;

     public String validateUser(User user){
         if(user.getId()==null){
            return  "更新角色出错，请刷新";
         }

         //用户title必须在names表中存在
         if(StringUtils.isNotBlank(user.getUserTitle())){
             boolean checkExists =  userService.checkExistsParam(user.getUserTitle());
             if(!checkExists){
                 return "配置表中无此职务";
             }
         }
         //用户workGroup必须在names表中存在
         if(StringUtils.isNotBlank(user.getWorkGroup())){
             boolean checkExists =  userService.checkExistsParam(user.getWorkGroup());
             if(!checkExists){
                 return "配置表中无此工作组";
             }
         }

         return "";
     }
}
