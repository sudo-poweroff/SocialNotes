package profilo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import it.unisa.utils.Validation;
import materiale.MaterialBean;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
		super();

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		HttpSession session = request.getSession(true);
		System.out.println("SSSSSS:"+session.getId());
		if(session.getAttribute("username")!=null){
			String username=(String)session.getAttribute("username");
			UserModelDS role=new UserModelDS(ds);
			
			response.sendRedirect("homepage.jsp");

		}
		else {

			String usernameEmail = request.getParameter("utente");
			String pwd = request.getParameter("password");

			//validazione

			if(usernameEmail==null || usernameEmail.trim().equals("") || pwd==null || pwd.trim().equals("") || !Validation.validatePassword(pwd)) {
				String error="Accesso negato";
				request.setAttribute("error",error);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
				dispatcher.forward(request, response);
				return;
			}

			UserModelDS model= new UserModelDS(ds);
			UserBean bean = null;
			try {
				bean = model.doRetrieveByUsername(usernameEmail);
			} catch (SQLException e) {
				//tento con l'email
				System.out.println("Non c'e' match con username");
			}

			if (bean==null){
				try {
					bean = model.doRetrieveByEmail(usernameEmail);
				} catch (SQLException e) {
					//l'utente non esiste nel DB
					String error="Username e/o password non corretti.";
					request.setAttribute("error",error);
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
					dispatcher.forward(request, response);
					return;
				}
			}

			boolean checkPwd = false;
			try {
				checkPwd = model.checkPassword(bean.getUsername(),pwd);
			} catch (SQLException | IllegalArgumentException e) {
				//nel caso i parametri non sono validi o l'utente non esiste
				String error="Username e/o password non corretti.";
				request.setAttribute("error",error);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
				dispatcher.forward(request, response);
				return;
			}
			if (!checkPwd){
				//La password non coincide
				String error="Username e/o password non corretti.";
				request.setAttribute("error",error);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
				dispatcher.forward(request, response);
				return;
			}

			if (!bean.isVerificato()){ //CR2
				String error="Mail non verificata";
				request.setAttribute("error",error);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
				dispatcher.forward(request, response);
				return;
			}

			Date dataAttuale=new Date(System.currentTimeMillis());
			System.out.println("Data attuale "+dataAttuale);
			if(bean.getBan()!=null&&bean.getBan().after(dataAttuale)) {
				System.out.println("Sei bannato");
				PrintWriter out = response.getWriter();
				out.write("Spiacente, sei stato bannato");
			}

			// System.out.println("USERNAME: "+bean.getUsername());
			session.setAttribute("username",bean.getUsername());
			session.setAttribute("nome",bean.getNome());
			session.setAttribute("cognome",bean.getCognome());
			session.setAttribute("img",bean.getImg());
			session.setAttribute("email",bean.getEmail());
			session.setAttribute("password",bean.getPass());
			session.setAttribute("dataNascita",bean.getDataNascita());
			session.setAttribute("coin",bean.getCoin());
			session.setAttribute("ban",bean.getBan());
			session.setAttribute("denominazione",bean.getDenominazione());
			session.setAttribute("dipName",bean.getDipName());
			int userRole= 0;
			try {
				userRole = model.getRole(bean.getUsername());
			} catch (SQLException e) {
				//non ci può essere errore
			}

			session.setAttribute("role", userRole);
			Collection<MaterialBean>cart=new LinkedList<MaterialBean>();
			session.setAttribute("cart", cart);
			//System.out.println("user role in login.java"+userRole);
			String homeURL = response.encodeURL("homepage.jsp");
			response.sendRedirect(homeURL);

		}


	}
}