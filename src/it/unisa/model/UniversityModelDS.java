package it.unisa.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.unisa.utils.Utility;

public class UniversityModelDS implements Model<UniversityBean>{
	
	 public UniversityModelDS(DataSource ds) {
		 this.ds=ds;
	 }

	@Override
	public UniversityBean doRetrieveByKey(String code) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
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
				bean.setLogo(rs.getBlob("Logo"));
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

	@Override
	public void doSave(UniversityBean item) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doUpdate(UniversityBean item) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doDelete(UniversityBean item) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
	
	private DataSource ds;
}
