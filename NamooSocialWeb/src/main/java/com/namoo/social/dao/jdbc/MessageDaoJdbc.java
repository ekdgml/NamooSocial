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

import com.mysql.jdbc.Statement;
import com.namoo.social.dao.MessageDao;
import com.namoo.social.domain.Message;
import com.namoo.social.domain.User;
import com.namoo.social.shared.exception.NamooSocialExceptionFactory;

@Repository
public class MessageDaoJdbc implements MessageDao {
	//
	@Autowired
	DataSource dataSource;

	@Override
	public List<Message> readAllMessages(String userId) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		List<Message> messages = new ArrayList<Message>();
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT a.msg_id, a.contents, a.writer_id, a.reg_dt, b.name FROM message_tb a "
					+ "INNER JOIN user_tb b ON a.writer_id=b.user_id WHERE a.writer_id=? ORDER BY a.msg_id DESC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				Message message = new Message();
				message.setMessgeId(rset.getInt("msg_id"));
				message.setContents(rset.getString("contents"));
				message.setWriter(new User(userId, rset.getString("name")));
				message.setReg_dt(rset.getDate("reg_dt"));

				messages.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			NamooSocialExceptionFactory.createRuntime("특정 사용자의 모든 메시지를 읽어오던 중 오류 발생");
		} finally {
			closeQueitly(rset, pstmt, conn);
		}
		return messages;
	}

	@Override
	public Message readMessage(int msgId) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		Message message = null;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT a.msg_id, a.contents, a.writer_id, a.reg_dt, b.name FROM message_tb a "
					+ "INNER JOIN user_tb b ON a.writer_id=b.user_id WHERE a.msg_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, msgId);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				message = new Message();
				message.setContents(rset.getString("contents"));
				message.setWriter(new User(rset.getString("writer_id"), rset.getString("name")));
				message.setMessgeId(rset.getInt("msg_id"));
				message.setReg_dt(rset.getDate("reg_dt"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			NamooSocialExceptionFactory.createRuntime("메시지아이디로 메시지를 불러오던 중 오류 발생");
		} finally {
			closeQueitly(rset, pstmt, conn);
		}
		return message;
	}

	@Override
	public int createMessage(Message msg) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dataSource.getConnection();
			String sql = "INSERT INTO message_tb(contents, writer_id, reg_dt) VALUES(?, ?, sysdate())";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, msg.getContents());
			pstmt.setString(2, msg.getWriter().getUserId());

			pstmt.executeUpdate();

			rset = pstmt.getGeneratedKeys();
			if (rset.next()) {
				msg.setMessgeId(rset.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			NamooSocialExceptionFactory.createRuntime("메시지 생성 중 오류 발생");
		} finally {
			closeQueitly(rset, pstmt, conn);
		}
		return msg.getMessgeId();
	}

	@Override
	public void updateMessage(Message msg) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();
			String sql = "UPDATE message_tb SET contents=?, reg_dt=sysdate() WHERE msg_id=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, msg.getContents());
			pstmt.setInt(2, msg.getMessgeId());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			NamooSocialExceptionFactory.createRuntime("메시지 정보 업데이트 중 오류 발생");
		} finally {
			closeQueitly(pstmt, conn);
		}
	}

	@Override
	public void removeMessage(int msgId) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();
			String sql = "DELETE FROM message_tb WHERE msg_id=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, msgId);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			NamooSocialExceptionFactory.createRuntime("메시지 삭제 중 오류 발생");
		} finally {
			closeQueitly(pstmt, conn);
		}
	}

	@Override
	public List<Message> readTimeLine(String userId) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		List<Message> messages = new ArrayList<Message>();
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT a.msg_id, a.contents, a.writer_id, a.reg_dt, b.name FROM message_tb a "
					+ "INNER JOIN user_tb b ON a.writer_id=b.user_id WHERE writer_id=? OR "+
			             "writer_id IN (SELECT whom FROM usertouser_tb WHERE who=?) ORDER BY a.msg_id DESC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, userId);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				Message message = new Message();
				message.setMessgeId(rset.getInt("msg_id"));
				message.setContents(rset.getString("contents"));
				message.setWriter(new User(rset.getString("writer_id"), rset.getString("name")));
				message.setReg_dt(rset.getDate("reg_dt"));
				
				messages.add(message);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			NamooSocialExceptionFactory.createRuntime("특정 사용자의 모든 메시지를 읽어오던 중 오류 발생");
		} finally {
			closeQueitly(rset, pstmt, conn);
		}
		return messages;
	}

	// -------------------------------------------------------------------------------------------
	// private method

	/**
	 * DB 관련 자원을 종료한다.
	 * 
	 * @param rs
	 * @param pstmt
	 * @param conn
	 */
	private void closeQueitly(AutoCloseable... autoCloseables) {
		//
		for (AutoCloseable closeable : autoCloseables) {
			if (closeable != null)
				try {
					closeable.close();
				} catch (Exception e) {
				}
		}
	}
}
