package profilo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.unisa.utils.Utility;

public class FriendsModelDS {

	public FriendsModelDS(DataSource ds) {
		this.ds=ds;
	}


	public void doSave(FriendsBean item) throws SQLException {
		if(item==null)
			throw new NullPointerException();
		Connection con=null;
		PreparedStatement ps=null;
		String sql="INSERT INTO Amicizia (Username1,Username2,DataInizio) values (?,?,?);";
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, item.getUsername1());
			ps.setString(2, item.getUsername2());
			ps.setDate(3, item.getDataInizio());
			ps.executeUpdate();
			System.out.println("Amicizia Stretta");
		}finally {
			try {
				if(ps!=null)
					ps.close();
			}
			finally {
				if(con!=null)
					con.close();

			}
		}

	}


	public void doDeleteFriend(String friend,String user) throws SQLException{
		if(friend==null||friend.equals("")||user==null||user.equals(""))
			throw new NullPointerException();
		Connection con=null;
		PreparedStatement ps=null;
		String sql="DELETE FROM Amicizia WHERE (Username1=? AND Username2=?) OR (Username1=? AND Username2=?);";
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, friend);
			ps.setString(2, user);
			ps.setString(3, user);
			ps.setString(4, friend);
			ps.executeUpdate();
			System.out.println("Amico eliminato");
		}finally {
			try {
				if(ps!=null)
					ps.close();
			}
			finally {
				if(con!=null)
					con.close();
			}
		}
	}


	public boolean isFriend(String friend,String user)throws SQLException{
		if(friend==null||friend.equals("")||user==null||user.equals(""))
			throw new NullPointerException();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="SELECT Username1 FROM Amicizia WHERE (Username1=? AND Username2=?) OR (Username1=? AND Username2=?);";
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, friend);
			ps.setString(2, user);
			ps.setString(3, user);
			ps.setString(4, friend);
			rs=ps.executeQuery();
			if(rs.next()) {
				System.out.println("Sono amici");
				return true;
			}

		}finally {
			try {
				if(rs!=null)
					rs.close();
				if(ps!=null)
					ps.close();
			}
			finally {
				if(con!=null)
					con.close();
			}
		}
		return false;
	}


	public int getNumberFriends(String username) throws SQLException{
		if(username==null||username.equals(""))
			throw new NullPointerException();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="SELECT count(*) as NumeroAmici FROM Amicizia WHERE Username1=? 	OR Username2=?";
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, username);
			rs=ps.executeQuery();
			if(rs.next()) 
				return rs.getInt("NumeroAmici");
			return -1;
		}finally {
			try {
				if(rs!=null)
					rs.close();
				if(ps!=null)
					ps.close();
			}
			finally {
				if(con!=null)
					con.close();
			}
		}
	}


	public Collection<FriendsBean> doRetrieveByUsername(String username) throws SQLException{
		if(username==null||username.equals(""))
			throw new NullPointerException();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="SELECT * FROM Amicizia WHERE Username1=? 	OR Username2=?  ORDER BY DataInizio desc LIMIT 10;";
		Collection<FriendsBean> friends=new LinkedList<FriendsBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			//Utility.print("doRetrieveAll:"+ps.toString());
			ps.setString(1, username);
			ps.setString(2, username);
			rs=ps.executeQuery();
			while(rs.next()) {
				FriendsBean bean=new FriendsBean();
				if(rs.getString("Username2").compareTo(username)==0) {
					bean.setUsername1(rs.getString("Username1"));
					bean.setUsername2(rs.getString("Username2"));
				}
				else {
					bean.setUsername1(rs.getString("Username2"));
					bean.setUsername2(rs.getString("Username1"));
				}
				bean.setDataInizio(rs.getDate("DataInizio"));
				friends.add(bean);
			}
		}
		finally {
			try {
				if(rs!=null)
					rs.close();
				if(ps!=null)
					ps.close();
			}
			finally {
				if(con!=null)
					con.close();
			}
		}
		return friends;

	}


	private DataSource ds;
}
