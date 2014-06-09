package com.namoo.social.dao.jdbc;

public class JdbcUtils {
	//
	public static void closeQuietly(AutoCloseable ... closeables) {
		//
		for (AutoCloseable closeable : closeables) {
			if (closeable != null) try {
				closeable.close();
			} catch (Exception e) { }
		}
	}

}
