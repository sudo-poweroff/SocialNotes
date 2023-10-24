package materiale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.unisa.utils.Utility;

public class FeedbackModelDS  {

	public FeedbackModelDS(DataSource ds) {
		this.ds=ds;
	}
	
	
	public int getFeedbackByUsername (String username) throws SQLException{
		if(username==null||username.equals(""))
			throw new NullPointerException();
		Connection con=null;
		PreparedStatement ps=null;
		
		String selectSQL="SELECT Utente.Username AS Username, ROUND(AVG(ValutazioneMedia)) AS feedback, Nome, Cognome, Denominazione, dipName, Img\n"
				+ "FROM Utente LEFT JOIN (SELECT Materiale.Username AS US, ROUND(AVG(Valutazione)) AS ValutazioneMedia\n"
				+ "FROM Materiale LEFT JOIN Feedback ON Materiale.CodiceMateriale = Feedback.CodiceMateriale \n"
				+ "WHERE Materiale.Hidden = false\n"
				+ "GROUP BY Materiale.Username\n"
				+ "ORDER BY ValutazioneMedia) AS FeedbackMedio ON Utente.Username = FeedbackMedio.US\n"
				+ "WHERE Username = ? AND Ruolo=0\n"
				+ "GROUP BY Utente.Username;";




		

		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);


			ps.setString(1, username);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
                       
            return rs.getInt("feedback");
    
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
	
	public Collection<FeedbackBean> doRetrieveByKeyMaterial(int codeMaterial) throws SQLException {
		if(codeMaterial<0)
			throw new NullPointerException();
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM Feedback WHERE CodiceMateriale = ?;";
		Collection<FeedbackBean> feeds=new LinkedList<FeedbackBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			//int codiceMateriale = Integer.parseInt(codeMaterial);
			ps.setInt(1,codeMaterial);
			//Utility.print("doRetrieveAll:"+ps.toString());
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				FeedbackBean bean=new FeedbackBean();
				bean.setCodiceMateriale(rs.getInt("CodiceMateriale"));
				bean.setUsername(rs.getString("Username"));
				bean.setDataFeed(rs.getTimestamp("DataFeed"));
				bean.setCommento(rs.getString("Commento"));
				bean.setValutazione(rs.getInt("Valutazione"));
				feeds.add(bean);
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
		return feeds;
	}
	
	public void doSave(FeedbackBean item) throws SQLException {
		if(item==null)
			throw new NullPointerException();
		Connection con=null;
		PreparedStatement ps=null;
		//ResultSet rs=null;
		String sql="INSERT INTO Feedback (Username,DataFeed,Commento,Valutazione, CodiceMateriale) VALUES (?,?,?,?,?)";
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, item.getUsername());
			ps.setTimestamp(2, item.getDataFeed());
			ps.setString(3, item.getCommento());
			ps.setInt(4, item.getValutazione());
			ps.setInt(5, item.getCodiceMateriale());
			ps.executeUpdate();


		}finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				if (con != null) {
					con.close();
				}
			}
		}

	}


	private DataSource ds;
}
