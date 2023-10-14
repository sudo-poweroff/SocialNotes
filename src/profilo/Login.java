package profilo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;

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

		HttpSession session = request.getSession(true);
		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		UserModelDS model= new UserModelDS(ds);
		if(session.getAttribute("username")!=null){
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
					String error="Utente e/o password non corretti.";
					request.setAttribute("error",error);
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
					dispatcher.forward(request, response);
					return;
				}
			}

      
      if (!bean.isVerificato()){ //CR2
				String error="Mail non verificata";
				request.setAttribute("error",error);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
				dispatcher.forward(request, response);
				return;
			}

			if(bean.getBloccato()!=null){
				try{
					long istanteCorrente = System.currentTimeMillis();
					cal.setTimeInMillis(istanteCorrente);
					if(bean.getBloccato().after(new Timestamp(cal.getTimeInMillis()))){
						String error="Sei attualmente bloccato";
						request.setAttribute("error",error);
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
						dispatcher.forward(request, response);
						return;
					}
				} catch (Exception e){
					return;
				}
			}
			try {
				HashMap<String, Integer> bloccati = (HashMap<String, Integer>) session.getAttribute("bloccati");
        if(!model.checkPassword(bean.getUsername(), pwd)){
					if(bloccati == null || !bloccati.containsKey(bean.getUsername())){
						HashMap<String, Integer> temp = new HashMap<>();
						temp.put(bean.getUsername(), 0);
						session.setAttribute("bloccati", temp);
						String error="Login e/o password non corretti";
						request.setAttribute("error",error);
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
						dispatcher.forward(request, response);
						return;
					} else if(bloccati.get(bean.getUsername())==3){
						try{
							cal.add(Calendar.MINUTE, 5);
							long istanteAggiornato = cal.getTimeInMillis();
							java.sql.Timestamp dataAggiornata = new java.sql.Timestamp(istanteAggiornato);
							model.doUpdateBloccato(bean.getUsername(), dataAggiornata);
						} catch (Exception e){
							e.printStackTrace();
							return;
						}
						String error="Accesso negato, eccessivo numero di tentativi falliti. Riprova tra 5 minuti";
						request.setAttribute("error",error);
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
						dispatcher.forward(request, response);
						return;
					} else {
						int valoreAttuale = bloccati.get(bean.getUsername());
						bloccati.put(bean.getUsername(),valoreAttuale+1);
						String error="Login e/o password non corretti";
						request.setAttribute("error",error);
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
						dispatcher.forward(request, response);
						return;
					}
				}
			} catch (SQLException | ServletException | IOException e) {
          String error="Utente e/o password non corretti.";
          request.setAttribute("error",error);
          RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
          dispatcher.forward(request, response);
          return;
			}

			try {
				Date dataAttuale=new Date(System.currentTimeMillis());
				Date ban=bean.getBan();
				if(bean.getBan()!=null&&bean.getBan().after(dataAttuale)) {
					System.out.println("Sei bannato");
					PrintWriter out = response.getWriter();
					out.write("Spiacente, sei stato bannato");
				} else {
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
					int userRole=model.getRole(bean.getUsername());

					session.setAttribute("role", userRole);
					Collection<MaterialBean>cart=new LinkedList<MaterialBean>();
					session.setAttribute("cart", cart);
					//System.out.println("user role in login.java"+userRole);
					String homeURL = response.encodeURL("homepage.jsp");
					response.sendRedirect(homeURL);
				}
			}catch(SQLException e) {
				e.printStackTrace();
				String error="Problema con la query";
				request.setAttribute("error",error);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
				dispatcher.forward(request, response);
			}catch (IOException i){
				i.printStackTrace();
			}
		}

	}

	Calendar cal = Calendar.getInstance();
}