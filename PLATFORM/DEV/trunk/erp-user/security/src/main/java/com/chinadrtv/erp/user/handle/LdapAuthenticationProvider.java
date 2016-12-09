/*
 * @(#)LdapAuthenticationProvider.java 1.0 2013-7-26上午10:28:32
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.user.handle;

import java.util.Collection;

import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.authentication.AbstractLdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.LdapAuthenticator;
import org.springframework.security.ldap.authentication.NullLdapAuthoritiesPopulator;
import org.springframework.security.ldap.ppolicy.PasswordPolicyException;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.util.Assert;

import com.chinadrtv.erp.user.util.PasswordPolicyHelper;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-7-26 上午10:28:32
 * 
 */
@Deprecated
public class LdapAuthenticationProvider extends AbstractLdapAuthenticationProvider {

	private LdapAuthenticator authenticator;
	private LdapAuthoritiesPopulator authoritiesPopulator;
	private boolean hideUserNotFoundExceptions = true;

	// ~ Constructors
	// ===================================================================================================

	/**
	 * Create an instance with the supplied authenticator and authorities
	 * populator implementations.
	 * 
	 * @param authenticator
	 *            the authentication strategy (bind, password comparison, etc)
	 *            to be used by this provider for authenticating users.
	 * @param authoritiesPopulator
	 *            the strategy for obtaining the authorities for a given user
	 *            after they've been authenticated.
	 */
	public LdapAuthenticationProvider(LdapAuthenticator authenticator,
			LdapAuthoritiesPopulator authoritiesPopulator) {
		this.setAuthenticator(authenticator);
		this.setAuthoritiesPopulator(authoritiesPopulator);
	}

	/**
	 * Creates an instance with the supplied authenticator and a null
	 * authorities populator. In this case, the authorities must be mapped from
	 * the user context.
	 * 
	 * @param authenticator
	 *            the authenticator strategy.
	 */
	public LdapAuthenticationProvider(LdapAuthenticator authenticator) {
		this.setAuthenticator(authenticator);
		this.setAuthoritiesPopulator(new NullLdapAuthoritiesPopulator());
	}

	// ~ Methods
	// ========================================================================================================

	private void setAuthenticator(LdapAuthenticator authenticator) {
		Assert.notNull(authenticator, "An LdapAuthenticator must be supplied");
		this.authenticator = authenticator;
	}

	private LdapAuthenticator getAuthenticator() {
		return authenticator;
	}

	private void setAuthoritiesPopulator(LdapAuthoritiesPopulator authoritiesPopulator) {
		Assert.notNull(authoritiesPopulator, "An LdapAuthoritiesPopulator must be supplied");
		this.authoritiesPopulator = authoritiesPopulator;
	}

	protected LdapAuthoritiesPopulator getAuthoritiesPopulator() {
		return authoritiesPopulator;
	}

	public void setHideUserNotFoundExceptions(boolean hideUserNotFoundExceptions) {
		this.hideUserNotFoundExceptions = hideUserNotFoundExceptions;
	}

	@Override
	protected DirContextOperations doAuthentication(
			UsernamePasswordAuthenticationToken authentication) {
		try {
			PasswordPolicyHelper.removeThreadLocal();
			DirContextOperations operations = getAuthenticator().authenticate(authentication);
			return operations;
		} catch (PasswordPolicyException ppe) {
			// The only reason a ppolicy exception can occur during a bind is
			// that the account is locked.
			throw new LockedException(messages.getMessage(ppe.getStatus().getErrorCode(), ppe
					.getStatus().getDefaultMessage()));
		} catch (UsernameNotFoundException notFound) {
			if (hideUserNotFoundExceptions) {
				throw new BadCredentialsException(messages.getMessage(
						"LdapAuthenticationProvider.badCredentials", "Bad credentials"));
			} else {
				throw notFound;
			}
		} catch (NamingException ldapAccessFailure) {
			throw new AuthenticationServiceException(ldapAccessFailure.getMessage(),
					ldapAccessFailure);
		}
	}

	@Override
	protected Collection<? extends GrantedAuthority> loadUserAuthorities(
			DirContextOperations userData, String username, String password) {
		return getAuthoritiesPopulator().getGrantedAuthorities(userData, username);
	}

}
