package it.unisa.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.unisa.utils.Utility;

public class CourseModelDS implements Model<CourseBean> {

	public CourseModelDS(DataSource ds) {
		this.ds=ds;
	}


	@Override
	public CourseBean doRetrieveByKey(String code) throws SQLException {
		// TODO Auto-generated method stub
		
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
			else {
				CourseBean course=new CourseBean();
				course.setNome(name);
				CourseModelDS newCourse=new CourseModelDS(ds);
				newCourse.doSave(course);
				rs=ps.executeQuery();
				if(rs.next())
					return rs.getInt("CodiceCorso");
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
		return -1;
	}

	@Override
	public Collection<CourseBean> doRetrieveAll() throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM Corso;";
		Collection<CourseBean> courses=new LinkedList<CourseBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			Utility.print("doRetrieveAll:"+ps.toString());
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				CourseBean bean=new CourseBean();
				bean.setCodiceCorso(rs.getInt("CodiceCorso"));
				bean.setNome(rs.getString("Nome"));
				courses.add(bean);
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
		return courses;
	}

	@Override
	public void doSave(CourseBean item) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		//ResultSet rs=null;
		String sql="INSERT INTO Corso (Nome) VALUES (?)";
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, item.getNome());
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

	@Override
	public void doUpdate(CourseBean item) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doDelete(CourseBean item) throws SQLException {
		// TODO Auto-generated method stub

	}

	private DataSource ds;
}
