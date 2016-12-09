package com.chinadrtv.erp.uc.service;

import java.util.List;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.PotentialContact;
import com.chinadrtv.erp.uc.dto.AddressDto;
import com.chinadrtv.erp.uc.dto.ContactAddressDto;
import com.chinadrtv.erp.uc.dto.CustomerDto;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none
 * </dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>none
 * </dd>
 * </dl>
 *
 * @author zhoutaotao
 * @version 1.0
 * @since 2013-5-8 下午5:47:34
 */
public interface PotentialContactService extends GenericService<PotentialContact, Long> {
    /**
     * <p>SR1: API-UC-20.新增潜客</p>
     *
     * @param potentialContact
     */
    Long savePotentialContact(PotentialContact potentialContact);

    /**
     * 根据基本信息查找潜客
     * @param potentialContactId
     * @param name
     * @param phoneNum
     * @param phone1
     * @param phone2
     * @param phone3
     * @param index
     * @param rows
     * @return List<CustomerDto>
     */
    List<CustomerDto> findByBaseCondition(String potentialContactId,
                                          String name, String phoneNum,
                                          String phone1, String phone2,
                                          String phone3, int index, int rows);

    /**
     * <p>API-UC-21.更新潜客 4</p>
     * @param potentialContact
     */
    void updatePotentialContact(PotentialContact potentialContact);
    
    /**
     * <p>查询</p>
     * @param id
     * @return PotentialContact
     */
    PotentialContact queryById(Long id);
    
    /**
     * <p>查询</p>
     * @param id
     * @return PotentialContact
     */
    PotentialContact findById(Long id);

    /**
     * 潜客升级为正式客户
     * @param potentialContactId
     * @param province
     * @param cityId
     * @param countyId
     * @param areaId
     * @param address
     * @param zip
     * @return
     */
    Boolean upgradeToContact(Long potentialContactId,
                              String province,
                              Integer cityId,
                              Integer countyId,
                              Integer areaId,
                              String address,
                              String zip);
    /**
     * 潜客升级为正式客户(多地址)
     * @param potentialContactId
     * @return
     */
    Boolean upgradeToContact(
            Long potentialContactId,
            List<ContactAddressDto> addresses);

    Boolean upgradeToContact2(
            Long potentialContactId,
            List<AddressDto> addresses);


    /**
     * 潜客升级为正式客户 走审批流程
     * @param potentialContactId
     * @return
     */
    Boolean upgradeToContactWithFlow(
            Long potentialContactId,
            List<AddressDto> addresses) throws Exception;
}