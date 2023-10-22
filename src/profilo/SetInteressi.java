package profilo;

import com.fasterxml.jackson.databind.ObjectMapper;
import materiale.MaterialBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

@WebServlet("/SetInteressi")
public class SetInteressi extends HttpServlet {
    public SetInteressi(){
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if (session == null) {
            String linkLogin = "login.jsp";
            String encodeURL = response.encodeRedirectURL(linkLogin);
            response.sendRedirect(encodeURL);
        } else {
            DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
            InteresseModelDS interesseModel = new InteresseModelDS(ds);

            String username = (String) session.getAttribute("username");

            StringBuilder requestBody = new StringBuilder();
            try (BufferedReader reader = request.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    requestBody.append(line);
                }
            }
            String jsonData = requestBody.toString();
            ObjectMapper objectMapper = new ObjectMapper();

            String[] interessi = objectMapper.readValue(jsonData, String[].class);
            if (interessi.length==0){
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"messaggio\": \"Errore, interessi non inseriti!.\"}");
            }
            else{
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"messaggio\": \"\"}");
            }

            for (int i = 0; i < interessi.length; i++) {
                try{
                    Date dataInserimento = new Date(System.currentTimeMillis());
                    InteresseBean interesse = new InteresseBean();
                    interesse.setUsername(username);
                    interesse.setCodiceCorso(Integer.parseInt(interessi[i]));
                    interesse.setDataInserimento(dataInserimento);
                    interesseModel.doSave(interesse);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

}
