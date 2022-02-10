package chat;


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



public class ChatModelDSTest extends DataSourceBasedDBTestCase  {
	
	private DataSource ds;
	private ChatModelDS chat;
	
	@Override
	protected DataSource getDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/Chat.sql'");
        dataSource.setUser("supo-poweroff");
        dataSource.setPassword("09134");
        return dataSource;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/init/Chat.xml"));
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
		chat=new ChatModelDS(ds);
	}
	
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testDoRetrieveByKeyPresente() throws SQLException{
		ChatBean result= chat.doRetrieveByKey("18");
		assertEquals(result.getChatID(),18);
		assertEquals(result.getTitolo(),"friendship");
	}
	
	@Test
	public void testDoRetrieveByKeyNonPresente() throws SQLException{		
		ChatBean result= chat.doRetrieveByKey("19");
		assertEquals(result,null);
	}
	
	@Test
	public void testDoRetrieveByKeyVuota() throws SQLException{		
		boolean flag = false;
		try {
			chat.doRetrieveByKey("");
		}catch(NullPointerException e) {
			flag=true;	
		}
		assertEquals(true,flag);
	}
	
	@Test
	public void testDoRetrieveByKeyNull() throws SQLException{		
		boolean flag = false;
		try {
			chat.doRetrieveByKey(null);
		}catch(NullPointerException e) {
			flag=true;	
		}
		assertEquals(true,flag);
	}

	@Test
	public void testDoRetrieveLast() throws SQLException{
		ChatBean result= chat.doRetrieveLast();
		assertEquals(result.getChatID(),65);
		assertEquals(result.getTitolo(),"progetto IS");
	}
	
	
	@Test
	public void testDoRetrieveLastNonPresente() throws SQLException{
		ds.getConnection().prepareStatement("DELETE FROM Chat WHERE ChatID=18").execute();
		ds.getConnection().prepareStatement("DELETE FROM Chat WHERE ChatID=16").execute();
		ds.getConnection().prepareStatement("DELETE FROM Chat WHERE ChatID=65").execute();
		
		ChatBean result= chat.doRetrieveLast();
		assertNull(result);
	}
	
	@Test
	public void testDoSave() throws Exception{
		ChatBean bean= new ChatBean();
		bean.setTitolo("nuova chat");
		bean.setChatID(1);
		chat.doSave(bean);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/ChatExpected.xml")).getTable("Chat");
		ITable actual=this.getConnection().createDataSet().getTable("Chat");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
	}
	
	
	@Test
	public void testDoSaveNull() throws Exception{
		boolean flag = false;
		try {
			chat.doSave(null);
		}catch(NullPointerException e) {
			flag=true;	
		}
		assertEquals(true,flag);
	}
	
	@Test
	public void testDoRetrieveChatNamePresente() throws SQLException{
		Collection<ChatBean> result=new ArrayList<>();
		result=chat.doRetrieveChatName("fry");
		ArrayList<ChatBean> rs=new ArrayList<>(result);
		ArrayList<ChatBean> expected=new ArrayList<>();
		ChatBean b1 = new ChatBean();
		b1.setChatID(65);
		b1.setTitolo("progetto IS");
		ChatBean b2 = new ChatBean();
		b2.setChatID(16);
		b2.setTitolo("gruppo di fuoco");
		expected.add(b2);
		expected.add(b1);
		for(int i=0;i<result.size();i++) {
			assertEquals(rs.get(i).getChatID(),expected.get(i).getChatID());
			assertEquals(rs.get(i).getTitolo(),expected.get(i).getTitolo());
		}
	}
	
	@Test
	public void testDoRetrieveChatNameNonPresente() throws SQLException{
		Collection<ChatBean> result=new ArrayList<>();
		result=chat.doRetrieveChatName("pluto");
		assertEquals(result.size(),0);
	}
	
	@Test
	public void testDoRetrieveChatNameVuoto() throws SQLException{
		boolean flag = false;
		try {
			chat.doRetrieveChatName("");
		}catch(NullPointerException e) {
			flag=true;	
		}
		assertEquals(true,flag);	
	}
	
	@Test
	public void testDoRetrieveChatNameNull() throws SQLException{
		boolean flag = false;
		try {
			chat.doRetrieveChatName(null);
		}catch(NullPointerException e) {
			flag=true;	
		}
		assertEquals(true,flag);	
	}
	


}
