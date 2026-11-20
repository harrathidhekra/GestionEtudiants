
package tn.devops;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ServletResponseUtil {

    private static final Logger LOGGER =
            Logger.getLogger(ServletResponseUtil.class.getName());

    // Empêcher la création d'objets de cette classe
    private ServletResponseUtil() {
    }

    // Envoyer une erreur HTTP en toute sécurité
    public static void sendErrorSafely(
            HttpServletResponse response,
            int status,
            String message) {

        try {
            response.sendError(status, message);

        } catch (IOException e) {
            handleFailure(
                response,
                "Impossible d'envoyer l'erreur HTTP",
                e
            );
        }
    }

    // Effectuer une redirection en toute sécurité
    public static void sendRedirectSafely(
            HttpServletResponse response,
            String url) {

        try {
            response.sendRedirect(url);

        } catch (IOException e) {
            handleFailure(
                response,
                "Impossible d'effectuer la redirection",
                e
            );
        }
    }

    // Gérer les erreurs internes
    public static void handleFailure(
            HttpServletResponse response,
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
