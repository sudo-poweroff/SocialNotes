package chat;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.sql.Timestamp;

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

public class ParticipationModelDSTest extends DataSourceBasedDBTestCase{

	private DataSource ds;
	private ParticipationModelDS partecipazione;
	
	@Override
	protected DataSource getDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/Partecipazione.sql'");
        dataSource.setUser("supo-poweroff");
        dataSource.setPassword("09134");
        return dataSource;
	}
	
	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/init/Partecipazione.xml"));
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
		partecipazione=new ParticipationModelDS(ds);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testDoSave() throws Exception{
		ParticipationBean bean= new ParticipationBean();
		bean.setChatID(18);
		bean.setUsername("fry");
		partecipazione.doSave(bean);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/PartecipazioneExpected.xml")).getTable("Partecipazione");
		ITable actual=this.getConnection().createDataSet().getTable("Partecipazione");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
	}
	
	@Test
	public void testDoSaveAlreadyPresent() throws Exception{
		ParticipationBean bean= new ParticipationBean();
		bean.setChatID(65);
		bean.setUsername("fry");		
		boolean flag = false;
		try {
			partecipazione.doSave(bean);
		}catch(SQLException e) {
			flag=true;	
		}
		assertEquals(true,flag);
	}
	
	@Test
	public void testDoSaveNull() throws Exception{
		boolean flag = false;
		try {
			partecipazione.doSave(null);
		}catch(NullPointerException e) {
			flag=true;	
		}
		assertEquals(true,flag);
	}

}
