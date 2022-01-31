package acquisto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import materiale.FileBean;
import materiale.FileModelDS;
import materiale.MaterialBean;
import materiale.MaterialModelDS;
import profilo.UserModelDS;

@WebServlet("/DownloadZip")
public class DownloadZip extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DownloadZip() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

	}

  
   



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session==null) {
			response.sendRedirect(response.encodeURL("homepage.jsp"));
		}
		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		MaterialModelDS mModel = new MaterialModelDS(ds);
		
		
		String[] materials = request.getParameterValues("materiale");
		
		
		List<FileBean> fileList = new ArrayList<FileBean>();
		FileModelDS fileModel=new FileModelDS(ds);

			if(materials!=null){
				for(String m: materials) {
					MaterialBean material=null;
					try {
						material = mModel.doRetrieveByKey(m);
	
						FileBean file=fileModel.doRetrieveByKey(material.getIdFile());
						fileList.add(file);
					} catch (SQLException e) {
						System.out.println("Errore nella restituzione di file");
						e.printStackTrace();
					}
				}
				response.setContentType("application/zip");
				response.setHeader("Content-Disposition", "attachment; filename=\"SocialNotes.zip\"");

				ZipOutputStream output = null;
				byte[] buffer = new byte[16777];
				try {
					output = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream(), 16777));
					for (FileBean file: fileList){
						InputStream input = null;
						try {
							input = new BufferedInputStream(file.getContenuto(),16777);

							output.putNextEntry(new ZipEntry(file.getFilename()));
							for (int length = 0; (length = input.read(buffer)) > 0;){
								output.write(buffer, 0, length);
							}
							output.closeEntry();
						}catch(Exception e){
							e.printStackTrace();
						}
						finally{
							if (input != null)
								try { input.close(); 
								}catch (IOException logOrIgnore) {
									logOrIgnore.printStackTrace();
								}
						}
					}
					/*
					 * Acquisto fatto,non è più necessario farlo qua in buyMaterial
					 * cart.clear();
					UserModelDS userModel=new UserModelDS(ds);
					String username=(String)session.getAttribute("username");
					userModel.doUpdateCoin(username, coin-totale);
					session.setAttribute("coin", coin-totale);
					session.setAttribute("cart", cart);
					String success="Acquisto effettuato con successo";
					request.setAttribute("success", success);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(); */
				}
				finally{
					output.close();
				}
			}else {
				response.sendRedirect(response.encodeURL("storicoMateriale.jsp"));
			}
	}

}
