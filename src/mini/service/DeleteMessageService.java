package mini.service;

import java.sql.Connection;
import java.sql.SQLException;

import mini.DAO.MessageDao;
import mini.jdbc.ConnectionProvider;
import mini.jdbc.JdbcUtil;
import mini.vo.Message;

public class DeleteMessageService {

	private static DeleteMessageService instance = new DeleteMessageService();
	
	public DeleteMessageService getInstance() {
		return instance;
	}
	
	public DeleteMessageService() {}
	
	public void deleteMessage(int messageId, String password) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
		
			MessageDao messageDao = MessageDao.getInstance();
			Message message = messageDao.select(conn, messageId);
			if(message == null) {
				throw new MessageNotFoundException("메시지없음");
			}
			if(!message.matchPassword(password)) {
				throw new InvalidPasswordException("bad password");
			}
			messageDao.delete(conn, messageId);
			conn.commit();
		}catch(SQLException ex) {
			JdbcUtil.rollback(conn);
			throw new ServiceException("삭제 실패 : " + ex.getMessage(), ex);
		}catch(InvalidPasswordException | MessageNotFoundException ex) {
			JdbcUtil.rollback(conn);
			throw ex;
		}finally {
			JdbcUtil.close(conn);
		}
	}
}
