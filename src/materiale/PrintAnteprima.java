package materiale;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.mysql.cj.Session;

import profilo.UserBean;
import profilo.UserModelDS;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.ImageType;

@WebServlet("/PrintAnteprima")
public class PrintAnteprima extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PrintAnteprima() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/png");
		String code=request.getParameter("codice");
		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		try{
			MaterialModelDS material=new MaterialModelDS(ds);
			MaterialBean bean=material.doRetrieveByKey(code);

			String fileName=bean.getNomeFile();
			String relativePath="WebContent\\material\\";
			ServletContext context = request.getServletContext();
			String absolutePath = context.getRealPath("");
			String[] path=absolutePath.split("\\\\");
			String effectivePath="";
			for(int i=0;i<path.length-3;i++)
				effectivePath+=path[i]+"\\";
			effectivePath+=relativePath+fileName;

			PDDocument document = PDDocument.load(new File(effectivePath));
			PDFRenderer pdfRenderer = new PDFRenderer(document);

			// Estrai la pagina come immagine
			BufferedImage bim = pdfRenderer.renderImageWithDPI(1 - 1, 300, ImageType.RGB);

			// Scrivi l'immagine nella risposta HTTP
			OutputStream out = response.getOutputStream();
			ImageIO.write(bim, "jpg", out);
			out.close();

			document.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
