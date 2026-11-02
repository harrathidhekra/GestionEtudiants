package tn.devops;

import java.util.ArrayList;
import java.util.List;

public class EtudiantService {

    private List<Etudiant> etudiants = new ArrayList<>();

    private int prochainId = 1;

    // Ajouter un étudiant
    public void ajouter(String nom, String prenom,
                        String email, double note) {

        Etudiant etudiant = new Etudiant(
            prochainId, nom, prenom, email, note
        );

        etudiants.add(etudiant);
        prochainId++;
    }

    // Afficher tous les étudiants
    public List<Etudiant> afficherTous() {
        return new ArrayList<>(etudiants);
    }

    // Rechercher un étudiant par ID
    public Etudiant rechercherParId(int id) {

        for (Etudiant e : etudiants) {
            if (e.getId() == id) {
                return e;
            }
        }

        return null;
    }

    // Modifier un étudiant
    public boolean modifier(int id, String nom,
                            String prenom, String email,
                            double note) {

        Etudiant e = rechercherParId(id);

        if (e == null) {
            return false;
        }

        e.setNom(nom);
        e.setPrenom(prenom);
        e.setEmail(email);
        e.setNote(note);

        return true;
    }

    // Supprimer un étudiant
    public boolean supprimer(int id) {

        Etudiant e = rechercherParId(id);

        if (e == null) {
            return false;
        }

        etudiants.remove(e);
        return true;
    }

// Rechercher un étudiant par nom ou prénom
public List<Etudiant> rechercherParNom(String recherche) {

    List<Etudiant> resultat = new ArrayList<>();

    String motCle = recherche.toLowerCase().trim();

    for (Etudiant e : etudiants) {
        if (e.getNom().toLowerCase().contains(motCle)
                || e.getPrenom().toLowerCase().contains(motCle)) {
            resultat.add(e);
        }
    }

    return resultat;
}

// Nombre total des étudiants
public int nombreTotal() {
    return etudiants.size();
}

// Nombre des étudiants admis
public int nombreAdmis() {
    int compteur = 0;

    for (Etudiant e : etudiants) {
        if (e.estAdmis()) {
            compteur++;
        }
    }

    return compteur;
}

// Nombre des étudiants non admis
public int nombreNonAdmis() {
    return nombreTotal() - nombreAdmis();
}

// Moyenne générale des notes
public double moyenneGenerale() {
    if (etudiants.isEmpty()) {
        return 0;
    }

    double somme = 0;

    for (Etudiant e : etudiants) {
        somme += e.getNote();
    }

    return somme / etudiants.size();
}

}
