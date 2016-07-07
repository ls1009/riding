package pms.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class AjaxFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    // AJAX 요청에 대해 모든 클라이언트의 접근을 허락하는 응답 헤더 추가
    HttpServletResponse httpResponse = (HttpServletResponse)response;
    httpResponse.addHeader("Access-Control-Allow-Origin", "*");
    
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {}
  
}







