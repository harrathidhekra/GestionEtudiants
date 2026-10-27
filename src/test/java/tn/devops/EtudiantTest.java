package tn.devops;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EtudiantTest {

    @Test
    void etudiantEstAdmisSiNoteSuperieureOuEgaleA10() {
        Etudiant etudiant = new Etudiant("Ali", 12);

        assertTrue(etudiant.estAdmis());
    }

    @Test
    void etudiantNestPasAdmisSiNoteInferieureA10() {
        Etudiant etudiant = new Etudiant("Sami", 8);

        assertFalse(etudiant.estAdmis());
    }
}