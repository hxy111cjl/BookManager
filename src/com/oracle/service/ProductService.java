package com.oracle.service;

import java.util.List;

import com.oracle.entity.PageBean;
import com.oracle.entity.Product;
import com.oracle.mapper.ProductMapper;

public class ProductService {
	private ProductMapper dao = new ProductMapper();

	// 添加商品
	public int addProduct(Product p) {
		return dao.addProduct(p);
	}

	// 查找所有商品信息
	public List<Product> listAll() {
		return dao.listAll();
	}

	// 分页操作
	public PageBean findProductByPage(int currentPage, int currentCount, String category) {
		PageBean bean = new PageBean();
		// 封装每页显示数据条数
		bean.setCurrentCount(currentCount);
		// 封装当前页码
		bean.setCurrentPage(currentPage);
		// 封装当前查找类别
		bean.setCategory(category);
		// 获取总条数
		int totalCount = dao.findAllCount(category);
		bean.setTotalCount(totalCount);
		// 获取总页数
		int totalPage = (int) Math.ceil(totalCount * 1.0 / currentCount);
		bean.setTotalPage(totalPage);
		// 获取当前页数据
		List<Product> ps = dao.findByPage(currentPage, currentCount, category);
		bean.setPs(ps);
		return bean;
	}

	// 根据id查找商品
	public Product findProductById(String id) {
		return dao.findProductById(id);

	}

	// 下载销售榜单
	public List<Object[]> download(String year, String month) {
		return dao.salesList(year, month);
	}

	// 多条件查询
	public List<Product> findProductByManyCondition(String id, String name, String category, String minprice,
			String maxprice) {
		return dao.findProductByManyCondition(id, name, category, minprice, maxprice);
	}

	// 修改商品信息
	public int editProduct(Product p) {
		return dao.editProduct(p);
	}

	// 前台，获取本周热销商品
	public List<Object[]> getWeekHotProduct() {
		return dao.getWeekHotProduct();
	}

	// 前台，用于搜索框根据书名来模糊查询相应的图书
	public PageBean findBookByName(int currentPage, int currentCount, String searchfield) {
		PageBean bean = new PageBean();
		// 封装每页显示数据条数
		bean.setCurrentCount(currentCount);
		// 封装当前页码
		bean.setCurrentPage(currentPage);
		// 封装模糊查询的图书名
		bean.setSearchfield(searchfield);
		// 获取总条数
		int totalCount = dao.findBookByNameAllCount(searchfield);
		bean.setTotalCount(totalCount);

		// 获取总页数
		int totalPage = (int) Math.ceil(totalCount * 1.0 / currentCount);
		bean.setTotalPage(totalPage);

		// 满足条件的图书
		List<Product> ps = dao.findBookByName(currentPage, currentCount, searchfield);
		bean.setPs(ps);
		return bean;
	}

	// 后台系统，根据id删除商品信息
	public int deleteProduct(String id) {
		return dao.deleteProduct(id);
	}
}
