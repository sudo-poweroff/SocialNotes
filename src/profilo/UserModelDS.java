package profilo;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;



public class UserModelDS {

	public UserModelDS(DataSource ds) {
		this.ds=ds;
	}


	public UserBean checkLogin(String name,String password)throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		String sql="SELECT Username,Nome,Cognome,Img,Email,Pass,DataNascita,Coin,Ban,Denominazione,DipName, AES_DECRYPT(Pass,'despacito') as Password,Ruolo FROM Utente WHERE Email = ? OR Username=?";
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
				bean.setCoin(rs.getInt("Coin"));
				bean.setBan(rs.getDate("Ban"));
				bean.setDenominazione(rs.getString("Denominazione"));
				bean.setDipName(rs.getString("DipName"));
				bean.setRuolo(rs.getInt("Ruolo"));
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
				bean.setCoin(rs.getInt("Coin"));
				bean.setBan(rs.getDate("Ban"));
				bean.setDenominazione(rs.getString("Denominazione"));
				bean.setDipName(rs.getString("DipName"));
				bean.setRuolo(rs.getInt("Ruolo"));
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
				bean.setCoin(rs.getInt("Coin"));
				bean.setBan(rs.getDate("Ban"));
				bean.setDenominazione(rs.getString("Denominazione"));
				bean.setDipName(rs.getString("DipName"));
				bean.setRuolo(rs.getInt("Ruolo"));
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
				bean.setCoin(rs.getInt("Coin"));
				bean.setBan(rs.getDate("Ban"));
				bean.setDenominazione(rs.getString("Denominazione"));
				bean.setDipName(rs.getString("DipName"));
				bean.setRuolo(rs.getInt("Ruolo"));
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
		String selectSQL="SELECT * FROM Utente WHERE Ruolo=0 ORDER BY Ban DESC;";
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
				bean.setCoin(rs.getInt("Coin"));
				bean.setBan(rs.getDate("Ban"));
				bean.setDenominazione(rs.getString("Denominazione"));
				bean.setDipName(rs.getString("DipName"));
				bean.setRuolo(rs.getInt("Ruolo"));
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
				+ "Select CodiceMateriale,  ROUND(AVG(Valutazione)) AS ValutazioneMedia\n"
				+ "FROM Feedback\n"
				+ "GROUP BY CodiceMateriale;";

		String dropViewFeedbackuserSQL = "DROP VIEW IF EXISTS FeedbackUser;";
		String viewFeedbackuserSQL = "CREATE VIEW FeedbackUser AS\n"
				+ "SELECT ROUND(AVG(ValutazioneMedia)) AS feedback, Utente.Username, Utente.Nome, Utente.Cognome,Utente.Denominazione, Utente.Ruolo, Utente.dipName, Img\n"
				+ "FROM Materiale LEFT JOIN FeedbackMedia ON Materiale.CodiceMateriale = FeedbackMedia.CodiceMateriale RIGHT JOIN Utente ON Materiale.Username = Utente.Username\n"
				+ "group by Utente.Username;";




		String selectSQL="SELECT feedback, Username, Nome, Cognome, Denominazione, dipName, Img FROM FeedbackUser;";

		if (rating == 0) {

			if ((ratingOrder.compareTo("DESC")==0)) {
				selectSQL="SELECT feedback, Username, Nome, Cognome, Denominazione, dipName, Img FROM FeedbackUser WHERE (Username LIKE ? OR Nome LIKE ? OR Cognome LIKE ?) AND Ruolo=0 ORDER BY feedback DESC;";
			}
			if ((ratingOrder.compareTo("ASC")==0)) {
				selectSQL="SELECT feedback,Username, Nome, Cognome, Denominazione, dipName, Img FROM FeedbackUser WHERE (Username LIKE ? OR Nome LIKE ? OR Cognome LIKE ?) AND Ruolo=0 ORDER BY feedback ASC;";
			}
			if ((ratingOrder.compareTo("novalue")==0)) {
				selectSQL="SELECT feedback,Username, Nome, Cognome, Denominazione, dipName, Img FROM FeedbackUser WHERE (Username LIKE ? OR Nome LIKE ? OR Cognome LIKE ?) AND Ruolo=0;";
			}


		}else {



			if ((ratingOrder.compareTo("DESC")==0)) {
				selectSQL="SELECT feedback, Username, Nome, Cognome, Denominazione, dipName, Img FROM FeedbackUser WHERE (Username LIKE ? OR Nome LIKE ? OR Cognome LIKE ?) AND feedback = ? AND Ruolo=0 ORDER BY feedback DESC;";
			}
			if ((ratingOrder.compareTo("ASC")==0)) {
				selectSQL="SELECT feedback, Username, Nome, Cognome, Denominazione, dipName, Img FROM FeedbackUser WHERE (Username LIKE ? OR Nome LIKE ? OR Cognome LIKE ?) AND feedback = ? AND Ruolo=0 ORDER BY feedback ASC;";
			}


			if ((ratingOrder.compareTo("novalue")==0)) {
				selectSQL="SELECT feedback, Username, Nome, Cognome, Denominazione, dipName, Img FROM FeedbackUser WHERE (Username LIKE ? OR Nome LIKE ? OR Cognome LIKE ?) AND feedback = ? AND Ruolo=0 ORDER BY feedback;";
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

	public void doSave(UserBean item) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;

		String insertSQL = "INSERT INTO Utente (Username, Nome, Cognome, Email, Pass, DataNascita, Coin, Denominazione, DipName,Ban,Ruolo) VALUES (?, ?, ?, ?, AES_ENCRYPT(?,'despacito'), ?, ?, ?, ?,?,?)";

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
			ps.setInt(7, item.getCoin());
			ps.setString(8, item.getDenominazione());
			ps.setString(9, item.getDipName());
			ps.setDate(10, item.getBan());
			ps.setInt(11, item.getRuolo());

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

	public void manageBan(String username,Date ban) throws SQLException{
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "UPDATE Utente SET Ban = ? WHERE Username = ?";
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setDate(1, ban);
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

	public float getValutazione(String username)throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="SELECT avg(Valutazione) as Media FROM Materiale,Feedback WHERE Materiale.CodiceMateriale=Feedback.CodiceMateriale and Feedback.CodiceMateriale in (select Materiale.CodiceMateriale from Materiale where Username=?);";
		float media=0;
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			rs=ps.executeQuery();
			if(rs.next()) 
				media=rs.getFloat("Media");
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
		return media;
	}


	public int getRole(String username) throws SQLException{
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="SELECT Ruolo FROM Utente WHERE Username=?;";
		int ruolo=-1;
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			rs=ps.executeQuery();
			if(rs.next())
				ruolo=rs.getInt("Ruolo");
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
		return ruolo;
	}


	private DataSource ds;
}
