package it.unisa.model;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.unisa.utils.Utility;

public class MaterialModelDS implements Model<MaterialBean> {
	
	public MaterialModelDS(DataSource ds) {
		this.ds=ds;
	}

	@Override
	public MaterialBean doRetrieveByKey(String code) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM Materiale WHERE CodiceMateriale=?;";
		MaterialBean bean=new MaterialBean();
		ResultSet rs=null;
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			int codice=Integer.parseInt(code);
			//System.out.println("Codice in materilmodel "+codice);
			ps.setInt(1, codice);
			rs=ps.executeQuery();
			//System.out.println("ciao");
			if(rs.next()) {
				bean.setCodiceMateriale(rs.getInt("CodiceMateriale"));
				bean.setDataCaricamento(rs.getDate("DataCaricamento"));
				bean.setKeywords(rs.getString("Keywords"));
				bean.setCosto(rs.getInt("Costo"));
				bean.setDescrizione(rs.getString("Descrizione"));
				bean.setHidden(rs.getBoolean("Hidden"));
				bean.setCodiceCorso(rs.getInt("CodiceCorso"));
				bean.setUsername(rs.getString("Username"));
				bean.setFileName(rs.getString("FileName"));
				bean.setAnteprima(rs.getBlob("Anteprima").getBinaryStream());
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
		return bean;
		
	}
	
	
	public Collection<MaterialBean> notValidated()throws SQLException{
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM Materiale WHERE Hidden=1 ;";
		Collection<MaterialBean> material=new LinkedList<MaterialBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			//Utility.print("doRetrieveAll:"+ps.toString());
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				MaterialBean bean=new MaterialBean();
				bean.setCodiceMateriale(rs.getInt("CodiceMateriale"));
				bean.setDataCaricamento(rs.getDate("DataCaricamento"));
				bean.setKeywords(rs.getString("Keywords"));
				bean.setCosto(rs.getInt("Costo"));
				bean.setDescrizione(rs.getString("Descrizione"));
				bean.setHidden(rs.getBoolean("Hidden"));
				bean.setCodiceCorso(rs.getInt("CodiceCorso"));
				bean.setUsername(rs.getString("Username"));
				bean.setFileName(rs.getString("FileName"));
				bean.setAnteprima(rs.getBlob("Anteprima").getBinaryStream());
				material.add(bean);
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
		return material;
		
	}
	
	
	public void setPrice(int codiceMateriale,int price)throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		String sql="UPDATE Materiale SET Costo=?,Hidden=0 WHERE CodiceMateriale=?";
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, price);
			ps.setInt(2, codiceMateriale);
			ps.executeUpdate();
			System.out.println("Prezzo impostato");
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
	

	@Override
	public Collection<MaterialBean> doRetrieveAll() throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM Materiale;";
		Collection<MaterialBean> material=new LinkedList<MaterialBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			Utility.print("doRetrieveAll:"+ps.toString());
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				MaterialBean bean=new MaterialBean();
				bean.setCodiceMateriale(rs.getInt("CodiceMateriale"));
				bean.setDataCaricamento(rs.getDate("DataCaricamento"));
				bean.setKeywords(rs.getString("Keywords"));
				bean.setCosto(rs.getInt("Costo"));
				bean.setDescrizione(rs.getString("Descrizione"));
				bean.setHidden(rs.getBoolean("Hidden"));
				bean.setCodiceCorso(rs.getInt("CodiceCorso"));
				bean.setUsername(rs.getString("Username"));
				bean.setFileName(rs.getString("FileName"));
				bean.setAnteprima(rs.getBlob("Anteprima").getBinaryStream());
				material.add(bean);
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
		return material;
	}
	
	public Collection<MaterialBean>  doRetrieveByString(String str) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;

		
		String selectSQL="SELECT * FROM Materiale WHERE Descrizione LIKE ? OR CodiceCorso IN (SELECT CodiceCorso From Corso WHERE Nome LIKE ?);";
		Collection<MaterialBean> material=new LinkedList<MaterialBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			ps.setString(1, '%'+str+'%');
			ps.setString(2, '%'+str+'%');
			
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				MaterialBean bean=new MaterialBean();
				bean.setCodiceMateriale(rs.getInt("CodiceMateriale"));
				bean.setDataCaricamento(rs.getDate("DataCaricamento"));
				bean.setKeywords(rs.getString("Keywords"));
				bean.setCosto(rs.getInt("Costo"));
				bean.setDescrizione(rs.getString("Descrizione"));
				bean.setHidden(rs.getBoolean("Hidden"));
				bean.setCodiceCorso(rs.getInt("CodiceCorso"));
				bean.setUsername(rs.getString("Username"));
				bean.setFileName(rs.getString("FileName"));
				bean.setAnteprima(rs.getBlob("Anteprima").getBinaryStream());
				material.add(bean);
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
		return material;
		
	}
	
	
	public Collection<MaterialBean>  doRetrieveByUsername(String username) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		String sql="SELECT * FROM Materiale WHERE Username=? AND Hidden=0 ORDER BY DataCaricamento desc;";
		Collection<MaterialBean> material=new LinkedList<MaterialBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1,username);
			
			rs=ps.executeQuery();
			while(rs.next()) {
				MaterialBean bean=new MaterialBean();
				bean.setCodiceMateriale(rs.getInt("CodiceMateriale"));
				bean.setDataCaricamento(rs.getDate("DataCaricamento"));
				bean.setKeywords(rs.getString("Keywords"));
				bean.setCosto(rs.getInt("Costo"));
				bean.setDescrizione(rs.getString("Descrizione"));
				bean.setHidden(rs.getBoolean("Hidden"));
				bean.setCodiceCorso(rs.getInt("CodiceCorso"));
				bean.setUsername(rs.getString("Username"));
				bean.setFileName(rs.getString("FileName"));
				bean.setAnteprima(rs.getBlob("Anteprima").getBinaryStream());
				material.add(bean);
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
		return material;
		
	}
	
	
	
	public Collection<MaterialBean>  doRetrieveByParameters(String str,String date,String ratingOrder, int rating) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		PreparedStatement view = null;
		PreparedStatement dropViewSQL = null;

		String dropView = "DROP VIEW IF EXISTS FeedbackMedia;";
		String viewSQL = "CREATE VIEW FeedbackMedia AS\n"
				+ "Select CodiceMateriale, ROUND(AVG(Valutazione)) AS ValutazioneMedia\n"
				+ "FROM Feedback\n"
				+ "GROUP BY CodiceMateriale;";
		
		
		String selectSQL="SELECT Materiale.CodiceMateriale, Materiale.DataCaricamento,Materiale.Keywords, Materiale.costo, Materiale.Descrizione, Materiale.Hidden, Materiale.Username, Materiale.CodiceCorso,Materiale.Username,Materiale.FileName, Materiale.Anteprima, FeedbackMedia.ValutazioneMedia\n"
				+ "FROM Materiale LEFT JOIN FeedbackMedia ON Materiale.CodiceMateriale = FeedbackMedia.CodiceMateriale \n"
				+ "WHERE Materiale.Hidden = 0 AND ( Descrizione LIKE ? OR CodiceCorso IN (SELECT CodiceCorso from Corso WHERE Nome LIKE ?) ) \n"
				+ "ORDER BY DataCaricamento , ValutazioneMedia;";
		
		if (rating == 0) {
		
		if ((date.compareTo("DESC")==0)&&(ratingOrder.compareTo("DESC")==0)) {
		selectSQL="SELECT Materiale.CodiceMateriale, Materiale.DataCaricamento,Materiale.Keywords, Materiale.costo, Materiale.Descrizione, Materiale.Hidden, Materiale.Username, Materiale.CodiceCorso,Materiale.Username,Materiale.FileName, Materiale.Anteprima, FeedbackMedia.ValutazioneMedia\n"
				+ "FROM Materiale LEFT JOIN FeedbackMedia ON Materiale.CodiceMateriale = FeedbackMedia.CodiceMateriale \n"
				+ "WHERE Materiale.Hidden = 0 AND ( Descrizione LIKE ? OR CodiceCorso IN (SELECT CodiceCorso from Corso WHERE Nome LIKE ?) ) \n"
				+ "ORDER BY DataCaricamento DESC, ValutazioneMedia DESC;";
		}
		if ((date.compareTo("DESC")==0)&&(ratingOrder.compareTo("ASC")==0)) {
			selectSQL="SELECT Materiale.CodiceMateriale, Materiale.DataCaricamento,Materiale.Keywords, Materiale.costo, Materiale.Descrizione, Materiale.Hidden, Materiale.Username, Materiale.CodiceCorso,Materiale.Username,Materiale.FileName, Materiale.Anteprima, FeedbackMedia.ValutazioneMedia\n"
					+ "FROM Materiale LEFT JOIN FeedbackMedia ON Materiale.CodiceMateriale = FeedbackMedia.CodiceMateriale \n"
					+ "WHERE Materiale.Hidden = 0 AND ( Descrizione LIKE ? OR CodiceCorso IN (SELECT CodiceCorso from Corso WHERE Nome LIKE ?) ) \n"
					+ "ORDER BY DataCaricamento DESC, ValutazioneMedia ASC;";
			}
		if ((date.compareTo("ASC")==0)&&(ratingOrder.compareTo("DESC")==0)) {
			selectSQL="SELECT Materiale.CodiceMateriale, Materiale.DataCaricamento,Materiale.Keywords, Materiale.costo, Materiale.Descrizione, Materiale.Hidden, Materiale.Username, Materiale.CodiceCorso,Materiale.Username,Materiale.FileName, Materiale.Anteprima, FeedbackMedia.ValutazioneMedia\n"
					+ "FROM Materiale LEFT JOIN FeedbackMedia ON Materiale.CodiceMateriale = FeedbackMedia.CodiceMateriale \n"
					+ "WHERE Materiale.Hidden = 0 AND ( Descrizione LIKE ? OR CodiceCorso IN (SELECT CodiceCorso from Corso WHERE Nome LIKE ?) ) \n"
					+ "ORDER BY DataCaricamento ASC, ValutazioneMedia DESC;";
			}
		
		if ((date.compareTo("novalue")==0)&&(ratingOrder.compareTo("ASC")==0)) {
			selectSQL="SELECT Materiale.CodiceMateriale, Materiale.DataCaricamento,Materiale.Keywords, Materiale.costo, Materiale.Descrizione, Materiale.Hidden, Materiale.Username, Materiale.CodiceCorso,Materiale.Username,Materiale.FileName, Materiale.Anteprima, FeedbackMedia.ValutazioneMedia\n"
					+ "FROM Materiale LEFT JOIN FeedbackMedia ON Materiale.CodiceMateriale = FeedbackMedia.CodiceMateriale \n"
					+ "WHERE Materiale.Hidden = 0 AND ( Descrizione LIKE ? OR CodiceCorso IN (SELECT CodiceCorso from Corso WHERE Nome LIKE ?) ) \n"
					+ "ORDER BY ValutazioneMedia ASC;";
			}
		if ((date.compareTo("ASC")==0)&&(ratingOrder.compareTo("novalue")==0)) {
			selectSQL="SELECT Materiale.CodiceMateriale, Materiale.DataCaricamento,Materiale.Keywords, Materiale.costo, Materiale.Descrizione, Materiale.Hidden, Materiale.Username, Materiale.CodiceCorso,Materiale.Username,Materiale.FileName, Materiale.Anteprima, FeedbackMedia.ValutazioneMedia\n"
					+ "FROM Materiale LEFT JOIN FeedbackMedia ON Materiale.CodiceMateriale = FeedbackMedia.CodiceMateriale \n"
					+ "WHERE Materiale.Hidden = 0 AND ( Descrizione LIKE ? OR CodiceCorso IN (SELECT CodiceCorso from Corso WHERE Nome LIKE ?) ) \n"
					+ "ORDER BY DataCaricamento ASC;";
			}
		if ((date.compareTo("novalue")==0)&&(ratingOrder.compareTo("novalue")==0)) {
			selectSQL="SELECT Materiale.CodiceMateriale, Materiale.DataCaricamento,Materiale.Keywords, Materiale.costo, Materiale.Descrizione, Materiale.Hidden, Materiale.Username, Materiale.CodiceCorso,Materiale.Username,Materiale.FileName, Materiale.Anteprima, FeedbackMedia.ValutazioneMedia\n"
					+ "FROM Materiale LEFT JOIN FeedbackMedia ON Materiale.CodiceMateriale = FeedbackMedia.CodiceMateriale \n"
					+ "WHERE Materiale.Hidden = 0 AND ( Descrizione LIKE ? OR CodiceCorso IN (SELECT CodiceCorso from Corso WHERE Nome LIKE ?) );";
			}
		if ((date.compareTo("DESC")==0)&&(ratingOrder.compareTo("novalue")==0)) {
			selectSQL="SELECT Materiale.CodiceMateriale, Materiale.DataCaricamento,Materiale.Keywords, Materiale.costo, Materiale.Descrizione, Materiale.Hidden, Materiale.Username, Materiale.CodiceCorso,Materiale.Username,Materiale.FileName, Materiale.Anteprima, FeedbackMedia.ValutazioneMedia\n"
					+ "FROM Materiale LEFT JOIN FeedbackMedia ON Materiale.CodiceMateriale = FeedbackMedia.CodiceMateriale \n"
					+ "WHERE Materiale.Hidden = 0 AND ( Descrizione LIKE ? OR CodiceCorso IN (SELECT CodiceCorso from Corso WHERE Nome LIKE ?) )\n"
					+ "ORDER BY DataCaricamento DESC;";
			}
		if ((date.compareTo("novalue")==0)&&(ratingOrder.compareTo("DESC")==0)) {
			selectSQL="SELECT Materiale.CodiceMateriale, Materiale.DataCaricamento,Materiale.Keywords, Materiale.costo, Materiale.Descrizione, Materiale.Hidden, Materiale.Username, Materiale.CodiceCorso,Materiale.Username,Materiale.FileName, Materiale.Anteprima, FeedbackMedia.ValutazioneMedia\n"
					+ "FROM Materiale LEFT JOIN FeedbackMedia ON Materiale.CodiceMateriale = FeedbackMedia.CodiceMateriale \n"
					+ "WHERE Materiale.Hidden = 0 AND ( Descrizione LIKE ? OR CodiceCorso IN (SELECT CodiceCorso from Corso WHERE Nome LIKE ?) )\n"
					+ "ORDER BY ValutazioneMedia DESC;";
			}
		
		}else {
			

		
		if ((date.compareTo("DESC")==0)&&(ratingOrder.compareTo("DESC")==0)) {
			selectSQL="SELECT Materiale.CodiceMateriale, Materiale.DataCaricamento,Materiale.Keywords, Materiale.costo, Materiale.Descrizione, Materiale.Hidden, Materiale.Username, Materiale.CodiceCorso,Materiale.Username,Materiale.FileName, Materiale.Anteprima, FeedbackMedia.ValutazioneMedia\n"
					+ "FROM Materiale LEFT JOIN FeedbackMedia ON Materiale.CodiceMateriale = FeedbackMedia.CodiceMateriale \n"
					+ "WHERE Materiale.Hidden = 0 AND((Descrizione LIKE ? OR CodiceCorso IN (SELECT CodiceCorso from Corso WHERE Nome LIKE ?)) AND (ValutazioneMedia = ?))\n"
					+ "ORDER BY DataCaricamento DESC, ValutazioneMedia DESC;";
			}
			if ((date.compareTo("DESC")==0)&&(ratingOrder.compareTo("ASC")==0)) {
				selectSQL="SELECT Materiale.CodiceMateriale, Materiale.DataCaricamento,Materiale.Keywords, Materiale.costo, Materiale.Descrizione, Materiale.Hidden, Materiale.Username, Materiale.CodiceCorso,Materiale.Username,Materiale.FileName, Materiale.Anteprima, FeedbackMedia.ValutazioneMedia\n"
						+ "FROM Materiale LEFT JOIN FeedbackMedia ON Materiale.CodiceMateriale = FeedbackMedia.CodiceMateriale \n"
						+ "WHERE Materiale.Hidden = 0 AND((Descrizione LIKE ? OR CodiceCorso IN (SELECT CodiceCorso from Corso WHERE Nome LIKE ?)) AND (ValutazioneMedia = ?)) \n"
						+ "ORDER BY DataCaricamento DESC, ValutazioneMedia ASC;";
				}
			if ((date.compareTo("ASC")==0)&&(ratingOrder.compareTo("DESC")==0)) {
				selectSQL="SELECT Materiale.CodiceMateriale, Materiale.DataCaricamento,Materiale.Keywords, Materiale.costo, Materiale.Descrizione, Materiale.Hidden, Materiale.Username, Materiale.CodiceCorso,Materiale.Username,Materiale.FileName, Materiale.Anteprima, FeedbackMedia.ValutazioneMedia\n"
						+ "FROM Materiale LEFT JOIN FeedbackMedia ON Materiale.CodiceMateriale = FeedbackMedia.CodiceMateriale \n"
						+ "WHERE Materiale.Hidden = 0 AND((Descrizione LIKE ? OR CodiceCorso IN (SELECT CodiceCorso from Corso WHERE Nome LIKE ?)) AND (ValutazioneMedia = ?))\n"
						+ "ORDER BY DataCaricamento ASC, ValutazioneMedia DESC;";
				}
			if ((date.compareTo("ASC")==0)&&(ratingOrder.compareTo("ASC")==0)) {
				selectSQL="SELECT Materiale.CodiceMateriale, Materiale.DataCaricamento,Materiale.Keywords, Materiale.costo, Materiale.Descrizione, Materiale.Hidden, Materiale.Username, Materiale.CodiceCorso,Materiale.Username,Materiale.FileName, Materiale.Anteprima, FeedbackMedia.ValutazioneMedia\n"
						+ "FROM Materiale LEFT JOIN FeedbackMedia ON Materiale.CodiceMateriale = FeedbackMedia.CodiceMateriale \n"
						+ "WHERE Materiale.Hidden = 0 AND((Descrizione LIKE ? OR CodiceCorso IN (SELECT CodiceCorso from Corso WHERE Nome LIKE ?)) AND (ValutazioneMedia = ?))\n"
						+ "ORDER BY DataCaricamento ASC, ValutazioneMedia DESC;";
				}
			
			if ((date.compareTo("novalue")==0)&&(ratingOrder.compareTo("ASC")==0)) {
				selectSQL="SELECT Materiale.CodiceMateriale, Materiale.DataCaricamento,Materiale.Keywords, Materiale.costo, Materiale.Descrizione, Materiale.Hidden, Materiale.Username, Materiale.CodiceCorso,Materiale.Username,Materiale.FileName, Materiale.Anteprima, FeedbackMedia.ValutazioneMedia\n"
						+ "FROM Materiale LEFT JOIN FeedbackMedia ON Materiale.CodiceMateriale = FeedbackMedia.CodiceMateriale \n"
						+ "WHERE Materiale.Hidden = 0 AND((Descrizione LIKE ? OR CodiceCorso IN (SELECT CodiceCorso from Corso WHERE Nome LIKE ?)) AND (ValutazioneMedia = ?))\n"
						+ "ORDER BY ValutazioneMedia ASC;";
				}
			if ((date.compareTo("ASC")==0)&&(ratingOrder.compareTo("novalue")==0)) {
				selectSQL="SELECT Materiale.CodiceMateriale, Materiale.DataCaricamento,Materiale.Keywords, Materiale.costo, Materiale.Descrizione, Materiale.Hidden, Materiale.Username, Materiale.CodiceCorso,Materiale.Username,Materiale.FileName, Materiale.Anteprima, FeedbackMedia.ValutazioneMedia\n"
						+ "FROM Materiale LEFT JOIN FeedbackMedia ON Materiale.CodiceMateriale = FeedbackMedia.CodiceMateriale \n"
						+ "WHERE Materiale.Hidden = 0 AND((Descrizione LIKE ? OR CodiceCorso IN (SELECT CodiceCorso from Corso WHERE Nome LIKE ?)) AND (ValutazioneMedia = ?))\n"
						+ "ORDER BY DataCaricamento ASC;";
				}
			if ((date.compareTo("novalue")==0)&&(ratingOrder.compareTo("novalue")==0)) {
				selectSQL="SELECT Materiale.CodiceMateriale, Materiale.DataCaricamento,Materiale.Keywords, Materiale.costo, Materiale.Descrizione, Materiale.Hidden, Materiale.Username, Materiale.CodiceCorso,Materiale.Username,Materiale.FileName, Materiale.Anteprima, FeedbackMedia.ValutazioneMedia\n"
						+ "FROM Materiale LEFT JOIN FeedbackMedia ON Materiale.CodiceMateriale = FeedbackMedia.CodiceMateriale \n"
						+ "WHERE Materiale.Hidden = 0 AND((Descrizione LIKE ? OR CodiceCorso IN (SELECT CodiceCorso from Corso WHERE Nome LIKE ?)) AND (ValutazioneMedia = ?));";
				}
			if ((date.compareTo("DESC")==0)&&(ratingOrder.compareTo("novalue")==0)) {
				selectSQL="SELECT Materiale.CodiceMateriale, Materiale.DataCaricamento,Materiale.Keywords, Materiale.costo, Materiale.Descrizione, Materiale.Hidden, Materiale.Username, Materiale.CodiceCorso,Materiale.Username,Materiale.FileName, Materiale.Anteprima, FeedbackMedia.ValutazioneMedia\n"
						+ "FROM Materiale LEFT JOIN FeedbackMedia ON Materiale.CodiceMateriale = FeedbackMedia.CodiceMateriale \n"
						+ "WHERE Materiale.Hidden = 0 AND((Descrizione LIKE ? OR CodiceCorso IN (SELECT CodiceCorso from Corso WHERE Nome LIKE ?)) AND (ValutazioneMedia = ?))\n"
						+ "ORDER BY DataCaricamento DESC;";
				}
			if ((date.compareTo("novalue")==0)&&(ratingOrder.compareTo("DESC")==0)) {
				selectSQL="SELECT Materiale.CodiceMateriale, Materiale.DataCaricamento,Materiale.Keywords, Materiale.costo, Materiale.Descrizione, Materiale.Hidden, Materiale.Username, Materiale.CodiceCorso,Materiale.Username,Materiale.FileName, Materiale.Anteprima, FeedbackMedia.ValutazioneMedia\n"
						+ "FROM Materiale LEFT JOIN FeedbackMedia ON Materiale.CodiceMateriale = FeedbackMedia.CodiceMateriale \n"
						+ "WHERE Materiale.Hidden = 0 AND((Descrizione LIKE ? OR CodiceCorso IN (SELECT CodiceCorso from Corso WHERE Nome LIKE ?)) AND (ValutazioneMedia = ?))\n"
						+ "ORDER BY ValutazioneMedia DESC;";
				}

		}

		Collection<MaterialBean> material=new LinkedList<MaterialBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			view = con.prepareStatement(viewSQL);
			dropViewSQL = con.prepareStatement(dropView);
			ps.setString(1, '%'+str+'%');
			ps.setString(2, '%'+str+'%');
			if(rating!=0)
				ps.setInt(3, rating);
			
			dropViewSQL.execute();
			view.execute();
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				MaterialBean bean=new MaterialBean();
				bean.setCodiceMateriale(rs.getInt("Materiale.CodiceMateriale"));
				bean.setDataCaricamento(rs.getDate("Materiale.DataCaricamento"));
				bean.setKeywords(rs.getString("Keywords"));
				bean.setCosto(rs.getInt("Costo"));
				bean.setDescrizione(rs.getString("Descrizione"));
				bean.setHidden(rs.getBoolean("Hidden"));
				bean.setCodiceCorso(rs.getInt("Materiale.CodiceCorso"));
				bean.setUsername(rs.getString("Materiale.Username"));
				bean.setFileName(rs.getString("FileName"));
				bean.setAnteprima(rs.getBlob("Anteprima").getBinaryStream());
				material.add(bean);
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
		
		return material;
		
	}
	
	public int doRetrieveFeedback(int code) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		PreparedStatement view = null;
		PreparedStatement dropViewSQL = null;

		String dropView = "DROP VIEW IF EXISTS FeedbackMedia;";
		String viewSQL = "CREATE VIEW FeedbackMedia AS\n"
				+ "Select CodiceMateriale, ROUND(AVG(Valutazione)) AS ValutazioneMedia\n"
				+ "FROM Feedback\n"
				+ "GROUP BY CodiceMateriale;";

		
		String selectSQL="SELECT * FROM FeedbackMedia WHERE codiceMateriale = ?";
		
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			dropViewSQL = con.prepareStatement(dropView);
			view = con.prepareStatement(viewSQL);
			ps.setInt(1, code);
			
			dropViewSQL.execute();
			view.execute();
			ResultSet rs=ps.executeQuery();
			
			if(rs.first()) {
				return rs.getInt("ValutazioneMedia");
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
	
	
	public Collection<MaterialBean> doRetrieveByOrderDate() throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM Materiale WHERE Hidden=0 ORDER BY DataCaricamento desc;";
		Collection<MaterialBean> material=new LinkedList<MaterialBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			//Utility.print("doRetrieveAll:"+ps.toString());
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				MaterialBean bean=new MaterialBean();
				bean.setCodiceMateriale(rs.getInt("CodiceMateriale"));
				bean.setDataCaricamento(rs.getDate("DataCaricamento"));
				bean.setKeywords(rs.getString("Keywords"));
				bean.setCosto(rs.getInt("Costo"));
				bean.setDescrizione(rs.getString("Descrizione"));
				bean.setHidden(rs.getBoolean("Hidden"));
				bean.setCodiceCorso(rs.getInt("CodiceCorso"));
				bean.setUsername(rs.getString("Username"));
				bean.setFileName(rs.getString("FileName"));
				bean.setAnteprima(rs.getBlob("Anteprima").getBinaryStream());
				material.add(bean);
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
		return material;
	}
	
	
	

	@Override
	public void doSave(MaterialBean item) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;

		String sql = "INSERT INTO Materiale" + " (DataCaricamento,Keywords,Costo,Descrizione,Hidden,CodiceCorso,Username,FileName,Anteprima) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			connection = ds.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setDate(1, item.getDataCaricamento());
			ps.setString(2, item.getKeywords());
			ps.setInt(3, item.getCosto());
			ps.setString(4, item.getDescrizione());
			ps.setBoolean(5, item.isHidden());
			ps.setInt(6, item.getCodiceCorso());
			ps.setString(7, item.getUsername());
			ps.setString(8, item.getFileName());
			ps.setBlob(9, item.getAnteprima());
			
		

			ps.executeUpdate();
			System.out.println("Materiale inserito");
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
	public void doUpdate(MaterialBean item) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doDelete(MaterialBean item) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
	public int getQuantitaMaterialeCondiviso(String username)throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="SELECT count(*) as QuantitaMateriale FROM Materiale WHERE Username=?";
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			rs=ps.executeQuery();
			if(rs.next())
				return rs.getInt("QuantitaMateriale");
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

	private DataSource ds;
}
