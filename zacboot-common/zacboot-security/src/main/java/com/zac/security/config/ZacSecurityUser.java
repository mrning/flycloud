package com.zac.security.config;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;

import java.io.Serial;
import java.util.Collection;

/**
 * @author lengleng
 * @date 2019/2/1 扩展用户信息
 */
@Getter
public class ZacSecurityUser extends User {

	@Serial
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	/**
	 * 用户ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private final Long id;

	/**
	 * 手机号
	 */
	private final String phone;

	public ZacSecurityUser(Long id, String username, String password, String phone, boolean enabled,
						   boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
						   Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.id = id;
		this.phone = phone;
	}
}
