package profilo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import materiale.FeedbackBean;

public class ReportModelDS {
	private DataSource ds;

	public ReportModelDS(DataSource ds) {
		this.ds = ds;
	}
	
	public Collection<ReportBean> doRetrieveAll() throws SQLException{
		
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM Segnalazione;";
		Collection<ReportBean> reports=new LinkedList<ReportBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				ReportBean bean=new ReportBean();
                bean.setCodiceSegnalazione(rs.getInt("CodiceSegnalazione"));
                bean.setMotivo(rs.getString("Motivo"));
                bean.setStato(rs.getInt("Stato"));
                bean.setUsername(rs.getString("Username"));
                reports.add(bean);
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
		
		
		return reports;
	}
	
	public Collection<ReportBean> doRetrieveArchived() throws SQLException{
		
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM Segnalazione WHERE Stato=1;";
		Collection<ReportBean> reports=new LinkedList<ReportBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				ReportBean bean=new ReportBean();
                bean.setCodiceSegnalazione(rs.getInt("CodiceSegnalazione"));
                bean.setMotivo(rs.getString("Motivo"));
                bean.setStato(rs.getInt("Stato"));
                bean.setUsername(rs.getString("Username"));
                reports.add(bean);
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
		
		
		return reports;
	}
	
	public Collection<ReportBean> doRetrieveNotArchived() throws SQLException{
		
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM Segnalazione WHERE Stato=0;";
		Collection<ReportBean> reports=new LinkedList<ReportBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				ReportBean bean=new ReportBean();
                bean.setCodiceSegnalazione(rs.getInt("CodiceSegnalazione"));
                bean.setMotivo(rs.getString("Motivo"));
                bean.setStato(rs.getInt("Stato"));
                bean.setUsername(rs.getString("Username"));
                reports.add(bean);
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
		
		
		return reports;
	}
	
	public void doSave (ReportBean item) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		//ResultSet rs=null;
		String sql="INSERT INTO Segnalazione (Stato, Motivo, Username) VALUES (?,?,?)";
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setInt(1, item.getStato());
			ps.setString(2, item.getMotivo());
			ps.setString(3, item.getUsername());
			ps.executeUpdate();


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
	
	public void removeReport(int codiceSegnalazione) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		String sql="DELETE FROM Segnalazione WHERE CodiceSegnalazioe = ?;";
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setInt(1, codiceSegnalazione);
			ps.executeUpdate();
			System.out.println("Amico eliminato");
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
	
	public void archiveReport(int codiceSegnalazione) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		String sql="UPDATE Segnalazione SET Stato = 1 WHERE CodiceSegnalazioe = ?;";
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setInt(1, codiceSegnalazione);
			ps.executeUpdate();
			System.out.println("Amico eliminato");
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
	
}
