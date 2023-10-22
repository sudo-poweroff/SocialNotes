package materiale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.unisa.utils.Utility;

public class CourseModelDS {

	public CourseModelDS(DataSource ds) {
		this.ds=ds;
	}


	public CourseBean doRetrieveByKey(String code) throws SQLException {
		if(code==null||code.equals(""))
			throw new NullPointerException();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="SELECT * FROM Corso WHERE CodiceCorso=  ?;";
	
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			
			int key = Integer.parseInt(code);
			
			ps.setInt(1, key);
			rs=ps.executeQuery();
			if(rs.next()) {
				CourseBean bean = new CourseBean();
				bean.setCodiceCorso(rs.getInt("CodiceCorso"));
				bean.setNome(rs.getString("Nome"));
				bean.setNomeDipartimento(rs.getString("NomeDipartimento"));
				bean.setDenominazione(rs.getString("Denominazione"));
				
				return bean;
			}
				



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) 
					con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
				
		}
		
		
		return null;
	}


	public int doRetrieveByName(String name)  {
		if(name==null||name.equals(""))
			throw new NullPointerException();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="SELECT CodiceCorso FROM Corso WHERE Nome=?;";
		System.out.println(name);
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, name);
			rs=ps.executeQuery();
			if(rs.next())
				return rs.getInt("CodiceCorso");


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) 
					con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
				
		}
		return -1;
	}


	public void doSave(CourseBean item) throws SQLException {
		if(item==null)
			throw new NullPointerException();
		Connection con=null;
		PreparedStatement ps=null;
		//ResultSet rs=null;
		String sql="INSERT INTO Corso (Nome,NomeDipartimento,Denominazione) VALUES (?,?,?)";
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, item.getNome());
			ps.setString(2, item.getNomeDipartimento());
			ps.setString(3,item.getDenominazione());
			System.out.println(ps.executeUpdate());


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

	public ArrayList<CourseBean> doRetrieveAll(){
		ArrayList<CourseBean> courses=new ArrayList<>();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="SELECT * FROM Corso;";
		try{
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				CourseBean bean=new CourseBean();
				bean.setCodiceCorso(rs.getInt("CodiceCorso"));
				bean.setNome(rs.getString("Nome"));
				bean.setNomeDipartimento(rs.getString("NomeDipartimento"));
				bean.setDenominazione(rs.getString("Denominazione"));
				courses.add(bean);
			}
		}
		finally {
			try {
				if(rs!=null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			return courses;
		}
	}


	private DataSource ds;
}
