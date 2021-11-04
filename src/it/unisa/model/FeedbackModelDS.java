package it.unisa.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.unisa.utils.Utility;

public class FeedbackModelDS implements Model<FeedbackBean> {

	public FeedbackModelDS(DataSource ds) {
		this.ds=ds;
	}
	
	
	@Override
	public FeedbackBean doRetrieveByKey(String code) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<FeedbackBean> doRetrieveAll() throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM Feedback;";
		Collection<FeedbackBean> feeds=new LinkedList<FeedbackBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
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
	
	public int getFeedbackByUsername (String username) throws SQLException{
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT Nome,feedback from FeedbackUser WHERE Username = ?;";
		
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
				+ "SELECT NULL AS feedback,Username,nome,Cognome,Denominazione,dipName,Img from Utente\n"
				+ "UNION\n"
				+ "SELECT ROUND(AVG(ValutazioneMedia)) AS feedback, Utente.Username, Utente.Nome, Utente.Cognome,Utente.Denominazione, Utente.dipName, Img\n"
				+ "FROM Materiale LEFT JOIN FeedbackMedia ON Materiale.CodiceMateriale = FeedbackMedia.CodiceMateriale INNER JOIN Utente ON Materiale.Username = Utente.Username\n"
				+ "group by Utente.Username;";
		
		

		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			   dropViewFeedbackmedia = con.prepareStatement(dropViewFeedbackmediaSQL);
			   viewFeedbackmedia = con.prepareStatement(viewFeedbackmediaSQL);
			   dropViewFeedbackuser = con.prepareStatement(dropViewFeedbackuserSQL);
			   viewFeedbackuser = con.prepareStatement(viewFeedbackuserSQL);

			//Utility.print("doRetrieveAll:"+ps.toString());

			dropViewFeedbackmedia.execute();
			viewFeedbackmedia.execute();
			dropViewFeedbackuser.execute();
			viewFeedbackuser.execute();
			
			ps.setString(1, username);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
                       if (rs.getInt("feedback")==0) {
                    	   if (rs.next()) {
                    		   return rs.getInt("feedback");
                    	   }
                       }
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
	
	public Collection<FeedbackBean> doRetrieveByKeyMaterial(String codeMaterial) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM Feedback WHERE CodiceMateriale = ?;";
		Collection<FeedbackBean> feeds=new LinkedList<FeedbackBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			int codiceMateriale = Integer.parseInt(codeMaterial);
			ps.setInt(1, codiceMateriale);
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
	
	@Override
	public void doSave(FeedbackBean item) throws SQLException {
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

	@Override
	public void doUpdate(FeedbackBean item) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doDelete(FeedbackBean item) throws SQLException {
		// TODO Auto-generated method stub

	}

	private DataSource ds;
}
