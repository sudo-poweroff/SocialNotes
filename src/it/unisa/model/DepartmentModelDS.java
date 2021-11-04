package it.unisa.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.unisa.utils.Utility;

public class DepartmentModelDS implements Model<DepartmentBean> {
	
	public DepartmentModelDS(DataSource ds) {
		this.ds=ds;
	}

	@Override
	public DepartmentBean doRetrieveByKey(String code) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<DepartmentBean> doRetrieveAll() throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM Dipartimento;";
		Collection<DepartmentBean> departments=new LinkedList<DepartmentBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			//Utility.print("doRetrieveAll:"+ps.toString());
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				DepartmentBean bean=new DepartmentBean();
				bean.setNome(rs.getString("Nome"));
				bean.setDenominazione(rs.getString("Denominazione"));
				bean.setDescrizione(rs.getString("Descrizione"));
				departments.add(bean);
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
		return departments;
	
	}

	public Collection<DepartmentBean> doRetrieveByDenominazione(String denominazione) throws SQLException{
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM Dipartimento WHERE Denominazione = ?;";
		Collection<DepartmentBean> departments=new LinkedList<DepartmentBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			ps.setString(1, denominazione);
			//Utility.print("doRetrieveAll:"+ps.toString());
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				DepartmentBean bean=new DepartmentBean();
				bean.setNome(rs.getString("Nome"));
				bean.setDenominazione(rs.getString("Denominazione"));
				bean.setDescrizione(rs.getString("Descrizione"));
				departments.add(bean);
				System.out.println("DIPARTIMENTO: "+bean.getNome());
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
		return departments;
	}
	
	@Override
	public void doSave(DepartmentBean item) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doUpdate(DepartmentBean item) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doDelete(DepartmentBean item) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
	private DataSource ds;
}
