package materiale;


import java.sql.SQLException;
import java.sql.Timestamp;
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


public class FeedbackModelDSTest extends DataSourceBasedDBTestCase{

	private DataSource ds;
	private FeedbackModelDS feedback;
	
	@Override
	protected DataSource getDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/Feedback.sql'");
        dataSource.setUser("supo-poweroff");
        dataSource.setPassword("09134");
        return dataSource;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/init/Feedback.xml"));
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
		feedback=new FeedbackModelDS(ds);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	//NON FUNZIONA PERCH� la query crea le view
	
	@Test
	public void testGetFeedbackByUsernamePresente() throws SQLException{
		int valutazioneMedia=feedback.getFeedbackByUsername("fry");
		assertEquals(2,valutazioneMedia);
		//fail("non funziona perch� la query crea le view");
	}
	
	
	
	@Test
	public void testDoRetrieveByKeyMaterial()throws Exception{
		Collection<FeedbackBean> result= feedback.doRetrieveByKeyMaterial(1);
		ArrayList<FeedbackBean> rs = new ArrayList<>(result);
		ArrayList<FeedbackBean> expected = new ArrayList<>();
		FeedbackBean b1 = new FeedbackBean();
		b1.setCodiceMateriale(1);
		b1.setUsername("califano87");
		b1.setDataFeed(Timestamp.valueOf("2021-01-20 01:52:41"));
		b1.setCommento("Davvero ottimi appunti");
		b1.setValutazione(4);		
		FeedbackBean b2 = new FeedbackBean();
		b2.setCodiceMateriale(1);
		b2.setUsername("fry");
		b2.setDataFeed(Timestamp.valueOf("2021-11-20 01:52:41"));
		b2.setCommento("truffatore");
		b2.setValutazione(0);
		
		expected.add(b1);
		expected.add(b2);
		
		for(int i=0;i<result.size();i++) {
			assertEquals(rs.get(i).getCodiceMateriale(),expected.get(i).getCodiceMateriale());
			assertEquals(rs.get(i).getCommento(),expected.get(i).getCommento());
			assertEquals(rs.get(i).getUsername(),expected.get(i).getUsername());
			assertEquals(rs.get(i).getValutazione(),expected.get(i).getValutazione());
			assertEquals(rs.get(i).getDataFeed(),expected.get(i).getDataFeed());
		}
	}
	
	@Test
	public void testDoRetrieveByKeyMaterialNonPresente()throws Exception{
		ds.getConnection().prepareStatement("DELETE FROM FEEDBACK WHERE CodiceMateriale=1").execute();
		ds.getConnection().prepareStatement("DELETE FROM FEEDBACK WHERE CodiceMateriale=2").execute();
		ds.getConnection().prepareStatement("DELETE FROM FEEDBACK WHERE CodiceMateriale=3").execute();
		
		Collection<FeedbackBean> result= feedback.doRetrieveByKeyMaterial(40);
		assertEquals(result.size(),0);		
	}
	
	@Test
	public void testDoSave() throws Exception{
		FeedbackBean bean= new FeedbackBean();
		bean.setCodiceMateriale(6);
		bean.setUsername("fry");
		bean.setDataFeed(Timestamp.valueOf("2021-01-20 01:52:45"));
		bean.setCommento("Mi sono trovato bene con questi appunti");
		bean.setValutazione(4);	
		feedback.doSave(bean);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/FeedbackExpected.xml")).getTable("Feedback");
		ITable actual=this.getConnection().createDataSet().getTable("Feedback");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
	}
	
	@Test
	public void testDoSaveAlreadyPresent() throws Exception{
		FeedbackBean bean= new FeedbackBean();
		bean.setCodiceMateriale(1);
		bean.setUsername("califano87");
		bean.setDataFeed(Timestamp.valueOf("2021-01-20 01:52:41"));
		bean.setCommento("Davvero ottimi appunti");
		bean.setValutazione(4);	
		boolean flag = false;
		try {
			feedback.doSave(bean);
		}catch(SQLException e) {
			flag=true;
		}
		assertEquals(true,flag);
	}
	
	@Test
	public void testDoSaveNull() throws Exception{
		boolean flag = false;
		try {
			feedback.doSave(null);
		}catch(NullPointerException e) {
			flag=true;	
		}
		assertEquals(true,flag);
	}

}
