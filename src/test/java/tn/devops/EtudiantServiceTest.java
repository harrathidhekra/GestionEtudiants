
package tn.devops;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class EtudiantServiceTest {

    @Test
    void testAjouterEtAfficher() {
        EtudiantService service = new EtudiantService();

        service.ajouter("Ali", "Mohamed", "ali@gmail.com", 15);

        List<Etudiant> liste = service.afficherTous();

        assertEquals(1, liste.size());
        assertEquals("Ali", liste.get(0).getNom());
    }

    @Test
    void testModifier() {
        EtudiantService service = new EtudiantService();
        service.ajouter("Ali", "Mohamed", "ali@gmail.com", 15);

        boolean resultat = service.modifier(
            1, "Ali", "Mohamed", "ali@gmail.com", 18
        );

        assertTrue(resultat);
        assertEquals(18, service.rechercherParId(1).getNote());
    }

    @Test
    void testSupprimer() {
        EtudiantService service = new EtudiantService();
        service.ajouter("Ali", "Mohamed", "ali@gmail.com", 15);

        boolean resultat = service.supprimer(1);

        assertTrue(resultat);
        assertEquals(0, service.afficherTous().size());
    }

    @Test
    void testRechercherParNom() {
        EtudiantService service = new EtudiantService();
        service.ajouter("Ali", "Mohamed", "ali@gmail.com", 15);
        service.ajouter("Eline", "Harrathi", "eline@gmail.com", 19);

        List<Etudiant> resultat = service.rechercherParNom("ali");

        assertEquals(1, resultat.size());
        assertEquals("Ali", resultat.get(0).getNom());
    }

    @Test
    void testStatistiques() {
        EtudiantService service = new EtudiantService();
        service.ajouter("Ali", "Mohamed", "ali@gmail.com", 15);
        service.ajouter("Eline", "Harrathi", "eline@gmail.com", 8);

        assertEquals(2, service.nombreTotal());
        assertEquals(1, service.nombreAdmis());
        assertEquals(1, service.nombreNonAdmis());
        assertEquals(11.5, service.moyenneGenerale(), 0.001);
    }
}
