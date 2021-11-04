package it.unisa.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.unisa.utils.Utility;

public class ParticipationModelDS implements Model<ParticipationBean>{

	public ParticipationModelDS(DataSource ds) {
		this.ds=ds;
	}
	
	@Override
	public ParticipationBean doRetrieveByKey(String code) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ParticipationBean> doRetrieveAll() throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM Partecipazione;";
		Collection<ParticipationBean> participation=new LinkedList<ParticipationBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			Utility.print("doRetrieveAll:"+ps.toString());
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				ParticipationBean bean=new ParticipationBean();
				bean.setUsername(rs.getString("Username"));
				bean.setChatID(rs.getInt("ChatID"));
				participation.add(bean);
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
		return participation;
	}

	@Override
	public void doSave(ParticipationBean item) throws SQLException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				Connection con=null;
				PreparedStatement ps=null;
				//ResultSet rs=null;
				String sql="INSERT INTO Partecipazione (Username,ChatID) VALUES (?,?)";
				try {
					con=ds.getConnection();
					ps=con.prepareStatement(sql);
					ps.setString(1, item.getUsername());
					ps.setInt(2, item.getChatID());
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
	public void doUpdate(ParticipationBean item) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doDelete(ParticipationBean item) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	private DataSource ds;
}
