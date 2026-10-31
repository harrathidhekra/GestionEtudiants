
package tn.devops;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/supprimer")
public class SupprimerServlet extends HttpServlet {

    private EtudiantService service;

    @Override
    public void init() throws ServletException {
        service = EtudiantServlet.getService();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            boolean supprime = service.supprimer(id);

            if (!supprime) {
                response.sendError(404, "Etudiant introuvable");
                return;
            }

            response.sendRedirect(
                request.getContextPath() + "/etudiants"
            );

        } catch (NumberFormatException e) {
            response.sendError(400, "Identifiant invalide");
        }
    }
}
