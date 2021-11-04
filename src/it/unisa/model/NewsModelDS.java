package it.unisa.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.unisa.utils.Utility;

public class NewsModelDS implements Model<NewsBean>{

	public NewsModelDS(DataSource ds) {
		this.ds=ds;
	}
	
	
	@Override
	public NewsBean doRetrieveByKey(String code) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<NewsBean> doRetrieveAll() throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM News;";
		Collection<NewsBean> news=new LinkedList<NewsBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			//Utility.print("doRetrieveAll:"+ps.toString());
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				NewsBean bean=new NewsBean();
				bean.setCodiceNews(rs.getInt("CodiceNews"));
				bean.setTitolo(rs.getString("Titolo"));
				bean.setArgomento(rs.getString("Argomento"));
				bean.setContenuto(rs.getString("Contenuto"));
				bean.setUsername(rs.getString("Username"));
				bean.setDataCaricamento(rs.getDate("DataCaricamento"));
				news.add(bean);
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
		return news;
	}
	public NewsBean doRetrieveByCodiceNews(int codiceNews) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM News WHERE CodiceNews = ?";
		NewsBean bean= new NewsBean();
		//System.out.println("doRetrieveByCodiceNews-codice:"+codiceNews);
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			//Utility.print("doRetrieveAll:"+ps.toString());
			ps.setInt(1, codiceNews);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				bean.setCodiceNews(rs.getInt("CodiceNews"));
				bean.setTitolo(rs.getString("Titolo"));
				bean.setArgomento(rs.getString("Argomento"));
				bean.setContenuto(rs.getString("Contenuto"));
				bean.setUsername(rs.getString("Username"));
				bean.setDataCaricamento(rs.getDate("DataCaricamento"));
				//System.out.println("News trovata");
			}
			else {
				//System.out.println("News non trovata");
				return null;
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
		return bean;
	}
	
	public int doRetrieveKey()throws SQLException{
		Connection con=null;
		PreparedStatement ps=null;
		String sql="SELECT CodiceNews FROM News ORDER BY CodiceNews;";
		ResultSet rs=null;
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.last()) {
				return rs.getInt("CodiceNews");
			}
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
	

	@Override
	public void doSave(NewsBean item) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;

		String insertSQL = "INSERT INTO News (Titolo, Argomento, Contenuto, Username, DataCaricamento) VALUES (?, ?, ?, ?, ?)";

		try {
			connection = ds.getConnection();
			ps = connection.prepareStatement(insertSQL);
			ps.setString(1, item.getTitolo());
			ps.setString(2, item.getArgomento());
			ps.setString(3, item.getContenuto());
			ps.setString(4, item.getUsername());
			ps.setDate(5, item.getDataCaricamento());

			ps.executeUpdate();
			System.out.println("News Salvata nel Database");
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
	public void doUpdate(NewsBean item) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doDelete(NewsBean item) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	private DataSource ds;
}
