package kr.co.green.filter;

import java.io.IOException;
import java.util.Enumeration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

@Component
@WebFilter("/*")
public class RequestLoggingFilter implements Filter{
	private static final Logger logger = LogManager.getLogger(RequestLoggingFilter.class);
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		long startTime = System.currentTimeMillis();
		
		chain.doFilter(request, response);
		
		long endTime = System.currentTimeMillis() - startTime;
		
		//logger.info("응답 시간 : {}", endTime);
		
		logDetails(httpRequest);
	}
	
	private void logDetails(HttpServletRequest request) {
		StringBuilder requestLog = new StringBuilder();
		requestLog.append("요청 정보 : ");
		requestLog.append("method = ").append(request.getMethod()).append(",");
		requestLog.append("URL = ").append(request.getRequestURL()).append(",");
		
		Enumeration<String> headerNames = request.getHeaderNames();
		requestLog.append("headers=[");
		while(headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			String headerValue = request.getHeader(headerName);
			requestLog.append(headerName).append("=").append(headerValue).append(",");
		}
		
		requestLog.append("]");
		
		//logger.info(requestLog.toString());
	}
}











