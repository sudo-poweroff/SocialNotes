package profilo;


import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

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
	/*@Test
	public void testCheckLoginDatiPresenti() throws SQLException {
		UserBean us=userModel.checkLogin("sime00","Sime1");
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
	}*/



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


	@Test
	public void testDoRetrieveByUsernameNonPresente() throws SQLException {
		UserBean us=userModel.doRetrieveByUsername("despacito");
		assertNull(us);

	}


	@Test
	public void testDoRetrieveByUsernameVuoto() throws SQLException {
		boolean flag=false;
		try {
			userModel.doRetrieveByUsername("");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoRetrieveByUsernameNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.doRetrieveByUsername(null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}



	//TEST doRetrieveByEmail()
	@Test
	public void testDoRetrieveByEmailPresente() throws SQLException {
		UserBean us=userModel.doRetrieveByEmail("sime@gmail.com");
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


	@Test
	public void testDoRetrieveByEmailNonPresente() throws SQLException {
		UserBean us=userModel.doRetrieveByEmail("despacito");
		assertNull(us);

	}


	@Test
	public void testDoRetrieveByEmailVuoto() throws SQLException {
		boolean flag=false;
		try {
			userModel.doRetrieveByEmail("");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoRetrieveByEmailNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.doRetrieveByEmail(null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}



	//TEST doRetrieveUsers()
	@Test
	public void testDoRetrieveUsersPresenti() throws SQLException {
		Collection<UserBean> result=new ArrayList<>();
		result=userModel.doRetrieveUsers();
		ArrayList<UserBean> rs=new ArrayList<>(result);
		ArrayList<UserBean> aspected=new ArrayList<>();
		UserBean us1=new UserBean();
		us1.setUsername("sime00");
		us1.setNome("Simone");
		us1.setCognome("Della Porta");
		us1.setEmail("sime@gmail.com");
		us1.setPass("Sime1");
		us1.setCoin(1200);
		us1.setDataNascita(Date.valueOf("2000-10-27"));
		us1.setBan(Date.valueOf("2022-03-01"));
		us1.setDenominazione("Universita degli studi di Salerno");
		us1.setDipName("Dipartimento di Informatica");
		us1.setRuolo(0);
		us1.setImg(null);
		aspected.add(us1);
		UserBean us2=new UserBean();
		us2.setUsername("fry");
		us2.setNome("Francesco");
		us2.setCognome("Di Lauro");
		us2.setEmail("fry@gmail.com");
		us2.setPass("Fry1");
		us2.setDataNascita(Date.valueOf("2000-05-18"));
		us2.setCoin(1100);
		us2.setBan(Date.valueOf("2022-01-27"));
		us2.setDenominazione("Universita degli studi di Salerno");
		us2.setDipName("Dipartimento di Informatica");
		us2.setRuolo(0);
		us2.setImg(null);
		UserBean us3=new UserBean();
		us3.setUsername("califano87");
		us3.setNome("Alfonso");
		us3.setCognome("Califano");
		us3.setEmail("califano87@gmail.com");
		us3.setPass("Califano87");
		us3.setDataNascita(Date.valueOf("2000-05-17"));
		us3.setCoin(900);
		us3.setBan(Date.valueOf("2022-02-06"));
		us3.setDenominazione("Universita degli studi di Salerno");
		us3.setDipName("Dipartimento di Informatica");
		us3.setRuolo(0);
		us3.setImg(null);
		aspected.add(us3);
		aspected.add(us2);
		UserBean us4=new UserBean();
		us4.setUsername("sim");
		us4.setNome("Simone");
		us4.setCognome("Rossi");
		us4.setEmail("sim@gmail.com");
		us4.setPass("Sim1");
		us4.setCoin(200);
		us4.setDataNascita(Date.valueOf("2000-08-27"));
		us4.setBan(Date.valueOf("2022-01-25"));
		us4.setDenominazione("Universita degli studi di Salerno");
		us4.setDipName("Dipartimento di Informatica");
		us4.setRuolo(0);
		us4.setImg(null);
		aspected.add(us4);
		for(int i=0;i<result.size();i++) {
			assertEquals(rs.get(i).getUsername(),aspected.get(i).getUsername());
			assertEquals(rs.get(i).getNome(),aspected.get(i).getNome());
			assertEquals(rs.get(i).getCognome(),aspected.get(i).getCognome());
			assertEquals(rs.get(i).getEmail(),aspected.get(i).getEmail());
			assertEquals(rs.get(i).getPass(),aspected.get(i).getPass());
			assertEquals(rs.get(i).getDataNascita(),aspected.get(i).getDataNascita());
			assertEquals(rs.get(i).getCoin(),aspected.get(i).getCoin());
			assertEquals(rs.get(i).getBan(),aspected.get(i).getBan());
			assertEquals(rs.get(i).getDenominazione(),aspected.get(i).getDenominazione());
			assertEquals(rs.get(i).getDipName(),aspected.get(i).getDipName());
			assertEquals(rs.get(i).getRuolo(),aspected.get(i).getRuolo());
			assertEquals(rs.get(i).getImg(),aspected.get(i).getImg());
		}
	}


	@Test
	public void testDoRetrieveUsersNonPresenti() throws SQLException {
		ds.getConnection().prepareStatement("DELETE FROM Utente WHERE Ruolo=0;").execute();
		Collection<UserBean>users=userModel.doRetrieveUsers();
		assertEquals(users.size(), 0);
	}


	//TEST doRetrieveByParameterUser()
	/*@Test
	public void testMotoreRicerca() throws SQLException {
		Collection<UserBean> result=new ArrayList<>();
		result=userModel.doRetrieveByParametersUser("sim", "ASC", 5);
		ArrayList<UserBean> rs=new ArrayList<>(result);
		ArrayList<UserBean> aspected=new ArrayList<>();
		UserBean us1=new UserBean();
		us1.setUsername("sime00");
		us1.setNome("Simone");
		us1.setCognome("Della Porta");
		us1.setEmail("sime@gmail.com");
		us1.setPass("Sime1");
		us1.setCoin(1200);
		us1.setDataNascita(Date.valueOf("2000-10-27"));
		us1.setBan(Date.valueOf("2022-03-01"));
		us1.setDenominazione("Universita degli studi di Salerno");
		us1.setDipName("Dipartimento di Informatica");
		us1.setRuolo(0);
		us1.setImg(null);
		aspected.add(us1);
		for(int i=0;i<result.size();i++) {
			assertEquals(rs.get(i).getUsername(),aspected.get(i).getUsername());
			assertEquals(rs.get(i).getNome(),aspected.get(i).getNome());
			assertEquals(rs.get(i).getCognome(),aspected.get(i).getCognome());
			assertEquals(rs.get(i).getEmail(),aspected.get(i).getEmail());
			assertEquals(rs.get(i).getPass(),aspected.get(i).getPass());
			assertEquals(rs.get(i).getDataNascita(),aspected.get(i).getDataNascita());
			assertEquals(rs.get(i).getCoin(),aspected.get(i).getCoin());
			assertEquals(rs.get(i).getBan(),aspected.get(i).getBan());
			assertEquals(rs.get(i).getDenominazione(),aspected.get(i).getDenominazione());
			assertEquals(rs.get(i).getDipName(),aspected.get(i).getDipName());
			assertEquals(rs.get(i).getRuolo(),aspected.get(i).getRuolo());
			assertEquals(rs.get(i).getImg(),aspected.get(i).getImg());
		}
	}*/


	//TEST doSave()
	/*@Test
	public void testDoSave() throws Exception{
		UserBean us1=new UserBean();
		us1.setUsername("ciccioCicogna");
		us1.setNome("Francesco");
		us1.setCognome("Cicogna");
		us1.setEmail("ciccioCicogna@gmail.com");
		us1.setPass("Ciccio1");
		us1.setCoin(50);
		us1.setDataNascita(Date.valueOf("2001-04-10"));
		us1.setBan(Date.valueOf("2021-09-10"));
		us1.setDenominazione("Universita degli studi di Salerno");
		us1.setDipName("Dipartimento di Informatica");
		us1.setRuolo(0);
		us1.setImg(null);
		userModel.doSave(us1);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/UtenteExpected.xml")).getTable("Utente");
		ITable actual=this.getConnection().createDataSet().getTable("Utente");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));

	}*/



	//TEST manageBan()
	@Test
	public void testManageBanDatiValidi() throws Exception {
		userModel.manageBan("sime00",Date.valueOf("2022-03-15"));
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/UtenteExpectedBan.xml")).getTable("Utente");
		ITable actual=this.getConnection().createDataSet().getTable("Utente");
		SortedTable tbexpected=new SortedTable(expected);
		SortedTable tbactual=new SortedTable(actual);
		assertEquals(tbexpected.getRowCount(), tbactual.getRowCount());
		for(int i=0;i<tbexpected.getRowCount();i++) {
			assertEquals(tbexpected.getValue(i, "Username"), tbactual.getValue(i, "Username"));
			assertEquals(tbexpected.getValue(i, "Nome"), tbactual.getValue(i, "Nome"));
			assertEquals(tbexpected.getValue(i, "Cognome"), tbactual.getValue(i, "Cognome"));
			assertEquals(tbexpected.getValue(i, "Email"), tbactual.getValue(i, "Email"));
			assertEquals(tbexpected.getValue(i, "Pass"), tbactual.getValue(i, "Pass"));
			assertEquals(tbexpected.getValue(i, "DataNascita").toString(),tbactual.getValue(i, "DataNascita").toString());
			assertEquals(tbexpected.getValue(i, "Coin").toString(), tbactual.getValue(i, "Coin").toString());
			assertEquals(tbexpected.getValue(i, "Ban").toString(), tbactual.getValue(i, "Ban").toString());
			assertEquals(tbexpected.getValue(i, "Denominazione"), tbactual.getValue(i, "Denominazione"));
			assertEquals(tbexpected.getValue(i, "dipName"), tbactual.getValue(i, "dipName"));
			assertEquals(tbexpected.getValue(i, "Ruolo").toString(), tbactual.getValue(i, "Ruolo").toString());
		}
	}

	@Test
	public void testManageBanDatNonValida() throws Exception {
		boolean flag=false;
		try {
			userModel.manageBan("sime00",Date.valueOf("2022-01-15"));
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testManageBanDataNull() throws Exception {
		boolean flag=false;
		try {
			userModel.manageBan("sime00",null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}

	@Test
	public void testManageBanUsernameNonPresente() throws Exception {
		userModel.manageBan("despacito",Date.valueOf("2022-03-15"));
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/UtenteExpectedBanUsernameNonPresente.xml")).getTable("Utente");
		ITable actual=this.getConnection().createDataSet().getTable("Utente");
		SortedTable tbexpected=new SortedTable(expected);
		SortedTable tbactual=new SortedTable(actual);
		assertEquals(tbexpected.getRowCount(), tbactual.getRowCount());
		for(int i=0;i<tbexpected.getRowCount();i++) {
			assertEquals(tbexpected.getValue(i, "Username"), tbactual.getValue(i, "Username"));
			assertEquals(tbexpected.getValue(i, "Nome"), tbactual.getValue(i, "Nome"));
			assertEquals(tbexpected.getValue(i, "Cognome"), tbactual.getValue(i, "Cognome"));
			assertEquals(tbexpected.getValue(i, "Email"), tbactual.getValue(i, "Email"));
			assertEquals(tbexpected.getValue(i, "Pass"), tbactual.getValue(i, "Pass"));
			assertEquals(tbexpected.getValue(i, "DataNascita").toString(),tbactual.getValue(i, "DataNascita").toString());
			assertEquals(tbexpected.getValue(i, "Coin").toString(), tbactual.getValue(i, "Coin").toString());
			assertEquals(tbexpected.getValue(i, "Ban").toString(), tbactual.getValue(i, "Ban").toString());
			assertEquals(tbexpected.getValue(i, "Denominazione"), tbactual.getValue(i, "Denominazione"));
			assertEquals(tbexpected.getValue(i, "dipName"), tbactual.getValue(i, "dipName"));
			assertEquals(tbexpected.getValue(i, "Ruolo").toString(), tbactual.getValue(i, "Ruolo").toString());
		}
	}


	@Test
	public void testManageBanUsernameNonPresenteDataNonValida() throws Exception {
		boolean flag=false;
		try {
			userModel.manageBan("despacito",Date.valueOf("2022-01-15"));
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testManageBanUsernameNonPresenteDataNull() throws Exception {
		boolean flag=false;
		try {
			userModel.manageBan("despacito",null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testManageBanUsernameVuotoDataValida() throws Exception {
		boolean flag=false;
		try {
			userModel.manageBan("",Date.valueOf("2022-03-15"));
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testManageBanUsernameVuotoDataNonValida() throws Exception {
		boolean flag=false;
		try {
			userModel.manageBan("",Date.valueOf("2022-01-15"));
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testManageBanUsernameVuotoDataNull() throws Exception {
		boolean flag=false;
		try {
			userModel.manageBan("",null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testManageBanUsernameNullDataValida() throws Exception {
		boolean flag=false;
		try {
			userModel.manageBan("",Date.valueOf("2022-03-15"));
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testManageBanUsernameNullDataNonValida() throws Exception {
		boolean flag=false;
		try {
			userModel.manageBan("",Date.valueOf("2022-01-15"));
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testManageBanUsernameNullDataNull() throws Exception {
		boolean flag=false;
		try {
			userModel.manageBan("",null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}




	//TEST doUpdateCoin()
	@Test
	public void testDoUpdateCoinDatiValidi() throws Exception {
		userModel.doUpdateCoin("sime00",1500);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/UtenteExpectedCoin.xml")).getTable("Utente");
		ITable actual=this.getConnection().createDataSet().getTable("Utente");
		SortedTable tbexpected=new SortedTable(expected);
		SortedTable tbactual=new SortedTable(actual);
		assertEquals(tbexpected.getRowCount(), tbactual.getRowCount());
		for(int i=0;i<tbexpected.getRowCount();i++) {
			assertEquals(tbexpected.getValue(i, "Username"), tbactual.getValue(i, "Username"));
			assertEquals(tbexpected.getValue(i, "Nome"), tbactual.getValue(i, "Nome"));
			assertEquals(tbexpected.getValue(i, "Cognome"), tbactual.getValue(i, "Cognome"));
			assertEquals(tbexpected.getValue(i, "Email"), tbactual.getValue(i, "Email"));
			assertEquals(tbexpected.getValue(i, "Pass"), tbactual.getValue(i, "Pass"));
			assertEquals(tbexpected.getValue(i, "DataNascita").toString(),tbactual.getValue(i, "DataNascita").toString());
			assertEquals(tbexpected.getValue(i, "Coin").toString(), tbactual.getValue(i, "Coin").toString());
			assertEquals(tbexpected.getValue(i, "Ban").toString(), tbactual.getValue(i, "Ban").toString());
			assertEquals(tbexpected.getValue(i, "Denominazione"), tbactual.getValue(i, "Denominazione"));
			assertEquals(tbexpected.getValue(i, "dipName"), tbactual.getValue(i, "dipName"));
			assertEquals(tbexpected.getValue(i, "Ruolo").toString(), tbactual.getValue(i, "Ruolo").toString());
		}
	}


	@Test
	public void testDoUpdateCoinNegativi() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdateCoin("sime00",-1);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);

	}


	@Test
	public void testDoUpdateCoinUsernameNonPresente() throws Exception {
		userModel.doUpdateCoin("despacito",1500);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/UtenteExpectedCoinUsNotPresent.xml")).getTable("Utente");
		ITable actual=this.getConnection().createDataSet().getTable("Utente");
		SortedTable tbexpected=new SortedTable(expected);
		SortedTable tbactual=new SortedTable(actual);
		assertEquals(tbexpected.getRowCount(), tbactual.getRowCount());
		for(int i=0;i<tbexpected.getRowCount();i++) {
			assertEquals(tbexpected.getValue(i, "Username"), tbactual.getValue(i, "Username"));
			assertEquals(tbexpected.getValue(i, "Nome"), tbactual.getValue(i, "Nome"));
			assertEquals(tbexpected.getValue(i, "Cognome"), tbactual.getValue(i, "Cognome"));
			assertEquals(tbexpected.getValue(i, "Email"), tbactual.getValue(i, "Email"));
			assertEquals(tbexpected.getValue(i, "Pass"), tbactual.getValue(i, "Pass"));
			assertEquals(tbexpected.getValue(i, "DataNascita").toString(),tbactual.getValue(i, "DataNascita").toString());
			assertEquals(tbexpected.getValue(i, "Coin").toString(), tbactual.getValue(i, "Coin").toString());
			assertEquals(tbexpected.getValue(i, "Ban").toString(), tbactual.getValue(i, "Ban").toString());
			assertEquals(tbexpected.getValue(i, "Denominazione"), tbactual.getValue(i, "Denominazione"));
			assertEquals(tbexpected.getValue(i, "dipName"), tbactual.getValue(i, "dipName"));
			assertEquals(tbexpected.getValue(i, "Ruolo").toString(), tbactual.getValue(i, "Ruolo").toString());
		}
	}


	@Test
	public void testDoUpdateCoinNegativiUsernameNonPresente() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdateCoin("despacito",-1);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateCoinUsernameVuoto() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdateCoin("",10);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateCoinUsernameVuotoCoinNegativi() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdateCoin("",-10);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateCoinUsernameNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdateCoin(null,10);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateCoinUsernameNullCoinNegativi() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdateCoin(null,-10);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	//TEST doUpdatePassword() non si può fare perchè la funzione AES_ENCRYPT di MySql non è supportata 


	//TEST doUpdateEmail()
	@Test
	public void testDoUpdateEmail() throws Exception {
		userModel.doUpdateEmail("sime00", "sime00@gmail.com");
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/UtenteExpectedEmail.xml")).getTable("Utente");
		ITable actual=this.getConnection().createDataSet().getTable("Utente");
		SortedTable tbexpected=new SortedTable(expected);
		SortedTable tbactual=new SortedTable(actual);
		assertEquals(tbexpected.getRowCount(), tbactual.getRowCount());
		for(int i=0;i<tbexpected.getRowCount();i++) {
			assertEquals(tbexpected.getValue(i, "Username"), tbactual.getValue(i, "Username"));
			assertEquals(tbexpected.getValue(i, "Nome"), tbactual.getValue(i, "Nome"));
			assertEquals(tbexpected.getValue(i, "Cognome"), tbactual.getValue(i, "Cognome"));
			assertEquals(tbexpected.getValue(i, "Email"), tbactual.getValue(i, "Email"));
			assertEquals(tbexpected.getValue(i, "Pass"), tbactual.getValue(i, "Pass"));
			assertEquals(tbexpected.getValue(i, "DataNascita").toString(),tbactual.getValue(i, "DataNascita").toString());
			assertEquals(tbexpected.getValue(i, "Coin").toString(), tbactual.getValue(i, "Coin").toString());
			assertEquals(tbexpected.getValue(i, "Ban").toString(), tbactual.getValue(i, "Ban").toString());
			assertEquals(tbexpected.getValue(i, "Denominazione"), tbactual.getValue(i, "Denominazione"));
			assertEquals(tbexpected.getValue(i, "dipName"), tbactual.getValue(i, "dipName"));
			assertEquals(tbexpected.getValue(i, "Ruolo").toString(), tbactual.getValue(i, "Ruolo").toString());
		}
	}


	@Test
	public void testDoUpdateEmailVuota() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdateEmail("sime00", "");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateEmailNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdateEmail("sime00",null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateEmailUsernameNonPresente() throws Exception {
		userModel.doUpdateEmail("despacito", "sime00@gmail.com");
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/UtenteExpectedEmailUsNotPresent.xml")).getTable("Utente");
		ITable actual=this.getConnection().createDataSet().getTable("Utente");
		SortedTable tbexpected=new SortedTable(expected);
		SortedTable tbactual=new SortedTable(actual);
		assertEquals(tbexpected.getRowCount(), tbactual.getRowCount());
		for(int i=0;i<tbexpected.getRowCount();i++) {
			assertEquals(tbexpected.getValue(i, "Username"), tbactual.getValue(i, "Username"));
			assertEquals(tbexpected.getValue(i, "Nome"), tbactual.getValue(i, "Nome"));
			assertEquals(tbexpected.getValue(i, "Cognome"), tbactual.getValue(i, "Cognome"));
			assertEquals(tbexpected.getValue(i, "Email"), tbactual.getValue(i, "Email"));
			assertEquals(tbexpected.getValue(i, "Pass"), tbactual.getValue(i, "Pass"));
			assertEquals(tbexpected.getValue(i, "DataNascita").toString(),tbactual.getValue(i, "DataNascita").toString());
			assertEquals(tbexpected.getValue(i, "Coin").toString(), tbactual.getValue(i, "Coin").toString());
			assertEquals(tbexpected.getValue(i, "Ban").toString(), tbactual.getValue(i, "Ban").toString());
			assertEquals(tbexpected.getValue(i, "Denominazione"), tbactual.getValue(i, "Denominazione"));
			assertEquals(tbexpected.getValue(i, "dipName"), tbactual.getValue(i, "dipName"));
			assertEquals(tbexpected.getValue(i, "Ruolo").toString(), tbactual.getValue(i, "Ruolo").toString());
		}
	}


	@Test
	public void testDoUpdateEmailVuotaUsernameNonPresente() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdateEmail("despacito", "");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateEmailNullUsernameNonPresente() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdateEmail("despacito",null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateEmailUsernameVuoto() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdateEmail("","sime00@gmail.com");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateEmailVuotaUsernameVuoto() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdateEmail("","");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateEmailNullUsernameVuoto() throws SQLException {
		boolean flag=true;
		try {
			userModel.doUpdateEmail("",null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateEmailUsernameNull() throws SQLException {
		boolean flag=true;
		try {
			userModel.doUpdateEmail(null,"sime00@gmail.com");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateEmailVuotaUsernameNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdateEmail(null,"");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateEmailNullUsernameNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdateEmail(null,null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	//TEST doUpdateDepartment()
	@Test
	public void testDoUpdateDepartment() throws Exception {
		userModel.doUpdateDepartment("sime00","Dipartimento di ingegneria","Unina");
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/UtenteExpectedDepartment.xml")).getTable("Utente");
		ITable actual=this.getConnection().createDataSet().getTable("Utente");
		SortedTable tbexpected=new SortedTable(expected);
		SortedTable tbactual=new SortedTable(actual);
		assertEquals(tbexpected.getRowCount(), tbactual.getRowCount());
		for(int i=0;i<tbexpected.getRowCount();i++) {
			assertEquals(tbexpected.getValue(i, "Username"), tbactual.getValue(i, "Username"));
			assertEquals(tbexpected.getValue(i, "Nome"), tbactual.getValue(i, "Nome"));
			assertEquals(tbexpected.getValue(i, "Cognome"), tbactual.getValue(i, "Cognome"));
			assertEquals(tbexpected.getValue(i, "Email"), tbactual.getValue(i, "Email"));
			assertEquals(tbexpected.getValue(i, "Pass"), tbactual.getValue(i, "Pass"));
			assertEquals(tbexpected.getValue(i, "DataNascita").toString(),tbactual.getValue(i, "DataNascita").toString());
			assertEquals(tbexpected.getValue(i, "Coin").toString(), tbactual.getValue(i, "Coin").toString());
			assertEquals(tbexpected.getValue(i, "Ban").toString(), tbactual.getValue(i, "Ban").toString());
			assertEquals(tbexpected.getValue(i, "Denominazione"), tbactual.getValue(i, "Denominazione"));
			assertEquals(tbexpected.getValue(i, "dipName"), tbactual.getValue(i, "dipName"));
			assertEquals(tbexpected.getValue(i, "Ruolo").toString(), tbactual.getValue(i, "Ruolo").toString());
		}
	}


	@Test
	public void testDoUpdateDepartmentUniVuota() throws Exception {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment("sime00","Dipartimento di ingegneria","");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentUniNull() throws Exception {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment("sime00","Dipartimento di ingegneria",null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentVuoto() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment("sime00","","Unina");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentVuotoUniVuota() throws Exception {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment("sime00","","");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentVuotoUniNull() throws Exception {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment("sime00","",null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment("sime00",null,"Unina");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentNullUniVuota() throws Exception {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment("sime00",null,"");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentNullUniNull() throws Exception {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment("sime00",null,null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentUserNotPresent() throws Exception {
		userModel.doUpdateDepartment("despacito","Dipartimento di ingegneria","Unina");
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/UtenteExpectedDepartmentUsNotPresent.xml")).getTable("Utente");
		ITable actual=this.getConnection().createDataSet().getTable("Utente");
		SortedTable tbexpected=new SortedTable(expected);
		SortedTable tbactual=new SortedTable(actual);
		assertEquals(tbexpected.getRowCount(), tbactual.getRowCount());
		for(int i=0;i<tbexpected.getRowCount();i++) {
			assertEquals(tbexpected.getValue(i, "Username"), tbactual.getValue(i, "Username"));
			assertEquals(tbexpected.getValue(i, "Nome"), tbactual.getValue(i, "Nome"));
			assertEquals(tbexpected.getValue(i, "Cognome"), tbactual.getValue(i, "Cognome"));
			assertEquals(tbexpected.getValue(i, "Email"), tbactual.getValue(i, "Email"));
			assertEquals(tbexpected.getValue(i, "Pass"), tbactual.getValue(i, "Pass"));
			assertEquals(tbexpected.getValue(i, "DataNascita").toString(),tbactual.getValue(i, "DataNascita").toString());
			assertEquals(tbexpected.getValue(i, "Coin").toString(), tbactual.getValue(i, "Coin").toString());
			assertEquals(tbexpected.getValue(i, "Ban").toString(), tbactual.getValue(i, "Ban").toString());
			assertEquals(tbexpected.getValue(i, "Denominazione"), tbactual.getValue(i, "Denominazione"));
			assertEquals(tbexpected.getValue(i, "dipName"), tbactual.getValue(i, "dipName"));
			assertEquals(tbexpected.getValue(i, "Ruolo").toString(), tbactual.getValue(i, "Ruolo").toString());
		}
	}


	@Test
	public void testDoUpdateDepartmentUniVuotaUserNotPresent() throws Exception {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment("despacito","Dipartimento di ingegneria","");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentUniNullUserNotPresent() throws Exception {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment("despacito","Dipartimento di ingegneria",null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentVuotoUserNotPresent() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment("despacito","","Unina");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentVuotoUniVuotaUserNotPresent() throws Exception {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment("despacito","","");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentVuotoUniNullUserNotPresent() throws Exception {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment("despacito","",null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentNullUserNotPresent() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment("despacito",null,"Unina");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentNullUniVuotaUserNotPresent() throws Exception {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment("despacito",null,"");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentNullUniNullUserNotPresent() throws Exception {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment("despacito",null,null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}



	@Test
	public void testDoUpdateDepartmentUserVuoto() throws Exception {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment("","Dipartimento di ingegneria","Unina");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentUniVuotaUserVuoto() throws Exception {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment("","Dipartimento di ingegneria","");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentUniNullUserVuoto() throws Exception {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment("","Dipartimento di ingegneria",null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentVuotoUserVuoto() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment("","","Unina");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentVuotoUniVuotaUserVuoto() throws Exception {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment("","","");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentVuotoUniNullUserVuoto() throws Exception {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment("","",null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentNullUserVuoto() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment("",null,"Unina");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentNullUniVuotaUserVuoto() throws Exception {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment("",null,"");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentNullUniNullUserVuoto() throws Exception {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment("",null,null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentUserNull() throws Exception {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment(null,"Dipartimento di ingegneria","Unina");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentUniVuotaUserNull() throws Exception {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment(null,"Dipartimento di ingegneria","");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentUniNullUserNull() throws Exception {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment(null,"Dipartimento di ingegneria",null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentVuotoUserNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment(null,"","Unina");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentVuotoUniVuotaUserNull() throws Exception {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment(null,"","");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentVuotoUniNullUserNull() throws Exception {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment(null,"",null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentNullUserNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment(null,null,"Unina");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentNullUniVuotaUserNull() throws Exception {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment(null,null,"");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateDepartmentNullUniNullUserNull() throws Exception {
		boolean flag=false;
		try {
			userModel.doUpdateDepartment(null,null,null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}










	//TEST getValutazione()
	@Test
	public void testGetValutazioneUsernamePresente() throws SQLException {
		float valutazione=userModel.getValutazione("sime00");
		assertEquals(valutazione, 5.0F);
	}


	@Test
	public void testGetValutazioneUsernameNonPresente() throws SQLException {
		float valutazione=userModel.getValutazione("despacito");
		assertEquals(valutazione, -1.0F);
	}


	@Test
	public void testGetValutazioneUsernameVuoto() throws SQLException {
		boolean flag=false;
		try {
			userModel.getValutazione("");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);;
	}

	@Test
	public void testGetValutazioneUsernameNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.getValutazione(null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);;
	}



	//TEST getRole()
	@Test
	public void testGetRoleUsernamePresente() throws SQLException {
		int role=userModel.getRole("sime00");
		assertEquals(role, 0);

	}

	@Test
	public void testGetRoleUsernameNonPresente() throws SQLException {
		int role=userModel.getRole("despacito");
		assertEquals(role, -1);


	}

	@Test
	public void testGetRoleUsernameVuoto() throws SQLException {
		boolean flag=false;
		try {
			userModel.getRole("");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}

	@Test
	public void testGetRoleUsernameNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.getRole(null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}
}
