package profilo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;


import javax.sql.DataSource;

import it.unisa.utils.Utility;

public class PaymentMethodModelDS {

	public PaymentMethodModelDS(DataSource ds) {
		this.ds=ds;
	}

	public PaymentMethodBean doRetrieveByKey(String code) throws SQLException {
		if(code==null||code.equals("")||code.length()!=16)
			throw new NullPointerException();
		Connection con=null;
		PreparedStatement ps=null;
		String sql="SELECT * FROM MetodoPagamento WHERE NumeroCarta=?;";
		ResultSet rs=null;
		PaymentMethodBean bean=new PaymentMethodBean();
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1,code);
			rs=ps.executeQuery();
			if(rs.next()) {
				bean.setNumeroCarta(rs.getString("NumeroCarta"));
				bean.setDataScadenza(rs.getDate("DataScadenza"));
				bean.setNomeIntestatario(rs.getString("NomeIntestatario"));
				bean.setCognomeIntestatario(rs.getString("CognomeIntestatario"));
				bean.setUsername(rs.getString("Username"));
			}
		}
		finally {
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
		return bean;
	}


	public Collection<PaymentMethodBean> doRetrieveByUsername(String username) throws SQLException{
		if(username==null||username.equals(""))
			throw new NullPointerException();
		Connection con=null;
		PreparedStatement ps=null;
		String sql="SELECT * FROM MetodoPagamento WHERE Username=?;";
		Collection<PaymentMethodBean> cards=new LinkedList<PaymentMethodBean>();
		ResultSet rs=null;
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			rs=ps.executeQuery();
			while(rs.next()) {
				PaymentMethodBean bean=new PaymentMethodBean();
				bean.setNumeroCarta(rs.getString("NumeroCarta"));
				bean.setDataScadenza(rs.getDate("DataScadenza"));
				bean.setNomeIntestatario(rs.getString("NomeIntestatario"));
				bean.setCognomeIntestatario(rs.getString("CognomeIntestatario"));
				bean.setUsername(rs.getString("Username"));
				cards.add(bean);
			}
		}
		finally {
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
		return cards;
	}


	public Collection<PaymentMethodBean> doRetrieveAll() throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		String selectSQL="SELECT * FROM MetodoPagamento;";
		Collection<PaymentMethodBean> cards=new LinkedList<PaymentMethodBean>();
		ResultSet rs=null;
		try {
			con=ds.getConnection();
			ps=con.prepareStatement(selectSQL);
			Utility.print("doRetrieveAll:"+ps.toString());
			rs=ps.executeQuery();
			while(rs.next()) {
				PaymentMethodBean bean=new PaymentMethodBean();
				bean.setNumeroCarta(rs.getString("NumeroCarta"));
				bean.setDataScadenza(rs.getDate("DataScadenza"));
				bean.setNomeIntestatario(rs.getString("NomeIntestatario"));
				bean.setCognomeIntestatario(rs.getString("CognomeIntestatario"));
				bean.setUsername(rs.getString("Username"));
				cards.add(bean);
			}
		}
		finally {
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
		return cards;
	}

	public void doSave(PaymentMethodBean item) throws SQLException {
		if(item==null)
			throw new NullPointerException();
		Connection connection = null;
		PreparedStatement ps = null;

		String insertSQL = "INSERT INTO MetodoPagamento (NumeroCarta, DataScadenza,NomeIntestatario,CognomeIntestatario,Username) VALUES (?,?,?,?,?)";

		try {
			connection = ds.getConnection();
			ps = connection.prepareStatement(insertSQL);

			ps.setString(1,item.getNumeroCarta());
			ps.setDate(2, item.getDataScadenza());
			ps.setString(3, item.getNomeIntestatario());
			ps.setString(4, item.getCognomeIntestatario());
			ps.setString(5,item.getUsername());
			ps.executeUpdate();
			System.out.println("Metodo di pagamento inserito");

		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}

	}

	public void doDeleteByNumber(String numeroCarta)throws SQLException{
		if(numeroCarta==null||numeroCarta.equals("")||numeroCarta.length()!=16)
			throw new NullPointerException();
		Connection connection = null;
		PreparedStatement ps = null;
		String sql="DELETE FROM MetodoPagamento WHERE NumeroCarta=?";
		try {
			connection = ds.getConnection();
			ps = connection.prepareStatement(sql);

			ps.setString(1,numeroCarta);
			ps.executeUpdate();
			System.out.println("Metodo di pagamento eliminato");

		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
	}

	private DataSource ds;
}
