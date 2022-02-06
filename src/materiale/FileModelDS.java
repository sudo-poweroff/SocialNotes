package materiale;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.unisa.utils.Utility;

public class FileModelDS  {
   
	
	public FileModelDS(DataSource ds) {
		this.ds=ds;
	}

	public FileBean doRetrieveByKey(int IdFile) throws SQLException {
		if(IdFile<0)
			throw new NullPointerException();
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM Files WHERE IdFile=?;";
		FileBean bean=new FileBean();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			ps.setInt(1, IdFile);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				bean.setIdFile(rs.getInt("IdFile"));
				bean.setFilename(rs.getString("FileName"));
				System.out.println(bean.getFilename());
				bean.setFormato(rs.getString("Formato"));			
				bean.setContenuto(rs.getBlob("Contenuto").getBinaryStream());
				if(bean.getContenuto()!=null)
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
	
	
	
	public void doSave(FileBean item) throws SQLException {
		if(item==null)
			throw new NullPointerException();
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
	
	
	public int doRetrieveKey()throws SQLException{
		Connection con=null;
		PreparedStatement ps=null;
		String sql="SELECT IdFile FROM Files ORDER BY IdFile;";
		ResultSet rs=null;
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.last()) {
				return rs.getInt("IdFile");
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


	private DataSource ds;



	
}


