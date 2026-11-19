
package tn.devops;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EtudiantTest {

    @Test
    void etudiantEstAdmisSiNoteSuperieureOuEgaleA10() {
        Etudiant etudiant = new Etudiant(
            1, "Ali", "Mohamed", "ali@gmail.com", 12
        );

        assertTrue(etudiant.estAdmis());
    }

    @Test
    void etudiantNestPasAdmisSiNoteInferieureA10() {
        Etudiant etudiant = new Etudiant(
            2, "Sami", "Ahmed", "sami@gmail.com", 8
        );

        assertFalse(etudiant.estAdmis());
    }

    @Test
    void verifierInformationsEtudiant() {
        Etudiant etudiant = new Etudiant(
            3, "Amira", "Ben Ali", "amira@gmail.com", 15
        );

        assertEquals(3, etudiant.getId());
        assertEquals("Amira", etudiant.getNom());
        assertEquals("Ben Ali", etudiant.getPrenom());
        assertEquals("amira@gmail.com", etudiant.getEmail());
        assertEquals(15, etudiant.getNote());
    }

    @Test
    void modifierInformationsEtudiant() {
        Etudiant etudiant = new Etudiant(
            4, "Sami", "Ahmed", "sami@gmail.com", 8
        );

        etudiant.setNote(14);

        assertEquals(14, etudiant.getNote());
        assertTrue(etudiant.estAdmis());
    }
}
