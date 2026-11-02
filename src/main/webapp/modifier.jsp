
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
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Modifier un étudiant</title>

    <style>
        * { box-sizing: border-box; }

        body {
            font-family: Arial, sans-serif;
            background: #F5F3FF;
            margin: 0;
            padding: 40px 20px;
            color: #312E3F;
        }

        .form-container {
            background: white;
            padding: 35px;
            margin: 30px auto;
            max-width: 550px;
            border-radius: 16px;
            border: 1px solid #E9DFFF;
            box-shadow: 0 5px 20px rgba(91, 33, 182, 0.08);
        }

        h1 {
            color: #5B21B6;
            text-align: center;
            margin-bottom: 30px;
        }

        label {
            display: block;
            margin: 15px 0 7px;
            font-weight: bold;
        }

        input {
            width: 100%;
            padding: 12px;
            border: 1px solid #D8C8F5;
            border-radius: 8px;
            font-size: 15px;
        }

        input:focus {
            outline: none;
            border-color: #7C3AED;
        }

        button {
            width: 100%;
            margin-top: 25px;
            padding: 13px;
            background: #7C3AED;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
        }

        button:hover { background: #5B21B6; }

        .back-link {
            display: block;
            text-align: center;
            margin-top: 22px;
            color: #6D28D9;
            text-decoration: none;
            font-weight: bold;
        }

        .back-link:hover { text-decoration: underline; }
    </style>
</head>

<body>

<div class="form-container">

    <h1>Modifier un étudiant</h1>

    <form action="<%= request.getContextPath() %>/modifier" method="post">

        <input type="hidden" name="id" value="<%= e.getId() %>">

        <label for="nom">Nom</label>
        <input type="text" id="nom" name="nom"
               value="<%= e.getNom() %>" required>

        <label for="prenom">Prénom</label>
        <input type="text" id="prenom" name="prenom"
               value="<%= e.getPrenom() %>" required>

        <label for="email">Email</label>
        <input type="email" id="email" name="email"
               value="<%= e.getEmail() %>" required>

        <label for="note">Note (/20)</label>
        <input type="number" id="note" name="note"
               value="<%= e.getNote() %>"
               min="0" max="20" step="0.01" required>

        <button type="submit">Enregistrer les modifications</button>

    </form>

    <a class="back-link"
       href="<%= request.getContextPath() %>/etudiants">
        ← Retour à la liste des étudiants
    </a>

</div>

</body>
</html>
