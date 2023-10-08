package profilo;

import org.dbunit.Assertion;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

public class InteresseModelDSTest extends DataSourceBasedDBTestCase {
    private DataSource ds;
    private InteresseModelDS interesseModel;

    @Override
    protected DataSource getDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/Interesse.sql'");
        dataSource.setUser("supo-poweroff");
        dataSource.setPassword("09134");
        return dataSource;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/init/Interesse.xml"));
    }

    @Override
    protected DatabaseOperation getSetUpOperation(){
        return DatabaseOperation.REFRESH;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() {
        return DatabaseOperation.DELETE_ALL;
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception{}

    @Before
    public void setUp() throws Exception {
        super.setUp();
        ds=this.getDataSource();
        interesseModel=new InteresseModelDS(ds);
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testDoRetrieveByUsernamePresent() throws SQLException {
        ArrayList<InteresseBean> interessi=interesseModel.doRetrieveByUsername("sime00");
        ArrayList<InteresseBean> expected= new ArrayList<InteresseBean>();
        InteresseBean i2=new InteresseBean();
        i2.setUsername("sime00");
        i2.setCodiceCorso(3);
        expected.add(i2);
        InteresseBean i3=new InteresseBean();
        i3.setUsername("sime00");
        i3.setCodiceCorso(11);
        expected.add(i3);
        InteresseBean i1=new InteresseBean();
        i1.setUsername("sime00");
        i1.setCodiceCorso(46);
        expected.add(i1);

        assertEquals(expected.size(),interessi.size());
        for(int i=0; i<interessi.size();i++){
            assertEquals(interessi.get(i).getUsername(), expected.get(i).getUsername());
            assertEquals(interessi.get(i).getCodiceCorso(),expected.get(i).getCodiceCorso());
        }
    }

    @Test
    public void testDoRetrieveByUsernameNotPresent() throws SQLException {
        ArrayList<InteresseBean> interessi=interesseModel.doRetrieveByUsername("ab");
        assertEquals(interessi.size(),0);
    }

    @Test
    public void testDoRetrieveByUsernameNull() throws SQLException {
        boolean flag=false;
        try{
           interesseModel.doRetrieveByUsername(null);
        }catch (NullPointerException e){
            flag=true;
        }
        assertTrue(flag);

    }

    @Test
    public void testDoRetrieveByUsernameEmpty() throws SQLException {
        boolean flag=false;
        try{
            interesseModel.doRetrieveByUsername("");
        }catch (NullPointerException e){
            flag=true;
        }
        assertTrue(flag);
    }

    @Test
    public void testDoRetrieveByCorsoPresent() throws SQLException {
        ArrayList<InteresseBean> interessi=interesseModel.doRetrieveByCorso(3);
        ArrayList<InteresseBean> expected= new ArrayList<InteresseBean>();
        InteresseBean i1=new InteresseBean();
        i1.setUsername("sime00");
        i1.setCodiceCorso(3);
        expected.add(i1);
        assertEquals(expected.size(),interessi.size());
        for(int i=0; i<interessi.size();i++){
            assertEquals(interessi.get(i).getUsername(), expected.get(i).getUsername());
            assertEquals(interessi.get(i).getCodiceCorso(),expected.get(i).getCodiceCorso());
        }
    }

    @Test
    public void testDoRetrieveByCorsoNotPresent() throws SQLException {
        ArrayList<InteresseBean> interessi=interesseModel.doRetrieveByCorso(70);
        assertEquals(interessi.size(),0);
    }

    @Test
    public void testDoSave() throws Exception {
        InteresseBean interesse= new InteresseBean();
        interesse.setUsername("sime00");
        interesse.setCodiceCorso(20);
        interesseModel.doSave(interesse);
        ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/InteresseExpected.xml")).getTable("Interesse");
        ITable actual=this.getConnection().createDataSet().getTable("Interesse");
        Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
    }

    @Test
    public void testDoSaveAlreadyExisits(){
        InteresseBean interesse= new InteresseBean();
        interesse.setUsername("sime00");
        interesse.setCodiceCorso(3);
        boolean flag=false;
        try{
            interesseModel.doSave(interesse);
        }catch (SQLException e){
            flag=true;
        }
        assertTrue(flag);
    }

    @Test
    public void testDoSaveNull() throws SQLException {
        boolean flag=false;
        try{
            interesseModel.doSave(null);
        }catch(NullPointerException e){flag=true;}
        assertTrue(flag);
    }

    @Test
    public void testDoDeleteInteressePresent() throws Exception {
        interesseModel.doDelete("sime00",3);
        ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/InteresseExpectedDelete.xml")).getTable("Interesse");
        ITable actual=this.getConnection().createDataSet().getTable("Interesse");
        Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
    }

    @Test
    public void testDoDeleteInteresseCorsoNotPresent() throws Exception {
        interesseModel.doDelete("sime00",70);
        ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/init/Interesse.xml")).getTable("Interesse");
        ITable actual=this.getConnection().createDataSet().getTable("Interesse");
        Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
    }

    @Test
    public void testDoDeleteInteresseUsernameNotPresent() throws Exception {
        interesseModel.doDelete("ab",3);
        ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/init/Interesse.xml")).getTable("Interesse");
        ITable actual=this.getConnection().createDataSet().getTable("Interesse");
        Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
    }

    @Test
    public void testDoDeleteInteresseUsernameNull() throws SQLException {
        boolean flag=false;
        try{
            interesseModel.doDelete(null,3);
        }catch (NullPointerException e){
            flag=true;
        }
        assertTrue(flag);

    }

    @Test
    public void testDoDeleteInteresseUsernameEmpty() throws SQLException {
        boolean flag=false;
        try{
            interesseModel.doDelete("",3);
        }catch (NullPointerException e){
            flag=true;
        }
        assertTrue(flag);
    }

    @Test
    public void testDoDeleteInteresseNotPresent() throws Exception {
        interesseModel.doDelete("ab",3);
        ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/init/Interesse.xml")).getTable("Interesse");
        ITable actual=this.getConnection().createDataSet().getTable("Interesse");
        Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
    }
}
