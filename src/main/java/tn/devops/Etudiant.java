
package tn.devops;

public class Etudiant {

    private int id;
    private String nom;
    private String prenom;
    private String email;
    private double note;

    public Etudiant(int id, String nom, String prenom, String email, double note) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public double getNote() {
        return note;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public boolean estAdmis() {
        return note >= 10;
    }
}
