package tn.devops;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;


@WebServlet("/etudiants")
public class EtudiantServlet extends HttpServlet {


    private EtudiantService service;

    private static class ServiceHolder {
        private static final EtudiantService INSTANCE =
                new EtudiantService();
    }

    @Override
    public void init() throws ServletException {
        service = getService();
    }

    public static EtudiantService getService() {
        return ServiceHolder.INSTANCE;
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) {

        try {
            String recherche = request.getParameter("recherche");

            if (recherche != null && !recherche.isBlank()) {
                request.setAttribute(
                    "etudiants",
                    service.rechercherParNom(recherche.trim())
                );
            } else {
                request.setAttribute(
                    "etudiants",
                    service.afficherTous()
                );
            }

            request.setAttribute("recherche", recherche);

            request.getRequestDispatcher("/etudiants.jsp")
                   .forward(request, response);

        } catch (ServletException | IOException e) {
            ServletResponseUtil.handleFailure(response, "Erreur lors de l'affichage des étudiants", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) {

        try {
            request.setCharacterEncoding(StandardCharsets.UTF_8.name());

        } catch (UnsupportedEncodingException e) {
            ServletResponseUtil.handleFailure(response, "Erreur d'encodage de la requête", e);
            return;
        }

        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String noteText = request.getParameter("note");

        if (noteText == null || noteText.isBlank()) {
            ServletResponseUtil.sendErrorSafely(
                response,
                HttpServletResponse.SC_BAD_REQUEST,
                "La note est obligatoire."
            );
            return;
        }

        double note;

        try {
            note = Double.parseDouble(noteText);

        } catch (NumberFormatException e) {
            ServletResponseUtil.sendErrorSafely(
                response,
                HttpServletResponse.SC_BAD_REQUEST,
                "La note doit être un nombre."
            );
            return;
        }

        if (nom == null || nom.isBlank()
                || prenom == null || prenom.isBlank()
                || email == null || email.isBlank()
                || !Double.isFinite(note)
                || note < 0 || note > 20) {

            ServletResponseUtil.sendErrorSafely(
                response,
                HttpServletResponse.SC_BAD_REQUEST,
                "Informations invalides."
            );
            return;
        }

        service.ajouter(nom, prenom, email, note);

        ServletResponseUtil.sendRedirectSafely(
            response,
            request.getContextPath() + "/etudiants"
        );
    }


}
