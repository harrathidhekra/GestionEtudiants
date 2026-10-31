
package tn.devops;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/modifier")
public class ModifierServlet extends HttpServlet {

    private EtudiantService service;

    @Override
    public void init() throws ServletException {
        service = EtudiantServlet.getService();
    }

    // Afficher le formulaire de modification
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        Etudiant etudiant = service.rechercherParId(id);

        if (etudiant == null) {
            response.sendError(404, "Etudiant introuvable");
            return;
        }

        request.setAttribute("etudiant", etudiant);

        request.getRequestDispatcher("/modifier.jsp")
               .forward(request, response);
    }

    // Enregistrer les modifications
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String email = request.getParameter("email");
            double note = Double.parseDouble(request.getParameter("note"));

            if (nom == null || nom.isBlank()
                    || prenom == null || prenom.isBlank()
                    || email == null || email.isBlank()
                    || !Double.isFinite(note)
                    || note < 0 || note > 20) {

                response.sendError(400, "Informations invalides");
                return;
            }

            boolean modifie = service.modifier(
                id, nom, prenom, email, note
            );

            if (!modifie) {
                response.sendError(404, "Etudiant introuvable");
                return;
            }

            response.sendRedirect(
                request.getContextPath() + "/etudiants"
            );

        } catch (NumberFormatException e) {
            response.sendError(400, "Identifiant ou note invalide");
        }
    }
}
