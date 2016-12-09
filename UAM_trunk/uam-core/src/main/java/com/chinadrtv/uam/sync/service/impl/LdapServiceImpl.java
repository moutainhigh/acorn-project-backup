package com.chinadrtv.uam.sync.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.LikeFilter;
import org.springframework.security.ldap.SpringSecurityLdapTemplate;
import org.springframework.stereotype.Service;

import com.chinadrtv.uam.sync.model.AcornRole;
import com.chinadrtv.uam.sync.model.AgentUser;
import com.chinadrtv.uam.sync.model.DepartmentInfo;
import com.chinadrtv.uam.sync.model.GroupInfo;
import com.chinadrtv.uam.sync.service.LdapService;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-3-26
 * Time: 下午2:44
 * To change this template use File | Settings | File Templates.
 */
@Service
@SuppressWarnings("unchecked")
public class LdapServiceImpl implements LdapService {
    @Autowired
    private SpringSecurityLdapTemplate springSecurityLdapTemplate;

    public static final String SEARCH_GROUP_ROOT = "ou=department";//搜索组的根group
    public static final String SEARCH_GROUP_FILTER = "(ou={0})";//搜索组过滤
    public static final String SEARCH_ROLE_ROOT = "ou=roles";//搜索组的根角色
    public static final String EXCLUDE_ROLE = "test";//过滤的角色```
    public static final String LDAP_ROOT_DN = "dc=chinadrtv,dc=com";
    public static final String ROLE_OUTBOUND_MANAGER = "OUTBOUND_MANAGER";//outbound部门主管角色
    public static final String ROLE_OUTBOUND_GROUP_MANAGER = "OUTBOUND_GROUP_MANAGER";//outbound组主管角色
    public static final String ROLE_INBOUND_GROUP_MANAGER = "INBOUND_GROUP_MANAGER";//inbound组主管角色
    public static final String ROLE_INBOUND_MANAGER = "INBOUND_MANAGER";//inbound主管角色

    /**
     * 获取部门列表
     *
     * @return
     */
	public List<DepartmentInfo> getDepartmentList() {
        AndFilter andfilter = new AndFilter();
        EqualsFilter objectClassFilter = new EqualsFilter("objectClass", "acornDept");
        andfilter.and(objectClassFilter);

        List<DepartmentInfo> list = springSecurityLdapTemplate.search(SEARCH_GROUP_ROOT, andfilter.encode(),
                new AttributesMapper() {
                    public DepartmentInfo mapFromAttributes(Attributes attributes) {

                        DepartmentInfo departmentInfo = new DepartmentInfo();
                        departmentInfo.setId(getAttribute(attributes, "departmentNumber"));
                        departmentInfo.setName(getAttribute(attributes, "description"));
                        departmentInfo.setCode(getAttribute(attributes, "ou"));
                        departmentInfo.setAcornAreaCode(getAttribute(attributes, "acornAreaCode"));
                        departmentInfo.setAcornName(getAttribute(attributes, "acornName"));
                        return departmentInfo;
                    }
                });

        return list;
    }

