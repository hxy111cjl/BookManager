package com.oracle.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import com.oracle.entity.Order;
import com.oracle.entity.User;
import com.oracle.utils.DataSourceUtils;
/**
 * 订单
 * @author admin
 *
 */
public class OrderDao {
	/**
	 *  生成订单
	 * @param order
	 * @
	 */
	public int addProduct(Order order)  {
		try {
			// 1.生成Sql语句
			String sql = "insert into orders values(?,?,?,?,?,0,null,?)";
			// 2.生成执行sql语句的QueryRunner,不传递参数
			QueryRunner runner = new QueryRunner();
	        // 3.执行update()方法插入数据
			return runner.update(DataSourceUtils.getConnection(), sql, order.getId(),order.getMoney(), order.getReceiverAddress(), order.getReceiverName(), order.getReceiverPhone(), order.getUser().getId());
		} catch (SQLException e) {
 			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 查找用户所属订单
	 */
	public List<Order> findOrderByUser(final User user)  {
		String sql = "select * from orders where user_id=?";
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		try {
			return runner.query(sql, new ResultSetHandler<List<Order>>() {
				public List<Order> handle(ResultSet rs)  {
					List<Order> orders = new ArrayList<Order>();
					try {
						while (rs.next()) {
							Order order = new Order();
							order.setId(rs.getString("id"));
							order.setMoney(rs.getDouble("money"));
							order.setOrdertime(rs.getDate("ordertime"));
							order.setPaystate(rs.getInt("paystate"));
							order.setReceiverAddress(rs.getString("receiverAddress"));
							order.setReceiverName(rs.getString("receiverName"));
							order.setReceiverPhone(rs.getString("receiverPhone"));
							order.setUser(user);
							orders.add(order);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return orders;
				}
			}, user.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据id查找订单信息
	 * @param id
	 * @return
	 * @
	 */
	public Order findOrderById(String id)  {
		String sql = "select * from orders,user where orders.user_id=user.id and orders.id=?";
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		try {
			return runner.query(sql, new ResultSetHandler<Order>() {
				public Order handle(ResultSet rs)  {
					Order order = new Order();
					try {
						while (rs.next()) {
							order.setId(rs.getString("orders.id"));
							order.setMoney(rs.getDouble("orders.money"));
							order.setOrdertime(rs.getDate("orders.ordertime"));
							order.setPaystate(rs.getInt("orders.paystate"));
							order.setReceiverAddress(rs.getString("orders.receiverAddress"));
							order.setReceiverName(rs.getString("orders.receiverName"));
							order.setReceiverPhone(rs.getString("orders.receiverPhone"));
							User user = new User();
							user.setId(rs.getInt("user.id"));
							user.setEmail(rs.getString("user.email"));
							user.setGender(rs.getString("user.gender"));
							user.setIntroduce(rs.getString("user.introduce"));
							user.setPassword(rs.getString("user.password"));
							user.setRegistTime(rs.getDate("user.registtime"));
							user.setRole(rs.getInt("user.role"));
							user.setTelephone(rs.getString("user.telephone"));
							user.setUsername(rs.getString("user.username"));
							order.setUser(user);
						}
					} catch (SQLException e) {
 						e.printStackTrace();
					}
					return order;
				}
			}, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *  查找所有订单
	 * @return
	 * @
	 */
	public List<Order> findAllOrder()  {
		//1.创建sql
		String sql = "select orders.*,user.* from orders,user where user.id=orders.user_id order by orders.user_id";
		//2.创建QueryRunner对象
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        //3.返回QueryRunner对象query()方法的查询结果
		try {
			return runner.query(sql, new ResultSetHandler<List<Order>>() {			
				public List<Order> handle(ResultSet rs)  {
					//创建订单集合
					List<Order> orders = new ArrayList<Order>();
			        //循环遍历订单和用户信息
					try {
						while (rs.next()) {
							Order order = new Order();
							order.setId(rs.getString("orders.id"));
							order.setMoney(rs.getDouble("orders.money"));
							order.setOrdertime(rs.getDate("orders.ordertime"));
							order.setPaystate(rs.getInt("orders.paystate"));
							order.setReceiverAddress(rs.getString("orders.receiverAddress"));
							order.setReceiverName(rs.getString("orders.receiverName"));
							order.setReceiverPhone(rs.getString("orders.receiverPhone"));
							orders.add(order);

							User user = new User();
							user.setId(rs.getInt("user.id"));
							user.setEmail(rs.getString("user.email"));
							user.setGender(rs.getString("user.gender"));
							user.setIntroduce(rs.getString("user.introduce"));
							user.setPassword(rs.getString("user.password"));
							user.setRegistTime(rs.getDate("user.registtime"));
							user.setRole(rs.getInt("user.role"));
							user.setTelephone(rs.getString("user.telephone"));
							user.setUsername(rs.getString("user.username"));
							order.setUser(user);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return orders;
				}
			});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *  根据订单号修改订单状态
	 * @param id
	 * @
	 */
	public int updateOrderState(String id)  {
		try {
			String sql = "update orders set paystate=1 where id=?";
			QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
			return	runner.update(sql, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
 		return 0;
	}
	/**
	 *  多条件查询
	 * @param id
	 * @param receiverName
	 * @return
	 * @
	 */
	public List<Order> findOrderByManyCondition(String id, String receiverName) {
		//1.创建集合对象
		List<Object> objs = new ArrayList<Object>();
		//2.定义查询sql
		String sql = "select orders.*,user.* from orders,user where user.id=orders.user_id ";
		//3.根据参数拼接sql语句
		if (id != null && id.trim().length() > 0) {
			sql += " and orders.id=?";
			objs.add(id);
		}
		if (receiverName != null && receiverName.trim().length() > 0) {
			sql += " and receiverName=?";
			objs.add(receiverName);
		}
		sql += " order by orders.user_id";
		//4.创建QueryRunner对象
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		//5.返回QueryRunner对象query方法的执行结果
		try {
			return runner.query(sql, new ResultSetHandler<List<Order>>() {
				public List<Order> handle(ResultSet rs)  {
					List<Order> orders = new ArrayList<Order>();
			       //循环遍历出订单和用户信息
					try {
						while (rs.next()) {
							Order order = new Order();
							order.setId(rs.getString("orders.id"));
							order.setMoney(rs.getDouble("orders.money"));
							order.setOrdertime(rs.getDate("orders.ordertime"));
							order.setPaystate(rs.getInt("orders.paystate"));
							order.setReceiverAddress(rs
									.getString("orders.receiverAddress"));
							order.setReceiverName(rs.getString("orders.receiverName"));
							order.setReceiverPhone(rs.getString("orders.receiverPhone"));
							orders.add(order);
							User user = new User();
							user.setId(rs.getInt("user.id"));
							user.setEmail(rs.getString("user.email"));
							user.setGender(rs.getString("user.gender"));
							user.setIntroduce(rs.getString("user.introduce"));
							user.setPassword(rs.getString("user.password"));
							user.setRegistTime(rs.getDate("user.registtime"));
							user.setRole(rs.getInt("user.role"));
							user.setTelephone(rs.getString("user.telephone"));
							user.setUsername(rs.getString("user.username"));
							order.setUser(user);

						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					return orders;
				}
			}, objs.toArray());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据id删除订单
	 * @param id
	 * @
	 */
	public int delOrderById(String id)  {
		String sql="delete from orders where id=?";		
		QueryRunner runner = new QueryRunner();		
		try {
			return runner.update(DataSourceUtils.getConnection(),sql,id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;		
	}
}
