package acquisto;


import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.DataSourceBasedDBTestCase;
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


public class PurchaseModelDSTest extends DataSourceBasedDBTestCase  {
	
	private DataSource ds;
	private PurchaseModelDS purchase;
	
	
	@Override
	protected DataSource getDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/Acquisto.sql'");
        dataSource.setUser("supo-poweroff");
        dataSource.setPassword("09134");
        return dataSource;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/init/Acquisto.xml"));
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
		purchase=new PurchaseModelDS(ds);
	}
	
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	//TEST doRetrieveByUsername
	@Test
	public void testDoRetrieveByUsernamePresente() throws SQLException {
		Collection<PurchaseBean> result=new ArrayList<>();
		result=purchase.doRetrieveByUsername("Fry");
		ArrayList<PurchaseBean> rs=new ArrayList<>(result);
		ArrayList<PurchaseBean> aspected=new ArrayList<>();
		PurchaseBean b1=new PurchaseBean();
		b1.setUsername("Fry");
		b1.setCodiceMateriale(47);
		b1.setDataAcquisto(Date.valueOf("2022-01-31"));
		aspected.add(b1);
		PurchaseBean b2=new PurchaseBean();
		b2.setUsername("Fry");
		b2.setCodiceMateriale(49);
		b2.setDataAcquisto(Date.valueOf("2022-01-30"));
		aspected.add(b2);
		for(int i=0;i<result.size();i++) {
			assertEquals(rs.get(i).getCodiceMateriale(),aspected.get(i).getCodiceMateriale());
			assertEquals(rs.get(i).getUsername(),aspected.get(i).getUsername());
			assertEquals(rs.get(i).getDataAcquisto(),aspected.get(i).getDataAcquisto());
			
		}
	}
	
	
	@Test
	public void testDoRetrieveByUsernameNonPresente() throws SQLException {
		Collection<PurchaseBean> result=new ArrayList<>();
		result=purchase.doRetrieveByUsername("nik");
		assertEquals(result.size(), 0);
	}
	
	
	@Test
	public void testDoRetrieveByUsernameVuoto() throws SQLException {
		Collection<PurchaseBean> result=new ArrayList<>();
		try {
		result=purchase.doRetrieveByUsername("");
		}catch(NullPointerException e) {
			assertEquals(result.size(), 0);
		}
	}
	

	@Test
	public void testDoRetrieveByUsernameNull() throws SQLException {
		Collection<PurchaseBean> result=new ArrayList<>();
		try {
		result=purchase.doRetrieveByUsername(null);
		}catch(NullPointerException e) {
			assertEquals(result.size(), 0);
		}
	}
	
//TEST doSave	
	@Test
	public void testDoSave() throws Exception{
		PurchaseBean bean=new PurchaseBean();
		bean.setUsername("sime00");
		bean.setCodiceMateriale(50);
		bean.setDataAcquisto(Date.valueOf("2022-02-06"));
		purchase.doSave(bean);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/AcquistoExpected.xml")).getTable("Acquisto");
		ITable actual=this.getConnection().createDataSet().getTable("Acquisto");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
	}
	
	
	@Test
	public void testDoSaveAlreadyPresent() throws Exception{
		PurchaseBean bean=new PurchaseBean();
		bean.setUsername("sime00");
		bean.setCodiceMateriale(52);
		bean.setDataAcquisto(Date.valueOf("2022-02-03"));
		boolean flag=false;
		try {
		purchase.doSave(bean);
		}catch(SQLException e) {
			flag=true;
		}
		assertEquals(flag, true);
	}
	
	
	
	@Test
	public void testDoSaveBeanNull() throws Exception{
		boolean flag=false;
		try {
		purchase.doSave(null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertEquals(flag, true);
	}

	
}
