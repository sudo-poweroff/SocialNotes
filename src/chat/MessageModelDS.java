package chat;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;



public class MessageModelDS {
	
	public MessageModelDS(DataSource ds) {
		this.ds=ds;
	}

	
	public Collection<MessageBean> doRetrieveByChatID(int chatID) throws SQLException {
		if(chatID<0)
			throw new NullPointerException();
		Connection con=null;
		PreparedStatement ps=null;
		String sql="SELECT * FROM Messaggio WHERE ChatID=? ORDER BY DataInvio;";
		Collection<MessageBean> messages=new LinkedList<MessageBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setInt(1, chatID);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				MessageBean bean=new MessageBean();
				bean.setIdMessaggio(rs.getInt("IDMessaggio"));
				bean.setTesto(rs.getString("Testo"));
				bean.setDataInvio(rs.getTimestamp("DataInvio"));
				bean.setUsername(rs.getString("Username"));
				bean.setChatID(rs.getInt("ChatID"));
				messages.add(bean);
			}
		}
		finally {
			try {
				if(ps!=null)
					ps.close();
			}
			finally {
				if(con!=null)
					con.close();
			}
		}
		return messages;
	}
	
	
	public Collection<MessageBean> doRetrieveLatestMessages(Timestamp orario,int chatID) throws SQLException {
		if(orario==null||chatID<0)
			throw new NullPointerException();
		Connection con=null;
		PreparedStatement ps=null;
		String sql="SELECT * FROM Messaggio WHERE DataInvio>? AND ChatID=? ORDER BY DataInvio;";
		Collection<MessageBean> messages=new LinkedList<MessageBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setTimestamp(1, orario);
			ps.setInt(2, chatID);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				MessageBean bean=new MessageBean();
				bean.setIdMessaggio(rs.getInt("IDMessaggio"));
				bean.setTesto(rs.getString("Testo"));
				bean.setDataInvio(rs.getTimestamp("DataInvio"));
				bean.setUsername(rs.getString("Username"));
				bean.setChatID(rs.getInt("ChatID"));
				messages.add(bean);
			}
		}
		finally {
			try {
				if(ps!=null)
					ps.close();
			}
			finally {
				if(con!=null)
					con.close();
			}
		}
		return messages;
	}
		
	
	public void doSave(MessageBean item) throws SQLException {
		if(item==null)
			throw new NullPointerException();
		Connection connection = null;
		PreparedStatement ps = null;

		String sql = "INSERT INTO Messaggio (Testo,DataInvio,Username,ChatID)  values (?,?,?,?);";
		
		try {
			connection = ds.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, item.getTesto());
			ps.setTimestamp(2, item.getDataInvio());
			ps.setString(3, item.getUsername());
			ps.setInt(4, item.getChatID());
		

			ps.executeUpdate();
			//System.out.println("Messaggio inserito");
		} finally {
			try {
				if(ps!=null)
					ps.close();
			}
			finally {
				if(connection!=null)
					connection.close();
			}
		}
	}
	

	private DataSource ds;
}
