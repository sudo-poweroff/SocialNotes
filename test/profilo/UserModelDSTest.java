package profilo;


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


public class UserModelDSTest extends DataSourceBasedDBTestCase{

	private DataSource ds;
	private UserModelDS userModel;
	
	
	@Override
	protected DataSource getDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/Utente.sql'");
        dataSource.setUser("supo-poweroff");
        dataSource.setPassword("09134");
        return dataSource;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/init/Utente.xml"));
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
		userModel=new UserModelDS(ds);
	}
	
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
//TEST checkLogin()
	@Test
	public void testCheckLoginDatiPresenti() throws SQLException {
		UserBean us=userModel.checkLogin("sime00","Sime00");
		assertEquals(us.getUsername(), "sime00");
		assertEquals(us.getNome(), "Simone");
		assertEquals(us.getCognome(),"Della Porta");
		assertEquals(us.getEmail(), "sime@gmail.com");
		assertEquals(us.getPass(), "Sime1");
		assertEquals(us.getDataNascita(), "2000-10-27");
		assertEquals(us.getCoin(), 1200);
		assertEquals(us.getBan(), "2022-03-01");
		assertEquals(us.getDenominazione(), "Universita degli studi di Salerno");
		assertEquals(us.getDipName(), "Dipartimento di Informatica");
		assertEquals(us.getRuolo(), 0);
		assertNull(us.getImg());
	}

	
	
	
	
	
	
	
	
	
	
	
	
	//TEST doRetrieveByUsername
	@Test
	public void testDoRetrieveByUsernamePresente() throws SQLException {
		UserBean us=userModel.doRetrieveByUsername("sime00");
		assertEquals(us.getUsername(), "sime00");
		assertEquals(us.getNome(), "Simone");
		assertEquals(us.getCognome(),"Della Porta");
		assertEquals(us.getEmail(), "sime@gmail.com");
		assertEquals(us.getPass(), "Sime1");
		assertEquals(us.getDataNascita(),Date.valueOf("2000-10-27"));
		assertEquals(us.getCoin(), 1200);
		assertEquals(us.getBan(), Date.valueOf("2022-03-01"));
		assertEquals(us.getDenominazione(), "Universita degli studi di Salerno");
		assertEquals(us.getDipName(), "Dipartimento di Informatica");
		assertEquals(us.getRuolo(), 0);
		assertNull(us.getImg());
	}

}
