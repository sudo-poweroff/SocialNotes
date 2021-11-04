package it.unisa.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.unisa.utils.Utility;

public class RoleModelDS implements Model<RoleBean>{

	public RoleModelDS(DataSource ds) {
		this.ds=ds;
	}


	@Override
	public RoleBean doRetrieveByKey(String code) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<RoleBean> doRetrieveAll() throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM Ruolo;";
		Collection<RoleBean> roles=new LinkedList<RoleBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			Utility.print("doRetrieveAll:"+ps.toString());
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				RoleBean bean=new RoleBean();
				bean.setIdRuolo(rs.getInt("IDRuolo"));
				bean.setRuolo(rs.getString("Ruolo"));
				roles.add(bean);
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
		return roles;
	}


	@Override
	public void doSave(RoleBean item) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doUpdate(RoleBean item) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doDelete(RoleBean item) throws SQLException {
		// TODO Auto-generated method stub

	}


	private DataSource ds;
}
