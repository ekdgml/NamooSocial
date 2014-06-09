package com.namoo.social.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.namoo.social.dao.MessageDao;
import com.namoo.social.domain.Message;
import com.namoo.social.domain.User;

@Repository
public class MessageDaoJdbc implements MessageDao {
	//
	@Autowired
	private DataSource dataSource;
	
	@Override
	public List<Message> readMessages(String userId) {
		// 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Message> messages = new ArrayList<Message>();
		
		try {
			conn = dataSource.getConnection();
			
			StringBuffer sb = new StringBuffer(512);
			sb.append("SELECT a.msg_id, a.contents, a.reg_dt, a.writer_id, b.user_nm, b.email ");
			sb.append("FROM message_tb a ");
			sb.append("JOIN user_tb b ON a.writer_id = b.user_id ");
			sb.append("WHERE a.writer_id = ? ");
			sb.append("ORDER BY a.reg_dt DESC");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				messages.add(mapToMessage(rs));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error readMessages.", e);
		} finally {
			JdbcUtils.closeQuietly(rs, pstmt, conn);
		}
		return messages;
	}

	@Override
	public List<Message> readRelatedMessages(String userId) {
		// 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Message> messages = new ArrayList<Message>();
		
		try {
			conn = dataSource.getConnection();
			
			StringBuffer sb = new StringBuffer(512);
			sb.append("SELECT a.msg_id, a.contents, a.reg_dt, a.writer_id, b.user_nm, b.email ");
			sb.append("FROM message_tb a ");
			sb.append("JOIN user_tb b ON a.writer_id = b.user_id ");
			sb.append("WHERE a.writer_id = ? OR a.writer_id IN ");
			sb.append("(SELECT to_id FROM usertouser_tb WHERE from_id = ?)");
			sb.append("ORDER BY a.reg_dt DESC");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);
			pstmt.setString(2, userId);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				messages.add(mapToMessage(rs));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error readRelatedMessages.", e);
		} finally {
			JdbcUtils.closeQuietly(rs, pstmt, conn);
		}
		return messages;
	}
	
	@Override
	public Message readMessage(int messageId) {
		// 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Message message = null;
		
		try {
			conn = dataSource.getConnection();
			
			StringBuffer sb = new StringBuffer(512);
			sb.append("SELECT a.msg_id, a.contents, a.reg_dt, a.writer_id, b.user_nm, b.email ");
			sb.append("FROM message_tb a ");
			sb.append("JOIN user_tb b ON a.writer_id = b.user_id ");
			sb.append("WHERE a.msg_id = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, messageId);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				message = mapToMessage(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error readMessage.", e);
		} finally {
			JdbcUtils.closeQuietly(rs, pstmt, conn);
		}
		return message;
	}

	@Override
	public int insertMessage(Message message) {
		// 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			
			StringBuffer sb = new StringBuffer(512);
			sb.append("INSERT INTO message_tb (contents, writer_id, reg_dt) ");
			sb.append("VALUES (?, ?, sysdate()) ");
			
			pstmt = conn.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, message.getContents());
			pstmt.setString(2, message.getWriter().getUserId());
			
			System.out.println("#####################################");
			System.out.println(message.getContents());
			
			pstmt.executeUpdate();
			
			// 자동생성 키 조회 및 세팅
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				message.setId(rs.getInt(1));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error insertMessage.", e);
		} finally {
			JdbcUtils.closeQuietly(rs, pstmt, conn);
		}
		return message.getId();
	}

	@Override
	public void updateMessage(Message message) {
		// 
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			
			StringBuffer sb = new StringBuffer(512);
			sb.append("UPDATE message_tb ");
			sb.append("SET contents = ? ");
			sb.append("WHERE msg_id = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, message.getContents());
			pstmt.setInt(2, message.getId());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error updateMessage.", e);
		} finally {
			JdbcUtils.closeQuietly(pstmt, conn);
		}
	}

	@Override
	public void deleteMessage(int messageId) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			
			StringBuffer sb = new StringBuffer(512);
			sb.append("DELETE FROM message_tb WHERE msg_id = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, messageId);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error deleteMessage.", e);
		} finally {
			JdbcUtils.closeQuietly(pstmt, conn);
		}
	}

	@Override
	public void deleteAllMessagesByUserId(String userId) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			
			StringBuffer sb = new StringBuffer(512);
			sb.append("DELETE FROM message_tb WHERE writer_id = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error deleteAllMessagesByUserId.", e);
		} finally {
			JdbcUtils.closeQuietly(pstmt, conn);
		}
	}

	//--------------------------------------------------------------------------
	
	/**
	 * ResultSet에서 값을 추출하여 Message 객체를 생성한다.
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Message mapToMessage(ResultSet rs) throws SQLException {
		// 
		Message message = new Message(rs.getInt("msg_id"));
		message.setContents(rs.getString("contents"));
		message.setRegDate(new Date(rs.getTimestamp("reg_dt").getTime()));
		
		User writer = new User(rs.getString("writer_id"));
		writer.setName(rs.getString("user_nm"));
		writer.setEmail(rs.getString("email"));
		message.setWriter(writer);
		
		return message;
	}
}
