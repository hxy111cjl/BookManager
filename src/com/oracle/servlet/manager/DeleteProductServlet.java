package com.oracle.servlet.manager;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oracle.service.ProductService;

/**
 * 后台系统
 * 删除商品信息的servlet
 */
@WebServlet("/deleteProduct")
public class DeleteProductServlet extends HttpServlet {
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取请求参数，产品id
		String id = request.getParameter("id");
		ProductService service = new ProductService();
		// 调用service完成添加商品操作
		service.deleteProduct(id);
		response.sendRedirect(request.getContextPath() + "/listProduct");
 	}

}
