package com.mattel.ecomm.coreservices.core.pojos;

import java.net.HttpCookie;

import javax.servlet.http.Cookie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CookiePojo {

	private String cookieName;
	private String cookieValue;
	private String cookiePath;
	private String domain;
	private boolean httpOnly;
	private boolean isSecure;

    /**
     * Default value set as -1.
     * @see {@link Cookie#getMaxAge()}
     * @see {@link HttpCookie#getMaxAge()}
     */
    private int maxAge = -1;
}
