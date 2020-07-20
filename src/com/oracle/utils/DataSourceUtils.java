package com.oracle.utils;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * 数据源工具
 */
public class DataSourceUtils {
	private static DataSource dataSource = new ComboPooledDataSource();
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();

	public static DataSource getDataSource() {
		return dataSource;
	}
	/**
	 * 当DBUtils需要手动控制事务时，调用该方法获得一个连接
	 * @return
	 * @
	 */
	public static Connection getConnection()  {
		Connection con = tl.get();
		if (con == null) {
			try {
				con = dataSource.getConnection();
			} catch (SQLException e) {
 				e.printStackTrace();
			}
			tl.set(con);
		}
		return con;
	}
	/**
	 * 开启事务
	 * @
	 */
	public static void startTransaction()  {
		Connection con = getConnection();
		if (con != null)
			try {
				con.setAutoCommit(false);
			} catch (SQLException e) {
 				e.printStackTrace();
			}
	}
	/**
	 * 从ThreadLocal中释放并且关闭Connection,并结束事务
	 * @
	 */
	public static void releaseAndCloseConnection()  {
		Connection con = getConnection();
		if (con != null) {
			try {
				con.commit();
				tl.remove();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	/**
	 * 事务回滚
	 * @ 
	 */
	public static void rollback()  {
		Connection con = getConnection();
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
