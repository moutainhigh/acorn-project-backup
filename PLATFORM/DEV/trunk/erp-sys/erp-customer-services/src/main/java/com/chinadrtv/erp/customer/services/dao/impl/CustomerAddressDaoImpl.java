package com.chinadrtv.erp.customer.services.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.customer.services.dao.CustomerAddressDao;
import com.chinadrtv.erp.customer.services.dto.AddressApiDto;
import com.chinadrtv.erp.uc.constants.CustomerConstant;

@Repository
public class CustomerAddressDaoImpl extends JdbcDaoSupport implements CustomerAddressDao {
	
	@Resource
	public void setInjectDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}
	
	private static final String SQL = 
			"select p.provinceid, p.chinese, ci.cityid, ci.cityname, co.countyid, " +
				"co.countyname, co.zipcode, co.spellid, ar.areaid, ar.areaname " +
			"from iagent.province p " +
			"left join iagent.city_all ci on p.capital = ci.citykey " +
			"left join iagent.county_all co on ci.cityid = co.cityid " +
			"left join iagent.area_all ar on co.countyid = ar.countyid " +
			"where co.countykey = '其他' and ar.areakey = '其他' and p.chinese = ?";

    private static final String SQL_FOR_FOUR =
            "select p.provinceid, p.chinese, ci.cityid, ci.cityname, co.countyid, " +
                    "co.countyname, co.zipcode, co.spellid, ar.areaid, ar.areaname " +
                    "from iagent.province p " +
                    "left join iagent.city_all ci on p.provinceid = ci.provid " +
                    "left join iagent.county_all co on ci.cityid = co.cityid " +
                    "left join iagent.area_all ar on co.countyid = ar.countyid " +
                    "where p.chinese = ? and ci.cityname=? " +
                    "and co.countyname=? and ar.areaname=?";
	
	@Override
	public AddressApiDto findAddressInfo(String provinceName) {
		return getJdbcTemplate().queryForObject(SQL, new RowMapper<AddressApiDto>() {
			@Override
			public AddressApiDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				AddressApiDto dto = new AddressApiDto();
				dto.setProvinceId(rs.getString(1));
				dto.setProvinceName(rs.getString(2));
				dto.setCityId(rs.getInt(3));
				dto.setCityName(rs.getString(4));
				dto.setCountyId(rs.getInt(5));
				dto.setCountyName(rs.getString(6));
				dto.setZip(rs.getString(7));
				dto.setSpellId(rs.getInt(8));
				dto.setAreaId(rs.getInt(9));
				dto.setAreaName(rs.getString(10));
				dto.setAuditState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITED);
				return dto;
			}
		}, provinceName);
	}

    @Override
    public AddressApiDto findAddressInfo(String provinceName, String cityName, String countyName, String areaName) {
        return getJdbcTemplate().queryForObject(SQL_FOR_FOUR, new RowMapper<AddressApiDto>() {
            @Override
            public AddressApiDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                AddressApiDto dto = new AddressApiDto();
                dto.setProvinceId(rs.getString(1));
                dto.setProvinceName(rs.getString(2));
                dto.setCityId(rs.getInt(3));
                dto.setCityName(rs.getString(4));
                dto.setCountyId(rs.getInt(5));
                dto.setCountyName(rs.getString(6));
                dto.setZip(rs.getString(7));
                dto.setSpellId(rs.getInt(8));
                dto.setAreaId(rs.getInt(9));
                dto.setAreaName(rs.getString(10));
                dto.setAuditState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITED);
                return dto;
            }
        }, provinceName, cityName, countyName, areaName);
    }

}
