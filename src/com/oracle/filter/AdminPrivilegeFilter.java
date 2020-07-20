package com.oracle.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oracle.entity.User;
/**
  * 使用注解标注过滤器
  * 属性filterName声明过滤器的名称,可选
  * 属性urlPatterns指定要过滤 的URL模式,也可使用属性value来声明.(指定要过滤的URL模式是必选属性)
 */
@WebFilter(filterName="adminPrivilegeFilter",urlPatterns="/admin/*")
public class AdminPrivilegeFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		// 1 强制转换
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		// 2判断是否具有权限
		User user = (User) request.getSession().getAttribute("user");

		if (user != null && user.getRole()==0) {
			// 3.放行
			chain.doFilter(request, response);
			return;
		}

		response.sendRedirect(request.getContextPath() + "/error/privilege.jsp");

	}

	public void destroy() {

	}

}
