
package tn.devops;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/supprimer")
public class SupprimerServlet extends HttpServlet {

    private static final Logger LOGGER =
            Logger.getLogger(SupprimerServlet.class.getName());

    private EtudiantService service;

    @Override
    public void init() {
        service = EtudiantServlet.getService();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) {

        String idText = request.getParameter("id");

        int id;

        try {
            if (idText == null || idText.isBlank()) {
                sendErrorSafely(
                    response,
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Identifiant obligatoire"
                );
                return;
            }

            id = Integer.parseInt(idText);

        } catch (NumberFormatException e) {
            sendErrorSafely(
                response,
                HttpServletResponse.SC_BAD_REQUEST,
                "Identifiant invalide"
            );
            return;
        }

        boolean supprime = service.supprimer(id);

        if (!supprime) {
            sendErrorSafely(
                response,
                HttpServletResponse.SC_NOT_FOUND,
                "Etudiant introuvable"
            );
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
            LOGGER.log(Level.SEVERE,
                    "Impossible d'envoyer l'erreur HTTP", e);

            if (!response.isCommitted()) {
                response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                );
            }
        }
    }

    private void sendRedirectSafely(HttpServletResponse response,
                                    String url) {
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,
                    "Impossible d'effectuer la redirection", e);

            if (!response.isCommitted()) {
                response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                );
            }
        }
    }
}
