package news;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.unisa.utils.Utility;

public class ContentModelDS {
	
	public ContentModelDS(DataSource ds) {
		this.ds=ds;
	}

	/**
	 * 
	 * @param codiceNews
	 * @return Restituisce una collezione di stringhe corrispondenti ai nomi dei file che ha quella news
	 * @throws SQLException
	 */
	public Collection<Integer> doRetrieveByCodiceNews(int codiceNews)throws SQLException{
		if(codiceNews<0)
			throw new NullPointerException();
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT IdFile FROM Contenuto WHERE CodiceNews = ? ;";
		Collection<Integer> fileNames=new LinkedList<Integer>();
		try {
			con=ds.getConnection();	
			ps=con.prepareStatement(selectSQL);
			ps.setInt(1, codiceNews);
			Utility.print("doRetrieveByCodiceNews:"+ps.toString());
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				fileNames.add(rs.getInt("IdFile"));
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
		return fileNames;
	}


	public void doSave(ContentBean item) throws SQLException {
		if(item==null)
			throw new NullPointerException();
		Connection con = null;
		PreparedStatement ps = null;
		String sql="INSERT INTO Contenuto (CodiceNews,IdFile) values (?,?);";
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setInt(1, item.getCodiceNews());
			ps.setInt(2, item.getIdFile());
			ps.executeUpdate();
		}finally {
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


	private DataSource ds;
}
