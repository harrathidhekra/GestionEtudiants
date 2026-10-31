
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="tn.devops.Etudiant" %>

<%
    Etudiant e = (Etudiant) request.getAttribute("etudiant");

    if (e == null) {
        response.sendError(404, "Etudiant introuvable");
        return;
    }
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Modifier un étudiant</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f2f4f8;
            margin: 40px;
        }

        .form-container {
            background: white;
            padding: 25px;
            margin: auto;
            max-width: 600px;
            border-radius: 8px;
        }

        input {
            width: 95%;
            padding: 10px;
            margin: 8px 0 15px;
        }

        button {
            background: #263b63;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
    </style>
</head>
<body>

<div class="form-container">
    <h1>Modifier un étudiant</h1>

    <form action="<%= request.getContextPath() %>/modifier" method="post">

        <input type="hidden" name="id" value="<%= e.getId() %>">

        <label>Nom :</label>
        <input type="text" name="nom"
               value="<%= e.getNom() %>" required>

        <label>Prénom :</label>
        <input type="text" name="prenom"
               value="<%= e.getPrenom() %>" required>

        <label>Email :</label>
        <input type="email" name="email"
               value="<%= e.getEmail() %>" required>

        <label>Note (0 - 20) :</label>
        <input type="number" name="note"
               value="<%= e.getNote() %>"
               min="0" max="20" step="0.01" required>

        <button type="submit">Enregistrer les modifications</button>
    </form>

    <p>
        <a href="<%= request.getContextPath() %>/etudiants">
            Retour à la liste
        </a>
    </p>
</div>

</body>
</html>
