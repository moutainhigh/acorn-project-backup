package com.chinadrtv.uam.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.chinadrtv.uam.model.auth.Role;
import com.chinadrtv.uam.model.uam.SysUser;

/**
 * Spring Security UserDetails implementation
 * 
 * @author dengqianyong
 *
 */
public class UserBean implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6162420636002310666L;
	
	private SysUser user;
	private Collection<Role> roles;
	
	public UserBean(SysUser user) {
		this.user = user;
		this.roles = user.getRoles();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getSignPass();
	}

	@Override
	public String getUsername() {
		return user.getSignName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.isEnabled();
	}
	
	public SysUser getSysUser() {
		return this.user;
	}
	public static void main(String[] args) {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		System.out.println(encoder.encodePassword("admin", ""));
	}
}
