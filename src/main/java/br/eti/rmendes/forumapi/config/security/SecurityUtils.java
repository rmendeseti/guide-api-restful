package br.eti.rmendes.forumapi.config.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

public class SecurityUtils {

	public static final String AUTHORIZATION_HEADER = "Authorization";

	public static String resolveToken(HttpServletRequest request) {
		
		String bearerToken = request.getHeader(SecurityUtils.AUTHORIZATION_HEADER);
		
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        
        return null;
        
	}

}
