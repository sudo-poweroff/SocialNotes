package materiale;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.http.Part;
import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialBlob;

import org.dbunit.Assertion;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.datatype.BlobDataType;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class MaterialModelDSTest extends DataSourceBasedDBTestCase {

	private DataSource ds;
	private MaterialModelDS material;

	@Override
	protected DataSource getDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/Materiale.sql'");
        dataSource.setUser("Ackermann32");
        dataSource.setPassword("alfonso");
        return dataSource;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/init/Materiale.xml"));
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
		material=new MaterialModelDS(ds);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testDoRetrieveByKeyPresent() throws Exception {
		MaterialBean mBean = material.doRetrieveByKey("3");



		InputStream is=mBean.getAnteprima();
		InputStream stream = new ByteArrayInputStream("'ciao'".getBytes());
		Blob blob = new SerialBlob(is.readAllBytes());
		Blob blob2 = new SerialBlob(stream.readAllBytes());
        String strBean = new String(blob.getBytes(1l, (int) blob.length()));
		String strExpected = new String(blob2.getBytes(1l, (int) blob2.length())); 
	
		assertEquals(mBean.getCodiceMateriale(), 3);
		assertEquals(mBean.getCodiceCorso(), 4);
		assertEquals(mBean.getCosto(), 15);
		assertEquals(mBean.getDescrizione(), "lezione 16");
		assertEquals(mBean.getIdFile(),1);
		assertEquals(strBean, strExpected);
		assertEquals(mBean.getKeywords(),"");
		assertEquals(mBean.getUsername(),"alfonso00");
	
		assertEquals(mBean.getDataCaricamento(),Date.valueOf("2021-08-26"));
		assertEquals(mBean.isHidden(),false);
	
		
	}
	
	@Test
	public void testDoRetrieveByKeyNotPresent() throws Exception {
		MaterialBean mBean = material.doRetrieveByKey("9");
		MaterialBean mBeanExpected = new MaterialBean();
		assertEquals(mBean, mBeanExpected);

	}
	
	@Test
	public void testDoRetrieveByVoidKey() throws Exception {
		boolean flag = false;
		try {
			material.doRetrieveByKey("");
		
		} catch (NullPointerException e) {
			flag = true;
			
		}
		assertEquals(flag, true);
		
	}
	
	@Test
	public void testDoRetrieveByKeyNull() throws Exception {
		boolean flag = false;
		try {
			material.doRetrieveByKey(null);
		
		} catch (NullPointerException e) {
			flag = true;
			
		}
		assertEquals(flag, true);
	    
		
	}
	
	@Test
	public void testNotValidated() throws Exception {
		Collection<MaterialBean> materialCollection = material.notValidated();
		ArrayList<MaterialBean> arrayMaterial = new ArrayList<>(materialCollection);
		
		for (MaterialBean mBean : arrayMaterial) {
			
			InputStream is=mBean.getAnteprima();
			InputStream stream = new ByteArrayInputStream("'ciao'".getBytes());
			Blob blob = new SerialBlob(is.readAllBytes());
			Blob blob2 = new SerialBlob(stream.readAllBytes());
	        String strBean = new String(blob.getBytes(1l, (int) blob.length()));
			String strExpected = new String(blob2.getBytes(1l, (int) blob2.length())); 
		
			
			
			assertEquals(mBean.getCodiceMateriale(),5 );
			assertEquals(mBean.getDataCaricamento(),Date.valueOf("2020-08-02") );
			assertEquals(mBean.getKeywords(),"");
			assertEquals(mBean.getCosto(),0);
			assertEquals(mBean.getDescrizione(),"provaAde" );
			assertEquals(mBean.isHidden(),true );
			assertEquals(mBean.getCodiceCorso(),1 );
			assertEquals(mBean.getUsername(),"sime00" );
			assertEquals(mBean.getIdFile(),3 );
			assertEquals(strBean,strExpected );
		}
		
		
	}
	
	
	@Test
	public void testNotValidatedVoidTable() throws Exception {
		
		ds.getConnection().prepareStatement("DELETE FROM Materiale WHERE codiceMateriale='3'").execute();
		ds.getConnection().prepareStatement("DELETE FROM Materiale WHERE codiceMateriale='4'").execute();
		ds.getConnection().prepareStatement("DELETE FROM Materiale WHERE codiceMateriale='5'").execute();
		ds.getConnection().prepareStatement("DELETE FROM Materiale WHERE codiceMateriale='6'").execute();
		
		
		
		Collection<MaterialBean> materialCollection = material.notValidated();
		
		assertEquals(materialCollection.size(), 0);
		
		
		
	}
	
	@Test
	public void testSetPriceCodMaterialePresentePrezzoValido() throws Exception {
	
		material.setPrice(5, 20);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/MaterialeExpectedUpdate.xml")).getTable("Materiale");
		ITable actual=this.getConnection().createDataSet().getTable("Materiale");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));

	}
	
	@Test
	public void testSetPriceCodMaterialeCodMaterialePresentePrezzoNonValido() throws Exception {
		boolean flag = false;
		try {
		material.setPrice(5, -20);
		}catch (NullPointerException e) {
			flag = true;
		}
		
		assertEquals(flag, true);

	}
	
	@Test
	public void testSetPriceCodMaterialeNonPresentePrezzoValido() throws Exception {
		
		material.setPrice(10, 10);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/init/Materiale.xml")).getTable("Materiale");
		ITable actual=this.getConnection().createDataSet().getTable("Materiale");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));

	}
	
	@Test
	public void testSetPriceCodMaterialeNonPresentePrezzoNonValido() throws Exception {
		boolean flag = false;
		try {
		material.setPrice(10, -20);
		}catch (NullPointerException e) {
			flag = true;
		}
		
		assertEquals(flag, true);

	}
	
	@Test
	public void testDoRetrieveByStringPresent() throws Exception {
		
		Collection<MaterialBean> materials = material.doRetrieveByString("prova");
		ArrayList<MaterialBean> materialArray = new ArrayList<>(materials);
	    ArrayList<MaterialBean> materialExpected = new ArrayList<>();
	    
	    //materiale m1
	    MaterialBean m1 = new MaterialBean();
	    m1.setCodiceMateriale(4);
	    m1.setDataCaricamento(Date.valueOf("2021-01-03"));
	    m1.setKeywords("");
	    m1.setCosto(20);
	    m1.setDescrizione("provaInf");
	    m1.setHidden(false);
	    m1.setCodiceCorso(1);
	    m1.setUsername("alfonso00");
	    InputStream streamM1 = new ByteArrayInputStream("'ciao'".getBytes());
	    m1.setAnteprima(streamM1);
	    m1.setIdFile(2);
	    
	  //materiale m2
	    MaterialBean m2 = new MaterialBean();
	    m2.setCodiceMateriale(5);
	    m2.setDataCaricamento(Date.valueOf("2020-08-02"));
	    m2.setKeywords("");
	    m2.setCosto(0);
	    m2.setDescrizione("provaAde");
	    m2.setHidden(true);
	    m2.setCodiceCorso(1);
	    m2.setUsername("sime00");
	    InputStream streamM2 = new ByteArrayInputStream("'ciao'".getBytes());
	    m2.setAnteprima(streamM2);
	    m2.setIdFile(3);
	    
	  //materiale m3
	    MaterialBean m3 = new MaterialBean();
	    m3.setCodiceMateriale(6);
	    m3.setDataCaricamento(Date.valueOf("2021-04-26"));
	    m3.setKeywords("");
	    m3.setCosto(50);
	    m3.setDescrizione("provaSO");
	    m3.setHidden(false);
	    m3.setCodiceCorso(2);
	    m3.setUsername("sime00");
	    InputStream streamM3 = new ByteArrayInputStream("'ciao'".getBytes());
	    m3.setAnteprima(streamM3);
	    m3.setIdFile(4);
	    
	    materialExpected.add(m1);
	    materialExpected.add(m2);
	    materialExpected.add(m3);
	    
	    for (int i=0;i<materials.size();i++) {
			InputStream is=materialArray.get(i).getAnteprima();
			InputStream stream = new ByteArrayInputStream("'ciao'".getBytes());
			Blob blob = new SerialBlob(is.readAllBytes());
			Blob blob2 = new SerialBlob(stream.readAllBytes());
	        String strBean = new String(blob.getBytes(1l, (int) blob.length()));
			String strExpected = new String(blob2.getBytes(1l, (int) blob2.length())); 
		
			
			
			assertEquals(materialArray.get(i).getCodiceMateriale(),materialExpected.get(i).getCodiceMateriale() );
			assertEquals(materialArray.get(i).getDataCaricamento(),materialExpected.get(i).getDataCaricamento() );
			assertEquals(materialArray.get(i).getKeywords(),materialExpected.get(i).getKeywords());
			assertEquals(materialArray.get(i).getCosto(),materialExpected.get(i).getCosto());
			assertEquals(materialArray.get(i).getDescrizione(),materialExpected.get(i).getDescrizione() );
			assertEquals(materialArray.get(i).isHidden(),materialExpected.get(i).isHidden() );
			assertEquals(materialArray.get(i).getCodiceCorso(),materialExpected.get(i).getCodiceCorso() );
			assertEquals(materialArray.get(i).getUsername(),materialExpected.get(i).getUsername() );
			assertEquals(materialArray.get(i).getIdFile(),materialExpected.get(i).getIdFile() );
			assertEquals(strBean,strExpected );
	    }
	    
	    
	}
	
	@Test
	public void testDoRetrieveByStringNotPresent() throws Exception {
		Collection<MaterialBean> materials = material.doRetrieveByString("APPUNTI-ADE");
		
		assertEquals(materials.size(), 0);
	}
	
	@Test
	public void testDoRetrieveByStringVuota() throws Exception {
		boolean flag=false;
		try {
			material.doRetrieveByString("");
		}catch (NullPointerException e) {
			flag = true;
		}
		assertTrue(flag);
	}
	
	@Test
	public void testDoRetrieveByStringNull() throws Exception {
		boolean flag=false;
		try {
			material.doRetrieveByString(null);
		}catch (NullPointerException e) {
			flag = true;
		}
		assertTrue(flag);
	}
	
	@Test
	public void testDoRetrieveByUsernamePresent() throws Exception {
		Collection<MaterialBean> materials = material.doRetrieveByUsername("sime00");
		ArrayList<MaterialBean> materialArray = new ArrayList<>(materials);
	    ArrayList<MaterialBean> materialExpected = new ArrayList<>();
	    

	    
	  //materiale m2
	    MaterialBean m2 = new MaterialBean();
	    m2.setCodiceMateriale(5);
	    m2.setDataCaricamento(Date.valueOf("2020-08-02"));
	    m2.setKeywords("");
	    m2.setCosto(0);
	    m2.setDescrizione("provaAde");
	    m2.setHidden(true);
	    m2.setCodiceCorso(1);
	    m2.setUsername("sime00");
	    InputStream streamM2 = new ByteArrayInputStream("'ciao'".getBytes());
	    m2.setAnteprima(streamM2);
	    m2.setIdFile(3);
	    
	  //materiale m3
	    MaterialBean m3 = new MaterialBean();
	    m3.setCodiceMateriale(6);
	    m3.setDataCaricamento(Date.valueOf("2021-04-26"));
	    m3.setKeywords("");
	    m3.setCosto(50);
	    m3.setDescrizione("provaSO");
	    m3.setHidden(false);
	    m3.setCodiceCorso(2);
	    m3.setUsername("sime00");
	    InputStream streamM3 = new ByteArrayInputStream("'ciao'".getBytes());
	    m3.setAnteprima(streamM3);
	    m3.setIdFile(4);
	    
	    materialExpected.add(m3);
	    materialExpected.add(m2);
	    
	    for (int i=0;i<materials.size();i++) {
			InputStream is=materialArray.get(i).getAnteprima();
			InputStream stream = new ByteArrayInputStream("'ciao'".getBytes());
			Blob blob = new SerialBlob(is.readAllBytes());
			Blob blob2 = new SerialBlob(stream.readAllBytes());
	        String strBean = new String(blob.getBytes(1l, (int) blob.length()));
			String strExpected = new String(blob2.getBytes(1l, (int) blob2.length())); 
		
			
			
			assertEquals(materialArray.get(i).getCodiceMateriale(),materialExpected.get(i).getCodiceMateriale() );
			assertEquals(materialArray.get(i).getDataCaricamento(),materialExpected.get(i).getDataCaricamento() );
			assertEquals(materialArray.get(i).getKeywords(),materialExpected.get(i).getKeywords());
			assertEquals(materialArray.get(i).getCosto(),materialExpected.get(i).getCosto());
			assertEquals(materialArray.get(i).getDescrizione(),materialExpected.get(i).getDescrizione() );
			assertEquals(materialArray.get(i).isHidden(),materialExpected.get(i).isHidden() );
			assertEquals(materialArray.get(i).getCodiceCorso(),materialExpected.get(i).getCodiceCorso() );
			assertEquals(materialArray.get(i).getUsername(),materialExpected.get(i).getUsername() );
			assertEquals(materialArray.get(i).getIdFile(),materialExpected.get(i).getIdFile() );
			assertEquals(strBean,strExpected );
	    }
	}
	
	@Test
	public void testDoRetrieveByUsernameNotPresent() throws Exception {
		Collection<MaterialBean> materials = material.doRetrieveByUsername("mario01292");
		
		assertEquals(materials.size(), 0);
	}
	
	@Test
	public void testDoRetrieveByVoidUsername() throws Exception {
		boolean flag=false;
		try {
			material.doRetrieveByUsername("");
		}catch (NullPointerException e) {
			flag = true;
		}
		assertTrue(flag);
	}
	
	@Test
	public void testDoRetrieveByUsernameNull() throws Exception {
		boolean flag=false;
		try {
			material.doRetrieveByUsername(null);
		}catch (NullPointerException e) {
			flag = true;
		}
		assertTrue(flag);
	}
	
	@Test
	public void testDoRetrieveByOrderDateTableNotVoid() throws Exception {
		
		Collection<MaterialBean> materials = material.doRetrieveByOrderDate();
		ArrayList<MaterialBean> materialArray = new ArrayList<>(materials);
	    ArrayList<MaterialBean> materialExpected = new ArrayList<>();
	    
	    //materiale m1
	    MaterialBean m1 = new MaterialBean();
	    m1.setCodiceMateriale(4);
	    m1.setDataCaricamento(Date.valueOf("2021-01-03"));
	    m1.setKeywords("");
	    m1.setCosto(20);
	    m1.setDescrizione("provaInf");
	    m1.setHidden(false);
	    m1.setCodiceCorso(1);
	    m1.setUsername("alfonso00");
	    InputStream streamM1 = new ByteArrayInputStream("'ciao'".getBytes());
	    m1.setAnteprima(streamM1);
	    m1.setIdFile(2);
	    
	  //materiale m2
	    MaterialBean m2 = new MaterialBean();
	    m2.setCodiceMateriale(5);
	    m2.setDataCaricamento(Date.valueOf("2020-08-02"));
	    m2.setKeywords("");
	    m2.setCosto(0);
	    m2.setDescrizione("provaAde");
	    m2.setHidden(true);
	    m2.setCodiceCorso(1);
	    m2.setUsername("sime00");
	    InputStream streamM2 = new ByteArrayInputStream("'ciao'".getBytes());
	    m2.setAnteprima(streamM2);
	    m2.setIdFile(3);
	    
	  //materiale m3
	    MaterialBean m3 = new MaterialBean();
	    m3.setCodiceMateriale(6);
	    m3.setDataCaricamento(Date.valueOf("2021-04-26"));
	    m3.setKeywords("");
	    m3.setCosto(50);
	    m3.setDescrizione("provaSO");
	    m3.setHidden(false);
	    m3.setCodiceCorso(2);
	    m3.setUsername("sime00");
	    InputStream streamM3 = new ByteArrayInputStream("'ciao'".getBytes());
	    m3.setAnteprima(streamM3);
	    m3.setIdFile(4);
	    
	    //materiale m4
	    MaterialBean m4 = new MaterialBean();
	    m4.setCodiceMateriale(3);
	    m4.setDataCaricamento(Date.valueOf("2021-08-26"));
	    m4.setKeywords("");
	    m4.setCosto(15);
	    m4.setDescrizione("lezione 16");
	    m4.setHidden(false);
	    m4.setCodiceCorso(4);
	    m4.setUsername("alfonso00");
	    InputStream streamM4 = new ByteArrayInputStream("'ciao'".getBytes());
	    m4.setAnteprima(streamM4);
	    m4.setIdFile(1);
	    
	    materialExpected.add(m4);
	    materialExpected.add(m3);
	    materialExpected.add(m1);
	    materialExpected.add(m2);
	    
	    for (int i=0;i<materials.size();i++) {
			InputStream is=materialArray.get(i).getAnteprima();
			InputStream stream = new ByteArrayInputStream("'ciao'".getBytes());
			Blob blob = new SerialBlob(is.readAllBytes());
			Blob blob2 = new SerialBlob(stream.readAllBytes());
	        String strBean = new String(blob.getBytes(1l, (int) blob.length()));
			String strExpected = new String(blob2.getBytes(1l, (int) blob2.length())); 
		
			
			
			assertEquals(materialArray.get(i).getCodiceMateriale(),materialExpected.get(i).getCodiceMateriale() );
			assertEquals(materialArray.get(i).getDataCaricamento(),materialExpected.get(i).getDataCaricamento() );
			assertEquals(materialArray.get(i).getKeywords(),materialExpected.get(i).getKeywords());
			assertEquals(materialArray.get(i).getCosto(),materialExpected.get(i).getCosto());
			assertEquals(materialArray.get(i).getDescrizione(),materialExpected.get(i).getDescrizione() );
			assertEquals(materialArray.get(i).isHidden(),materialExpected.get(i).isHidden() );
			assertEquals(materialArray.get(i).getCodiceCorso(),materialExpected.get(i).getCodiceCorso() );
			assertEquals(materialArray.get(i).getUsername(),materialExpected.get(i).getUsername() );
			assertEquals(materialArray.get(i).getIdFile(),materialExpected.get(i).getIdFile() );
			assertEquals(strBean,strExpected );
	    }
	    
	}
	
	@Test
	public void testDoRetrieveByOrderDateTableVoid() throws Exception {
		ds.getConnection().prepareStatement("DELETE FROM Materiale").execute();
	    Collection<MaterialBean> materials = material.doRetrieveByOrderDate();
	    
	    assertEquals(materials.size(), 0);
	
	}
	
    @Test
    public void testDoSaveMaterialNotPresent() throws Exception {
    	MaterialBean materialBean = new MaterialBean();

	    InputStream streamM = new ByteArrayInputStream("'ciao'".getBytes());
    	
    	materialBean.setDataCaricamento(Date.valueOf("2021-03-20"));
    	materialBean.setKeywords("");
    	materialBean.setCosto(0);
    	materialBean.setHidden(true);
    	materialBean.setCodiceCorso(3);
    	materialBean.setUsername("fry");
    	materialBean.setIdFile(2);
    	materialBean.setDescrizione("AppuntiMMI");
    	materialBean.setAnteprima(streamM);
    	
		material.doSave(materialBean);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/MaterialeExpected.xml")).getTable("Materiale");
		ITable actual=this.getConnection().createDataSet().getTable("Materiale");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
	}
    
    
    @Test
	public void testDoSaveNull() throws Exception{
		boolean flag = false;
		try {
			material.doSave(null);
		}catch(NullPointerException e) {
			flag=true;	
		}
		assertEquals(true,flag);
	}
    
    @Test
    public void testGetQuantitaMaterialeCondivisoUsernamePresent() throws Exception {
    	int quantitaMaterialeExpected = material.getQuantitaMaterialeCondiviso("sime00");
    	
    	assertEquals(quantitaMaterialeExpected,2);
    }
    
    @Test
    public void testGetQuantitaMaterialeCondivisoUsernameNotPresent() throws Exception {
    	int quantitaMaterialeExpected = material.getQuantitaMaterialeCondiviso("mario0129239");
    	
    	assertEquals(quantitaMaterialeExpected,-1);
    }
    
    @Test
    public void testGetQuantitaMaterialeCondivisoVoidUsername() throws Exception {
    	boolean flag = false;
		try {
			material.getQuantitaMaterialeCondiviso("");
		}catch(NullPointerException e) {
			flag=true;	
		}
		assertEquals(true,flag);
	}
    
    @Test
    public void testGetQuantitaMaterialeCondivisoUsernameNull() throws Exception {
    	boolean flag = false;
		try {
			material.getQuantitaMaterialeCondiviso(null);
		}catch(NullPointerException e) {
			flag=true;	
		}
		assertEquals(true,flag);
	}
    
    @Test
    public void testDoDeleteCodiceMaterialePresent() throws Exception {
    	
    	material.doDelete(6);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/MaterialeExpectedDelete.xml")).getTable("Materiale");
		ITable actual=this.getConnection().createDataSet().getTable("Materiale");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
    }
    
    @Test
    public void testDoDeleteCodiceMaterialeNotPresent() throws Exception {
    	
    	material.doDelete(7);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/init/Materiale.xml")).getTable("Materiale");
		ITable actual=this.getConnection().createDataSet().getTable("Materiale");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
    }
    
    
    @Test
    public void testDoRetrieveFeedback() throws SQLException {
    	int result=material.doRetrieveFeedback(3);
    	assertEquals(result, 3);
    }
    
    
    @Test
    public void testDoRetrieveFeedbackMaterialNotPresent() throws SQLException {
    	int result=material.doRetrieveFeedback(10);
    	assertEquals(result, 0);
    }
    
    
    @Test
    public void testDoRetrieveFeedbackCodeMarerialNotValid() throws SQLException {
    	boolean flag=false;
    	try {
    		material.doRetrieveFeedback(-1);
    	}catch(NullPointerException e) {
    		flag=true;
    	}
    	assertTrue(flag);
    }
    
    
    
    //caso STU1-RTO1-RT1
    @Test
    public void testRetrieveByParamatersStrPresentRatingASCRatingPresent() throws Exception {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("prova","ASC",4);
		ArrayList<MaterialBean> materialArray = new ArrayList<>(materials);
	    ArrayList<MaterialBean> materialExpected = new ArrayList<>();
	    

	    
	  //materiale m1
	    MaterialBean m1 = new MaterialBean();
	    m1.setCodiceMateriale(4);
	    m1.setDataCaricamento(Date.valueOf("2021-01-03"));
	    m1.setKeywords("");
	    m1.setCosto(20);
	    m1.setDescrizione("provaInf");
	    m1.setHidden(false);
	    m1.setCodiceCorso(1);
	    m1.setUsername("alfonso00");
	    InputStream streamM2 = new ByteArrayInputStream("'ciao'".getBytes());
	    m1.setAnteprima(streamM2);
	    m1.setIdFile(2);
	    
	    
	    materialExpected.add(m1);

	    
	    for (int i=0;i<materials.size();i++) {
			InputStream is=materialArray.get(i).getAnteprima();
			InputStream stream = new ByteArrayInputStream("'ciao'".getBytes());
			Blob blob = new SerialBlob(is.readAllBytes());
			Blob blob2 = new SerialBlob(stream.readAllBytes());
	        String strBean = new String(blob.getBytes(1l, (int) blob.length()));
			String strExpected = new String(blob2.getBytes(1l, (int) blob2.length())); 
		   			
			
			assertEquals(materialArray.get(i).getCodiceMateriale(),materialExpected.get(i).getCodiceMateriale() );
			assertEquals(materialArray.get(i).getDataCaricamento(),materialExpected.get(i).getDataCaricamento() );
			assertEquals(materialArray.get(i).getKeywords(),materialExpected.get(i).getKeywords());
			assertEquals(materialArray.get(i).getCosto(),materialExpected.get(i).getCosto());
			assertEquals(materialArray.get(i).getDescrizione(),materialExpected.get(i).getDescrizione() );
			assertEquals(materialArray.get(i).isHidden(),materialExpected.get(i).isHidden() );
			assertEquals(materialArray.get(i).getCodiceCorso(),materialExpected.get(i).getCodiceCorso() );
			assertEquals(materialArray.get(i).getUsername(),materialExpected.get(i).getUsername() );
			assertEquals(materialArray.get(i).getIdFile(),materialExpected.get(i).getIdFile() );
			assertEquals(strBean,strExpected );
	    }
    }
    
    //CASO  STU1-RTO1-RT2
    @Test
    public void testRetrieveByParamatersStrPresentRatingASCRatingNotPresent() throws Exception {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("prova","ASC",0);
		ArrayList<MaterialBean> materialArray = new ArrayList<>(materials);
	    ArrayList<MaterialBean> materialExpected = new ArrayList<>();
	    

	    
	    
	  //materiale m2
	    MaterialBean m2 = new MaterialBean();
	    m2.setCodiceMateriale(4);
	    m2.setDataCaricamento(Date.valueOf("2021-01-03"));
	    m2.setKeywords("");
	    m2.setCosto(20);
	    m2.setDescrizione("provaInf");
	    m2.setHidden(false);
	    m2.setCodiceCorso(1);
	    m2.setUsername("alfonso00");
	    InputStream streamM2 = new ByteArrayInputStream("'ciao'".getBytes());
	    m2.setAnteprima(streamM2);
	    m2.setIdFile(2);
	    
	  //materiale m3
	    MaterialBean m3 = new MaterialBean();
	    m3.setCodiceMateriale(5);
	    m3.setDataCaricamento(Date.valueOf("2020-08-02"));
	    m3.setKeywords("");
	    m3.setCosto(0);
	    m3.setDescrizione("provaAde");
	    m3.setHidden(true);
	    m3.setCodiceCorso(1);
	    m3.setUsername("sime00");
	    InputStream streamM3 = new ByteArrayInputStream("'ciao'".getBytes());
	    m3.setAnteprima(streamM3);
	    m3.setIdFile(3);
	    
	  //materiale m4
	    MaterialBean m4 = new MaterialBean();
	    m4.setCodiceMateriale(6);
	    m4.setDataCaricamento(Date.valueOf("2021-04-26"));
	    m4.setKeywords("");
	    m4.setCosto(50);
	    m4.setDescrizione("provaSO");
	    m4.setHidden(false);
	    m4.setCodiceCorso(2);
	    m4.setUsername("sime00");
	    InputStream streamM4 = new ByteArrayInputStream("'ciao'".getBytes());
	    m4.setAnteprima(streamM4);
	    m4.setIdFile(4);
	    
	    
	    materialExpected.add(m4);
	    materialExpected.add(m2);
	    materialExpected.add(m3);

	    
	    for (int i=0;i<materials.size();i++) {
			InputStream is=materialArray.get(i).getAnteprima();
			InputStream stream = new ByteArrayInputStream("'ciao'".getBytes());
			Blob blob = new SerialBlob(is.readAllBytes());
			Blob blob2 = new SerialBlob(stream.readAllBytes());
	        String strBean = new String(blob.getBytes(1l, (int) blob.length()));
			String strExpected = new String(blob2.getBytes(1l, (int) blob2.length())); 
		
			
			
			assertEquals(materialArray.get(i).getCodiceMateriale(),materialExpected.get(i).getCodiceMateriale() );
			assertEquals(materialArray.get(i).getDataCaricamento(),materialExpected.get(i).getDataCaricamento() );
			assertEquals(materialArray.get(i).getKeywords(),materialExpected.get(i).getKeywords());
			assertEquals(materialArray.get(i).getCosto(),materialExpected.get(i).getCosto());
			assertEquals(materialArray.get(i).getDescrizione(),materialExpected.get(i).getDescrizione() );
			assertEquals(materialArray.get(i).isHidden(),materialExpected.get(i).isHidden() );
			assertEquals(materialArray.get(i).getCodiceCorso(),materialExpected.get(i).getCodiceCorso() );
			assertEquals(materialArray.get(i).getUsername(),materialExpected.get(i).getUsername() );
			assertEquals(materialArray.get(i).getIdFile(),materialExpected.get(i).getIdFile() );
			assertEquals(strBean,strExpected );
	    }
    }
    
    //CASO  STU1-RTO2-RT1
    @Test
    public void testRetrieveByParamatersStrPresentRatingDESCRatingPresent() throws Exception {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("prova","DESC",3);
		ArrayList<MaterialBean> materialArray = new ArrayList<>(materials);
	    ArrayList<MaterialBean> materialExpected = new ArrayList<>();
	    

	    
	    
		  //materiale m3
	    MaterialBean m3 = new MaterialBean();
	    m3.setCodiceMateriale(5);
	    m3.setDataCaricamento(Date.valueOf("2020-08-02"));
	    m3.setKeywords("");
	    m3.setCosto(0);
	    m3.setDescrizione("provaAde");
	    m3.setHidden(true);
	    m3.setCodiceCorso(1);
	    m3.setUsername("sime00");
	    InputStream streamM3 = new ByteArrayInputStream("'ciao'".getBytes());
	    m3.setAnteprima(streamM3);
	    m3.setIdFile(3);
	    
	    
	    
	    materialExpected.add(m3);


	    
	    for (int i=0;i<materials.size();i++) {
			InputStream is=materialArray.get(i).getAnteprima();
			InputStream stream = new ByteArrayInputStream("'ciao'".getBytes());
			Blob blob = new SerialBlob(is.readAllBytes());
			Blob blob2 = new SerialBlob(stream.readAllBytes());
	        String strBean = new String(blob.getBytes(1l, (int) blob.length()));
			String strExpected = new String(blob2.getBytes(1l, (int) blob2.length())); 
		
			
			
			assertEquals(materialArray.get(i).getCodiceMateriale(),materialExpected.get(i).getCodiceMateriale() );
			assertEquals(materialArray.get(i).getDataCaricamento(),materialExpected.get(i).getDataCaricamento() );
			assertEquals(materialArray.get(i).getKeywords(),materialExpected.get(i).getKeywords());
			assertEquals(materialArray.get(i).getCosto(),materialExpected.get(i).getCosto());
			assertEquals(materialArray.get(i).getDescrizione(),materialExpected.get(i).getDescrizione() );
			assertEquals(materialArray.get(i).isHidden(),materialExpected.get(i).isHidden() );
			assertEquals(materialArray.get(i).getCodiceCorso(),materialExpected.get(i).getCodiceCorso() );
			assertEquals(materialArray.get(i).getUsername(),materialExpected.get(i).getUsername() );
			assertEquals(materialArray.get(i).getIdFile(),materialExpected.get(i).getIdFile() );
			assertEquals(strBean,strExpected );
	    }
    }
    
    //CASO  STU1-RTO2-RT2
    @Test
    public void testRetrieveByParamatersStrPresentRatingDESCRatingNotPresent() throws Exception {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("prova","DESC",0);
		ArrayList<MaterialBean> materialArray = new ArrayList<>(materials);
	    ArrayList<MaterialBean> materialExpected = new ArrayList<>();
	    

	    
	    
	  //materiale m2
	    MaterialBean m2 = new MaterialBean();
	    m2.setCodiceMateriale(4);
	    m2.setDataCaricamento(Date.valueOf("2021-01-03"));
	    m2.setKeywords("");
	    m2.setCosto(20);
	    m2.setDescrizione("provaInf");
	    m2.setHidden(false);
	    m2.setCodiceCorso(1);
	    m2.setUsername("alfonso00");
	    InputStream streamM2 = new ByteArrayInputStream("'ciao'".getBytes());
	    m2.setAnteprima(streamM2);
	    m2.setIdFile(2);
	    

	    
	  //materiale m4
	    MaterialBean m4 = new MaterialBean();
	    m4.setCodiceMateriale(6);
	    m4.setDataCaricamento(Date.valueOf("2021-04-26"));
	    m4.setKeywords("");
	    m4.setCosto(50);
	    m4.setDescrizione("provaSO");
	    m4.setHidden(false);
	    m4.setCodiceCorso(2);
	    m4.setUsername("sime00");
	    InputStream streamM4 = new ByteArrayInputStream("'ciao'".getBytes());
	    m4.setAnteprima(streamM4);
	    m4.setIdFile(4);
	    
	    
	    materialExpected.add(m2);
	    materialExpected.add(m4);

	    
	    for (int i=0;i<materials.size();i++) {
			InputStream is=materialArray.get(i).getAnteprima();
			InputStream stream = new ByteArrayInputStream("'ciao'".getBytes());
			Blob blob = new SerialBlob(is.readAllBytes());
			Blob blob2 = new SerialBlob(stream.readAllBytes());
	        String strBean = new String(blob.getBytes(1l, (int) blob.length()));
			String strExpected = new String(blob2.getBytes(1l, (int) blob2.length())); 
		
			System.out.println("MEDIA "+material.doRetrieveFeedback(materialArray.get(i).getCodiceMateriale())+" CODICE: "+materialArray.get(i).getCodiceMateriale());
			
			assertEquals(materialArray.get(i).getCodiceMateriale(),materialExpected.get(i).getCodiceMateriale() );
			assertEquals(materialArray.get(i).getDataCaricamento(),materialExpected.get(i).getDataCaricamento() );
			assertEquals(materialArray.get(i).getKeywords(),materialExpected.get(i).getKeywords());
			assertEquals(materialArray.get(i).getCosto(),materialExpected.get(i).getCosto());
			assertEquals(materialArray.get(i).getDescrizione(),materialExpected.get(i).getDescrizione() );
			assertEquals(materialArray.get(i).isHidden(),materialExpected.get(i).isHidden() );
			assertEquals(materialArray.get(i).getCodiceCorso(),materialExpected.get(i).getCodiceCorso() );
			assertEquals(materialArray.get(i).getUsername(),materialExpected.get(i).getUsername() );
			assertEquals(materialArray.get(i).getIdFile(),materialExpected.get(i).getIdFile() );
			assertEquals(strBean,strExpected );
			
	    }
    }
    
    //CASO  STU1-RTO3-RT1
    @Test
    public void testRetrieveByParamatersStrPresentRatingANoValueRatingPresent() throws Exception {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("prova","novalue",3);
		ArrayList<MaterialBean> materialArray = new ArrayList<>(materials);
	    ArrayList<MaterialBean> materialExpected = new ArrayList<>();
	    

	    
	    
	  //materiale m2
	    MaterialBean m2 = new MaterialBean();
	    m2.setCodiceMateriale(4);
	    m2.setDataCaricamento(Date.valueOf("2021-01-03"));
	    m2.setKeywords("");
	    m2.setCosto(20);
	    m2.setDescrizione("provaInf");
	    m2.setHidden(false);
	    m2.setCodiceCorso(1);
	    m2.setUsername("alfonso00");
	    InputStream streamM2 = new ByteArrayInputStream("'ciao'".getBytes());
	    m2.setAnteprima(streamM2);
	    m2.setIdFile(2);
	   
	    
	    
	    materialExpected.add(m2);


	    
	    for (int i=0;i<materials.size();i++) {
			InputStream is=materialArray.get(i).getAnteprima();
			InputStream stream = new ByteArrayInputStream("'ciao'".getBytes());
			Blob blob = new SerialBlob(is.readAllBytes());
			Blob blob2 = new SerialBlob(stream.readAllBytes());
	        String strBean = new String(blob.getBytes(1l, (int) blob.length()));
			String strExpected = new String(blob2.getBytes(1l, (int) blob2.length())); 
		
			
			
			assertEquals(materialArray.get(i).getCodiceMateriale(),materialExpected.get(i).getCodiceMateriale() );
			assertEquals(materialArray.get(i).getDataCaricamento(),materialExpected.get(i).getDataCaricamento() );
			assertEquals(materialArray.get(i).getKeywords(),materialExpected.get(i).getKeywords());
			assertEquals(materialArray.get(i).getCosto(),materialExpected.get(i).getCosto());
			assertEquals(materialArray.get(i).getDescrizione(),materialExpected.get(i).getDescrizione() );
			assertEquals(materialArray.get(i).isHidden(),materialExpected.get(i).isHidden() );
			assertEquals(materialArray.get(i).getCodiceCorso(),materialExpected.get(i).getCodiceCorso() );
			assertEquals(materialArray.get(i).getUsername(),materialExpected.get(i).getUsername() );
			assertEquals(materialArray.get(i).getIdFile(),materialExpected.get(i).getIdFile() );
			assertEquals(strBean,strExpected );
	    }
    }
    
    //CASO STU1-RTO3-RT2
    @Test
    public void testRetrieveByParamatersStrPresentRatingNoValueRatingNotPresent() throws Exception {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("prova","novalue",0);
		ArrayList<MaterialBean> materialArray = new ArrayList<>(materials);
	    ArrayList<MaterialBean> materialExpected = new ArrayList<>();
	    

	    
	    
	  //materiale m2
	    MaterialBean m2 = new MaterialBean();
	    m2.setCodiceMateriale(4);
	    m2.setDataCaricamento(Date.valueOf("2021-01-03"));
	    m2.setKeywords("");
	    m2.setCosto(20);
	    m2.setDescrizione("provaInf");
	    m2.setHidden(false);
	    m2.setCodiceCorso(1);
	    m2.setUsername("alfonso00");
	    InputStream streamM2 = new ByteArrayInputStream("'ciao'".getBytes());
	    m2.setAnteprima(streamM2);
	    m2.setIdFile(2);
	    
	  //materiale m3
	    MaterialBean m3 = new MaterialBean();
	    m3.setCodiceMateriale(5);
	    m3.setDataCaricamento(Date.valueOf("2020-08-02"));
	    m3.setKeywords("");
	    m3.setCosto(0);
	    m3.setDescrizione("provaAde");
	    m3.setHidden(true);
	    m3.setCodiceCorso(1);
	    m3.setUsername("sime00");
	    InputStream streamM3 = new ByteArrayInputStream("'ciao'".getBytes());
	    m3.setAnteprima(streamM3);
	    m3.setIdFile(3);
	    
	  //materiale m4
	    MaterialBean m4 = new MaterialBean();
	    m4.setCodiceMateriale(6);
	    m4.setDataCaricamento(Date.valueOf("2021-04-26"));
	    m4.setKeywords("");
	    m4.setCosto(50);
	    m4.setDescrizione("provaSO");
	    m4.setHidden(false);
	    m4.setCodiceCorso(2);
	    m4.setUsername("sime00");
	    InputStream streamM4 = new ByteArrayInputStream("'ciao'".getBytes());
	    m4.setAnteprima(streamM4);
	    m4.setIdFile(4);
	    
	    
	    materialExpected.add(m2);
	    materialExpected.add(m4);
	    materialExpected.add(m3);

	    
	    for (int i=0;i<materials.size();i++) {
			InputStream is=materialArray.get(i).getAnteprima();
			InputStream stream = new ByteArrayInputStream("'ciao'".getBytes());
			Blob blob = new SerialBlob(is.readAllBytes());
			Blob blob2 = new SerialBlob(stream.readAllBytes());
	        String strBean = new String(blob.getBytes(1l, (int) blob.length()));
			String strExpected = new String(blob2.getBytes(1l, (int) blob2.length())); 
		
			
			
			assertEquals(materialArray.get(i).getCodiceMateriale(),materialExpected.get(i).getCodiceMateriale() );
			assertEquals(materialArray.get(i).getDataCaricamento(),materialExpected.get(i).getDataCaricamento() );
			assertEquals(materialArray.get(i).getKeywords(),materialExpected.get(i).getKeywords());
			assertEquals(materialArray.get(i).getCosto(),materialExpected.get(i).getCosto());
			assertEquals(materialArray.get(i).getDescrizione(),materialExpected.get(i).getDescrizione() );
			assertEquals(materialArray.get(i).isHidden(),materialExpected.get(i).isHidden() );
			assertEquals(materialArray.get(i).getCodiceCorso(),materialExpected.get(i).getCodiceCorso() );
			assertEquals(materialArray.get(i).getUsername(),materialExpected.get(i).getUsername() );
			assertEquals(materialArray.get(i).getIdFile(),materialExpected.get(i).getIdFile() );
			assertEquals(strBean,strExpected );
	    }
    }
    
    //CASO STU1-RTO4-RT1
    @Test
    public void testRetrieveByParamatersStrPresentRatingVoidRatingPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("prova","",3);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    }
    
    //CASO STU1-RTO4-RT2
    @Test
    public void testRetrieveByParamatersStrPresentRatingVoidRatingNotPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("prova","",0);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    }
    
    //CASO STU1-RTO5-RT1
    @Test
    public void testRetrieveByParamatersStrPresentRatingNullRatingPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("prova",null,3);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    }
    
    //CASO STU1-RTO5-RT2
    @Test
    public void testRetrieveByParamatersStrPresentRatingNullRatingNotPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("prova",null,0);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    }
    
    //CASO STU2-RTO1-RT1
    @Test
    public void testRetrieveByParamatersStrNotPresentRatingASCRatingPresent() throws Exception {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("APPUNTI-GLOTTOLOGIA","ASC",4);
		
    	assertEquals(materials.size(), 0);
    	
    }
    
    //CASO STU2-RTO1-RT2
    @Test
    public void testRetrieveByParamatersStrNotPresentRatingASCRatingNotPresent() throws Exception {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("APPUNTI-GLOTTOLOGIA","ASC",0);
		
    	assertEquals(materials.size(), 0);
    	
    }
    
    //CASO STU2-RTO2-RT1
    @Test
    public void testRetrieveByParamatersStrNotPresentRatingDESCRatingPresent() throws Exception {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("APPUNTI-GLOTTOLOGIA","DESC",4);
		
    	assertEquals(materials.size(), 0);
    	
    }
    
    //CASO STU2-RTO2-RT2
    @Test
    public void testRetrieveByParamatersStrNotPresentRatingDESCRatingNotPresent() throws Exception {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("APPUNTI-GLOTTOLOGIA","DESC",0);
		
    	assertEquals(materials.size(), 0);
    	
    }
    
    //CASO STU2-RTO3-RT1
    @Test
    public void testRetrieveByParamatersStrNotPresentRatingNoValueRatingPresent() throws Exception {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("APPUNTI-GLOTTOLOGIA","novalue",4);
		
    	assertEquals(materials.size(), 0);
    	
    }
    
    //CASO STU2-RTO3-RT2
    @Test
    public void testRetrieveByParamatersStrNotPresentRatingNoValueRatingNotPresent() throws Exception {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("APPUNTI-GLOTTOLOGIA","novalue",0);
		
    	assertEquals(materials.size(), 0);
    	
    }
    
    //CASO STU2-RTO4-RT1
    @Test
    public void testRetrieveByParamatersStrNotPresentRatingVoidRatingPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("APPUNTI-GLOTTOLOGIA","",3);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    	
    }
    
    //CASO STU2-RTO4-RT2
    @Test
    public void testRetrieveByParamatersStrNotPresentRatingVoidRatingNotPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("APPUNTI-GLOTTOLOGIA","",0);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    	
    }
    
    //CASO STU2-RTO5-RT1
    @Test
    public void testRetrieveByParamatersStrNotPresentRatingNullRatingPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("APPUNTI-GLOTTOLOGIA",null,3);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    	
    }
    
    //CASO STU2-RTO5-RT2
    @Test
    public void testRetrieveByParamatersStrNotPresentRatingNullRatingNotPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("APPUNTI-GLOTTOLOGIA",null,0);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    	
    }
    
    //CASO STU3-RTO1-RT1
    @Test
    public void testRetrieveByParamatersStrVoidRatingASCRatingPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("","ASC",3);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    	
    }
    
    //CASO STU3-RTO1-RT2
    @Test
    public void testRetrieveByParamatersStrVoidRatingASCRatingNotPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("","ASC",0);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    	
    }
    
    //CASO STU3-RTO2-RT1
    @Test
    public void testRetrieveByParamatersStrVoidRatingDESCRatingPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("","DESC",3);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    	
    }
    
    //CASO STU3-RTO2-RT2
    @Test
    public void testRetrieveByParamatersStrVoidRatingDESCRatingNotPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("","DESC",0);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    	
    }
    
    //CASO STU3-RTO3-RT1
    @Test
    public void testRetrieveByParamatersStrVoidRatingNovalueRatingPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("","novalue",3);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    	
    }
    
    //CASO STU3-RTO3-RT2
    @Test
    public void testRetrieveByParamatersStrVoidRatingNovalueRatingNotPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("","novalue",0);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    	
    }
    
    //CASO STU3-RTO4-RT1
    @Test
    public void testRetrieveByParamatersStrVoidRatingVoidRatingPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("","",3);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    	
    }
    
    //CASO STU3-RTO4-RT2
    @Test
    public void testRetrieveByParamatersStrVoidRatingVoidRatingNotPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("","",0);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    	
    }
    
    //CASO STU3-RTO5-RT1
    @Test
    public void testRetrieveByParamatersStrVoidRatingNullRatingPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("",null,3);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    	
    }
    
    //CASO STU3-RTO5-RT2
    @Test
    public void testRetrieveByParamatersStrVoidRatingNullRatingNotPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters("",null,0);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    	
    }
    
    //CASO STU4-RTO1-RT1
    @Test
    public void testRetrieveByParamatersStrNullRatingASCRatingPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters(null,"ASC",3);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    	
    }
    
    //CASO STU4-RTO1-RT2
    @Test
    public void testRetrieveByParamatersStrNullRatingASCRatingNotPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters(null,"ASC",0);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    	
    }
    
    //CASO STU4-RTO2-RT1
    @Test
    public void testRetrieveByParamatersStrNullRatingDESCRatingPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters(null,"DESC",3);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    	
    }
    
    //CASO STU4-RTO2-RT2
    @Test
    public void testRetrieveByParamatersStrNullRatingDESCRatingNotPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters(null,"DESC",0);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    	
    }
    
    //CASO STU4-RTO3-RT1
    @Test
    public void testRetrieveByParamatersStrNullRatingNovalueRatingPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters(null,"novalue",3);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    	
    }
    
    //CASO STU4-RTO3-RT2
    @Test
    public void testRetrieveByParamatersStrNullRatingNovalueRatingNotPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters(null,"novalue",0);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    	
    }
    
    //CASO STU4-RTO4-RT1
    @Test
    public void testRetrieveByParamatersStrNullRatingVoidRatingPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters(null,"",3);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    	
    }
    
    //CASO STU4-RTO4-RT2
    @Test
    public void testRetrieveByParamatersStrNullRatingVoidRatingNotPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters(null,"",0);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    	
    }
    
    //CASO STU4-RTO5-RT1
    @Test
    public void testRetrieveByParamatersStrNullRatingNullRatingPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters(null,null,3);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    	
    }
    
    //CASO STU4-RTO5-RT2
    @Test
    public void testRetrieveByParamatersStrNullRatingNullRatingNotPresent() throws Exception {
    	boolean flag = false;
    	try {
    	Collection<MaterialBean> materials = material.doRetrieveByParameters(null,null,0);
    	}catch(NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
    	
    }
    

    
    
    
    
    
}