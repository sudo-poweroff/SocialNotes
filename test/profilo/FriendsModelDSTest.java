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

public class FriendsModelDSTest extends DataSourceBasedDBTestCase{
	private DataSource ds;
	private FriendsModelDS friendsModel;


	@Override
	protected DataSource getDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/Amicizia.sql'");
		dataSource.setUser("supo-poweroff");
		dataSource.setPassword("09134");
		return dataSource;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/init/Amicizia.xml"));
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
		friendsModel=new FriendsModelDS(ds);
	}


	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}


	@Test
	public void testDoSave() throws Exception {
		FriendsBean bean=new FriendsBean();
		bean.setUsername1("sime00");
		bean.setUsername2("pipp8");
		bean.setDataInizio(Date.valueOf("2022-02-08"));
		friendsModel.doSave(bean);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/AmiciziaExpected.xml")).getTable("Amicizia");
		ITable actual=this.getConnection().createDataSet().getTable("Amicizia");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
	}


	@Test
	public void doSaveAlreadyPresent(){
		boolean flag=false;
		FriendsBean bean=new FriendsBean();
		bean.setUsername1("sime00");
		bean.setUsername2("fry");
		bean.setDataInizio(Date.valueOf("2021-09-15"));
		try {
			friendsModel.doSave(bean);
		}catch(SQLException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoSaveNull() throws SQLException {
		boolean flag=false;
		try {
			friendsModel.doSave(null);
		}catch(NullPointerException e){
			flag=true;
		}
		assertTrue(flag);
	}


	//TEST doDeleteFriend()
	@Test
	public void testDoDeleteFriend() throws Exception {
		friendsModel.doDeleteFriend("pipp8","fry");
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/AmiciziaExpectedDelete.xml")).getTable("Amicizia");
		ITable actual=this.getConnection().createDataSet().getTable("Amicizia");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
	}


	@Test
	public void testDoDeleteFriendUserNonPresente() throws Exception {
		friendsModel.doDeleteFriend("pipp8","despacito");
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/AmiciziaExpectedDeleteUsNotPresent.xml")).getTable("Amicizia");
		ITable actual=this.getConnection().createDataSet().getTable("Amicizia");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
	}


	@Test
	public void testDoDeleteFriendUserVuoto() throws SQLException {
		boolean flag=false;
		try {
			friendsModel.doDeleteFriend("fry", "");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoDeleteFriendUserNull() throws SQLException {
		boolean flag=false;
		try {
			friendsModel.doDeleteFriend("fry",null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoDeleteFriendNonPresente() throws Exception {
		friendsModel.doDeleteFriend("despacito","fry");
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/AmiciziaExpectedDeleteUsNotPresent.xml")).getTable("Amicizia");
		ITable actual=this.getConnection().createDataSet().getTable("Amicizia");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
	}


	@Test
	public void testDoDeleteFriendNonPresenteUserNonPresente() throws Exception {
		friendsModel.doDeleteFriend("xiao","despacito");
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/AmiciziaExpectedDeleteUsNotPresent.xml")).getTable("Amicizia");
		ITable actual=this.getConnection().createDataSet().getTable("Amicizia");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
	}


	@Test
	public void testDoDeleteFriendNonPresenteUserVuoto() throws SQLException {
		boolean flag=false;
		try {
			friendsModel.doDeleteFriend("despacito", "");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoDeleteFriendNonPresenteUserNull() throws SQLException {
		boolean flag=false;
		try {
			friendsModel.doDeleteFriend("despacito",null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoDeleteFriendVuoto() throws Exception {
		boolean flag=false;
		try {
			friendsModel.doDeleteFriend("", "fry");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoDeleteFriendVuotoUserNonPresente() throws Exception {
		boolean flag=false;
		try {
			friendsModel.doDeleteFriend("", "despacito");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoDeleteFriendVuotoUserVuoto() throws SQLException {
		boolean flag=false;
		try {
			friendsModel.doDeleteFriend("", "");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoDeleteFriendVutoUserNull() throws SQLException {
		boolean flag=false;
		try {
			friendsModel.doDeleteFriend("",null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoDeleteFriendNull() throws Exception {
		boolean flag=false;
		try {
			friendsModel.doDeleteFriend(null, "fry");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoDeleteFriendNullUserNonPresente() throws Exception {
		boolean flag=false;
		try {
			friendsModel.doDeleteFriend(null, "despacito");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoDeleteFriendNullUserVuoto() throws SQLException {
		boolean flag=false;
		try {
			friendsModel.doDeleteFriend(null, "");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoDeleteFriendNullUserNull() throws SQLException {
		boolean flag=false;
		try {
			friendsModel.doDeleteFriend(null,null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	//TEST isFriend()
	@Test
	public void testIsFriend() throws SQLException {
		boolean result=friendsModel.isFriend("sime00", "fry");
		assertTrue(result);
	}


	@Test
	public void testIsFriendUserNonPresente() throws SQLException {
		boolean result=friendsModel.isFriend("sime00", "despacito");
		assertFalse(result);
	}


	@Test
	public void testIsFriendUserVuoto() throws SQLException {
		boolean flag=false;
		try {
			friendsModel.isFriend("sime00", "");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testIsFriendUserNull() throws SQLException {
		boolean flag=false;
		try {
			friendsModel.isFriend("sime00", null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testIsFriendNonPresente() throws SQLException {
		boolean result=friendsModel.isFriend("sim", "fry");
		assertFalse(result);
	}


	@Test
	public void testIsFriendNonPresenteUserNonPresente() throws SQLException {
		boolean result=friendsModel.isFriend("sim", "despacito");
		assertFalse(result);
	}


	@Test
	public void testIsFriendNonPresenteUserVuoto() throws SQLException {
		boolean flag=false;
		try {
			friendsModel.isFriend("sim", "");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testIsFriendNonPresenteUserNull() throws SQLException {
		boolean flag=false;
		try {
			friendsModel.isFriend("sim", null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testIsFriendVuoto() throws SQLException {
		boolean flag=false;
		try {
			friendsModel.isFriend("", "fry");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testIsFriendVuotoUserNonPresente() throws SQLException {
		boolean flag=false;
		try {
			friendsModel.isFriend("", "despacito");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testIsFriendVuotoUserVuoto() throws SQLException {
		boolean flag=false;
		try {
			friendsModel.isFriend("", "");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testIsFriendVuotoUserNull() throws SQLException {
		boolean flag=false;
		try {
			friendsModel.isFriend("", null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testIsFriendNull() throws SQLException {
		boolean flag=false;
		try {
			friendsModel.isFriend("", "fry");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testIsFriendNullUserNonPresente() throws SQLException {
		boolean flag=false;
		try {
			friendsModel.isFriend("", "despacito");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testIsFriendNullUserVuoto() throws SQLException {
		boolean flag=false;
		try {
			friendsModel.isFriend(null, "");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testIsFriendNullUserNull() throws SQLException {
		boolean flag=false;
		try {
			friendsModel.isFriend(null, null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	//TEST getNumberFriends()
	@Test
	public void testGetNumberFriends() throws SQLException {
		int numFriend=friendsModel.getNumberFriends("sime00");
		assertEquals(numFriend, 2);
	}


	@Test
	public void testGetNumberFriendsUserNonPresente() throws SQLException {
		int numFriend=friendsModel.getNumberFriends("despacito");
		assertEquals(numFriend, 0);
	}


	@Test
	public void testGetNumberFriendsUserVuoto() throws SQLException {
		boolean flag=false;
		try {
			friendsModel.getNumberFriends("");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}
	
	
	@Test
	public void testGetNumberFriendsUserNull() throws SQLException {
		boolean flag=false;
		try {
			friendsModel.getNumberFriends(null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}

	
	//TEST doRetrieveByUsername()
	@Test 
	public void testDoRetrieveByUsername() throws SQLException{
		Collection<FriendsBean> result=new ArrayList<>();
		result=friendsModel.doRetrieveByUsername("sime00");
		ArrayList<FriendsBean> rs=new ArrayList<>(result);
		ArrayList<FriendsBean> aspected=new ArrayList<>();
		FriendsBean fr1=new FriendsBean();
		fr1.setUsername2("sime00");
		fr1.setUsername1("fry");
		fr1.setDataInizio(Date.valueOf("2021-09-15"));
		aspected.add(fr1);
		FriendsBean fr2=new FriendsBean();
		fr2.setUsername2("sime00");
		fr2.setUsername1("califano87");
		fr2.setDataInizio(Date.valueOf("2021-09-10"));
		aspected.add(fr2);
		
		assertEquals(rs.size(), aspected.size());
		for(int i=0;i<rs.size();i++) {
			assertEquals(rs.get(i).getUsername1(), aspected.get(i).getUsername1());
			assertEquals(rs.get(i).getUsername2(), aspected.get(i).getUsername2());
			assertEquals(rs.get(i).getDataInizio(), aspected.get(i).getDataInizio());
		}
	}
	
	
	@Test
	public void testRetrieveByUsernameNonPresente() throws SQLException {
		Collection<FriendsBean> result=new ArrayList<>();
		result=friendsModel.doRetrieveByUsername("despacito");
		assertEquals(result.size(), 0);
	}
	
	
	@Test
	public void testRetrieveByUsernameVuoto() throws SQLException {
		boolean flag=false;
		try {
			friendsModel.doRetrieveByUsername("");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}
	
	
	@Test
	public void testRetrieveByUsernameNull() throws SQLException {
		boolean flag=false;
		try {
			friendsModel.doRetrieveByUsername(null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}
}
