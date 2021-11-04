package it.unisa.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.unisa.model.*;

/**
 * Servlet implementation class PrintNewsImage
 */
@WebServlet("/PrintNewsImage")
public class PrintNewsImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrintNewsImage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/png");
		String filename=request.getParameter("filename");
		//System.out.println("Nome del file preso dalla request: "+filename);
		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		try{
			FileModelDS filemodel=new FileModelDS(ds);
			FileBean bean=filemodel.doRetrieveByKey(filename);
			
			//Blob image=bean.getAnteprima();
			//InputStream is=image.getBinaryStream();
			//System.out.println("CodiceMateriale "+bean.getCodiceMateriale());
			InputStream is=bean.getContenuto();
			OutputStream outStream = response.getOutputStream();
			byte[] buf = new byte[4096];
			int len = -1;

			//Write the file contents to the servlet response
			//Using a buffer of 4kb (configurable). This can be
			//optimized based on web server and app server
			//properties
			while ((len = is.read(buf)) != -1) {
				outStream.write(buf, 0, len);
			}

			outStream.flush();
			outStream.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
