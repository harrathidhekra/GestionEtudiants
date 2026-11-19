
package tn.devops;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/etudiants")
public class EtudiantServlet extends HttpServlet {

private static EtudiantService service;

@Override
public void init() throws ServletException {
    service = getService();
}

public static synchronized EtudiantService getService() {
    if (service == null) {
        service = new EtudiantService();
    }
    return service;
}
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {

        String recherche = request.getParameter("recherche");

if (recherche != null && !recherche.isBlank()) {
    request.setAttribute(
        "etudiants",
        service.rechercherParNom(recherche.trim())
    );
} else {
    request.setAttribute("etudiants", service.afficherTous());
}

request.setAttribute("recherche", recherche);

        request.getRequestDispatcher("/etudiants.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String noteText = request.getParameter("note");

        try {
            double note = Double.parseDouble(noteText);

            if (nom == null || nom.isBlank()
                    || prenom == null || prenom.isBlank()
                    || email == null || email.isBlank()
                    || note < 0 || note > 20) {

                response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Informations invalides."
                );
                return;
            }

            service.ajouter(nom, prenom, email, note);

            response.sendRedirect(
                request.getContextPath() + "/etudiants"
            );

        } catch (NumberFormatException e) {
            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "La note doit être un nombre."
            );
        }
    }
}
