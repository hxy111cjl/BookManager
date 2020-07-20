package com.oracle.service;

import java.sql.SQLException;
import java.util.List;

import com.oracle.entity.Order;
import com.oracle.entity.OrderItem;
import com.oracle.entity.User;
import com.oracle.mapper.OrderDao;
import com.oracle.mapper.OrderItemDao;
import com.oracle.mapper.ProductMapper;
import com.oracle.utils.DataSourceUtils;

public class OrderService {
	private OrderItemDao oidao = new OrderItemDao();
	private OrderDao odao = new OrderDao();
	private ProductMapper pdao = new ProductMapper();

	// 1.添加订单
	public boolean addOrder(Order order) {
		// 1.开启事务
		DataSourceUtils.startTransaction();
		// 2.完成操作
		// 2.1向orders表中添加数据
		int addProduct = odao.addProduct(order);
		// 2.2向orderItem表中添加数据
		int[] addOrderItem = oidao.addOrderItem(order);
		// 2.3修改商品表中数据.
		int[] changeProductNum = pdao.changeProductNum(order);
		if (addProduct != 0 && (addOrderItem != null && addOrderItem.length > 0)
				&& (changeProductNum != null && changeProductNum.length > 0)) {
			DataSourceUtils.releaseAndCloseConnection();
			return true;
		}
		DataSourceUtils.rollback();
		return false;

	}

	// 根据用户查找订单
	public List<Order> findOrderByUser(User user) {
		// 查找出订单信息
		return odao.findOrderByUser(user);
		// // 查找出订单项信息.
		// for (Order order : orders) {
		// List<OrderItem> items = oidao.findOrderItemByOrder(order);
		// //查找到订单中的订单项信息
		//
		// order.setOrderItems(items);
		// }
	}

	// 根据id查找订单
	public Order findOrderById(String id) {
		Order order = odao.findOrderById(id);
		List<OrderItem> items = oidao.findOrderItemByOrder(order);
		order.setOrderItems(items);
		return order;
	}

	// 查找所有订单
	public List<Order> findAllOrder() {
		// 查找出订单信息
		return odao.findAllOrder();
	}

	// 支付成功后修改订单状态
	public int updateState(String id) {
		return odao.updateOrderState(id);
	}

	// 多条件查询订单信息
	public List<Order> findOrderByManyCondition(String id, String receiverName) {
		return odao.findOrderByManyCondition(id, receiverName);

	}

	// 根据id删除订单 管理员删除订单
	public boolean delOrderById(String id) {
		DataSourceUtils.startTransaction();// 开启事务
		int delOrderItems = oidao.delOrderItems(id);
		int delOrderById = odao.delOrderById(id);
		if (delOrderItems > 0 && delOrderById > 0) {
			DataSourceUtils.releaseAndCloseConnection();
			return true;
		}
		DataSourceUtils.rollback();
		return false;
	}

	// 普通用户删除订单
	public boolean delOrderByIdWithClient(String id) {
		DataSourceUtils.startTransaction();// 开启事务
		// 从订单项中查找商品购买数量
		Order order = new Order();
		order.setId(id);
		List<OrderItem> items = oidao.findOrderItemByOrder(order);
		// 修改商品数量
		int[] updateProductNum = pdao.updateProductNum(items);
		int delOrderItems = oidao.delOrderItems(id); // 删除订单项
		int delOrderById = odao.delOrderById(id); // 删除订单
		if (delOrderItems != 0 && delOrderById != 0 && (updateProductNum != null && updateProductNum.length > 0)) {
			DataSourceUtils.releaseAndCloseConnection();
			return true;
		}
		DataSourceUtils.rollback();
		return false;

	}
}