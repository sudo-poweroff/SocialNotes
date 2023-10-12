package profilo;

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
 * Consente di verificare la mail dell'utente
 */
@WebServlet("/Verifica")
public class VerificaMail extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String mail = request.getParameter("mail");
        String numero = request.getParameter("accessNumber");
        try {
            int accessNumber = Integer.parseInt(numero);
            if (accessNumber==0 || accessNumber==1){
                DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
                UserModelDS utenteDS = new UserModelDS(ds);
                //Controllo se l'utente con questo username esiste
                UserBean utenteBean = utenteDS.doRetrieveByUsername(username);
                if (utenteBean.getEmail().equals(mail)){
                    if (!utenteBean.isVerificato()) {
                        HttpSession session = request.getSession(true);
                        utenteDS.doUpdateVerificato(mail, true); //aggiorno lo stato verificato
                        session.setAttribute("accessNumber",accessNumber);
                        request.removeAttribute("username");
                        request.removeAttribute("mail");
                        request.removeAttribute("accessNumber");
                        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/mailVerificata.jsp");
                        dispatcher.forward(request, response);
                    }
                    else{
                        //utente già verificato
                        String toHomepage = response.encodeRedirectURL("homepage.jsp");
                        response.sendRedirect(toHomepage);
                        return;
                    }
                }
                else{
                    //mail non associata all'utente
                    String toHomepage = response.encodeRedirectURL("homepage.jsp");
                    response.sendRedirect(toHomepage);
                    return;
                }
            }
            else{
                //accessNumber non valido
                String toHomepage = response.encodeRedirectURL("homepage.jsp");
                response.sendRedirect(toHomepage);
                return;
            }
        }
        catch (NumberFormatException | SQLException | NullPointerException e) {
            //accesNumber non è un numero o l'utente non esiste nel DB
            String toHomepage = response.encodeRedirectURL("homepage.jsp");
            response.sendRedirect(toHomepage);
            return;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
