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
	//�̱������� ����
	private static MessageDao messageDao = new MessageDao();
	
	//������ ���� ���� �޼���
	public static MessageDao getInstance() {
		return messageDao;
	}
	
	private MessageDao() {}
	
	//������ ����(C)
	public int insert(Connection conn, Message message) throws SQLException{
		PreparedStatement pstmt = null;
		int result=0;
		try {
			pstmt = conn.prepareStatement("INSERT INTO guestbook_message"
						+ "(guest_name, password, message) values (?, ?, ?)");
			pstmt.setString(1, message.getGuestName());
			pstmt.setString(2, message.getPassword());
			pstmt.setString(3, message.getMessage());
			
			//������ �ݿ��� ���ڵ��� �Ǽ��� ��ȯ���� (int)
			//create, drop ���� ���������� -1�� ��ȯ��
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
	//������ ��ȸ(R)
	public Message select(Connection conn, int messageId) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Message message = null;
		try {
			pstmt = conn.prepareStatement("SELECT * FROM guestbook_message WHERE message_id=?");
			pstmt.setInt(1, messageId);
			//���� ���� �� ��ȯ�Ǵ� ������� rs�� �����
			rs = pstmt.executeQuery();
			if(rs.next()) {
				message = makeMessageFromResultSet(rs);
			}else {
				throw new Exception("�ش� ��ȣ�� �޽����� ã�� �� �����ϴ�.");
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
	//������ ��ȸ(���õ� ����)
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
	//������ ��ȸ(selectList�� ��ȸ)
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
	//������ ���� (D)
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

