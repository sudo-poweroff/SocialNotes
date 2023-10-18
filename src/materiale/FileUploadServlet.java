package materiale;

import java.io.*;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.sql.DataSource;

@WebServlet("/FileUploadServlet")
@MultipartConfig(maxFileSize = 1024*1024*50)  
public class FileUploadServlet extends HttpServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4001419296020204828L;

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		// gets values of text fields
		//String description = request.getParameter("Descrizione");
		HttpSession session=request.getSession(true);
		if(session.getAttribute("username")==null) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
		}
		InputStream is = null; // input stream of the upload file

		// obtains the upload file part in this multipart request
		MaterialBean material=new MaterialBean();
		Part filePart = request.getPart("Contenuto");
		if (filePart != null) {
			//salvataggio del file nella cartella del server
			String relativePath="WebContent\\material\\";
			ServletContext context = request.getServletContext();
			String absolutePath = context.getRealPath("");
			String[] path=absolutePath.split("\\\\");
			String effectivePath="";
			for(int i=0;i<path.length-3;i++)
				effectivePath+=path[i]+"\\";
			effectivePath+=relativePath;
			File directory = new File(effectivePath);

			//ottengo il numero di file salvati nella directory per evitare di avere errori in fase di salvataggio dovuti a file con lo stesso nome
			int fileCount=0;
			if (directory.exists() && directory.isDirectory()) {
				File[] files = directory.listFiles();
				fileCount = files.length;
				fileCount++;
			}

			//ottengo il nome del file e lo salvo nella cartella del server
			String fileName = filePart.getSubmittedFileName();
			fileName=fileName.substring(0,fileName.length()-4)+"_"+fileCount+".pdf";
			String filePath = effectivePath + File.separator + fileName;
			try (InputStream input = filePart.getInputStream();
				 OutputStream output = new FileOutputStream(filePath)) {

				byte[] buffer = new byte[1024];
				int length;
				while ((length = input.read(buffer)) > 0) {
					output.write(buffer, 0, length);
				}
			}

			//inserimento informazioni nel materialBean
			Date dataCaricamento = new Date(System.currentTimeMillis());
			material.setDataCaricamento(dataCaricamento);
			String descrizione=request.getParameter("Descrizione");
			material.setDescrizione(descrizione);
			material.setHidden(true);

			//ottengo la chiave esterna codiceCorso
			DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
			CourseModelDS course=new CourseModelDS(ds);
			String nome=request.getParameter("Corso");
			int codiceCorso=course.doRetrieveByName(nome,(String) session.getAttribute("dipName"),(String) session.getAttribute("denominazione"));

			//continuo inserimento dati
			material.setCodiceCorso(codiceCorso);
			material.setUsername((String)session.getAttribute("username"));
			material.setNomeFile(fileName);

		}



		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		MaterialModelDS materialModel=new MaterialModelDS(ds);
		try {
			materialModel.doSave(material);
			String success = "Il materiale è stato caricato correttamente!";
			request.setAttribute("success", success);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/homepage_user.jsp");
			dispatcher.forward(request, response);
		} 
		catch (SQLException e) {
			String error = "Spiacenti, la registrazione delle informazioni nel database non è andata a buon fine.";
			request.setAttribute("error", error);
			//Mando una alert 
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/homepage_user.jsp");
			dispatcher.forward(request, response);
			e.printStackTrace();
		} 
	}
	
	
}