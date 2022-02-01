package profilo;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet("/ArchiveReport")
public class ArchiveReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ArchiveReport() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session==null) {
			response.sendRedirect("homepage.jsp");
		}
		String codice=request.getParameter("code");
		int code=Integer.parseInt(codice);
		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		ReportModelDS report=new ReportModelDS(ds);
		try {
			report.archiveReport(code);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String editUserLink=response.encodeURL("/reports.jsp");
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(editUserLink);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
