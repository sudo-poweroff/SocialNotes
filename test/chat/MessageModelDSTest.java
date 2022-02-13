package chat;

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


public class MessageModelDSTest extends DataSourceBasedDBTestCase{

	private DataSource ds;
	private MessageModelDS message;
	
	@Override
	protected DataSource getDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/Messaggio.sql'");
        dataSource.setUser("supo-poweroff");
        dataSource.setPassword("09134");
        return dataSource;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/init/Messaggio.xml"));
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
		message=new MessageModelDS(ds);
	}
	
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testDoRetrieveByChatIDPresente() throws SQLException{
		Collection<MessageBean> result= message.doRetrieveByChatID(65);
		ArrayList<MessageBean> rs = new ArrayList<>(result);
		ArrayList<MessageBean> expected = new ArrayList<MessageBean>();
		MessageBean b1 = new MessageBean();
		b1.setChatID(65);
		b1.setDataInvio(Timestamp.valueOf("2022-01-31 16:29:37"));
		b1.setIdMessaggio(3);
		b1.setTesto("hello");
		b1.setUsername("fry");
		expected.add(b1);
		
		for(int i=0;i<result.size();i++) {
			assertEquals(rs.get(i).getChatID(),expected.get(i).getChatID());
			assertEquals(rs.get(i).getDataInvio(),expected.get(i).getDataInvio());
			assertEquals(rs.get(i).getIdMessaggio(),expected.get(i).getIdMessaggio());
			assertEquals(rs.get(i).getTesto(),expected.get(i).getTesto());
			assertEquals(rs.get(i).getUsername(),expected.get(i).getUsername());
		}
		
	}
	
	@Test
	public void testDoRetrieveByChatIDNonValido() throws SQLException{
		boolean flag = false;
		try {
			message.doRetrieveByChatID(-2);
		}catch(NullPointerException e) {
			flag=true;	
		}
		assertEquals(true,flag);	
	}
	
	@Test
	public void testDoRetrieveByChatIDNonPresente() throws SQLException{
		Collection<MessageBean> result=new ArrayList<>();
		result=message.doRetrieveByChatID(34);
		assertEquals(result.size(), 0);
	}
	
	@Test
	public void testDoRetrieveLatestMessagesPresente()throws SQLException{
		Collection<MessageBean> result= message.doRetrieveLatestMessages(Timestamp.valueOf("2022-01-31 16:29:35"),65);
		ArrayList<MessageBean> rs = new ArrayList<>(result);
		ArrayList<MessageBean> expected = new ArrayList<MessageBean>();
		MessageBean b1 = new MessageBean();
		b1.setChatID(65);
		b1.setDataInvio(Timestamp.valueOf("2022-01-31 16:29:37"));
		b1.setIdMessaggio(3);
		b1.setTesto("hello");
		b1.setUsername("fry");
		expected.add(b1);
		
		for(int i=0;i<result.size();i++) {
			assertEquals(rs.get(i).getChatID(),expected.get(i).getChatID());
			assertEquals(rs.get(i).getDataInvio(),expected.get(i).getDataInvio());
			assertEquals(rs.get(i).getIdMessaggio(),expected.get(i).getIdMessaggio());
			assertEquals(rs.get(i).getTesto(),expected.get(i).getTesto());
			assertEquals(rs.get(i).getUsername(),expected.get(i).getUsername());
		}
	}
	
	@Test
	public void testDoRetrieveLatestMessagesOrarioNonValido()throws SQLException{
		boolean flag = false;
		try {
			message.doRetrieveLatestMessages(null,65);
		}catch(NullPointerException e) {
			flag=true;	
		}
		assertEquals(true,flag);	
	}
	
	@Test
	public void testDoRetrieveLatestMessagesNonPresente()throws SQLException{
		Collection<MessageBean> result=new ArrayList<>();
		result=message.doRetrieveLatestMessages(Timestamp.valueOf("2022-01-31 16:29:35"),34);
		assertEquals(result.size(), 0);
	}
	
	@Test
	public void testDoRetrieveLatestMessagesNonPresenteOrarioNonValido()throws SQLException{
		boolean flag = false;
		try {
			message.doRetrieveLatestMessages(null,34);
		}catch(NullPointerException e) {
			flag=true;	
		}
		assertEquals(true,flag);	
	}
	
	@Test
	public void testDoSave() throws Exception{
		MessageBean bean= new MessageBean();
		bean.setIdMessaggio(1);
		bean.setTesto("ciaoo");
		bean.setDataInvio(Timestamp.valueOf("2022-01-31 16:29:39"));
		bean.setUsername("fry");
		bean.setChatID(65);
		message.doSave(bean);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/MessaggioExpected.xml")).getTable("Messaggio");
		ITable actual=this.getConnection().createDataSet().getTable("Messaggio");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
	}
	
	@Test
	public void testDoSaveNull() throws Exception{
		boolean flag = false;
		try {
			message.doSave(null);
		}catch(NullPointerException e) {
			flag=true;	
		}
		assertEquals(true,flag);
	}
	
	

}
