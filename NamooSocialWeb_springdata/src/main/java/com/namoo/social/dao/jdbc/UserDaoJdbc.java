package com.namoo.social.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.namoo.social.dao.UserDao;
import com.namoo.social.domain.User;

@Repository
public class UserDaoJdbc implements UserDao {
	//
	@Autowired
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<User> readAllUsers() {
		// 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<User> users = new ArrayList<User>();
		
		try {
			conn = dataSource.getConnection();
			
			StringBuffer sb = new StringBuffer(512);
			sb.append("SELECT a.user_id, a.user_nm, a.email, a.password ");
			sb.append(" FROM user_tb a");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				users.add(mapToUser(rs));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error readUsers.", e);
		} finally {
			JdbcUtils.closeQuietly(rs, pstmt, conn);
		}
		return users;
	}

	@Override
	public User readUser(String userId) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;
		
		try {
			conn = dataSource.getConnection();
			
			StringBuffer sb = new StringBuffer(512);
			sb.append("SELECT a.user_id, a.user_nm, a.email, a.password ");
			sb.append("FROM user_tb a ");
			sb.append("WHERE a.user_id = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				user = mapToUser(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error readUser.", e);
		} finally {
			JdbcUtils.closeQuietly(rs, pstmt, conn);
		}
		return user;
	}

	@Override
	public void insertUser(User user) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			
			StringBuffer sb = new StringBuffer(512);
			sb.append("INSERT INTO user_tb (user_id, user_nm, email, password) ");
			sb.append("VALUES (?, ?, ?, ?)");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getName());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getPassword());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error insertUser.", e);
		} finally {
			JdbcUtils.closeQuietly(pstmt, conn);
		}
	}

	@Override
	public void updateUser(User user) {
		// 
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			
			StringBuffer sb = new StringBuffer(512);
			sb.append("UPDATE user_tb ");
			sb.append("SET user_nm = ?, email = ?, password = ? ");
			sb.append("WHERE user_id = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getUserId());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error updateUser.", e);
		} finally {
			JdbcUtils.closeQuietly(pstmt, conn);
		}
		
	}

	@Override
	public void deleteUser(String userId) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			
			StringBuffer sb = new StringBuffer(512);
			sb.append("DELETE FROM user_tb WHERE user_id = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error deleteUser.", e);
		} finally {
			JdbcUtils.closeQuietly(pstmt, conn);
		}
	}
	
	//--------------------------------------------------------------------------
	
	@Override
	public List<User> readFollowings(String userId) {
		// 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<User> users = new ArrayList<User>();
		
		try {
			conn = dataSource.getConnection();
			
			StringBuffer sb = new StringBuffer(512);
			sb.append("SELECT b.user_id, b.user_nm, b.email, b.password ");
			sb.append("FROM usertouser_tb a ");
			sb.append("JOIN user_tb b ON a.to_id = b.user_id ");
			sb.append("WHERE a.from_id = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				users.add(mapToUser(rs));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error readFollowings.", e);
		} finally {
			JdbcUtils.closeQuietly(rs, pstmt, conn);
		}
		return users;
	}

	@Override
	public List<User> readFollowers(String userId) {
		// 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<User> users = new ArrayList<User>();
		
		try {
			conn = dataSource.getConnection();
			
			StringBuffer sb = new StringBuffer(512);
			sb.append("SELECT b.user_id, b.user_nm, b.email, b.password ");
			sb.append("FROM usertouser_tb a ");
			sb.append("JOIN user_tb b ON a.from_id = b.user_id ");
			sb.append("WHERE a.to_id = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				users.add(mapToUser(rs));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error readFollowers.", e);
		} finally {
			JdbcUtils.closeQuietly(rs, pstmt, conn);
		}
		return users;
	}
	
	@Override
	public void insertRelationship(String fromId, String toId) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			
			StringBuffer sb = new StringBuffer(512);
			sb.append("INSERT INTO usertouser_tb (from_id, to_id) ");
			sb.append("VALUES (?, ?)");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, fromId);
			pstmt.setString(2, toId);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error insertRelationship.", e);
		} finally {
			JdbcUtils.closeQuietly(pstmt, conn);
		}
	}
	
	@Override
	public void deleteRelationship(String fromId, String toId) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			
			StringBuffer sb = new StringBuffer(512);
			sb.append("DELETE FROM usertouser_tb ");
			sb.append("WHERE from_id = ? AND to_id = ? ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, fromId);
			pstmt.setString(2, toId);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error deleteRelationship.", e);
		} finally {
			JdbcUtils.closeQuietly(pstmt, conn);
		}		
	}
	
	@Override
	public void deleteAllRelationship(String fromId) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			
			StringBuffer sb = new StringBuffer(512);
			sb.append("DELETE FROM usertouser_tb ");
			sb.append("WHERE from_id = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, fromId);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error deleteAllRelationship.", e);
		} finally {
			JdbcUtils.closeQuietly(pstmt, conn);
		}
	}
	
	//--------------------------------------------------------------------------

	/**
	 * ResultSet에서 값을 추출하여 User객체를 생성한다.
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private User mapToUser(ResultSet rs) throws SQLException {
		// 
		User user = new User();
		user.setUserId(rs.getString("user_id"));
		user.setName(rs.getString("user_nm"));
		user.setPassword(rs.getString("password"));
		user.setEmail(rs.getString("email"));
		
		return user;
	}
}
