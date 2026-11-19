package tn.devops;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

class EtudiantServletTest {

    private EtudiantServlet servlet;
    private EtudiantService service;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() throws Exception {
        servlet = new EtudiantServlet();
        service = mock(EtudiantService.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);

        Field field = EtudiantServlet.class.getDeclaredField("service");
        field.setAccessible(true);
        field.set(servlet, service);

        when(request.getRequestDispatcher("/etudiants.jsp"))
                .thenReturn(dispatcher);
    }

    @Test
    void testAfficherTousLesEtudiants() throws Exception {
        List<Etudiant> liste = List.of(
            new Etudiant(1, "Ali", "Mohamed", "ali@gmail.com", 15)
        );

        when(request.getParameter("recherche")).thenReturn(null);
        when(service.afficherTous()).thenReturn(liste);

        servlet.doGet(request, response);

        verify(service).afficherTous();
        verify(request).setAttribute("etudiants", liste);
        verify(request).setAttribute("recherche", null);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testRechercherEtudiant() throws Exception {
        List<Etudiant> resultat = List.of(
            new Etudiant(1, "Ali", "Mohamed", "ali@gmail.com", 15)
        );

        when(request.getParameter("recherche")).thenReturn("  Ali  ");
        when(service.rechercherParNom("Ali")).thenReturn(resultat);

        servlet.doGet(request, response);

        verify(service).rechercherParNom("Ali");
        verify(request).setAttribute("etudiants", resultat);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testAjouterEtudiantValide() throws Exception {
        when(request.getParameter("nom")).thenReturn("Ali");
        when(request.getParameter("prenom")).thenReturn("Mohamed");
        when(request.getParameter("email")).thenReturn("ali@gmail.com");
        when(request.getParameter("note")).thenReturn("15");
        when(request.getContextPath()).thenReturn("/GestionEtudiants");

        servlet.doPost(request, response);

        verify(service).ajouter("Ali", "Mohamed", "ali@gmail.com", 15.0);
        verify(response).sendRedirect("/GestionEtudiants/etudiants");
    }

    @Test
    void testAjouterAvecNomVide() throws Exception {
        when(request.getParameter("nom")).thenReturn("");
        when(request.getParameter("prenom")).thenReturn("Mohamed");
        when(request.getParameter("email")).thenReturn("ali@gmail.com");
        when(request.getParameter("note")).thenReturn("15");

        servlet.doPost(request, response);

        verify(response).sendError(
            HttpServletResponse.SC_BAD_REQUEST,
            "Informations invalides."
        );

        verify(service, never()).ajouter(
            anyString(), anyString(), anyString(), anyDouble()
        );
    }

    @Test
    void testAjouterAvecNoteNonNumerique() throws Exception {
        when(request.getParameter("nom")).thenReturn("Ali");
        when(request.getParameter("prenom")).thenReturn("Mohamed");
        when(request.getParameter("email")).thenReturn("ali@gmail.com");
        when(request.getParameter("note")).thenReturn("abc");

        servlet.doPost(request, response);

        verify(response).sendError(
            eq(HttpServletResponse.SC_BAD_REQUEST),
            contains("nombre")
        );
    }

    @Test
    void testAjouterAvecNoteManquante() throws Exception {
        when(request.getParameter("nom")).thenReturn("Ali");
        when(request.getParameter("prenom")).thenReturn("Mohamed");
        when(request.getParameter("email")).thenReturn("ali@gmail.com");
        when(request.getParameter("note")).thenReturn(null);

        servlet.doPost(request, response);

        verify(response).sendError(
            HttpServletResponse.SC_BAD_REQUEST,
            "La note est obligatoire."
        );
    }

    @Test
    void testAjouterAvecNoteNaN() throws Exception {
        when(request.getParameter("nom")).thenReturn("Ali");
        when(request.getParameter("prenom")).thenReturn("Mohamed");
        when(request.getParameter("email")).thenReturn("ali@gmail.com");
        when(request.getParameter("note")).thenReturn("NaN");

        servlet.doPost(request, response);

        verify(response).sendError(
            HttpServletResponse.SC_BAD_REQUEST,
            "Informations invalides."
        );
    }

@Test
void testForwardAvecIOException() throws Exception {

    doThrow(new IOException("Erreur de transfert"))
        .when(dispatcher).forward(request, response);

    servlet.doGet(request, response);

    verify(response).setStatus(
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR
    );
}

@Test
void testForwardAvecServletException() throws Exception {

    doThrow(new ServletException("Erreur Servlet"))
        .when(dispatcher).forward(request, response);

    servlet.doGet(request, response);

    verify(response).setStatus(
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR
    );
}

@Test
void testForwardQuandReponseDejaEngagee() throws Exception {

    when(response.isCommitted()).thenReturn(true);

    doThrow(new IOException("Erreur de transfert"))
        .when(dispatcher).forward(request, response);

    servlet.doGet(request, response);

    verify(response, never()).setStatus(anyInt());
}

@Test
void testErreurEncodage() throws Exception {

    doThrow(new UnsupportedEncodingException("UTF-8"))
        .when(request).setCharacterEncoding("UTF-8");

    servlet.doPost(request, response);

    verify(response).setStatus(
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR
    );
}

@Test
void testSendErrorAvecIOException() throws Exception {

    when(request.getParameter("note")).thenReturn(null);

    doThrow(new IOException("Erreur HTTP"))
        .when(response).sendError(
            HttpServletResponse.SC_BAD_REQUEST,
            "La note est obligatoire."
        );

    servlet.doPost(request, response);

    verify(response).setStatus(
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR
    );
}

@Test
void testSendRedirectAvecIOException() throws Exception {

    when(request.getParameter("nom")).thenReturn("Ali");
    when(request.getParameter("prenom")).thenReturn("Mohamed");
    when(request.getParameter("email")).thenReturn("ali@gmail.com");
    when(request.getParameter("note")).thenReturn("15");
    when(request.getContextPath()).thenReturn("/GestionEtudiants");

    doThrow(new IOException("Erreur redirection"))
        .when(response).sendRedirect("/GestionEtudiants/etudiants");

    servlet.doPost(request, response);

    verify(service).ajouter("Ali", "Mohamed", "ali@gmail.com", 15.0);

    verify(response).setStatus(
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR
    );
}
@Test
void testGetServiceRetourneMemeInstance() {

    EtudiantService premier = EtudiantServlet.getService();

    EtudiantService deuxieme = EtudiantServlet.getService();

    assertNotNull(premier);
    assertSame(premier, deuxieme);
}

}
