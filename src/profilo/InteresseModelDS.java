package profilo;

import materiale.CourseBean;
import org.checkerframework.checker.units.qual.A;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InteresseModelDS {
    private DataSource ds;
    public InteresseModelDS(DataSource ds){
        this.ds=ds;
    }

    public void doSave(InteresseBean interesse)throws SQLException {
        if(interesse==null)
            throw new NullPointerException();
        Connection con =null;
        PreparedStatement ps=null;
        String sql="INSERT INTO Interesse(Username,CodiceCorso,DataInserimento) values (?,?,?);";
        try{
            con=ds.getConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1,interesse.getUsername());
            ps.setInt(2,interesse.getCodiceCorso());
            ps.setDate(3,interesse.getDataInserimento());
            ps.executeUpdate();
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


    public ArrayList<InteresseBean> doRetrieveByUsername(String username) throws SQLException {
        if(username==null||username.equals(""))
            throw new NullPointerException();
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="SELECT * FROM Interesse WHERE Username=?";
        ArrayList<InteresseBean> interessi=new ArrayList<>();
        try{
            con=ds.getConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1,username);
            rs=ps.executeQuery();
            while(rs.next()){
                InteresseBean bean=new InteresseBean();
                bean.setUsername(rs.getString("Username"));
                bean.setCodiceCorso(rs.getInt("CodiceCorso"));
                bean.setDataInserimento(rs.getDate("DataInserimento"));
                interessi.add(bean);
            }
        }finally {
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
        return interessi;
    }
    public ArrayList<InteresseBean> doRetrieveByCorso(int codiceCorso) throws SQLException {
        if(codiceCorso<0)
            throw new NullPointerException();
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="SELECT * FROM Interesse WHERE CodiceCorso=?";
        ArrayList<InteresseBean> interessi=new ArrayList<>();
        try{
            con=ds.getConnection();
            ps=con.prepareStatement(sql);
            ps.setInt(1,codiceCorso);
            rs=ps.executeQuery();
            while(rs.next()){
                InteresseBean bean=new InteresseBean();
                bean.setUsername(rs.getString("Username"));
                bean.setCodiceCorso(rs.getInt("CodiceCorso"));
                bean.setDataInserimento(rs.getDate("DataInserimento"));
                interessi.add(bean);
            }
        }finally {
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
        return interessi;
    }
    public  void doDelete(String username,int codiceCorso) throws SQLException {
        if(username==null||username.equals("")||codiceCorso<0)
            throw new NullPointerException();
        Connection con=null;
        PreparedStatement ps=null;
        String sql="DELETE FROM Interesse WHERE Username=? AND CodiceCorso=?";
        try{
            con=ds.getConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setInt(2,codiceCorso);
            ps.executeUpdate();

        }finally {
                if(con!=null)
                    con.close();
        }
    }

    public ArrayList<CourseBean> doRetrieveNewInteressi(String Username) throws SQLException {
        if(Username==null||Username.equals(""))
            throw new IllegalArgumentException();
        ArrayList<CourseBean>newInteressi=new ArrayList<>();
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="SELECT CodiceCorso,Nome FROM corso WHERE codiceCorso NOT IN (SELECT CodiceCorso FROM interesse WHERE Username=?);";
        try{
            con=ds.getConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1,Username);
            rs=ps.executeQuery();
            while(rs.next()){
                CourseBean bean=new CourseBean();
                bean.setCodiceCorso(rs.getInt("CodiceCorso"));
                bean.setNome(rs.getString("Nome"));
                newInteressi.add(bean);
            }
        }finally {
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
        return newInteressi;
    }

    public ArrayList<CourseBean> getInteressi(String Username) throws SQLException {
        if(Username==null||Username.equals(""))
            throw new IllegalArgumentException();
        ArrayList<CourseBean>interessi=new ArrayList<>();
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="SELECT CodiceCorso,Nome FROM corso WHERE codiceCorso IN (SELECT CodiceCorso FROM interesse WHERE Username=?);";
        try{
            con=ds.getConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1,Username);
            rs=ps.executeQuery();
            while(rs.next()){
                CourseBean bean=new CourseBean();
                bean.setCodiceCorso(rs.getInt("CodiceCorso"));
                bean.setNome(rs.getString("Nome"));
                interessi.add(bean);
            }
        }finally {
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
        return interessi;
    }


}
