package chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;


public class ParticipationModelDS {

	public ParticipationModelDS(DataSource ds) {
		this.ds=ds;
	}


	public void doSave(ParticipationBean item) throws SQLException {
		if(item==null)
			throw new NullPointerException();
		Connection con=null;
		PreparedStatement ps=null;
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


	private DataSource ds;
}
