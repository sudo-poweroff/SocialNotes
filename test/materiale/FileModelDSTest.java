package materiale;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialBlob;

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

import com.sun.source.tree.AssertTree;


public class FileModelDSTest extends DataSourceBasedDBTestCase{
	private DataSource ds;
	private FileModelDS fileModel;

	@Override
	protected DataSource getDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/Files.sql'");
		dataSource.setUser("supo-poweroff");
		dataSource.setPassword("09134");
		return dataSource;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/init/Files.xml"));
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
		fileModel=new FileModelDS(ds);
	}


	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}


	//TEST doRetrieveBuKey()
	@Test
	public void testDoRetrieveByKey() throws SQLException, IOException {
		FileBean bean=fileModel.doRetrieveByKey(3);
		assertEquals(bean.getFilename(), "FIA");
		assertEquals(bean.getFormato(), "pdf");
		assertEquals(bean.getDimensione(), 200);
		assertEquals(bean.getIdFile(), 3);

		InputStream is=bean.getContenuto();
		InputStream stream = new ByteArrayInputStream("'contenuto'".getBytes());
		Blob blob = new SerialBlob(is.readAllBytes());
		Blob blob2 = new SerialBlob(stream.readAllBytes());
		String strBean = new String(blob.getBytes(1l, (int) blob.length()));
		String strExpected = new String(blob2.getBytes(1l, (int) blob2.length())); 
		assertEquals(strBean, strExpected);
	}


	@Test
	public void testDoRetrieveByKeyNotPresent() throws SQLException {
		FileBean bean=fileModel.doRetrieveByKey(10);
		assertEquals(bean,null);
	}


	//TEST doSave()
	@Test
	public void testDoSave() throws Exception{
		FileBean bean=new FileBean();
		bean.setFilename("programmazione 1");
		bean.setFormato("pdf");
		bean.setDimensione(250);
		bean.setIdFile(1);
		bean.setContenuto(new ByteArrayInputStream("'contenuto'".getBytes()));
		fileModel.doSave(bean);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/FilesExpected.xml")).getTable("Files");
		ITable actual=this.getConnection().createDataSet().getTable("Files");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));

	}


	@Test
	public void testDoSaveNUll() throws SQLException {
		boolean flag=false;
		try {
			fileModel.doSave(null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}

	
	//TEST doRetrieveKey()
	@Test 
	public void testDoRetrieveKey() throws SQLException{
		int lastCode=fileModel.doRetrieveKey();
		assertEquals(lastCode, 6);
	}


	@Test
	public void testDoRetrieveKeyNotPresent() throws SQLException {
		ds.getConnection().prepareStatement("DELETE FROM Files WHERE IdFile=3;").execute();
		ds.getConnection().prepareStatement("DELETE FROM Files WHERE IdFile=4;").execute();
		ds.getConnection().prepareStatement("DELETE FROM Files WHERE IdFile=5;").execute();
		ds.getConnection().prepareStatement("DELETE FROM Files WHERE IdFile=6;").execute();
		int lastCode=fileModel.doRetrieveKey();
		assertEquals(lastCode, -1); 
	}


}





