package materiale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;


public class MaterialModelDS {
	
	public MaterialModelDS(DataSource ds) {
		this.ds=ds;
	}

	public MaterialBean doRetrieveByKey(String code) throws SQLException {
		if(code==null||code.equals(""))
			throw new NullPointerException();
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
				bean.setNomeFile(rs.getString("nomeFile"));
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
		String selectSQL="SELECT * FROM Materiale WHERE Hidden=true ;";
		Collection<MaterialBean> material=new LinkedList<MaterialBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
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
				bean.setNomeFile(rs.getString("nomeFile"));
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
		if(codiceMateriale<0||price<0)
			throw new NullPointerException();
		Connection con=null;
		PreparedStatement ps=null;
		String sql="UPDATE Materiale SET Costo=?,Hidden=false WHERE CodiceMateriale=?";
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
	
	
	public Collection<MaterialBean>  doRetrieveByString(String str) throws SQLException {
		if(str==null||str.equals(""))
			throw new NullPointerException();
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
				bean.setNomeFile(rs.getString("nomeFile"));
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
		if(username==null||username.equals(""))
			throw new NullPointerException();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		String sql="SELECT * FROM Materiale WHERE Username=? AND Hidden=false ORDER BY DataCaricamento desc;";
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
				bean.setNomeFile(rs.getString("nomeFile"));
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
	
	
	
	public Collection<MaterialBean>  doRetrieveByParameters(String str,String ratingOrder, int rating) throws SQLException {
		if(str==null||str.equals("")||ratingOrder==null||ratingOrder.equals("")||rating<0)
			throw new NullPointerException();
		Connection con=null;
		PreparedStatement ps=null;
		
		
		
		String selectSQL="SELECT Materiale.CodiceMateriale AS CodiceMateriale, Materiale.DataCaricamento,Materiale.Keywords, Materiale.costo, Materiale.Descrizione, Materiale.Hidden, Materiale.Username, Materiale.CodiceCorso,Materiale.nomeFile, ROUND(AVG(Valutazione)) AS ValutazioneMedia\n"
				+ "FROM Materiale LEFT JOIN Feedback ON Materiale.CodiceMateriale = Feedback.CodiceMateriale \n"
				+ "WHERE Materiale.Hidden = false AND ( Descrizione LIKE ? OR CodiceCorso IN (SELECT CodiceCorso from Corso WHERE Nome LIKE ?) )\n"
				+ "GROUP BY CodiceMateriale\n"
				+ "ORDER BY ValutazioneMedia;";
		

		
		if ((ratingOrder.compareTo("DESC")==0)) {
			selectSQL="SELECT Materiale.CodiceMateriale AS CodiceMateriale, Materiale.DataCaricamento,Materiale.Keywords, Materiale.costo, Materiale.Descrizione, Materiale.Hidden, Materiale.Username, Materiale.CodiceCorso,Materiale.nomeFile, ROUND(AVG(Valutazione)) AS ValutazioneMedia\n"
					+ "FROM Materiale LEFT JOIN Feedback ON Materiale.CodiceMateriale = Feedback.CodiceMateriale \n"
					+ "WHERE Materiale.Hidden = false AND ( Descrizione LIKE ? OR CodiceCorso IN (SELECT CodiceCorso from Corso WHERE Nome LIKE ?) )\n"
					+ "GROUP BY CodiceMateriale\n"
					+ "ORDER BY ValutazioneMedia DESC;";
		}
		if ((ratingOrder.compareTo("ASC")==0)) {
			selectSQL="SELECT Materiale.CodiceMateriale AS CodiceMateriale, Materiale.DataCaricamento,Materiale.Keywords, Materiale.costo, Materiale.Descrizione, Materiale.Hidden, Materiale.Username, Materiale.CodiceCorso,Materiale.nomeFile, ROUND(AVG(Valutazione)) AS ValutazioneMedia\n"
					+ "FROM Materiale LEFT JOIN Feedback ON Materiale.CodiceMateriale = Feedback.CodiceMateriale\n"
					+ "WHERE Materiale.Hidden = false AND ( Descrizione LIKE ? OR CodiceCorso IN (SELECT CodiceCorso from Corso WHERE Nome LIKE ?) )\n"
					+ "GROUP BY CodiceMateriale\n"
					+ "ORDER BY ValutazioneMedia;";
			}

		
		if ((ratingOrder.compareTo("novalue")==0)) {
			selectSQL="SELECT Materiale.CodiceMateriale AS CodiceMateriale, Materiale.DataCaricamento,Materiale.Keywords, Materiale.costo, Materiale.Descrizione, Materiale.Hidden, Materiale.Username, Materiale.CodiceCorso,Materiale.nomeFile, ROUND(AVG(Valutazione)) AS ValutazioneMedia\n"
					+ "FROM Materiale LEFT JOIN Feedback ON Materiale.CodiceMateriale = Feedback.CodiceMateriale\n"
					+ "WHERE Materiale.Hidden = false AND ( Descrizione LIKE ? OR CodiceCorso IN (SELECT CodiceCorso from Corso WHERE Nome LIKE ?) )\n"
					+ "GROUP BY CodiceMateriale";
			}




		
		

		Collection<MaterialBean> material=new LinkedList<MaterialBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);

			ps.setString(1, '%'+str+'%');
			ps.setString(2, '%'+str+'%');
			
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				
				if (rating!=0) {
					if (rs.getInt("ValutazioneMedia")==rating) {
					MaterialBean bean=new MaterialBean();
					bean.setCodiceMateriale(rs.getInt("Materiale.CodiceMateriale"));
					bean.setDataCaricamento(rs.getDate("Materiale.DataCaricamento"));
					bean.setKeywords(rs.getString("Keywords"));
					bean.setCosto(rs.getInt("Costo"));
					bean.setDescrizione(rs.getString("Descrizione"));
					bean.setHidden(rs.getBoolean("Hidden"));
					bean.setCodiceCorso(rs.getInt("Materiale.CodiceCorso"));
					bean.setUsername(rs.getString("Materiale.Username"));
					bean.setNomeFile(rs.getString("nomeFile"));
					material.add(bean);
					}
				}else {
				
				MaterialBean bean=new MaterialBean();
				bean.setCodiceMateriale(rs.getInt("Materiale.CodiceMateriale"));
				bean.setDataCaricamento(rs.getDate("Materiale.DataCaricamento"));
				bean.setKeywords(rs.getString("Keywords"));
				bean.setCosto(rs.getInt("Costo"));
				bean.setDescrizione(rs.getString("Descrizione"));
				bean.setHidden(rs.getBoolean("Hidden"));
				bean.setCodiceCorso(rs.getInt("Materiale.CodiceCorso"));
				bean.setUsername(rs.getString("Materiale.Username"));
				bean.setNomeFile(rs.getString("nomeFile"));
				material.add(bean);
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
		
		return material;
		
	}
	
	public int doRetrieveFeedback(int code) throws SQLException {
		if(code<=0)
			throw new NullPointerException();
		Connection con=null;
		PreparedStatement ps=null;
	

		
		String selectSQL="SELECT Materiale.CodiceMateriale AS CodiceMateriale, ROUND(AVG(Valutazione)) AS ValutazioneMedia\n"
				+ "FROM Materiale LEFT JOIN Feedback ON Materiale.CodiceMateriale = Feedback.CodiceMateriale \n"
				+ "WHERE Materiale.CodiceMateriale = ? GROUP BY Materiale.CodiceMateriale;";
		
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);

			ps.setInt(1, code);
			
	
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
		String selectSQL="SELECT * FROM Materiale WHERE Hidden=false ORDER BY DataCaricamento desc;";
		Collection<MaterialBean> material=new LinkedList<MaterialBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
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
				bean.setNomeFile(rs.getString("nomeFile"));
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

	
	public void doSave(MaterialBean item) throws SQLException {
		if(item==null)
			throw new NullPointerException();
		Connection connection = null;
		PreparedStatement ps = null;

		String sql = "INSERT INTO Materiale" + " (DataCaricamento,Keywords,Costo,Descrizione,Hidden,CodiceCorso,Username,nomeFile) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
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
			ps.setString(8, item.getNomeFile());
			
		

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
	

	public int getQuantitaMaterialeCondiviso(String username)throws SQLException {
		if(username==null||username.equals(""))
			throw new NullPointerException();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="SELECT count(*) as QuantitaMateriale FROM Materiale WHERE Username=? GROUP BY Username";
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			rs=ps.executeQuery();
			if(rs.next())
				return rs.getInt("QuantitaMateriale");
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

	
	public void doDelete(int codiceMateriale) throws SQLException {
		if(codiceMateriale<0)
			throw new NullPointerException();
		Connection connection = null;
		PreparedStatement ps = null;

		String sql= "DELETE FROM Materiale WHERE CodiceMateriale=? ";
		try {
			connection = ds.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setInt(1, codiceMateriale);
			ps.executeUpdate();
		}
		finally {
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
