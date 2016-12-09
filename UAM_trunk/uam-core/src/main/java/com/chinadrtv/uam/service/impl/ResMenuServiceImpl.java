package com.chinadrtv.uam.service.impl;

import com.chinadrtv.uam.dao.ResMenuDao;
import com.chinadrtv.uam.dto.ResMenuTreeDto;
import com.chinadrtv.uam.service.ResMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ResMenuServiceImpl extends ServiceSupportImpl implements ResMenuService {

	@Resource
	private ResMenuDao resMenuDao;

    @Override
    public List<ResMenuTreeDto> getMenuTreeListByUserId(Long userId, String siteName) {
        List<ResMenuTreeDto> result = resMenuDao.getRoleListByUserId(userId, siteName, null);
        for (ResMenuTreeDto resMenuTreeDto : result) {
            resMenuTreeDto.setChildrens(resMenuDao.getRoleListByUserId(userId, siteName, resMenuTreeDto.getId()));
            for (ResMenuTreeDto menuTreeDto : resMenuTreeDto.getChildrens()) {
                menuTreeDto.setChildrens(resMenuDao.getRoleListByUserId(userId, siteName, menuTreeDto.getId()));
            }
        }
        return result;
    }
}
