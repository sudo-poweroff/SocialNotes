package acquisto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import materiale.FeedbackBean;


public class PurchaseModelDS {
	private DataSource ds;

	public PurchaseModelDS(DataSource ds) {
		this.ds = ds;
	}
	
	public Collection<PurchaseBean> doRetrieveByUsername(String username)throws SQLException{
		if(username==null||username.equals(""))
			throw new NullPointerException();
		Connection con=null;
		PreparedStatement ps=null;		
		String selectSQL="SELECT * FROM Acquisto WHERE username = ?;";
		Collection<PurchaseBean> purchases=new LinkedList<PurchaseBean>();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			ps.setString(1,username);
		
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				PurchaseBean bean=new PurchaseBean();
				bean.setCodiceMateriale(rs.getInt("CodiceMateriale"));
				bean.setUsername(username);
				bean.setDataAcquisto(rs.getDate("DataAcquisto"));
				purchases.add(bean);
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
		return purchases;
	}
	public void doSave(PurchaseBean item) throws SQLException{
		if(item==null)
			throw new NullPointerException();
		Connection con=null;
		PreparedStatement ps=null;
		//ResultSet rs=null;
		String sql="INSERT INTO Acquisto (Username, CodiceMateriale, DataAcquisto) VALUES (?,?,?)";
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, item.getUsername());
			ps.setInt(2, item.getCodiceMateriale());
			ps.setDate(3, item.getDataAcquisto());
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
	}
