package it.unisa.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.unisa.utils.Utility;

public class FormedModelDS implements Model<FormedBean> {

	public FormedModelDS(DataSource ds) {
		this.ds=ds;
	}
	
	
	@Override
	public FormedBean doRetrieveByKey(String code) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Collection<FormedBean> doRetrieveAll() throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM Formato;";
		Collection<FormedBean> formed=new LinkedList<FormedBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			Utility.print("doRetrieveAll:"+ps.toString());
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				FormedBean bean=new FormedBean();
				bean.setNome(rs.getString("Nome"));
				bean.setDenominazione(rs.getString("Denominazione"));
				bean.setCodiceCorso(rs.getInt("CodiceCorso"));
				formed.add(bean);
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
		return formed;
	}
	



	@Override
	public void doSave(FormedBean item) throws SQLException {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void doUpdate(FormedBean item) throws SQLException {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void doDelete(FormedBean item) throws SQLException {
		// TODO Auto-generated method stub
		
	}


	private DataSource ds;

}