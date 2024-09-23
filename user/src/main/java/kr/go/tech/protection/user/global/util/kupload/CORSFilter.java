package kr.go.tech.protection.user.global.util.kupload;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class CORSFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, RAONK-Encoded");
        response.setHeader("Access-Control-Allow-Origin", "*");
        chain.doFilter(req, res);

    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

}
