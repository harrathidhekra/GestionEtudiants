
package tn.devops;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/statistiques")
public class StatistiquesServlet extends HttpServlet {

    private static final Logger LOGGER =
            Logger.getLogger(StatistiquesServlet.class.getName());

    private EtudiantService service;

    @Override
    public void init() throws ServletException {
        service = EtudiantServlet.getService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) {

        try {
            request.setAttribute("total", service.nombreTotal());
            request.setAttribute("admis", service.nombreAdmis());
            request.setAttribute("nonAdmis", service.nombreNonAdmis());
            request.setAttribute("moyenne", service.moyenneGenerale());

            request.getRequestDispatcher("/statistiques.jsp")
                   .forward(request, response);

        } catch (ServletException | IOException e) {

            LOGGER.log(
                Level.SEVERE,
                "Erreur lors de l'affichage des statistiques",
                e
            );

            if (!response.isCommitted()) {
                response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                );
            }
        }
    }
}
