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
import org.springframework.util.StringUtils;

import com.namoo.social.dao.UserDao;
import com.namoo.social.domain.User;
import com.namoo.social.shared.exception.NamooSocialExceptionFactory;

@Repository
public class UserDaoJdbc implements UserDao {
	//
	@Autowired
	private DataSource dataSource;
	
	@Override
	public List<User> readAllUsers() {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		List<User> users = new ArrayList<User>();
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT user_id, name, email, password, img_type, img_file FROM user_tb";
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				users.add(mapToUser(rset));
			}
		} catch(SQLException e) {
			e.printStackTrace();
			NamooSocialExceptionFactory.createRuntime("모든 사용자 조회중 오류발생");
		} finally {
			closeQueitly(rset, pstmt, conn);
		}
		return users;
	}

	@Override
	public User readUser(String userId) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		User user = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT user_id, name, email, password, img_type, img_file FROM user_tb WHERE user_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				user = mapToUser(rset);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			NamooSocialExceptionFactory.createRuntime("사용자 아이디로 사용자 조회중 오류 발생");
		} finally {
			closeQueitly(rset, pstmt, conn);
		}
		return user;
	}

	@Override
	public void createUser(User user) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "INSERT INTO user_tb(user_id, name, email, password, img_type, img_file) VALUES(?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getName());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getPassword());
			
			 if(user.getProfileImage() != null) {
				 pstmt.setString(5, user.getProfileImage().getContentType());
				 pstmt.setString(6, user.getProfileImage().getFileName());
			 } else {
				 pstmt.setString(5, "");
				 pstmt.setString(6, "");
			 }
			
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			NamooSocialExceptionFactory.createRuntime("사용자 생성 중 오류 발생");
		} finally {
			closeQueitly(pstmt, conn);
		}
	}

	@Override
	public void updateUser(User user) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "UPDATE user_tb SET name=?, email=?, password=?, img_type=?, img_file=? WHERE user_id=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getPassword());
			

			 if(user.getProfileImage() != null) {
				 pstmt.setString(4, user.getProfileImage().getContentType());
				 pstmt.setString(5, user.getProfileImage().getFileName());
			 } else {
				 pstmt.setString(4, "");
				 pstmt.setString(5, "");
			 }
			
			 pstmt.setString(6, user.getUserId());
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			NamooSocialExceptionFactory.createRuntime("사용자 정보 업데이트 중 오류 발생");
		} finally {
			closeQueitly(pstmt, conn);
		}
	}

	@Override
	public void deleteUser(String userId) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "DELETE FROM user_tb WHERE user_id=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			NamooSocialExceptionFactory.createRuntime("사용자 삭제 중 오류 발생");
		} finally {
			closeQueitly(pstmt, conn);
		}
	}

	@Override
	public List<User> readFollowings(String userId) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		List<User> users = new ArrayList<User>();
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT a.whom, b.name FROM usertouser_tb a "+
			"INNER JOIN user_tb b ON a.whom=b.user_id WHERE a.who=? ORDER BY a.whom";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				User user = new User(rset.getString("whom"), rset.getString("name"));
				
				users.add(user);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			NamooSocialExceptionFactory.createRuntime("특정 사용자의 팔로잉목록을 조회하던 중 오류 발생");
		} finally {
			closeQueitly(rset, pstmt, conn);
		}

		return users;
	}
	
	@Override
	public List<User> readFollowers(String userId) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		List<User> users = new ArrayList<User>();
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT a.who, b.name FROM usertouser_tb a "+
			"INNER JOIN user_tb b ON a.who=b.user_id WHERE a.whom=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				User user = new User(rset.getString("who"), rset.getString("name"));
				
				users.add(user);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			NamooSocialExceptionFactory.createRuntime("특정 사용자의 팔로워목록을 조회하던 중 오류 발생");
		} finally {
			closeQueitly(rset, pstmt, conn);
		}
		return users;
	}
	
	@Override
	public void createFollowing(String who, String whom) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "INSERT INTO usertouser_tb(who, whom) VALUES(?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, who);
			pstmt.setString(2, whom);
			
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			NamooSocialExceptionFactory.createRuntime("팔로잉 생성 중 오류 발생");
		} finally {
			closeQueitly(pstmt, conn);
		}
	}

	@Override
	public void deleteFollowing(String who, String whom) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "DELETE FROM usertouser_tb WHERE who=? AND whom=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, who);
			pstmt.setString(2, whom);
			
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			NamooSocialExceptionFactory.createRuntime("팔로잉 삭제 중 오류 발생");
		} finally {
			closeQueitly(pstmt, conn);
		}
	}

	//-------------------------------------------------------------------------------------------
	//private method
	
	/**
	 * DB 관련 자원을 종료한다.
	 * 
	 * @param rs 
	 * @param pstmt
	 * @param conn
	 */
	private void closeQueitly(AutoCloseable ...autoCloseables) {
		//
		for (AutoCloseable closeable : autoCloseables) {
			if (closeable != null) try { closeable.close(); } catch(Exception e) { }
		}
	}
	
	/**
	 * ResultSet을 User객체로 변환한다.
	 * 
	 * @param rset 조회결과
	 * @return {@link Recipe}
	 * @throws SQLException
	 */
	private User mapToUser(ResultSet rset) throws SQLException {
		//
		User user = new User();
		user.setEmail(rset.getString("email"));
		user.setName(rset.getString("name"));
		user.setPassword(rset.getString("password"));
		user.setUserId(rset.getString("user_id"));
		
		String imgType = rset.getString("img_type");
		String imgFile = rset.getString("img_file");
		if (!StringUtils.isEmpty(imgType) && !StringUtils.isEmpty(imgFile)) {
			//
			user.setProfileImage(new com.namoo.social.domain.ImageFile(imgType, imgFile));
		}
		
		return user;
	}
}
