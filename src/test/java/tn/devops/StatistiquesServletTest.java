package tn.devops;
import jakarta.servlet.ServletException;
import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

class StatistiquesServletTest {

    private StatistiquesServlet servlet;
    private EtudiantService service;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() throws Exception {

        servlet = new StatistiquesServlet();
        service = mock(EtudiantService.class);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);

        Field field = StatistiquesServlet.class.getDeclaredField("service");
        field.setAccessible(true);
        field.set(servlet, service);

        when(request.getRequestDispatcher("/statistiques.jsp"))
                .thenReturn(dispatcher);
    }

    @Test
    void testAfficherStatistiques() throws Exception {

        when(service.nombreTotal()).thenReturn(10);
        when(service.nombreAdmis()).thenReturn(7);
        when(service.nombreNonAdmis()).thenReturn(3);
        when(service.moyenneGenerale()).thenReturn(14.5);

        servlet.doGet(request, response);

        verify(request).setAttribute("total", 10);
        verify(request).setAttribute("admis", 7);
        verify(request).setAttribute("nonAdmis", 3);
        verify(request).setAttribute("moyenne", 14.5);

        verify(dispatcher).forward(request, response);
    }

    @Test
    void testStatistiquesSansEtudiants() throws Exception {

        when(service.nombreTotal()).thenReturn(0);
        when(service.nombreAdmis()).thenReturn(0);
        when(service.nombreNonAdmis()).thenReturn(0);
        when(service.moyenneGenerale()).thenReturn(0.0);

        servlet.doGet(request, response);

        verify(request).setAttribute("total", 0);
        verify(request).setAttribute("admis", 0);
        verify(request).setAttribute("nonAdmis", 0);
        verify(request).setAttribute("moyenne", 0.0);

        verify(dispatcher).forward(request, response);
    }

@Test
void testErreurIOExceptionPendantForward() throws Exception {

    doThrow(new IOException("Erreur de transfert"))
        .when(dispatcher).forward(request, response);

    servlet.doGet(request, response);

    verify(response).setStatus(
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR
    );
}

@Test
void testErreurServletExceptionPendantForward() throws Exception {

    doThrow(new ServletException("Erreur Servlet"))
        .when(dispatcher).forward(request, response);

    servlet.doGet(request, response);

    verify(response).setStatus(
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR
    );
}

@Test
void testErreurForwardAvecReponseDejaEngagee() throws Exception {

    when(response.isCommitted()).thenReturn(true);

    doThrow(new IOException("Erreur de transfert"))
        .when(dispatcher).forward(request, response);

    servlet.doGet(request, response);

    verify(response, never()).setStatus(anyInt());
}

}
