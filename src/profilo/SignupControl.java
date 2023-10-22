package profilo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

import it.unisa.utils.SendEmail;
import it.unisa.utils.Validation;
import materiale.MaterialBean;



/**
 * Servlet implementation class SignupControl
 * Registra l'utente
 */
@WebServlet("/SignupControl")
public class SignupControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SignupControl() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          
	/*	HttpSession sessionUser = request.getSession();
		if (sessionUser.getAttribute("username")!=null) {
			System.out.println("Sei già loggato");
		} */
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//TODO controlli lato server
		
		HttpSession session = request.getSession(true);
		System.out.println("SSSSSS:"+session.getId());
		if(session.getAttribute("username")!=null){
			String link = "homepage_user.jsp";
			 String encodedURL = response.encodeRedirectURL(link);
			 response.sendRedirect(encodedURL);
		}
		else {
			  
		
			String username = request.getParameter("username");
			String nome = request.getParameter("firstName");
			String cognome = request.getParameter("lastName");
			String pwd = request.getParameter("password");
			String email = request.getParameter("email");
			String nascita = request.getParameter("nascita");
			String denomonazione = request.getParameter("uni");
			String dipName = request.getParameter("corso");
		
			System.out.println("data:"+nascita);
			Date dataNascita = Date.valueOf(nascita);

		
			if(!checkValidity(nome,cognome,username,pwd,email)) {
				String error = "Spiacenti, la registrazione non e' andata a buon fine.";
				request.setAttribute("error", error);
				//Mando una alert
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/signup.jsp");
				dispatcher.forward(request, response);
			}else {
				request.removeAttribute("error");
			}
			DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
			UserBean user = new UserBean();
		
			user.setNome(nome);
			user.setCognome(cognome);
			user.setUsername(username);
			user.setPass(pwd);
			user.setEmail(email);
			user.setDenominazione(denomonazione);
			user.setDipName(dipName);
			user.setDataNascita(dataNascita);
			user.setCoin(50);
			user.setRuolo(0);
			String path=getServletContext().getResource("/img/avatar7.png").getPath();
			File file=new File(path);
			InputStream image=new FileInputStream(file);

			UserModelDS model= new UserModelDS(ds);
	
		
			try {
				model.doSave(user);

				String from = "socialnotes2021@gmail.com";
				String pass = "fxyffsvvabkrvqrj";
				String[] to = { user.getEmail() }; // list of recipient email addresses
				String subject = "CONFERMA DI AVVENUTA REGISTRAZIONE SU Social Notes";
				String body = "Ciao "+user.getUsername()+ " ! Il team di SocialNotes e' lieto di accoglierti sul sito! Verifica la tua mail tramite questo link:\n"+
															"http://localhost:8080/SocialNotes/Verifica?username="+user.getUsername()+"&mail="+user.getEmail()+"&accessNumber=0";

				SendEmail sendEmail = new SendEmail(from,pass,to,subject,body);
				sendEmail.SendMail();


				 model.doUpdateImage(username, image);
				 user=model.doRetrieveByUsername(username);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//System.out.println("ho lanciato l'eccezione");
				String error = "Spiacenti, la registrazione non e' andata a buon fine.";
				request.setAttribute("error", error);
				//Mando una alert
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/signup.jsp");
				dispatcher.forward(request, response);
				e.printStackTrace();
			}

			String link = "Success.jsp";
			String encodedURL = response.encodeRedirectURL(link);
			response.sendRedirect(encodedURL);
		  
			doGet(request, response);
		}
	}
	//Da migliorare il filtro
	private boolean checkValidity(String nome,String cognome,String uname,String pwd, String email) {
		if(nome!=null && !nome.trim().equals("") && Validation.validateName(nome)) {
			if(cognome!=null && !cognome.trim().equals("") && Validation.validateName(cognome)) {
				if(uname!=null && !uname.trim().equals("") &&  Validation.validateUsername(uname)) {
					if(pwd!=null && !pwd.trim().equals("") && Validation.validatePassword(pwd)) {
						if(email!=null && !email.trim().equals("") &&  Validation.validateEmail(email)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
}
