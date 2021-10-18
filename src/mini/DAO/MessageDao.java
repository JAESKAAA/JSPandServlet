package mini.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mini.vo.Message;

public class MessageDao {
	//싱글톤으로 구현
	private static MessageDao messageDao = new MessageDao();
	
	//유일한 접근 가능 메서드
	public static MessageDao getInstance() {
		return messageDao;
	}
	
	private MessageDao() {}
	
	//데이터 삽입(C)
	public int insert(Connection conn, Message message) throws SQLException{
		PreparedStatement pstmt = null;
		int result=0;
		try {
			pstmt = conn.prepareStatement("INSERT INTO guestbook_message"
						+ "(guest_name, password, message) values (?, ?, ?)");
			pstmt.setString(1, message.getGuestName());
			pstmt.setString(2, message.getPassword());
			pstmt.setString(3, message.getMessage());
			
			//쿼리가 반영된 레코드의 건수를 반환해줌 (int)
			//create, drop 관련 쿼리에서는 -1을 반환함
			result = pstmt.executeUpdate();
		}catch(Exception e) {
		e.printStackTrace();	
		}finally {
			try {
				
				if (pstmt != null){
					pstmt.close();
					}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	//데이터 조회(R)
	public Message select(Connection conn, int messageId) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Message message = null;
		try {
			pstmt = conn.prepareStatement("SELECT * FROM guestbook_message WHERE message_id=?");
			pstmt.setInt(1, messageId);
			//쿼리 실행 후 반환되는 결과값을 rs에 담아줌
			rs = pstmt.executeQuery();
			if(rs.next()) {
				message = makeMessageFromResultSet(rs);
			}else {
				throw new Exception("해당 번호의 메시지를 찾을 수 없습니다.");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return message;
	}
	//데이터 조회(선택된 갯수)
	public int selectCount(Connection conn) throws SQLException{
		Statement stmt = null;
		ResultSet rs = null;
		int result =0;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT count(*) from guestbook_message");
			rs.next();
			result = rs.getInt(1);
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs !=null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	//데이터 조회(selectList로 조회)
	public List<Message> selectList(Connection conn, int firstRow, int endRow) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Message> messageList = new ArrayList<>();
		
		try {
			pstmt = conn.prepareStatement("SELECT * from guestbook_message"
											+ "order by message_id desc limit ?, ?");
			pstmt.setInt(1, firstRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				do {
					messageList.add(makeMessageFromResultSet(rs));
				}while(rs.next());

			}else {
				return Collections.emptyList();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return messageList;

	}
	//데이터 삭제 (D)
	public int delete(Connection conn, int messageId) throws SQLException {
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement("delete from guestbook_message where message_id = ?");
			pstmt.setInt(1, messageId);
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				
				if(pstmt != null) {
					pstmt.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	private Message makeMessageFromResultSet(ResultSet rs) throws SQLException{
		Message message = new Message();
		message.setId(rs.getInt("message_id"));
		message.setGuestName(rs.getString("guest_name"));
		message.setPassword(rs.getString("password"));
		message.setMessage(rs.getString("message"));
		return message;
	}
}

