package it.unisa.model;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;


public class UserModelDS implements Model<UserBean> {

	public UserModelDS(DataSource ds) {
		this.ds=ds;
	}

	@Override
	public UserBean doRetrieveByKey(String code) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	public UserBean checkLogin(String name,String password)throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		String sql="SELECT Username,Nome,Cognome,Img,Email,Pass,DataNascita,Matricola,UltimoAccesso,Coin,Ban,Denominazione,DipName, AES_DECRYPT(Pass,'despacito') as Password FROM Utente WHERE Email = ? OR Username=?";
		//System.out.println("name in usermodel "+name);
		//System.out.println("pass in usermodel "+password);
		UserBean bean=new UserBean();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, name);
			ResultSet rs=ps.executeQuery();

			if(rs.next()&&(rs.getString("Password").compareTo(password))==0) {
				System.out.println("Utente loggato");
				bean.setUsername(rs.getString("Username"));
				bean.setNome(rs.getString("Nome"));
				bean.setCognome(rs.getString("Cognome"));
				bean.setImg(rs.getBlob("Img"));
				bean.setEmail(rs.getString("Email"));
				bean.setPass(rs.getString("Pass"));
				bean.setDataNascita(rs.getDate("DataNascita"));
				bean.setMatricola(rs.getString("Matricola"));
				bean.setUltimoAccesso(rs.getTimestamp("UltimoAccesso"));
				bean.setCoin(rs.getInt("Coin"));
				bean.setBan(rs.getBoolean("Ban"));
				bean.setDenominazione(rs.getString("Denominazione"));
				bean.setDipName(rs.getString("DipName"));
			}
			else {
				System.out.println("Utente non loggato");
				return null;
			}
			if(rs!=null)
				rs.close();
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
		//System.out.println(bean.toString());
		return bean;
	}

	public UserBean doRetrieveByUsername(String name)throws SQLException{
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM Utente WHERE Username = ?";
		UserBean bean = new UserBean();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			ps.setString(1, name);	
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				bean.setUsername(rs.getString("Username"));
				bean.setNome(rs.getString("Nome"));
				bean.setCognome(rs.getString("Cognome"));
				bean.setImg(rs.getBlob("Img"));
				bean.setEmail(rs.getString("Email"));
				bean.setPass(rs.getString("Pass"));
				bean.setDataNascita(rs.getDate("DataNascita"));
				bean.setMatricola(rs.getString("Matricola"));
				bean.setUltimoAccesso(rs.getTimestamp("UltimoAccesso"));
				bean.setCoin(rs.getInt("Coin"));
				bean.setBan(rs.getBoolean("Ban"));
				bean.setDenominazione(rs.getString("Denominazione"));
				bean.setDipName(rs.getString("DipName"));
			}
			else
				return null;
			if(rs!=null)
				rs.close();
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
		return bean;
	}

	public UserBean doRetrieveByEmail(String Email)throws SQLException{
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM Utente WHERE Email = ?";
		UserBean bean = new UserBean();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			ps.setString(1, Email);	
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				bean.setUsername(rs.getString("Username"));
				bean.setNome(rs.getString("Nome"));
				bean.setCognome(rs.getString("Cognome"));
				bean.setImg(rs.getBlob("Img"));
				bean.setEmail(rs.getString("Email"));
				bean.setPass(rs.getString("Pass"));
				bean.setDataNascita(rs.getDate("DataNascita"));
				bean.setMatricola(rs.getString("Matricola"));
				bean.setUltimoAccesso(rs.getTimestamp("UltimoAccesso"));
				bean.setCoin(rs.getInt("Coin"));
				bean.setBan(rs.getBoolean("Ban"));
				bean.setDenominazione(rs.getString("Denominazione"));
				bean.setDipName(rs.getString("DipName"));
			}
			else
				return null;
			if(rs!=null)
				rs.close();

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

		return bean;
	}

	@Override
	public Collection<UserBean> doRetrieveAll() throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM Utente;";
		Collection<UserBean> users=new LinkedList<UserBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			//Utility.print("doRetrieveAll:"+ps.toString());
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				UserBean bean=new UserBean();
				bean.setUsername(rs.getString("Username"));
				bean.setNome(rs.getString("Nome"));
				bean.setCognome(rs.getString("Cognome"));
				bean.setImg(rs.getBlob("Img"));
				bean.setEmail(rs.getString("Email"));
				bean.setPass(rs.getString("Pass"));
				bean.setDataNascita(rs.getDate("DataNascita"));
				bean.setMatricola(rs.getString("Matricola"));
				bean.setUltimoAccesso(rs.getTimestamp("UltimoAccesso"));
				bean.setCoin(rs.getInt("Coin"));
				bean.setBan(rs.getBoolean("Ban"));
				bean.setDenominazione(rs.getString("Denominazione"));
				bean.setDipName(rs.getString("DipName"));
				users.add(bean);
			}
			if(rs!=null)
				rs.close();
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
		return users;
	}

	
	public Collection<UserBean> doRetrieveUsers() throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM Utente,Copre WHERE Utente.Username=Copre.Username AND IDRuolo!=1;";
		Collection<UserBean> users=new LinkedList<UserBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			//Utility.print("doRetrieveAll:"+ps.toString());
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				UserBean bean=new UserBean();
				bean.setUsername(rs.getString("Username"));
				bean.setNome(rs.getString("Nome"));
				bean.setCognome(rs.getString("Cognome"));
				bean.setImg(rs.getBlob("Img"));
				bean.setEmail(rs.getString("Email"));
				bean.setPass(rs.getString("Pass"));
				bean.setDataNascita(rs.getDate("DataNascita"));
				bean.setMatricola(rs.getString("Matricola"));
				bean.setUltimoAccesso(rs.getTimestamp("UltimoAccesso"));
				bean.setCoin(rs.getInt("Coin"));
				bean.setBan(rs.getBoolean("Ban"));
				bean.setDenominazione(rs.getString("Denominazione"));
				bean.setDipName(rs.getString("DipName"));
				users.add(bean);
			}
			if(rs!=null)
				rs.close();
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
		return users;
	}

	public Collection<UserBean>  doRetrieveByParametersUser(String str,String ratingOrder, int rating) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		PreparedStatement viewFeedbackmedia = null;
		PreparedStatement dropViewFeedbackmedia = null;
		PreparedStatement viewFeedbackuser = null;
		PreparedStatement dropViewFeedbackuser = null;

		String dropViewFeedbackmediaSQL = "DROP VIEW IF EXISTS FeedbackMedia;";
		String viewFeedbackmediaSQL = "CREATE VIEW FeedbackMedia AS\n"
				+ "Select CodiceMateriale, ROUND(AVG(Valutazione)) AS ValutazioneMedia\n"
				+ "FROM Feedback\n"
				+ "GROUP BY CodiceMateriale;";
		
		String dropViewFeedbackuserSQL = "DROP VIEW IF EXISTS FeedbackUser;";
		String viewFeedbackuserSQL = "CREATE VIEW FeedbackUser AS\n"
				+ "SELECT ROUND(AVG(ValutazioneMedia)) AS feedback, Utente.Username, Utente.Nome, Utente.Cognome,Utente.Denominazione, Utente.dipName, Img\n"
				+ "FROM Materiale LEFT JOIN FeedbackMedia ON Materiale.CodiceMateriale = FeedbackMedia.CodiceMateriale RIGHT JOIN Utente ON Materiale.Username = Utente.Username\n"
				+ "group by Utente.Username;";
		

		
		
		String selectSQL="SELECT feedback, Username, Nome, Cognome, Denominazione, dipName, Img FROM FeedbackUser;";
		
		if (rating == 0) {
		
		if ((ratingOrder.compareTo("DESC")==0)) {
		selectSQL="SELECT feedback, Username, Nome, Cognome, Denominazione, dipName, Img FROM FeedbackUser WHERE (Username LIKE ? OR Nome LIKE ? OR Cognome LIKE ?)  ORDER BY feedback DESC;";
		}
		if ((ratingOrder.compareTo("ASC")==0)) {
			selectSQL="SELECT feedback,Username, Nome, Cognome, Denominazione, dipName, Img FROM FeedbackUser WHERE (Username LIKE ? OR Nome LIKE ? OR Cognome LIKE ?) ORDER BY feedback ASC;";
			}
		if ((ratingOrder.compareTo("novalue")==0)) {
			selectSQL="SELECT feedback,Username, Nome, Cognome, Denominazione, dipName, Img FROM FeedbackUser WHERE (Username LIKE ? OR Nome LIKE ? OR Cognome LIKE ?);";
			}
		
		
		}else {
			

		
		if ((ratingOrder.compareTo("DESC")==0)) {
			selectSQL="SELECT feedback, Username, Nome, Cognome, Denominazione, dipName, Img FROM FeedbackUser WHERE (Username LIKE ? OR Nome LIKE ? OR Cognome LIKE ?) AND feedback = ? ORDER BY feedback DESC;";
			}
			if ((ratingOrder.compareTo("ASC")==0)) {
				selectSQL="SELECT feedback, Username, Nome, Cognome, Denominazione, dipName, Img FROM FeedbackUser WHERE (Username LIKE ? OR Nome LIKE ? OR Cognome LIKE ?) AND feedback = ? ORDER BY feedback ASC;";
				}


			if ((ratingOrder.compareTo("novalue")==0)) {
				selectSQL="SELECT feedback, Username, Nome, Cognome, Denominazione, dipName, Img FROM FeedbackUser WHERE (Username LIKE ? OR Nome LIKE ? OR Cognome LIKE ?) AND feedback = ? ORDER BY feedback;";
				}


		}

		Collection<UserBean> collectionBean=new LinkedList<UserBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);

            
		   dropViewFeedbackmedia = con.prepareStatement(dropViewFeedbackmediaSQL);
		   viewFeedbackmedia = con.prepareStatement(viewFeedbackmediaSQL);
		   dropViewFeedbackuser = con.prepareStatement(dropViewFeedbackuserSQL);
		   viewFeedbackuser = con.prepareStatement(viewFeedbackuserSQL);
		   
			if(rating!=0) {
				ps.setInt(4, rating);
				ps.setString(1, '%'+str+'%');
				ps.setString(2, '%'+str+'%');
				ps.setString(3, '%'+str+'%');
			}else {
				ps.setString(1, '%'+str+'%');
				ps.setString(2, '%'+str+'%');
				ps.setString(3, '%'+str+'%');
			}
			
			dropViewFeedbackmedia.execute();
			viewFeedbackmedia.execute();
			dropViewFeedbackuser.execute();
			viewFeedbackuser.execute();
			
			ResultSet rs=ps.executeQuery();

			while(rs.next()) {
				
							UserBean bean=new UserBean();
							
							bean.setUsername(rs.getString("Username"));
							bean.setNome(rs.getString("Nome"));
							bean.setCognome(rs.getString("Cognome"));
						    bean.setImg(rs.getBlob("Img"));
							bean.setDenominazione(rs.getString("Denominazione"));
							bean.setDipName(rs.getString("dipName"));
							
							
							collectionBean.add(bean);
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
		
		return collectionBean;
		
	}
	
	
	@Override
	public void doSave(UserBean item) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;

		String insertSQL = "INSERT INTO Utente (Username, Nome, Cognome, Email, Pass, DataNascita, UltimoAccesso, Coin, Denominazione, DipName,Ban) VALUES (?, ?, ?, ?, AES_ENCRYPT(?,'despacito'), ?, ?, ?, ?, ?,?)";

		try {
			connection = ds.getConnection();
			ps = connection.prepareStatement(insertSQL);
			ps.setString(1, item.getUsername());
			ps.setString(2, item.getNome());
			ps.setString(3, item.getCognome());
			ps.setString(4, item.getEmail());
			ps.setString(5, item.getPass());

			//	Date dataNascita = item.getDataNascita();

			ps.setDate(6, item.getDataNascita());
			ps.setTimestamp(7, item.getUltimoAccesso());
			ps.setInt(8, item.getCoin());
			ps.setString(9, item.getDenominazione());
			ps.setString(10, item.getDipName());
			ps.setBoolean(11, item.getBan());

			ps.executeUpdate();
			System.out.println("Salvato nel Database");
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
	public void doUpdate(UserBean item) throws SQLException {
		// TODO Auto-generated method stub

	}
	
	public void manageBan(String username,boolean ban) throws SQLException{
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "UPDATE Utente SET Ban = ? WHERE Username = ?";
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setBoolean(1, ban);
			ps.setString(2, username);
			ps.executeUpdate();
			System.out.println("Ban aggiornato");
		} finally {
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
	
	
	public void doUpdateCoin(String username,int coin)throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		String sql="UPDATE Utente SET Coin=? WHERE Username=?";
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setInt(1, coin);
			ps.setString(2, username);
			ps.executeUpdate();
			System.out.println("Coins aggiornati");
		}catch(Exception e) {
			e.printStackTrace();
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
	}
	
	
	public void doUpdatePassword(String username,String newPassword)throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;

		String sql = "UPDATE Utente SET Pass = AES_ENCRYPT(?,'despacito') WHERE Username = ?";
		
		try {
			connection = ds.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, newPassword);
			ps.setString(2, username);
			ps.executeUpdate();
			System.out.println("Password aggiornata");
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
	
	
	public void doUpdateEmail(String username,String newMail)throws SQLException{
		Connection connection = null;
		PreparedStatement ps = null;

		String sql = "UPDATE Utente SET  Email= ? WHERE Username = ?";
		
		try {
			connection = ds.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, newMail);
			ps.setString(2, username);
			ps.executeUpdate();
			System.out.println("Email aggiornata");
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
	
	
	public void doUpdateDepartment(String username,String newDipName,String newUniversity) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;

		String sql = "UPDATE Utente SET  dipName= ?,Denominazione=? WHERE Username = ?";
		
		try {
			connection = ds.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, newDipName);
			ps.setString(2, newUniversity);
			ps.setString(3, username);
			ps.executeUpdate();
			System.out.println("Dipartimento aggiornato");
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
	
	
	public void doUpdateImage(String username,InputStream image) throws SQLException{
		Connection connection = null;
		PreparedStatement ps = null;

		String sql = "UPDATE Utente SET  Img=? WHERE Username = ?";
		
		try {
			connection = ds.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setBlob(1, image);
			ps.setString(2, username);
			ps.executeUpdate();
			System.out.println("Immagine di profilo aggiornata");
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
	
	
	public void doUpdateUltimoAccesso(String username,Timestamp ultimoAccesso)throws SQLException{
		Connection connection = null;
		PreparedStatement ps = null;

		String sql = "UPDATE Utente SET  UltimoAccesso=? WHERE Username = ?";
		
		try {
			connection = ds.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setTimestamp(1, ultimoAccesso);
			ps.setString(2, username);
			ps.executeUpdate();
			System.out.println("Ultimo accesso aggiornato");
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
	public void doDelete(UserBean item) throws SQLException {
		// TODO Auto-generated method stub

	}
	
	
	public float getValutazione(String username)throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="SELECT avg(Valutazione) as Media FROM Materiale,Feedback WHERE Materiale.CodiceMateriale=Feedback.CodiceMateriale and Feedback.CodiceMateriale in (select Materiale.CodiceMateriale from Materiale where Username=?);";
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			rs=ps.executeQuery();
			if(rs.next()) 
				return rs.getFloat("Media");
			return 0;
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
	

	private DataSource ds;
}
