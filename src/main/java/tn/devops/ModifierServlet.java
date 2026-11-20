package tn.devops;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

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
                         HttpServletResponse response) {

        String idText = request.getParameter("id");

        if (idText == null || idText.isBlank()) {
            ServletResponseUtil.sendErrorSafely(response, 400, "Identifiant obligatoire");
            return;
        }

        int id;

        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            ServletResponseUtil.sendErrorSafely(response, 400, "Identifiant invalide");
            return;
        }

        Etudiant etudiant = service.rechercherParId(id);

        if (etudiant == null) {
            ServletResponseUtil.sendErrorSafely(response, 404, "Etudiant introuvable");
            return;
        }

        try {
            request.setAttribute("etudiant", etudiant);

            request.getRequestDispatcher("/modifier.jsp")
                   .forward(request, response);

        } catch (ServletException | IOException e) {
            ServletResponseUtil.handleFailure(response, "Erreur lors de l'affichage de l'étudiant", e);
        }
    }

    // Enregistrer les modifications
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) {

        try {
            request.setCharacterEncoding(StandardCharsets.UTF_8.name());

        } catch (UnsupportedEncodingException e) {
            ServletResponseUtil.handleFailure(response, "Erreur d'encodage", e);
            return;
        }

        String idText = request.getParameter("id");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String noteText = request.getParameter("note");

        int id;
        double note;

        try {
            if (idText == null || idText.isBlank()
                    || noteText == null || noteText.isBlank()) {

                ServletResponseUtil.sendErrorSafely(response, 400, "Identifiant et note obligatoires");
                return;
            }

            id = Integer.parseInt(idText);
            note = Double.parseDouble(noteText);

        } catch (NumberFormatException e) {
            ServletResponseUtil.sendErrorSafely(response, 400, "Identifiant ou note invalide");
            return;
        }

        if (nom == null || nom.isBlank()
                || prenom == null || prenom.isBlank()
                || email == null || email.isBlank()
                || !Double.isFinite(note)
                || note < 0 || note > 20) {

            ServletResponseUtil.sendErrorSafely(response, 400, "Informations invalides");
            return;
        }

        boolean modifie = service.modifier(
            id, nom, prenom, email, note
        );

        if (!modifie) {
            ServletResponseUtil.sendErrorSafely(response, 404, "Etudiant introuvable");
            return;
        }

        ServletResponseUtil.sendRedirectSafely(
            response,
            request.getContextPath() + "/etudiants"
        );
    }

}
