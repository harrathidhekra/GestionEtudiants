package tn.devops;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.mockito.Mockito.*;
import java.io.IOException;

class SupprimerServletTest {

    private SupprimerServlet servlet;
    private EtudiantService service;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    void setUp() throws Exception {

        servlet = new SupprimerServlet();
        service = mock(EtudiantService.class);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        Field field = SupprimerServlet.class.getDeclaredField("service");
        field.setAccessible(true);
        field.set(servlet, service);
    }

    @Test
    void testSupprimerAvecSucces() throws Exception {

        when(request.getParameter("id")).thenReturn("1");
        when(request.getContextPath()).thenReturn("/GestionEtudiants");
        when(service.supprimer(1)).thenReturn(true);

        servlet.doPost(request, response);

        verify(service).supprimer(1);

        verify(response).sendRedirect(
            "/GestionEtudiants/etudiants"
        );
    }

    @Test
    void testSupprimerEtudiantInexistant() throws Exception {

        when(request.getParameter("id")).thenReturn("99");
        when(service.supprimer(99)).thenReturn(false);

        servlet.doPost(request, response);

        verify(response).sendError(
            HttpServletResponse.SC_NOT_FOUND,
            "Etudiant introuvable"
        );
    }

    @Test
    void testSupprimerAvecIdentifiantInvalide() throws Exception {

        when(request.getParameter("id")).thenReturn("abc");

        servlet.doPost(request, response);

        verify(response).sendError(
            HttpServletResponse.SC_BAD_REQUEST,
            "Identifiant invalide"
        );

        verifyNoInteractions(service);
    }

    @Test
    void testSupprimerSansIdentifiant() throws Exception {

        when(request.getParameter("id")).thenReturn(null);

        servlet.doPost(request, response);

        verify(response).sendError(
            HttpServletResponse.SC_BAD_REQUEST,
            "Identifiant obligatoire"
        );

        verifyNoInteractions(service);
    }

@Test
void testSendErrorAvecIOException() throws Exception {

    when(request.getParameter("id")).thenReturn(null);

    doThrow(new IOException("Erreur HTTP"))
        .when(response).sendError(
            HttpServletResponse.SC_BAD_REQUEST,
            "Identifiant obligatoire"
        );

    servlet.doPost(request, response);

    verify(response).setStatus(
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR
    );

    verifyNoInteractions(service);
}

@Test
void testSendErrorIntrouvableAvecIOException() throws Exception {

    when(request.getParameter("id")).thenReturn("99");
    when(service.supprimer(99)).thenReturn(false);

    doThrow(new IOException("Erreur HTTP"))
        .when(response).sendError(
            HttpServletResponse.SC_NOT_FOUND,
            "Etudiant introuvable"
        );

    servlet.doPost(request, response);

    verify(response).setStatus(
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR
    );
}

@Test
void testSendRedirectAvecIOException() throws Exception {

    when(request.getParameter("id")).thenReturn("1");
    when(request.getContextPath()).thenReturn("/GestionEtudiants");
    when(service.supprimer(1)).thenReturn(true);

    doThrow(new IOException("Erreur redirection"))
        .when(response).sendRedirect(
            "/GestionEtudiants/etudiants"
        );

    servlet.doPost(request, response);

    verify(response).setStatus(
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR
    );
}

@Test
void testSendRedirectQuandReponseDejaEngagee() throws Exception {

    when(request.getParameter("id")).thenReturn("1");
    when(request.getContextPath()).thenReturn("/GestionEtudiants");
    when(service.supprimer(1)).thenReturn(true);
    when(response.isCommitted()).thenReturn(true);

    doThrow(new IOException("Erreur redirection"))
        .when(response).sendRedirect(
            "/GestionEtudiants/etudiants"
        );

    servlet.doPost(request, response);

    verify(response, never()).setStatus(anyInt());
}

}
