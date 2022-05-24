package com.demo.usersec.jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

//import org.hibernate.cache.spi.SecondLevelCacheLogger_.logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.demo.usersec.model.Role;
import com.demo.usersec.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class JwtUserDetails implements UserDetails {

	private static final Logger logger = LoggerFactory.getLogger(JwtUserDetails.class);

	private static final long serialVersionUID = 3951889481671809885L;

	private User user;

	private Integer active;

	// private final Long id;
	// private final String username;
	// private final String password;
	// private final Collection<? extends GrantedAuthority> authorities;

	public JwtUserDetails(User user) {
		this.user = user;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Role> roles = this.user.getRoles();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		}

		return authorities;
	}

	/*
	 * public JwtUserDetails(Long id, String username, String password, String role)
	 * { this.id = id; this.username = username; this.password = password;
	 * 
	 * Set<Role> roles = user.getRoles(); // .getRoles();
	 * List<SimpleGrantedAuthority> authorities = new
	 * ArrayList<SimpleGrantedAuthority>(); // authorities.add(new
	 * SimpleGrantedAuthority(role));
	 * 
	 * for (Role role1 : roles) { authorities.add(new
	 * SimpleGrantedAuthority(role1.getRoleName())); }
	 * 
	 * this.authorities = authorities; }
	 */

	@Override
	public String getUsername() {
		return this.user.getUserName();
	}

	public Long getUserId() {
		return this.user.getUserUid();
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return this.user.getPassword();
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/*
	 * @Override public Collection<? extends GrantedAuthority> getAuthorities() {
	 * //Set<Role> roles = user.getRoles(); List<SimpleGrantedAuthority> authorities
	 * = new ArrayList<>();
	 * 
	 * for (Role role : roles) { authorities.add(new
	 * SimpleGrantedAuthority(role.getRoleName())); }
	 * 
	 * return authorities;
	 * 
	 * }
	 */

	@Override
	public boolean isEnabled() {
		return true;
	}
}
