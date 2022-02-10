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

public class PaymentMethodModelDSTest extends DataSourceBasedDBTestCase{
	private DataSource ds;
	private PaymentMethodModelDS paymentModel;


	@Override
	protected DataSource getDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/MetodoPagamento.sql'");
		dataSource.setUser("supo-poweroff");
		dataSource.setPassword("09134");
		return dataSource;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/init/MetodoPagamento.xml"));
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
		paymentModel=new PaymentMethodModelDS(ds);
	}


	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}


	@Test
	public void testDoRetrieveByKey() throws SQLException {
		PaymentMethodBean payment=new PaymentMethodBean();
		payment.setNumeroCarta("0000000000000000");
		payment.setDataScadenza(Date.valueOf("2024-05-15"));
		payment.setNomeIntestatario("Simone");
		payment.setCognomeIntestatario("Della Porta");
		payment.setUsername("sime00");
		PaymentMethodBean result=paymentModel.doRetrieveByKey("0000000000000000");
		assertEquals(payment.getNumeroCarta(), result.getNumeroCarta());
		assertEquals(payment.getDataScadenza(), result.getDataScadenza());
		assertEquals(payment.getNomeIntestatario(), result.getNomeIntestatario());
		assertEquals(payment.getCognomeIntestatario(), result.getCognomeIntestatario());
		assertEquals(payment.getUsername(), result.getUsername());
	}


	@Test
	public void testDoRetrieveByKeyNotPresent() throws SQLException {
		PaymentMethodBean payment=new PaymentMethodBean();
		PaymentMethodBean result=paymentModel.doRetrieveByKey("2222222222222222");
		assertEquals(payment, result);
	}


	@Test
	public void testDoRetrieveByKeyFormatoNonCorretto() throws SQLException {
		boolean flag=false;
		try {
			paymentModel.doRetrieveByKey("1234");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoRetrieveByKeyVuota() throws SQLException {
		boolean flag=false;
		try {
			paymentModel.doRetrieveByKey("");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	@Test
	public void testDoRetrieveByKeyNull() throws SQLException {
		boolean flag=false;
		try {
			paymentModel.doRetrieveByKey(null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}


	//TEST doRetrieveByUsername()
	@Test
	public void testDoRetrieveByUsername() throws SQLException {
		Collection<PaymentMethodBean> result=new ArrayList<>();
		result=paymentModel.doRetrieveByUsername("sime00");
		ArrayList<PaymentMethodBean> rs=new ArrayList<>(result);
		ArrayList<PaymentMethodBean> aspected=new ArrayList<>();
		PaymentMethodBean pb1=new PaymentMethodBean();
		pb1.setNumeroCarta("0000000000000000");
		pb1.setDataScadenza(Date.valueOf("2024-05-15"));
		pb1.setNomeIntestatario("Simone");
		pb1.setCognomeIntestatario("Della Porta");
		pb1.setUsername("sime00");
		aspected.add(pb1);
		PaymentMethodBean pb2=new PaymentMethodBean();
		pb2.setNumeroCarta("1111111111111111");
		pb2.setDataScadenza(Date.valueOf("2024-07-12"));
		pb2.setNomeIntestatario("Simone");
		pb2.setCognomeIntestatario("Della Porta");
		pb2.setUsername("sime00");
		aspected.add(pb2);
		assertEquals(rs.size(), aspected.size());
		for(int i=0;i<rs.size();i++) {
			assertEquals(rs.get(i).getNumeroCarta(),aspected.get(i).getNumeroCarta());
			assertEquals(rs.get(i).getDataScadenza(),aspected.get(i).getDataScadenza());
			assertEquals(rs.get(i).getNomeIntestatario(),aspected.get(i).getNomeIntestatario());
			assertEquals(rs.get(i).getCognomeIntestatario(),aspected.get(i).getCognomeIntestatario());
			assertEquals(rs.get(i).getUsername(),aspected.get(i).getUsername());
		}
		
	}
	
	
	@Test
	public void testDoRetrieveByUsernameNonPresente() throws SQLException {
		Collection<PaymentMethodBean> result=new ArrayList<>();
		result=paymentModel.doRetrieveByUsername("despacito");
		assertEquals(result.size(), 0);
	}
	
	
	@Test
	public void testDoRetrieveByUsernameVuoto() throws SQLException {
		boolean flag=false;
		try {
			paymentModel.doRetrieveByUsername("");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}
	
	
	@Test
	public void testDoRetrieveByUsernameNull() throws SQLException {
		boolean flag=false;
		try {
			paymentModel.doRetrieveByUsername(null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}
	
	
	//TEST doSave()
	@Test
	public void testDoSave() throws Exception {
		PaymentMethodBean bean=new PaymentMethodBean();
		bean.setNumeroCarta("3333333333333333");
		bean.setDataScadenza(Date.valueOf("2025-06-18"));
		bean.setNomeIntestatario("Simone");
		bean.setCognomeIntestatario("Della Porta");
		bean.setUsername("sime00");
		paymentModel.doSave(bean);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/MetodoPagamentoExpectedSave.xml")).getTable("MetodoPagamento");
		ITable actual=this.getConnection().createDataSet().getTable("MetodoPagamento");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
	}
	
	
	@Test
	public void testDoSaveAlreadyExist() throws Exception {
		PaymentMethodBean bean=new PaymentMethodBean();
		bean.setNumeroCarta("1111111111111111");
		bean.setDataScadenza(Date.valueOf("2024-07-12"));
		bean.setNomeIntestatario("Simone");
		bean.setCognomeIntestatario("Della Porta");
		bean.setUsername("sime00");
		boolean flag=false;
		try {
		paymentModel.doSave(bean);
		}catch(SQLException e) {
			flag=true;
		}
		assertTrue(flag);
	}
	
	
	@Test
	public void testDoSaveNull() throws SQLException {
		boolean flag=false;
		try {
			paymentModel.doSave(null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}
	
	
	//TEST doDelete()
	@Test
	public void testDoDelete() throws Exception {
		paymentModel.doDeleteByNumber("1111111111111111");
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/MetodoPagamentoExpectedDelete.xml")).getTable("MetodoPagamento");
		ITable actual=this.getConnection().createDataSet().getTable("MetodoPagamento");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
	}
	
	
	@Test
	public void testDoDeleteCartaNonPresente()throws Exception {
		paymentModel.doDeleteByNumber("5555555555555555");
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/MetodoPagamentoExpectedDeleteCardNotPreset.xml")).getTable("MetodoPagamento");
		ITable actual=this.getConnection().createDataSet().getTable("MetodoPagamento");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
	}
	
	
	@Test
	public void testDoDeleteFormatoNonCorretto() throws SQLException {
		boolean flag=false;
		try {
			paymentModel.doDeleteByNumber("1234");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}	
	
	
	@Test
	public void testDoDeleteVuoto() throws SQLException {
		boolean flag=false;
		try {
			paymentModel.doDeleteByNumber("");
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}
	
	
	@Test
	public void testDoDeleteNull() throws SQLException {
		boolean flag=false;
		try {
			paymentModel.doDeleteByNumber(null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}

}
