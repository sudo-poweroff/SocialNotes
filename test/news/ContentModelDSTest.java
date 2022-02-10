package news;

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

public class ContentModelDSTest extends DataSourceBasedDBTestCase{
	
	private DataSource ds;
	private ContentModelDS content;
	
	@Override
	protected DataSource getDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/Contenuto.sql'");
        dataSource.setUser("supo-poweroff");
        dataSource.setPassword("09134");
        return dataSource;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/init/Contenuto.xml"));
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
		content=new ContentModelDS(ds);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testDoRetrieveByCodiceNews()throws Exception{
		Collection<Integer> result= content.doRetrieveByCodiceNews(24);
		ArrayList<Integer> rs = new ArrayList<>(result);
		ArrayList<Integer> expected = new ArrayList<>();	
		
		expected.add(2);
		expected.add(4);
		
		for(int i=0;i<result.size();i++) {
			assertEquals(rs.get(i),expected.get(i));

		}
	}
	
	@Test
	public void testDoRetrieveByCodiceNewsNonPresente()throws Exception{
		Collection<Integer> result= content.doRetrieveByCodiceNews(50);
		assertEquals(result.size(),0);
	}
	
	
	@Test
	public void testDoRetrieveByCodiceNewsNonValido()throws Exception{
		boolean flag=false;
		try {
			content.doRetrieveByCodiceNews(-1);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}
	
	
	@Test
	public void testDoSave() throws Exception{
		ContentBean bean= new ContentBean();
		bean.setCodiceNews(41);
		bean.setIdFile(6);
		content.doSave(bean);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/ContenutoExpected.xml")).getTable("Contenuto");
		ITable actual=this.getConnection().createDataSet().getTable("Contenuto");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
	}
	
	@Test
	public void testDoSaveAlreadyPresent() throws Exception{
		ContentBean bean= new ContentBean();
		bean.setCodiceNews(24);
		bean.setIdFile(4);
		boolean flag=false;
		try {
			content.doSave(bean);
		}catch(SQLException e) {
			flag=true;
		}
		assertEquals(true,flag);
	}
	
	@Test
	public void testDoSaveNull() throws Exception{
		boolean flag = false;
		try {
			content.doSave(null);
		}catch(NullPointerException e) {
			flag=true;	
		}
		assertEquals(true,flag);
	}

}
