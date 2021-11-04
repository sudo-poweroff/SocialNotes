package it.unisa.control;

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

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import it.unisa.model.FileBean;
import it.unisa.model.FileModelDS;
import it.unisa.model.MaterialBean;
import it.unisa.model.UserModelDS;

@WebServlet("/ProvaZip")
public class ProvaZip extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProvaZip() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session==null) {
			response.sendRedirect("homepage.jsp");
		}
		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		Collection<MaterialBean>cart=(Collection<MaterialBean>)session.getAttribute("cart");
		List<FileBean> fileList = new ArrayList<FileBean>();
		FileModelDS fileModel=new FileModelDS(ds);
		int coin=(int)session.getAttribute("coin");
		String tot=request.getParameter("tot");
		int totale=Integer.parseInt(tot);
			if(cart!=null&&cart.size()>0){
				Iterator<?> it=cart.iterator();
				while(it.hasNext()) {
					MaterialBean material=(MaterialBean)it.next();
					try {
						FileBean file=fileModel.doRetrieveByKey(material.getFileName());
						fileList.add(file);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
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
					cart.clear();
					UserModelDS userModel=new UserModelDS(ds);
					String username=(String)session.getAttribute("username");
					userModel.doUpdateCoin(username, coin-totale);
					session.setAttribute("coin", coin-totale);
					session.setAttribute("cart", cart);
					String success="Acquisto effettuato con successo";
					request.setAttribute("success", success);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					output.close();
				}
			} 

	}

  
   



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
