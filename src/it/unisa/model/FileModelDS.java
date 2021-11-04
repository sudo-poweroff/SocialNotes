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

public class FileModelDS implements Model<FileBean> {
   
	
	public FileModelDS(DataSource ds) {
		this.ds=ds;
	}

	@Override
	public FileBean doRetrieveByKey(String fileName) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM Files WHERE FileName=?;";
		FileBean bean=new FileBean();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			ps.setString(1, fileName);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				bean.setFilename(rs.getString("FileName"));
				System.out.println(bean.getFilename());
				bean.setFormato(rs.getString("Formato"));			
				bean.setContenuto(rs.getBlob("Contenuto").getBinaryStream());
				if(bean.getContenuto()!=null)
				System.out.println("ciao33");
				bean.setDimensione(rs.getInt("Dimensione"));
				
			}
			else {
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
	
	public boolean isPresent(String fileName) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="SELECT Filename FROM Files WHERE FileName=?;";
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, fileName);
			rs=ps.executeQuery();
			if(rs.next())
				return true;
			else
				return false;
		}finally{
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
	public Collection<FileBean> doRetrieveAll() throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM Files;";
		Collection<FileBean> files=new LinkedList<FileBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			Utility.print("doRetrieveAll:"+ps.toString());
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				FileBean bean=new FileBean();
				bean.setFilename(rs.getString("FileName"));
				bean.setFormato(rs.getString("Formato"));
				bean.setContenuto((InputStream)rs.getBlob("Contenuto"));
				bean.setDimensione(rs.getInt("Dimensione"));
				files.add(bean);
				
		
				
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
		return files;
	}
	
	@Override
	public void doSave(FileBean item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO Files (FileName, Formato,Contenuto,Dimensione) VALUES (?,?,?,?)";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);

			preparedStatement.setString(1,item.getFilename());
			preparedStatement.setString(2, item.getFormato());
			preparedStatement.setBlob(3, item.getContenuto());
			preparedStatement.setInt(4, item.getDimensione());

			preparedStatement.executeUpdate();
			System.out.println("File inserito");

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
	}

	@Override
	public void doUpdate(FileBean item) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doDelete(FileBean item) throws SQLException {
		// TODO Auto-generated method stub
		
	}


	private DataSource ds;



	
}


