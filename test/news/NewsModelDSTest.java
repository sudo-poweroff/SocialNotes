package news;

import java.sql.Date;
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

import chat.MessageBean;

public class NewsModelDSTest extends DataSourceBasedDBTestCase{
	
	private DataSource ds;
	private NewsModelDS news;
	
	@Override
	protected DataSource getDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/News.sql'");
        dataSource.setUser("supo-poweroff");
        dataSource.setPassword("09134");
        return dataSource;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/init/News.xml"));
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
		news=new NewsModelDS(ds);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testDoRetrieveAll() throws Exception{
		Collection<NewsBean> result= news.doRetrieveAll();
		ArrayList<NewsBean> rs = new ArrayList<>(result);
		ArrayList<NewsBean> expected = new ArrayList<>();
		NewsBean b1 = new NewsBean();
		b1.setCodiceNews(24);
		b1.setTitolo("Ateneo | UNISA Studenti Vax Day");
		b1.setArgomento("8 settembre (dalle 14 alle 20) al Centro Vaccinale Ruggi");
		b1.setContenuto("Nell ottica di un auspicabile ripresa della vita universitaria in presenza, mercoledi 8 settembre, dalle ore 14.00 e fino alle ore 20.00, presso il Centro Vaccinale dell A.O.U. San Giovanni di Dio e Ruggi d Aragona, si terra l UNISA STUDENTI VAX DAY, una seduta di vaccinazione riservata agli studenti dell Ateneo.");
		b1.setUsername("NewsManager");
		b1.setDataCaricamento(Date.valueOf("2021-09-06"));
		
		NewsBean b2 = new NewsBean();
		b2.setCodiceNews(41);
		b2.setTitolo("Virtual Job Meeting STEM GIRLS ");
		b2.setArgomento("(Giovedi 16 settembre, 9:30-16:30 - Registrazioni aperte)");
		b2.setContenuto("Una grande opportunita per studentesse e laureate in materie scientifiche, tecnologiche, ingegneristiche e matematiche di tutte le Universita italiane.");
		b2.setUsername("NewsManager");
		b2.setDataCaricamento(Date.valueOf("2021-09-14"));
		
		
		
		expected.add(b1);
		expected.add(b2);
		
		for(int i=0;i<result.size();i++) {
			assertEquals(rs.get(i).getCodiceNews(),expected.get(i).getCodiceNews());
			assertEquals(rs.get(i).getTitolo(),expected.get(i).getTitolo());
			assertEquals(rs.get(i).getArgomento(),expected.get(i).getArgomento());
			assertEquals(rs.get(i).getContenuto(),expected.get(i).getContenuto());
			assertEquals(rs.get(i).getDataCaricamento(),expected.get(i).getDataCaricamento());
		}
	}
	
	@Test
	public void testDoRetrieveAllNotPresent() throws Exception{
		ds.getConnection().prepareStatement("DELETE FROM News WHERE CodiceNews=24").execute();
		ds.getConnection().prepareStatement("DELETE FROM News WHERE CodiceNews=41").execute();
		
		Collection<NewsBean> result= news.doRetrieveAll();
		assertEquals(result.size(),0);
	}
	
	@Test
	public void testDoRetrieveByCodiceNewsValido() throws SQLException{
		NewsBean b = news.doRetrieveByCodiceNews(24);
		assertEquals(b.getCodiceNews(),24);
		assertEquals(b.getTitolo(),"Ateneo | UNISA Studenti Vax Day");
		assertEquals(b.getArgomento(),"8 settembre (dalle 14 alle 20) al Centro Vaccinale Ruggi");
		assertEquals(b.getContenuto(),"Nell ottica di un auspicabile ripresa della vita universitaria in presenza, mercoledi 8 settembre, dalle ore 14.00 e fino alle ore 20.00, presso il Centro Vaccinale dell A.O.U. San Giovanni di Dio e Ruggi d Aragona, si terra l UNISA STUDENTI VAX DAY, una seduta di vaccinazione riservata agli studenti dell Ateneo.");
		assertEquals(b.getUsername(),"NewsManager");
		assertEquals(b.getDataCaricamento(),Date.valueOf("2021-09-06"));	
	}
	
	@Test
	public void testDoRetrieveByCodiceNewsNonPresente() throws SQLException{
		NewsBean b = news.doRetrieveByCodiceNews(50);
		assertNull(b);
	}
	
	
	@Test
	public void testDoRetrieveByCodiceNewsNonValido() throws SQLException {
		boolean flag=false;
		try{
			news.doRetrieveByCodiceNews(-1);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}
	
	@Test
	public void testDoRetrieveKey() throws SQLException{
		int expected = news.doRetrieveKey();
		assertEquals(expected,41);
	}
	
	@Test
	public void testDoRetrieveKeyNotPresent() throws SQLException{
		ds.getConnection().prepareStatement("DELETE FROM News WHERE CodiceNews=24").execute();
		ds.getConnection().prepareStatement("DELETE FROM News WHERE CodiceNews=41").execute();
		
		int expected = news.doRetrieveKey();
		assertEquals(expected,-1);
	}
	
	@Test
	public void testDoSave() throws Exception{
		NewsBean bean= new NewsBean();
		bean.setTitolo("Nuova news");
		bean.setArgomento("studio");
		bean.setContenuto("Progetto IS");		
		bean.setUsername("fry");
		bean.setDataCaricamento(Date.valueOf("2022-01-31"));
		news.doSave(bean);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/NewsExpected.xml")).getTable("News");
		ITable actual=this.getConnection().createDataSet().getTable("News");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
	}
	
	@Test
	public void testDoSaveNull() throws Exception{
		boolean flag = false;
		try {
			news.doSave(null);
		}catch(NullPointerException e) {
			flag=true;	
		}
		assertEquals(true,flag);
	}
	
	
	

}
