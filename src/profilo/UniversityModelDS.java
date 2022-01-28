package profilo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;


public class UniversityModelDS {
	
	 public UniversityModelDS(DataSource ds) {
		 this.ds=ds;
	 }


	public Collection<UniversityBean> doRetrieveAll() throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM Universita;";
		Collection<UniversityBean> universities=new LinkedList<UniversityBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			//Utility.print("doRetrieveAll:"+ps.toString());
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				UniversityBean bean=new UniversityBean();
				bean.setDenominazione(rs.getString("Denominazione"));
				bean.setIndirizzo(rs.getString("Indirizzo"));
				bean.setTelefono(rs.getString("Telefono"));
				bean.setEmail(rs.getString("Email"));
				bean.setDescrizione(rs.getString("Descrizione"));
				universities.add(bean);
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
		return universities;

	}

	
	private DataSource ds;
}
