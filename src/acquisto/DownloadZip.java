package acquisto;

import java.io.*;
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
		response.setContentType("application/zip");
		response.setHeader("Content-Disposition", "attachment; filename=\"SocialNotes.zip\"");
		ZipOutputStream output = null;
		byte[] buffer = new byte[16777];

		if(materials!=null){
			output = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream(), 16777));
			try {
				for (String m : materials) {
					MaterialBean material = null;
					try {
						material = mModel.doRetrieveByKey(m);
						String filePath = "C:\\Users\\sdell\\projects\\SocialNotes\\material\\" + material.getNomeFile();

						File pdfFile = new File(filePath);
						InputStream is = new FileInputStream(pdfFile);


						output.putNextEntry(new ZipEntry(material.getNomeFile()));
						for (int length = 0; (length = is.read(buffer)) > 0; ) {
							output.write(buffer, 0, length);
						}
						output.closeEntry();
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}finally {
				output.close();
			}
		}else {
			response.sendRedirect(response.encodeURL("storicoMateriale.jsp"));
		}
	}

}