    private String getAttribute(Attributes attributes, String attrID) {
        Attribute attribute = attributes.get(attrID);
        if (attribute == null) {
            return "";
        }

        try {
            Object object = attribute.get();
            if (object != null) {
                return object.toString();
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 获取角色列表
     *
     * @return
     */
    @Override
    public List<AcornRole> getAcornRoleList() {
        AndFilter andfilter = new AndFilter();
        EqualsFilter objectClassFilter = new EqualsFilter("objectClass", "acornRole");
        andfilter.and(objectClassFilter);

        SearchControls searchCtls = new SearchControls(); //创建搜索控制器
        searchCtls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        List<AcornRole> list = springSecurityLdapTemplate.search(SEARCH_ROLE_ROOT, andfilter.encode(), searchCtls,
                new AttributesMapper() {
                    public AcornRole mapFromAttributes(Attributes attributes) throws NamingException {

                        AcornRole role = new AcornRole();
                        NamingEnumeration<?> cnAll = attributes.get("cn").getAll();
                        while (cnAll.hasMoreElements()) {
                            String anotherString = cnAll.nextElement().toString();
                            if (!EXCLUDE_ROLE.equalsIgnoreCase(anotherString)) {
                                role.setName(anotherString);
                            }
                        }

                        role.setDescription(getAttribute(attributes, "description"));
                        role.setAcornPriority(getAttribute(attributes, "acornPriority"));
                        return role;
                    }
                });

        return list;
    }

    /**
     * 获取角色对应用户ID列表
     *
     * @param acornRole
     * @return
     */
    @Override
    public List<String> getUserIdList(String acornRole) {
        AndFilter andfilter = new AndFilter();
        EqualsFilter objectClassFilter = new EqualsFilter("objectClass", "acornRole");
        andfilter.and(objectClassFilter);
        EqualsFilter cnFilter = new EqualsFilter("cn", acornRole);
        andfilter.and(cnFilter);

        SearchControls searchCtls = new SearchControls(); // Create the
        searchCtls.setSearchScope(SearchControls.ONELEVEL_SCOPE);


        List<String> list = springSecurityLdapTemplate.search(SEARCH_ROLE_ROOT, andfilter.encode(), searchCtls,
                new AttributesMapper() {
                    public String mapFromAttributes(Attributes attrs) throws NamingException {
                        StringBuffer stringBuffer = new StringBuffer();

                        NamingEnumeration<?> memberAll = attrs.get("uniqueMember").getAll();
                        while (memberAll.hasMoreElements()) {
                            String anotherString = memberAll.nextElement().toString();
                            String[] values = anotherString.split(",");
                            for (String value : values) {
                                if (value.contains("uid=")) {
                                    stringBuffer.append(value.substring(value.indexOf("=") + 1)).append(",");
                                }
                            }
                        }

                        return stringBuffer.toString();
                    }
                });

        List<String> userIdList = new ArrayList<String>();
        if (userIdList != null) {
            for (String userStr : list) {
                String[] uidArray = userStr.split(",");
                for (String uid : uidArray) {
                    userIdList.add(uid);
                }
            }
        }

        return userIdList;
    }

    /**
     * 获取部门下的组列表
     *
     * @param departmentInfo
     * @return
     */
    @Override
    public List<GroupInfo> getGroupList(DepartmentInfo departmentInfo) {
        if (departmentInfo == null) {
            return new ArrayList<GroupInfo>();
        }

        String dn = getGroupDn(departmentInfo.getCode());

        AndFilter andfilter = new AndFilter();
        EqualsFilter objectClassFilter = new EqualsFilter("objectClass", "acornGroup");
        andfilter.and(objectClassFilter);
        SearchControls searchCtls = new SearchControls(); // 创建搜索控制器
        searchCtls.setSearchScope(SearchControls.ONELEVEL_SCOPE);

        List<GroupInfo> list = springSecurityLdapTemplate.search(dn, andfilter.encode(), searchCtls,
                new AttributesMapper() {
                    public GroupInfo mapFromAttributes(Attributes attrs) throws NamingException {

                        GroupInfo group = new GroupInfo();
                        group.setId(getAttribute(attrs, "ou"));
                        group.setName(getAttribute(attrs, "description"));
                        group.setType(getAttribute(attrs, "acornGroupType"));
                        group.setAreaCode(getAttribute(attrs, "acornAreaCode"));
                        return group;
                    }
                });

        return list;

    }

    private String getGroupDn(String groupCode) {
        try {
            DirContextOperations dir = springSecurityLdapTemplate.searchForSingleEntry(SEARCH_GROUP_ROOT, SEARCH_GROUP_FILTER,
                    new String[]{groupCode});

            if (dir != null) {
                return dir.getDn().toString();
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }


    @Override
    public List<AgentUser> getUserList(String groupCode) {
        EqualsFilter filter = new EqualsFilter("objectClass", "acornUser");


        String dn = getGroupDn(groupCode);
        if (StringUtils.isEmpty(dn)) {
            return null;
        }
        List<AgentUser> list = springSecurityLdapTemplate.search(dn, filter.encode(),
                new AttributesMapper() {
                    public Object mapFromAttributes(Attributes attrs) throws NamingException {
                        AgentUser user = new AgentUser();
                        user.setUserId(getAttribute(attrs, "uid"));
                        user.setDisplayName(getAttribute(attrs, "displayName"));
                        user.setDepartment(getAttribute(attrs, "departmentNumber"));
                        user.setEmployeeType(getAttribute(attrs, "employeeType"));
                        user.setDescription(getAttribute(attrs, "description"));
                        user.setDefGrp(getAttribute(attrs, "dn"));

                        user.setLastTime(getAttribute(attrs, "acornLastTime"));
                        user.setMaxFailure(getAttribute(attrs, "acornMaxFailure"));
                        Attribute attribute = attrs.get("userPassword");
                        if(attribute!=null && attribute.get()!=null){
                            byte[] object = (byte[]) attribute.get();
                            String password = new String(object);
                            user.setPassword(password);
                        }
                        return user;
                    }
                });
        List<AgentUser> list2 = new ArrayList<AgentUser>();
        for (AgentUser u : list) {
            u.setDefGrp(groupCode);
            list2.add(u);
        }

        Collections.sort(list2, new Comparator<AgentUser>() {
            public int compare(AgentUser a, AgentUser b) {
                return a.getUserId().compareToIgnoreCase(b.getUserId());
            }
        });

        return list2;
    }

    public List<String> getManageGroupList(String departmentNum) {

        DepartmentInfo dept = getDepartmentInfo(departmentNum);
        if (dept != null) {

            final String deptDn = getGroupDn(dept.getCode());

            AndFilter andfilter = new AndFilter();
            EqualsFilter objectClassFilter = new EqualsFilter("objectClass", "acornRole");
            andfilter.and(objectClassFilter);

            LikeFilter uniqueMemberFilter = new LikeFilter("uniqueMember", "*" + deptDn + "," + LDAP_ROOT_DN);
            andfilter.and(uniqueMemberFilter);

            final Map<String, String> groupsMap = new HashMap<String, String>();

            // 创建搜索控制器
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
            springSecurityLdapTemplate.search(SEARCH_ROLE_ROOT, andfilter.encode(), searchCtls, new AttributesMapper() {
                public String mapFromAttributes(Attributes attrs)
                        throws NamingException {
                    String role = getAttribute(attrs, "cn").toUpperCase();
                    String group = null;
                    if (role.equals(ROLE_OUTBOUND_GROUP_MANAGER)
                            || role.equals(ROLE_OUTBOUND_MANAGER)
                            || role.equals(ROLE_INBOUND_GROUP_MANAGER)
                            || role.equals(ROLE_INBOUND_MANAGER)) {
                        Attribute obj = attrs.get("uniqueMember");
                        if (obj != null) {
                            for (int i = 0; i < obj.size(); i++) {
                                if (obj.get(i).toString().indexOf(deptDn) > -1) {
                                    group = getGroup(obj.get(i).toString());
                                    groupsMap.put(group, group);
                                }

                            }
                        }
                    }
                    return group;
                }
            });

            List<String> list = new ArrayList<String>();
            if (!groupsMap.isEmpty()) {
                Set<String> set = groupsMap.keySet();
                list.addAll(set);
            }
            return list;
        }
        return null;
    }

    public static final String  SEARCH_SEAT_ROOT  = "ou=P3,ou=department";//搜索坐席的根是P3
    @Override
    public AgentUser findUserByUid(String userId) {
        AndFilter andfilter = new AndFilter();

        EqualsFilter objectClassFilter = new EqualsFilter("uid", userId);
        andfilter.and(objectClassFilter);

        List<AgentUser> list =  springSecurityLdapTemplate.search(SEARCH_SEAT_ROOT, andfilter.encode(),
                SearchControls.SUBTREE_SCOPE,
                new AttributesMapper() {
                    public AgentUser mapFromAttributes(Attributes attrs)
                            throws NamingException {
                        AgentUser user = new AgentUser();
                        user.setUserId(getAttribute(attrs, "uid"));
                        user.setDisplayName(getAttribute(attrs, "displayName"));
                        user.setDepartment(getAttribute(attrs, "departmentNumber"));
                        user.setEmployeeType(getAttribute(attrs, "employeeType"));
                        user.setDescription(getAttribute(attrs, "description"));
                        user.setDefGrp(getAttribute(attrs, "dn"));

                        user.setLastTime(getAttribute(attrs, "acornLastTime"));
                        user.setMaxFailure(getAttribute(attrs, "acornMaxFailure"));
                        Attribute attribute = attrs.get("userPassword");
                        if(attribute!=null && attribute.get()!=null){
                            byte[] object = (byte[]) attribute.get();
                            String password = new String(object);
                            user.setPassword(password);
                        }
                        return user;
                    }
                });

       if(list==null || list.isEmpty()) {
           return  null;
       }
        return list.get(0);
    }

    private String getGroup(String dn) {

        String result = "";
        Pattern pattern = Pattern.compile("ou=([A-Za-z0-9]+)");
        Matcher matcher = pattern.matcher(dn);
        if (matcher.find()) {
            result = matcher.group(1);
        }

        return result;
    }

    @SuppressWarnings("rawtypes")
	private DepartmentInfo getDepartmentInfo(String departmentNum) {
        if (!StringUtils.isEmpty(departmentNum)) {
            AndFilter andfilter = new AndFilter();
            EqualsFilter objectClassFilter = new EqualsFilter("objectClass", "acornDept");
            andfilter.and(objectClassFilter);
            EqualsFilter deptNumfilter = new EqualsFilter("departmentNumber", departmentNum);
            andfilter.and(deptNumfilter);
            List list = springSecurityLdapTemplate.search(SEARCH_GROUP_ROOT, andfilter.encode(),
                    new AttributesMapper() {
                        public DepartmentInfo mapFromAttributes(Attributes attrs)
                                throws NamingException {

                            DepartmentInfo departmentInfo = new DepartmentInfo();
                            departmentInfo.setId(getAttribute(attrs, "departmentNumber"));
                            departmentInfo.setName(getAttribute(attrs, "description"));
                            departmentInfo.setAreaCode(getAttribute(attrs, "acornAreaCode"));
                            departmentInfo.setCode(getAttribute(attrs, "ou"));
                            return departmentInfo;
                        }
                    });

            if (!list.isEmpty()) {
                return (DepartmentInfo) list.get(0);
            }
            return null;
        }
        return null;
    }
}
