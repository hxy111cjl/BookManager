package com.oracle.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import com.oracle.entity.Order;
import com.oracle.entity.OrderItem;
import com.oracle.entity.Product;
import com.oracle.utils.DataSourceUtils;

public class OrderItemDao {

	// 添加订单项
	public int[] addOrderItem(Order order)  {
		try {
			// 1.生成sql语句
			String sql = "insert into orderItem values(?,?,?)";
			QueryRunner runner = new QueryRunner();
			List<OrderItem> items = order.getOrderItems();
			Object[][] params = new Object[items.size()][3];
			for (int i = 0; i < params.length; i++) {
				params[i][0] = items.get(i).getOrder().getId();
				params[i][1] = items.get(i).getP().getId();
				params[i][2] = items.get(i).getBuynum();
			}
			return runner.batch(DataSourceUtils.getConnection(), sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	// 根据订单查找订单项.并将订单项中商品查找到。
	public List<OrderItem> findOrderItemByOrder(final Order order)
			 {
		String sql = "select * from orderItem,Products where products.id=orderItem.product_id and order_id=?";
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		try {
			return runner.query(sql, new ResultSetHandler<List<OrderItem>>() {
				public List<OrderItem> handle(ResultSet rs)  {

					List<OrderItem> items = new ArrayList<OrderItem>();
					try {
						while (rs.next()) {
							OrderItem item = new OrderItem();

							item.setOrder(order);
							item.setBuynum(rs.getInt("buynum"));

							Product p = new Product();
							p.setCategory(rs.getString("category"));
							p.setId(rs.getString("id"));
							p.setDescription(rs.getString("description"));
							p.setImgurl(rs.getString("imgurl"));
							p.setName(rs.getString("name"));
							p.setPnum(rs.getInt("pnum"));
							p.setPrice(rs.getDouble("price"));
							item.setP(p);
							items.add(item);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return items;
				}
			}, order.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//根据订单id删除订单项
	public int delOrderItems(String id)  {
		try {
			String sql="delete from orderItem where order_id=?";
			QueryRunner runner=new QueryRunner();
			return	runner.update(DataSourceUtils.getConnection(),sql,id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
