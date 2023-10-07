package profilo;

import it.unisa.utils.Validation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet implementation class SetNewPassword
 */
@WebServlet("/SetNewPassword")
public class SetNewPassword extends HttpServlet {

    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataSource ds=(DataSource) getServletContext().getAttribute("DataSource");
        String password1=request.getParameter("password1");
        String password2=request.getParameter("password2");
        if(!Validation.validatePassword(password1)){
            String error="Password formattata male";
            request.setAttribute("error",error);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/setNewPassword.jsp");
            dispatcher.forward(request, response);
        } else {
            if(!password1.equals(password2)){
                String error="Le password non coincidono";
                request.setAttribute("error",error);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/setNewPassword.jsp");
                dispatcher.forward(request, response);
            } else {
                UserModelDS model = new UserModelDS(ds);
                HttpSession session = request.getSession();
                try {
                    model.doUpdatePassword((String) session.getAttribute("usernameRecupero"), password1);
                    session.removeAttribute("usernameRecupero");
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
                    dispatcher.forward(request, response);
                } catch (SQLException e) {
                    String error="Errore nella richiesta";
                    request.setAttribute("error",error);
                    RequestDispatcher dispatcher2 = getServletContext().getRequestDispatcher("/setNewPassword.jsp");
                    dispatcher2.forward(request, response);
                }
            }
        }
    }
}
