
package tn.devops;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/statistiques")
public class StatistiquesServlet extends HttpServlet {

    private EtudiantService service;

    @Override
    public void init() throws ServletException {
        service = EtudiantServlet.getService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {

        request.setAttribute("total", service.nombreTotal());
        request.setAttribute("admis", service.nombreAdmis());
        request.setAttribute("nonAdmis", service.nombreNonAdmis());
        request.setAttribute("moyenne", service.moyenneGenerale());

        request.getRequestDispatcher("/statistiques.jsp")
               .forward(request, response);
    }
}
