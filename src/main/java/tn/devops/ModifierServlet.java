
package tn.devops;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/modifier")
public class ModifierServlet extends HttpServlet {

    private static final Logger LOGGER =
            Logger.getLogger(ModifierServlet.class.getName());

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
            sendErrorSafely(response, 400, "Identifiant obligatoire");
            return;
        }

        int id;

        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            sendErrorSafely(response, 400, "Identifiant invalide");
            return;
        }

        Etudiant etudiant = service.rechercherParId(id);

        if (etudiant == null) {
            sendErrorSafely(response, 404, "Etudiant introuvable");
            return;
        }

        try {
            request.setAttribute("etudiant", etudiant);

            request.getRequestDispatcher("/modifier.jsp")
                   .forward(request, response);

        } catch (ServletException | IOException e) {
            handleFailure(response, "Erreur lors de l'affichage de l'étudiant", e);
        }
    }

    // Enregistrer les modifications
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) {

        try {
            request.setCharacterEncoding(StandardCharsets.UTF_8.name());

        } catch (UnsupportedEncodingException e) {
            handleFailure(response, "Erreur d'encodage", e);
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

                sendErrorSafely(response, 400, "Identifiant et note obligatoires");
                return;
            }

            id = Integer.parseInt(idText);
            note = Double.parseDouble(noteText);

        } catch (NumberFormatException e) {
            sendErrorSafely(response, 400, "Identifiant ou note invalide");
            return;
        }

        if (nom == null || nom.isBlank()
                || prenom == null || prenom.isBlank()
                || email == null || email.isBlank()
                || !Double.isFinite(note)
                || note < 0 || note > 20) {

            sendErrorSafely(response, 400, "Informations invalides");
            return;
        }

        boolean modifie = service.modifier(
            id, nom, prenom, email, note
        );

        if (!modifie) {
            sendErrorSafely(response, 404, "Etudiant introuvable");
            return;
        }

        sendRedirectSafely(
            response,
            request.getContextPath() + "/etudiants"
        );
    }

    private void sendErrorSafely(HttpServletResponse response,
                                 int status,
                                 String message) {
        try {
            response.sendError(status, message);
        } catch (IOException e) {
            handleFailure(response, "Impossible d'envoyer l'erreur HTTP", e);
        }
    }

    private void sendRedirectSafely(HttpServletResponse response,
                                    String url) {
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            handleFailure(response, "Impossible d'effectuer la redirection", e);
        }
    }

    private void handleFailure(HttpServletResponse response,
                               String message,
                               Exception e) {
        LOGGER.log(Level.SEVERE, message, e);

        if (!response.isCommitted()) {
            response.setStatus(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );
        }
    }
}
