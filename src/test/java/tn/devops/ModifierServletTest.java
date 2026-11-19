package tn.devops;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

class ModifierServletTest {

    private ModifierServlet servlet;
    private EtudiantService service;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() throws Exception {
        servlet = new ModifierServlet();
        service = mock(EtudiantService.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);

        Field field = ModifierServlet.class.getDeclaredField("service");
        field.setAccessible(true);
        field.set(servlet, service);

        when(request.getRequestDispatcher("/modifier.jsp"))
                .thenReturn(dispatcher);
    }

    @Test
    void testAfficherEtudiantExistant() throws Exception {
        Etudiant etudiant = new Etudiant(
            1, "Ali", "Mohamed", "ali@gmail.com", 15
        );

        when(request.getParameter("id")).thenReturn("1");
        when(service.rechercherParId(1)).thenReturn(etudiant);

        servlet.doGet(request, response);

        verify(request).setAttribute("etudiant", etudiant);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testAfficherEtudiantInexistant() throws Exception {
        when(request.getParameter("id")).thenReturn("99");
        when(service.rechercherParId(99)).thenReturn(null);

        servlet.doGet(request, response);

        verify(response).sendError(404, "Etudiant introuvable");
    }

    @Test
    void testAfficherAvecIdentifiantInvalide() throws Exception {
        when(request.getParameter("id")).thenReturn("abc");

        servlet.doGet(request, response);

        verify(response).sendError(400, "Identifiant invalide");
        verifyNoInteractions(service);
    }

    @Test
    void testModifierEtudiantAvecSucces() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("nom")).thenReturn("Ali");
        when(request.getParameter("prenom")).thenReturn("Mohamed");
        when(request.getParameter("email")).thenReturn("ali@gmail.com");
        when(request.getParameter("note")).thenReturn("18");
        when(request.getContextPath()).thenReturn("/GestionEtudiants");

        when(service.modifier(1, "Ali", "Mohamed",
                "ali@gmail.com", 18.0)).thenReturn(true);

        servlet.doPost(request, response);

        verify(service).modifier(
            1, "Ali", "Mohamed", "ali@gmail.com", 18.0
        );

        verify(response).sendRedirect("/GestionEtudiants/etudiants");
    }

    @Test
    void testModifierAvecInformationsInvalides() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("nom")).thenReturn("");
        when(request.getParameter("prenom")).thenReturn("Mohamed");
        when(request.getParameter("email")).thenReturn("ali@gmail.com");
        when(request.getParameter("note")).thenReturn("15");

        servlet.doPost(request, response);

        verify(response).sendError(400, "Informations invalides");
        verify(service, never()).modifier(
            anyInt(), anyString(), anyString(), anyString(), anyDouble()
        );
    }

    @Test
    void testModifierEtudiantInexistant() throws Exception {
        when(request.getParameter("id")).thenReturn("99");
        when(request.getParameter("nom")).thenReturn("Ali");
        when(request.getParameter("prenom")).thenReturn("Mohamed");
        when(request.getParameter("email")).thenReturn("ali@gmail.com");
        when(request.getParameter("note")).thenReturn("15");

        when(service.modifier(99, "Ali", "Mohamed",
                "ali@gmail.com", 15.0)).thenReturn(false);

        servlet.doPost(request, response);

        verify(response).sendError(404, "Etudiant introuvable");
    }

    @Test
    void testModifierAvecNoteNonNumerique() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("nom")).thenReturn("Ali");
        when(request.getParameter("prenom")).thenReturn("Mohamed");
        when(request.getParameter("email")).thenReturn("ali@gmail.com");
        when(request.getParameter("note")).thenReturn("abc");

        servlet.doPost(request, response);

        verify(response).sendError(400, "Identifiant ou note invalide");
    }

@Test
void testForwardAvecIOException() throws Exception {

    Etudiant etudiant = new Etudiant(
        1, "Ali", "Mohamed", "ali@gmail.com", 15
    );

    when(request.getParameter("id")).thenReturn("1");
    when(service.rechercherParId(1)).thenReturn(etudiant);

    doThrow(new IOException("Erreur transfert"))
        .when(dispatcher).forward(request, response);

    servlet.doGet(request, response);

    verify(response).setStatus(
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR
    );
}

@Test
void testForwardAvecServletException() throws Exception {

    Etudiant etudiant = new Etudiant(
        1, "Ali", "Mohamed", "ali@gmail.com", 15
    );

    when(request.getParameter("id")).thenReturn("1");
    when(service.rechercherParId(1)).thenReturn(etudiant);

    doThrow(new ServletException("Erreur Servlet"))
        .when(dispatcher).forward(request, response);

    servlet.doGet(request, response);

    verify(response).setStatus(
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR
    );
}

@Test
void testForwardAvecReponseDejaEngagee() throws Exception {

    Etudiant etudiant = new Etudiant(
        1, "Ali", "Mohamed", "ali@gmail.com", 15
    );

    when(request.getParameter("id")).thenReturn("1");
    when(service.rechercherParId(1)).thenReturn(etudiant);
    when(response.isCommitted()).thenReturn(true);

    doThrow(new IOException("Erreur transfert"))
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

    doThrow(new IOException("Erreur HTTP"))
        .when(response).sendError(
            HttpServletResponse.SC_BAD_REQUEST,
            "Identifiant et note obligatoires"
        );

    servlet.doPost(request, response);

    verify(response).setStatus(
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR
    );
}

@Test
void testSendRedirectAvecIOException() throws Exception {

    when(request.getParameter("id")).thenReturn("1");
    when(request.getParameter("nom")).thenReturn("Ali");
    when(request.getParameter("prenom")).thenReturn("Mohamed");
    when(request.getParameter("email")).thenReturn("ali@gmail.com");
    when(request.getParameter("note")).thenReturn("18");
    when(request.getContextPath()).thenReturn("/GestionEtudiants");

    when(service.modifier(
        1, "Ali", "Mohamed", "ali@gmail.com", 18.0
    )).thenReturn(true);

    doThrow(new IOException("Erreur redirection"))
        .when(response).sendRedirect(
            "/GestionEtudiants/etudiants"
        );

    servlet.doPost(request, response);

    verify(response).setStatus(
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR
    );
}

}
