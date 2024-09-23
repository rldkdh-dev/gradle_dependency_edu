package kr.go.tech.protection.user.global.util.kupload;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;

public class CustomMultipartResolver extends CommonsMultipartResolver {
	
	public boolean isMultipart(HttpServletRequest request) {
		String _url = request.getRequestURL().toString();
		if(_url.contains("/kupload")) {
			return false;
		}
		
		return super.isMultipart(request);
	}
}
