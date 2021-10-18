package mini.service;

import java.sql.Connection;
import java.sql.SQLException;

import mini.DAO.MessageDao;
import mini.jdbc.ConnectionProvider;
import mini.jdbc.JdbcUtil;
import mini.vo.Message;

public class WriteMessageService {
	private static WriteMessageService instance = new WriteMessageService();
	
	public static WriteMessageService getInstance () {
		return instance;
	}
	
	private WriteMessageService() {
		
	}
	
	public void write(Message message) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			MessageDao messageDao = MessageDao.getInstance();
			messageDao.insert(conn, message);
		}catch(SQLException e) {
			throw new ServiceException("메시지 등록 실패 : "+ e.getMessage(), e);
		}finally {
			JdbcUtil.close(conn);
		}
	}
}
