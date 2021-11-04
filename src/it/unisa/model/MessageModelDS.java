package it.unisa.model;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;


public class MessageModelDS implements Model<MessageBean> {
	
	public MessageModelDS(DataSource ds) {
		this.ds=ds;
	}

	@Override
	public MessageBean doRetrieveByKey(String code) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<MessageBean> doRetrieveAll() throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM Messaggio;";
		Collection<MessageBean> messages=new LinkedList<MessageBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
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
	
	
	public Collection<MessageBean> doRetrieveByChatID(int chatID) throws SQLException {
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
	

	public int doRetrieveNumberOfMessagesUnread(String username) throws SQLException{
		Connection con=null;
		PreparedStatement ps=null;
		String sql="SELECT count(*) as numeroMessaggiNonLetti FROM Utente,Partecipazione,Messaggio WHERE Utente.Username=Partecipazione.Username AND Partecipazione.ChatID=Messaggio.ChatID AND DataInvio>UltimoAccesso and Messaggio.chatID AND Partecipazione.Username=?;";
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				return rs.getInt("numeroMessaggiNonLetti");
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
		return 0;
	}
	
	
	@Override
	public void doSave(MessageBean item) throws SQLException {
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
	
	@Override
	public void doUpdate(MessageBean item) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doDelete(MessageBean item) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	private DataSource ds;
}
