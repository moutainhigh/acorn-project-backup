/**
 * 
 */
package com.chinadrtv.uam.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.chinadrtv.uam.dao.SysUserDao;
import com.chinadrtv.uam.model.uam.SysUser;

/**
 * @author dengqianyong
 *
 */
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private SysUserDao sysUserDao;

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		SysUser user = sysUserDao.loadByName(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("User[" + username + "] not found.");
		}
			
		return new UserBean(user);
	}

	public void setSysUserDao(SysUserDao sysUserDao) {
		this.sysUserDao = sysUserDao;
	}

}
