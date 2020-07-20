package com.oracle.mapper;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.ibatis.annotations.Param;

import com.oracle.entity.Order;
import com.oracle.entity.OrderItem;
import com.oracle.entity.Product;
import com.oracle.utils.DataSourceUtils;

public interface ProductMapper {
	// 添加商品
	public int addProduct(Product product);
	
	// 查找所有商品
	public List<Product> listAll() ;
	// 获取数据总条数
	public int findAllCount(String category); 
	// 获取当前页数据   (currentPage - 1) * currentCount,currentCount
	public List<Product> findByPage(@Param("currentPage") int currentPage,@Param("currentCount") int currentCount,@Param("category") String category);
	// 根据id查找商品
	public Product findProductById(String id);

	// 生成订单时，将商品数量减少
	public void changeProductNum(List<Order> item);

	// 销售榜单
	public List<Object[]> salesList(@Param("year") String year,@Param("month") String month);
	

	// 多条件查询
	public List<Product> findProductByManyCondition(@Param("id") String id,@Param("name") String name,@Param("category") String category,@Param("minprice") String minprice,@Param("maxprice") String maxprice);
	// 修改商品信息
	public int editProduct(Product p);
	//删除订单时，修改商品数量
	public int[] updateProductNum(List<OrderItem> items);

	//前台，获取本周热销商品
	/*public List<Object[]> getWeekHotProduct(){
		try {
			String sql = "SELECT products.id,products.name, "+
                    " products.imgurl,SUM(orderitem.buynum) totalsalnum "+
            " FROM orderitem,orders,products "+
            " WHERE orderitem.order_id = orders.id "+
                    " AND products.id = orderitem.product_id "+
                    " AND orders.paystate=1 "+
                    " AND orders.ordertime > DATE_SUB(NOW(), INTERVAL 7 DAY) "+
            " GROUP BY products.id,products.name,products.imgurl "+
            " ORDER BY totalsalnum DESC "+
            " LIMIT 0,2 ";
			QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
			return runner.query(sql, new ArrayListHandler());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	//前台，用于搜索框根据书名来模糊查询相应的图书
	public List<Product> findBookByName(int currentPage, int currentCount,String searchfield)  {
		try {
			//根据名字模糊查询图书
			String sql = "SELECT * FROM products WHERE name LIKE '%"+searchfield+"%' LIMIT ?,?";
			QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
			return runner.query(sql,new BeanListHandler<Product>(Product.class),currentPage-1,currentCount);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	//前台搜索框，根据书名模糊查询出的图书总数量
	public Integer findBookByNameAllCount(String searchfield)  {
		//查询出满足条件的总数量，为long类型
 		try {
			String sql = "SELECT COUNT(*) FROM products WHERE name LIKE '%"+searchfield+"%'";
			QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
			return  (Integer)runner.query(sql, new ScalarHandler());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	//后台系统，根据id删除商品信息
	public int deleteProduct(String id)  {
		try {
			String sql = "DELETE FROM products WHERE id = ?";
			QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
			return runner.update(sql, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}*/
}
