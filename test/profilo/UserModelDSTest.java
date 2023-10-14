package profilo;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialBlob;
import org.dbunit.Assertion;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.DataSetException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;



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

	@Test
	public void testCheckLoginPassNonCorretta() throws SQLException {//da errore perchè la funzione AES_ENCRYPT di MySql non è supportata da JUnit
		assertFalse(userModel.checkPassword("sime00","Sime2"));
	}


	@Test
	public void testCheckLoginPassVuota() throws SQLException {
		boolean flag=false;
		try {
			userModel.checkPassword("sime00", "");
		}catch(IllegalArgumentException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testCheckLoginPassNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.checkPassword("sime00", null);
		}catch(IllegalArgumentException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testCheckLoginUserNonPresentePassVuota() throws SQLException {
		boolean flag=false;
		try {
			userModel.checkPassword("despacito","");
		}catch(IllegalArgumentException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testCheckLoginUserNonPresentePassNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.checkPassword("despacito",null);
		}catch(IllegalArgumentException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testCheckLoginUserVuotoPassVuota() throws SQLException {
		boolean flag=false;
		try {
			userModel.checkPassword("","");
		}catch(IllegalArgumentException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testCheckLoginUserVuotoPassNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.checkPassword("",null);
		}catch(IllegalArgumentException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testCheckLoginUserNullPassVuota() throws SQLException {
		boolean flag=false;
		try {
			userModel.checkPassword("","");
		}catch(IllegalArgumentException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testCheckLoginUserNullPassNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.checkPassword(null,null);
		}catch(IllegalArgumentException e) {
			flag=true;
		}
		assertTrue(flag);
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
    assertEquals(Timestamp.valueOf("2011-11-11 00:00:00.0"), new Timestamp(us.getBloccato().getTime()));
		assertEquals(us.isVerificato(), false); //CR2
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
    assertNull(us.getBloccato());
		assertEquals(us.isVerificato(), false); //CR2
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
	public void testDoRetrieveUsersPresenti() throws SQLException, IOException {
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
		InputStream stream1=new ByteArrayInputStream("'Img'".getBytes());
		Blob b1=new SerialBlob(stream1.readAllBytes());
		us1.setImg(b1);
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
		InputStream stream2=new ByteArrayInputStream("'Img'".getBytes());
		Blob b2=new SerialBlob(stream2.readAllBytes());
		us2.setImg(b2);

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
		InputStream stream3=new ByteArrayInputStream("'Img'".getBytes());
		Blob b3=new SerialBlob(stream3.readAllBytes());
		us3.setImg(b3);
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
		InputStream stream4=new ByteArrayInputStream("'Img'".getBytes());
		Blob b4=new SerialBlob(stream4.readAllBytes());
		us4.setImg(b4);
		aspected.add(us4);

		assertEquals(rs.size(), aspected.size());
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
		}
	}


	@Test
	public void testDoRetrieveUsersNonPresenti() throws SQLException {
		ds.getConnection().prepareStatement("DELETE FROM Utente WHERE Ruolo=0;").execute();
		Collection<UserBean>users=userModel.doRetrieveUsers();
		assertEquals(users.size(), 0);
	}


	//TEST doRetrieveByParameterUser()
	@Test
	public void testMotoreRicercaROrderASCRating() throws SQLException {
		Collection<UserBean> result=new ArrayList<>();
		result=userModel.doRetrieveByParametersUser("sim", "ASC", 5);
		ArrayList<UserBean> rs=new ArrayList<>(result);
		ArrayList<UserBean> aspected=new ArrayList<>();
		UserBean us1=new UserBean();
		us1.setUsername("sime00");
		us1.setNome("Simone");
		us1.setCognome("Della Porta");
		us1.setDenominazione("Universita degli studi di Salerno");
		us1.setDipName("Dipartimento di Informatica");
		aspected.add(us1);
		assertEquals(rs.size(), aspected.size());
		for(int i=0;i<result.size();i++) {
			assertEquals(rs.get(i).getUsername(),aspected.get(i).getUsername());
			assertEquals(rs.get(i).getNome(),aspected.get(i).getNome());
			assertEquals(rs.get(i).getCognome(),aspected.get(i).getCognome());
			assertEquals(rs.get(i).getDenominazione(),aspected.get(i).getDenominazione());
			assertEquals(rs.get(i).getDipName(),aspected.get(i).getDipName());
		}
	}


	@Test
	public void testMotoreRicercaROrderASCNoRating() throws SQLException {
		Collection<UserBean> result=new ArrayList<>();
		result=userModel.doRetrieveByParametersUser("sim", "ASC",0);
		ArrayList<UserBean> rs=new ArrayList<>(result);
		ArrayList<UserBean> aspected=new ArrayList<>();
		UserBean us1=new UserBean();
		us1.setUsername("sime00");
		us1.setNome("Simone");
		us1.setCognome("Della Porta");
		us1.setDenominazione("Universita degli studi di Salerno");
		us1.setDipName("Dipartimento di Informatica");
		UserBean us2=new UserBean();
		us2.setUsername("sim");
		us2.setNome("Simone");
		us2.setCognome("Rossi");
		us2.setDenominazione("Universita degli studi di Salerno");
		us2.setDipName("Dipartimento di Informatica");
		aspected.add(us2);
		aspected.add(us1);
		assertEquals(rs.size(), aspected.size());
		for(int i=0;i<result.size();i++) {
			assertEquals(rs.get(i).getUsername(),aspected.get(i).getUsername());
			assertEquals(rs.get(i).getNome(),aspected.get(i).getNome());
			assertEquals(rs.get(i).getCognome(),aspected.get(i).getCognome());
			assertEquals(rs.get(i).getDenominazione(),aspected.get(i).getDenominazione());
			assertEquals(rs.get(i).getDipName(),aspected.get(i).getDipName());
		}
	}


	@Test
	public void testMotoreRicercaROrderDESCRating() throws SQLException {
		Collection<UserBean> result=new ArrayList<>();
		result=userModel.doRetrieveByParametersUser("sim", "DESC", 5);
		ArrayList<UserBean> rs=new ArrayList<>(result);
		ArrayList<UserBean> aspected=new ArrayList<>();
		UserBean us1=new UserBean();
		us1.setUsername("sime00");
		us1.setNome("Simone");
		us1.setCognome("Della Porta");
		us1.setDenominazione("Universita degli studi di Salerno");
		us1.setDipName("Dipartimento di Informatica");
		aspected.add(us1);
		assertEquals(rs.size(), aspected.size());
		for(int i=0;i<result.size();i++) {
			assertEquals(rs.get(i).getUsername(),aspected.get(i).getUsername());
			assertEquals(rs.get(i).getNome(),aspected.get(i).getNome());
			assertEquals(rs.get(i).getCognome(),aspected.get(i).getCognome());
			assertEquals(rs.get(i).getDenominazione(),aspected.get(i).getDenominazione());
			assertEquals(rs.get(i).getDipName(),aspected.get(i).getDipName());
		}
	}


	@Test
	public void testMotoreRicercaROrderDESCNoRating() throws SQLException {
		Collection<UserBean> result=new ArrayList<>();
		result=userModel.doRetrieveByParametersUser("sim", "DESC",0);
		ArrayList<UserBean> rs=new ArrayList<>(result);
		ArrayList<UserBean> aspected=new ArrayList<>();
		UserBean us1=new UserBean();
		us1.setUsername("sime00");
		us1.setNome("Simone");
		us1.setCognome("Della Porta");
		us1.setDenominazione("Universita degli studi di Salerno");
		us1.setDipName("Dipartimento di Informatica");
		aspected.add(us1);
		UserBean us2=new UserBean();
		us2.setUsername("sim");
		us2.setNome("Simone");
		us2.setCognome("Rossi");
		us2.setDenominazione("Universita degli studi di Salerno");
		us2.setDipName("Dipartimento di Informatica");
		aspected.add(us2);

		assertEquals(rs.size(), aspected.size());
		for(int i=0;i<result.size();i++) {
			assertEquals(rs.get(i).getUsername(),aspected.get(i).getUsername());
			assertEquals(rs.get(i).getNome(),aspected.get(i).getNome());
			assertEquals(rs.get(i).getCognome(),aspected.get(i).getCognome());
			assertEquals(rs.get(i).getDenominazione(),aspected.get(i).getDenominazione());
			assertEquals(rs.get(i).getDipName(),aspected.get(i).getDipName());
		}
	}


	@Test
	public void testMotoreRicercaROrderNVRating() throws SQLException {
		Collection<UserBean> result=new ArrayList<>();
		result=userModel.doRetrieveByParametersUser("sim", "novalue", 5);
		ArrayList<UserBean> rs=new ArrayList<>(result);
		ArrayList<UserBean> aspected=new ArrayList<>();
		UserBean us1=new UserBean();
		us1.setUsername("sime00");
		us1.setNome("Simone");
		us1.setCognome("Della Porta");
		us1.setDenominazione("Universita degli studi di Salerno");
		us1.setDipName("Dipartimento di Informatica");
		aspected.add(us1);
		assertEquals(rs.size(), aspected.size());
		for(int i=0;i<result.size();i++) {
			assertEquals(rs.get(i).getUsername(),aspected.get(i).getUsername());
			assertEquals(rs.get(i).getNome(),aspected.get(i).getNome());
			assertEquals(rs.get(i).getCognome(),aspected.get(i).getCognome());
			assertEquals(rs.get(i).getDenominazione(),aspected.get(i).getDenominazione());
			assertEquals(rs.get(i).getDipName(),aspected.get(i).getDipName());
		}
	}


	@Test
	public void testMotoreRicercaROrderNVNoRating() throws SQLException {
		Collection<UserBean> result=new ArrayList<>();
		result=userModel.doRetrieveByParametersUser("sim", "novalue",0);
		ArrayList<UserBean> rs=new ArrayList<>(result);
		ArrayList<UserBean> aspected=new ArrayList<>();
		UserBean us1=new UserBean();
		us1.setUsername("sime00");
		us1.setNome("Simone");
		us1.setCognome("Della Porta");
		us1.setDenominazione("Universita degli studi di Salerno");
		us1.setDipName("Dipartimento di Informatica");
		UserBean us2=new UserBean();
		us2.setUsername("sim");
		us2.setNome("Simone");
		us2.setCognome("Rossi");
		us2.setDenominazione("Universita degli studi di Salerno");
		us2.setDipName("Dipartimento di Informatica");
		aspected.add(us2);
		aspected.add(us1);
		assertEquals(rs.size(), aspected.size());
		for(int i=0;i<result.size();i++) {
			assertEquals(rs.get(i).getUsername(),aspected.get(i).getUsername());
			assertEquals(rs.get(i).getNome(),aspected.get(i).getNome());
			assertEquals(rs.get(i).getCognome(),aspected.get(i).getCognome());
			assertEquals(rs.get(i).getDenominazione(),aspected.get(i).getDenominazione());
			assertEquals(rs.get(i).getDipName(),aspected.get(i).getDipName());
		}
	}



	@Test
	public void testMotoreRicercaROrderEmptyRating() throws SQLException {
		boolean flag=false;
		try {
			userModel.doRetrieveByParametersUser("sim", "", 5);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testMotoreRicercaROrderEmptyNoRating() throws SQLException {
		boolean flag=false;
		try {
			userModel.doRetrieveByParametersUser("sim", "", 0);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testMotoreRicercaROrderNullRating() throws SQLException {
		boolean flag=false;
		try {
			userModel.doRetrieveByParametersUser("sim",null, 5);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testMotoreRicercaROrderNullNoRating() throws SQLException {
		boolean flag=false;
		try {
			userModel.doRetrieveByParametersUser("sim",null, 0);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}



	@Test
	public void testMotoreRicercaROrderASCRatingUserNotPresent() throws SQLException {
		Collection<UserBean> result=new ArrayList<>();
		result=userModel.doRetrieveByParametersUser("despacito", "ASC", 5);
		ArrayList<UserBean> aspected=new ArrayList<>();
		assertEquals(result.size(), aspected.size());
	}


	@Test
	public void testMotoreRicercaROrderASCNoRatingUserNotPresent() throws SQLException {
		Collection<UserBean> result=new ArrayList<>();
		result=userModel.doRetrieveByParametersUser("despacito", "ASC",0);
		ArrayList<UserBean> aspected=new ArrayList<>();
		assertEquals(result.size(), aspected.size());
	}


	@Test
	public void testMotoreRicercaROrderDESCRatingUserNotPresent() throws SQLException {
		Collection<UserBean> result=new ArrayList<>();
		result=userModel.doRetrieveByParametersUser("despacito", "DESC", 5);
		ArrayList<UserBean> aspected=new ArrayList<>();
		assertEquals(result.size(), aspected.size());
	}


	@Test
	public void testMotoreRicercaROrderDESCNoRatingUserNotPresent() throws SQLException {
		Collection<UserBean> result=new ArrayList<>();
		result=userModel.doRetrieveByParametersUser("despacito", "DESC",0);
		ArrayList<UserBean> aspected=new ArrayList<>();
		assertEquals(result.size(), aspected.size());
	}


	@Test
	public void testMotoreRicercaROrderNVRatingUserNotPresent() throws SQLException {
		Collection<UserBean> result=new ArrayList<>();
		result=userModel.doRetrieveByParametersUser("despacito", "novalue", 5);
		ArrayList<UserBean> aspected=new ArrayList<>();
		assertEquals(result.size(), aspected.size());
	}


	@Test
	public void testMotoreRicercaROrderNVNoRatingUserNotPresent() throws SQLException {
		Collection<UserBean> result=new ArrayList<>();
		result=userModel.doRetrieveByParametersUser("despacito", "novalue",0);
		ArrayList<UserBean> aspected=new ArrayList<>();
		assertEquals(result.size(), aspected.size());
	}



	@Test
	public void testMotoreRicercaROrderEmptyRatingUserNotPresent() throws SQLException {
		boolean flag=false;
		try {
			userModel.doRetrieveByParametersUser("despacito", "", 5);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testMotoreRicercaROrderEmptyNoRatingUserNotPresent() throws SQLException {
		boolean flag=false;
		try {
			userModel.doRetrieveByParametersUser("despacito", "", 0);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testMotoreRicercaROrderNullRatingUserNotPresent() throws SQLException {
		boolean flag=false;
		try {
			userModel.doRetrieveByParametersUser("despacito",null, 5);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testMotoreRicercaROrderNullNoRatingUserNotPresent() throws SQLException {
		boolean flag=false;
		try {
			userModel.doRetrieveByParametersUser("despacito",null, 0);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testMotoreRicercaROrderASCRatingUserVuoto() throws SQLException {
		Collection<UserBean> result=new ArrayList<>();
		result=userModel.doRetrieveByParametersUser("", "ASC", 5);
		ArrayList<UserBean> rs=new ArrayList<>(result);
		ArrayList<UserBean> aspected=new ArrayList<>();
		UserBean us1=new UserBean();
		us1.setUsername("sime00");
		us1.setNome("Simone");
		us1.setCognome("Della Porta");
		us1.setDenominazione("Universita degli studi di Salerno");
		us1.setDipName("Dipartimento di Informatica");
		UserBean us3=new UserBean();
		us3.setUsername("fry");
		us3.setNome("Francesco");
		us3.setCognome("Di Lauro");
		us3.setDenominazione("Universita degli studi di Salerno");
		us3.setDipName("Dipartimento di Informatica");
		aspected.add(us3);
		aspected.add(us1);
		assertEquals(rs.size(), aspected.size());
		for(int i=0;i<result.size();i++) {
			assertEquals(rs.get(i).getUsername(),aspected.get(i).getUsername());
			assertEquals(rs.get(i).getNome(),aspected.get(i).getNome());
			assertEquals(rs.get(i).getCognome(),aspected.get(i).getCognome());
			assertEquals(rs.get(i).getDenominazione(),aspected.get(i).getDenominazione());
			assertEquals(rs.get(i).getDipName(),aspected.get(i).getDipName());
		}
	}


	@Test
	public void testMotoreRicercaROrderASCNoRatingUserVuoto() throws SQLException {
		Collection<UserBean> result;
		result=userModel.doRetrieveByParametersUser("", "ASC",0);
		ArrayList<UserBean> rs=new ArrayList<>(result);
		ArrayList<UserBean> aspected=new ArrayList<>();
		UserBean us1=new UserBean();
		us1.setUsername("sime00");
		us1.setNome("Simone");
		us1.setCognome("Della Porta");
		us1.setDenominazione("Universita degli studi di Salerno");
		us1.setDipName("Dipartimento di Informatica");
		UserBean us2=new UserBean();
		us2.setUsername("sim");
		us2.setNome("Simone");
		us2.setCognome("Rossi");
		us2.setDenominazione("Universita degli studi di Salerno");
		us2.setDipName("Dipartimento di Informatica");
		UserBean us3=new UserBean();
		us3.setUsername("fry");
		us3.setNome("Francesco");
		us3.setCognome("Di Lauro");
		us3.setDenominazione("Universita degli studi di Salerno");
		us3.setDipName("Dipartimento di Informatica");
		UserBean us4=new UserBean();
		us4.setUsername("califano87");
		us4.setNome("Alfonso");
		us4.setCognome("Califano");
		us4.setDenominazione("Universita degli studi di Salerno");
		us4.setDipName("Dipartimento di Informatica");
		aspected.add(us4);
		aspected.add(us2);
		aspected.add(us3);
		aspected.add(us1);

		assertEquals(rs.size(), aspected.size());
		for(int i=0;i<result.size();i++) {
			assertEquals(rs.get(i).getUsername(),aspected.get(i).getUsername());
			assertEquals(rs.get(i).getNome(),aspected.get(i).getNome());
			assertEquals(rs.get(i).getCognome(),aspected.get(i).getCognome());
			assertEquals(rs.get(i).getDenominazione(),aspected.get(i).getDenominazione());
			assertEquals(rs.get(i).getDipName(),aspected.get(i).getDipName());
		}
	}


	@Test
	public void testMotoreRicercaROrderDESCRatingUserVuoto() throws SQLException {
		Collection<UserBean> result=new ArrayList<>();
		result=userModel.doRetrieveByParametersUser("", "DESC", 5);
		ArrayList<UserBean> rs=new ArrayList<>(result);
		ArrayList<UserBean> aspected=new ArrayList<>();
		UserBean us1=new UserBean();
		us1.setUsername("sime00");
		us1.setNome("Simone");
		us1.setCognome("Della Porta");
		us1.setDenominazione("Universita degli studi di Salerno");
		us1.setDipName("Dipartimento di Informatica");
		UserBean us3=new UserBean();
		us3.setUsername("fry");
		us3.setNome("Francesco");
		us3.setCognome("Di Lauro");
		us3.setDenominazione("Universita degli studi di Salerno");
		us3.setDipName("Dipartimento di Informatica");
		aspected.add(us3);
		aspected.add(us1);
		assertEquals(rs.size(), aspected.size());
		for(int i=0;i<result.size();i++) {
			assertEquals(rs.get(i).getUsername(),aspected.get(i).getUsername());
			assertEquals(rs.get(i).getNome(),aspected.get(i).getNome());
			assertEquals(rs.get(i).getCognome(),aspected.get(i).getCognome());
			assertEquals(rs.get(i).getDenominazione(),aspected.get(i).getDenominazione());
			assertEquals(rs.get(i).getDipName(),aspected.get(i).getDipName());
		}
	}


	@Test
	public void testMotoreRicercaROrderDESCNoRatingUserVuoto() throws SQLException {
		Collection<UserBean> result=new ArrayList<>();
		result=userModel.doRetrieveByParametersUser("", "DESC",0);
		ArrayList<UserBean> rs=new ArrayList<>(result);
		ArrayList<UserBean> aspected=new ArrayList<>();
		UserBean us1=new UserBean();
		us1.setUsername("sime00");
		us1.setNome("Simone");
		us1.setCognome("Della Porta");
		us1.setDenominazione("Universita degli studi di Salerno");
		us1.setDipName("Dipartimento di Informatica");
		UserBean us2=new UserBean();
		us2.setUsername("sim");
		us2.setNome("Simone");
		us2.setCognome("Rossi");
		us2.setDenominazione("Universita degli studi di Salerno");
		us2.setDipName("Dipartimento di Informatica");
		UserBean us3=new UserBean();
		us3.setUsername("fry");
		us3.setNome("Francesco");
		us3.setCognome("Di Lauro");
		us3.setDenominazione("Universita degli studi di Salerno");
		us3.setDipName("Dipartimento di Informatica");
		UserBean us4=new UserBean();
		us4.setUsername("califano87");
		us4.setNome("Alfonso");
		us4.setCognome("Califano");
		us4.setDenominazione("Universita degli studi di Salerno");
		us4.setDipName("Dipartimento di Informatica");
		aspected.add(us3);
		aspected.add(us1);
		aspected.add(us4);
		aspected.add(us2);
		assertEquals(rs.size(), aspected.size());
		for(int i=0;i<result.size();i++) {
			assertEquals(rs.get(i).getUsername(),aspected.get(i).getUsername());
			assertEquals(rs.get(i).getNome(),aspected.get(i).getNome());
			assertEquals(rs.get(i).getCognome(),aspected.get(i).getCognome());
			assertEquals(rs.get(i).getDenominazione(),aspected.get(i).getDenominazione());
			assertEquals(rs.get(i).getDipName(),aspected.get(i).getDipName());
		}
	}


	@Test
	public void testMotoreRicercaROrderNVRatingUserVuoto() throws SQLException {
		Collection<UserBean> result=new ArrayList<>();
		result=userModel.doRetrieveByParametersUser("", "novalue", 5);
		ArrayList<UserBean> rs=new ArrayList<>(result);
		ArrayList<UserBean> aspected=new ArrayList<>();
		UserBean us1=new UserBean();
		us1.setUsername("sime00");
		us1.setNome("Simone");
		us1.setCognome("Della Porta");
		us1.setDenominazione("Universita degli studi di Salerno");
		us1.setDipName("Dipartimento di Informatica");
		UserBean us3=new UserBean();
		us3.setUsername("fry");
		us3.setNome("Francesco");
		us3.setCognome("Di Lauro");
		us3.setDenominazione("Universita degli studi di Salerno");
		us3.setDipName("Dipartimento di Informatica");
		aspected.add(us3);
		aspected.add(us1);
		assertEquals(rs.size(), aspected.size());
		for(int i=0;i<result.size();i++) {
			assertEquals(rs.get(i).getUsername(),aspected.get(i).getUsername());
			assertEquals(rs.get(i).getNome(),aspected.get(i).getNome());
			assertEquals(rs.get(i).getCognome(),aspected.get(i).getCognome());
			assertEquals(rs.get(i).getDenominazione(),aspected.get(i).getDenominazione());
			assertEquals(rs.get(i).getDipName(),aspected.get(i).getDipName());
		}
	}


	@Test
	public void testMotoreRicercaROrderNVNoRatingUserVuoto() throws SQLException {
		Collection<UserBean> result=new ArrayList<>();
		result=userModel.doRetrieveByParametersUser("", "novalue",0);
		ArrayList<UserBean> rs=new ArrayList<>(result);
		ArrayList<UserBean> aspected=new ArrayList<>();
		UserBean us1=new UserBean();
		us1.setUsername("sime00");
		us1.setNome("Simone");
		us1.setCognome("Della Porta");
		us1.setDenominazione("Universita degli studi di Salerno");
		us1.setDipName("Dipartimento di Informatica");
		UserBean us2=new UserBean();
		us2.setUsername("sim");
		us2.setNome("Simone");
		us2.setCognome("Rossi");
		us2.setDenominazione("Universita degli studi di Salerno");
		us2.setDipName("Dipartimento di Informatica");
		UserBean us3=new UserBean();
		us3.setUsername("fry");
		us3.setNome("Francesco");
		us3.setCognome("Di Lauro");
		us3.setDenominazione("Universita degli studi di Salerno");
		us3.setDipName("Dipartimento di Informatica");
		UserBean us4=new UserBean();
		us4.setUsername("califano87");
		us4.setNome("Alfonso");
		us4.setCognome("Califano");
		us4.setDenominazione("Universita degli studi di Salerno");
		us4.setDipName("Dipartimento di Informatica");
		aspected.add(us4);
		aspected.add(us3);
		aspected.add(us2);
		aspected.add(us1);

		assertEquals(rs.size(), aspected.size());
		for(int i=0;i<result.size();i++) {
			assertEquals(rs.get(i).getUsername(),aspected.get(i).getUsername());
			assertEquals(rs.get(i).getNome(),aspected.get(i).getNome());
			assertEquals(rs.get(i).getCognome(),aspected.get(i).getCognome());
			assertEquals(rs.get(i).getDenominazione(),aspected.get(i).getDenominazione());
			assertEquals(rs.get(i).getDipName(),aspected.get(i).getDipName());
		}
	}


	@Test
	public void testMotoreRicercaROrderEmptyRatingUserVuoto() throws SQLException {
		boolean flag=false;
		try {
			userModel.doRetrieveByParametersUser("", "", 5);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testMotoreRicercaROrderEmptyNoRatingUserVuoto() throws SQLException {
		boolean flag=false;
		try {
			userModel.doRetrieveByParametersUser("", "", 0);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testMotoreRicercaROrderNullRatingUserVuoto() throws SQLException {
		boolean flag=false;
		try {
			userModel.doRetrieveByParametersUser("",null, 5);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testMotoreRicercaROrderNullNoRatingUserVuoto() throws SQLException {
		boolean flag=false;
		try {
			userModel.doRetrieveByParametersUser("",null, 0);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}

	
	@Test
	public void testMotoreRicercaROrderASCRatingUserNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.doRetrieveByParametersUser(null, "ASC", 5);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testMotoreRicercaROrderASCNoRatingUserNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.doRetrieveByParametersUser(null, "ASC",0);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testMotoreRicercaROrderDESCRatingUserNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.doRetrieveByParametersUser(null, "DESC", 5);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testMotoreRicercaROrderDESCNoRatingUserNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.doRetrieveByParametersUser(null, "DESC",0);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}



	@Test
	public void testMotoreRicercaROrderNVRatingUserNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.doRetrieveByParametersUser(null, "novalue", 5);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testMotoreRicercaROrderNVNoRatingUserNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.doRetrieveByParametersUser(null, "novalue",0);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);

	}


	@Test
	public void testMotoreRicercaROrderEmptyRatingUserNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.doRetrieveByParametersUser(null, "", 5);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testMotoreRicercaROrderEmptyNoRatingUserNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.doRetrieveByParametersUser(null, "", 0);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testMotoreRicercaROrderNullRatingUserNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.doRetrieveByParametersUser(null,null, 5);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testMotoreRicercaROrderNullNoRatingUserNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.doRetrieveByParametersUser(null,null, 0);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}

	//TEST doSave()
	@Test
	public void testDoSave() throws Exception{//da errore perchè la funzione AES_ENCRYPT di MySql non è supportata da JUnit
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
		us1.setBloccato(null);
		us1.setVerificato(false); //CR2
		InputStream stream1=new ByteArrayInputStream("'Img'".getBytes());
		Blob b1=new SerialBlob(stream1.readAllBytes());
		us1.setImg(b1);
		userModel.doSave(us1);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/UtenteExpected.xml")).getTable("Utente");
		ITable actual=this.getConnection().createDataSet().getTable("Utente");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
	}


	@Test
	public void testDoSaveAlreadyExists() throws IOException, SQLException {
		boolean flag = false;
		UserBean us1 = new UserBean();
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
		us1.setBloccato(null);
		us1.setVerificato(false);
		InputStream stream1=new ByteArrayInputStream("'Img'".getBytes());
		Blob b1=new SerialBlob(stream1.readAllBytes());
		us1.setImg(b1);
		try {
			userModel.doSave(us1);
		} catch (SQLException e) {
			flag = true;
		}
		assertTrue(flag);
	}

	public void testDoSaveNull() throws SQLException {
		assertThrows(NullPointerException.class, ()->{userModel.doSave(null);});
	}

	//TEST manageBan()
	@Test
	public void testManageBanDatiValidi() throws Exception {
		userModel.manageBan("sime00",Date.valueOf("2023-12-15"));
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
			assertEquals(tbexpected.getValue(i, "Bloccato").toString(),tbactual.getValue(i, "Bloccato").toString());
		}
	}

	@Test
	public void testManageBanDataNonValida() throws Exception {
		boolean flag=false;
		try {
			userModel.manageBan("sime00",Date.valueOf("2022-01-15"));
		}catch(IllegalArgumentException e) {
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
		userModel.manageBan("despacito",Date.valueOf("2023-12-15"));
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
		}catch(IllegalArgumentException e) {
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
		}catch(IllegalArgumentException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testManageBanUsernameVuotoDataNonValida() throws Exception {
		boolean flag=false;
		try {
			userModel.manageBan("",Date.valueOf("2022-01-15"));
		}catch(IllegalArgumentException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testManageBanUsernameVuotoDataNull() throws Exception {
		boolean flag=false;
		try {
			userModel.manageBan("",null);
		}catch(IllegalArgumentException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testManageBanUsernameNullDataValida() throws Exception {
		boolean flag=false;
		try {
			userModel.manageBan(null,Date.valueOf("2022-03-15"));
		}catch(IllegalArgumentException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testManageBanUsernameNullDataNonValida() throws Exception {
		boolean flag=false;
		try {
			userModel.manageBan(null,Date.valueOf("2022-01-15"));
		}catch(IllegalArgumentException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testManageBanUsernameNullDataNull() throws Exception {
		boolean flag=false;
		try {
			userModel.manageBan(null,null);
		}catch(IllegalArgumentException e) {
			flag=true;
		}
		assertTrue(flag);
	}

	public void testDoUpdateBloccatoUsername() throws Exception {
		String username = "califano87";
		Timestamp blockdate = Timestamp.valueOf("2023-12-25 00:00:00.0");
		userModel.doUpdateBloccato(username, blockdate);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/UtenteExpectedBloccatoUpdate.xml")).getTable("Utente");
		ITable actual=this.getConnection().createDataSet().getTable("Utente");
		SortedTable tbexpected = new SortedTable(expected);
		SortedTable tbactual = new SortedTable(actual);
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
			assertEquals(tbexpected.getValue(i, "Bloccato").toString(),tbactual.getValue(i, "Bloccato").toString());
		}
	}

	public void testDoUpdateBloccatoEmail() throws Exception{
		String email = "califano87@gmail.com";
		Timestamp blockdate = Timestamp.valueOf("2023-12-25 00:00:00.0");
		userModel.doUpdateBloccato(email, blockdate);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/UtenteExpectedBloccatoUpdate.xml")).getTable("Utente");
		ITable actual=this.getConnection().createDataSet().getTable("Utente");
		SortedTable tbexpected = new SortedTable(expected);
		SortedTable tbactual = new SortedTable(actual);
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
			assertEquals(tbexpected.getValue(i, "Bloccato").toString(),tbactual.getValue(i, "Bloccato").toString());
		}
	}
	@Test(expected = SQLException.class)
	public void testDoUpdateBloccatoNotExistingUsername() throws Exception {
		String username = "pip";
		Timestamp blockdate = Timestamp.valueOf("2023-12-25 00:00:00.0");
		userModel.doUpdateBloccato(username, blockdate);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/UtenteExpectedBloccatoUpdate.xml")).getTable("Utente");
		ITable actual=this.getConnection().createDataSet().getTable("Utente");
		SortedTable tbexpected = new SortedTable(expected);
		SortedTable tbactual = new SortedTable(actual);
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
			assertEquals(tbexpected.getValue(i, "Bloccato").toString(),tbactual.getValue(i, "Bloccato").toString());
		}
	}
	@Test(expected = SQLException.class)
	public void testDoUpdateBloccatoNotExistingEmail() throws Exception {
		String email = "a@aasdasdasd.aa";
		Timestamp blockdate = Timestamp.valueOf("2023-12-25 00:00:00.0");
		userModel.doUpdateBloccato(email, blockdate);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/UtenteExpectedBloccatoUpdate.xml")).getTable("Utente");
		ITable actual=this.getConnection().createDataSet().getTable("Utente");
		SortedTable tbexpected = new SortedTable(expected);
		SortedTable tbactual = new SortedTable(actual);
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
			assertEquals(tbexpected.getValue(i, "Bloccato").toString(),tbactual.getValue(i, "Bloccato").toString());
		}
	}

	public void testDoUpdateBloccatoEmptyUsernameOrEmail() throws IllegalArgumentException {
		assertThrows(IllegalArgumentException.class, () -> {userModel.doUpdateBloccato("", Timestamp.valueOf("2023-12-25 00:00:00.0"));});
	}

	public void testDoUpdateBloccatoNullUsernameOrEmail() throws NullPointerException {
		assertThrows(IllegalArgumentException.class, () -> {userModel.doUpdateBloccato(null, Timestamp.valueOf("2023-12-25 00:00:00.0"));});
	}

	public void testDoUpdateUsernameWithInvalidDate() throws NullPointerException {
		assertThrows(IllegalArgumentException.class, () -> {userModel.doUpdateBloccato("califano87", null);});
	}

	public void testDoUpdateEmailWithInvalidDate() throws NullPointerException {
		assertThrows(IllegalArgumentException.class, () -> {userModel.doUpdateBloccato("califano87@gmail.com", null);});
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


	//TEST doUpdatePassword()
	@Test
	public void testDoUpdatePassword() throws Exception {//da errore perchè la funzione AES_ENCRYPT di MySql non è supportata da JUnit
		userModel.doUpdatePassword("sime00","Sime2");
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/UtenteExpectedPass.xml")).getTable("Utente");
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
	public void testDoUpdatePasswordVuota() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdatePassword("sime00", "");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdatePasswordNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdatePassword("sime00",null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdatePasswordUsernameNonPresente() throws Exception {//da errore perchè la funzione AES_ENCRYPT di MySql non è supportata da JUnit
		userModel.doUpdatePassword("despacito", "Sime2");
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/UtenteExpectedPassUsNotPresent.xml")).getTable("Utente");
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
	public void testDoUpdatePasswordVuotaUsernameNonPresente() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdatePassword("despacito", "");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdatePasswordNullUsernameNonPresente() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdatePassword("despacito",null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdatePasswordUsernameVuoto() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdatePassword("","Sime2");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdatePasswordVuotaUsernameVuoto() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdatePassword("", "");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdatePasswordNullUsernameVuoto() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdatePassword("",null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdatePasswordUsernameNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdatePassword(null,"Sime2");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdatePasswordVuotaUsernameNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdatePassword(null, "");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdatePasswordNullUsernameNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.doUpdatePassword(null,null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


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


	//TEst doUpdateImage()
	@Test 
	public void testDoUpdateImage() throws Exception {
		userModel.doUpdateImage("sime00", new ByteArrayInputStream("'newImg'".getBytes()));
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/UtenteExpectedImage.xml")).getTable("Utente");
		ITable actual=this.getConnection().createDataSet().getTable("Utente");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
	}


	@Test
	public void testDoUpdateImageNull() throws Exception{
		boolean flag=false;
		try{
			userModel.doUpdateImage("sime00", null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test 
	public void testDoUpdateImageUserNonPresente() throws Exception {
		userModel.doUpdateImage("despacito", new ByteArrayInputStream("'newImg'".getBytes()));
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/UtenteExpectedImageUserNotPresent.xml")).getTable("Utente");
		ITable actual=this.getConnection().createDataSet().getTable("Utente");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
	}


	@Test
	public void testDoUpdateImageNullUserNonPresente() throws Exception{
		boolean flag=false;
		try{
			userModel.doUpdateImage("despacito", null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateImageUserVuoto() throws Exception{
		boolean flag=false;
		try{
			userModel.doUpdateImage("", new ByteArrayInputStream("'newImg'".getBytes()));
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateImageNullUserVuoto() throws Exception{
		boolean flag=false;
		try{
			userModel.doUpdateImage("", null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateImageUserNull() throws Exception{
		boolean flag=false;
		try{
			userModel.doUpdateImage(null, new ByteArrayInputStream("'newImg'".getBytes()));
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoUpdateImageNullUserNull() throws Exception{
		boolean flag=false;
		try{
			userModel.doUpdateImage(null, null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}

	//Test doUpdateVerificato() CR2
	@Test
	public void testDoUpdateVerificatoOK() throws Exception{
		userModel.doUpdateVerificato("fry@gmail.com", true);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/UtenteExpectedVerificato.xml")).getTable("Utente");
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
			assertEquals(tbexpected.getValue(i, "Verificato").toString(), tbactual.getValue(i, "Verificato").toString()); //CR2
		}
	}

	@Test(expected = SQLException.class)
	public void testDoUpdateVerificatoUtenteNonPresente() throws Exception{
		userModel.doUpdateVerificato("prova@gmail.com", true);
	}

	@Test
	public void testDoUpdateVerificatoUtenteNull() throws Exception{
		assertThrows(IllegalArgumentException.class, ()->{userModel.doUpdateVerificato(null, true);});
	}

	@Test
	public void testDoUpdateVerificatoUtenteEmpty() throws Exception{
		assertThrows(IllegalArgumentException.class, ()->{userModel.doUpdateVerificato("", true);});
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
		assertTrue(flag);
	}

	@Test
	public void testGetValutazioneUsernameNull() throws SQLException {
		boolean flag=false;
		try {
			userModel.getValutazione(null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
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
