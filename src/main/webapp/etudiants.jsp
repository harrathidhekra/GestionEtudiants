
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="tn.devops.Etudiant" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des étudiants</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f4f8;
            margin: 40px;
        }

        h1 {
            color: #263b63;
            text-align: center;
        }

        .form-container {
            background: white;
            padding: 20px;
            margin: 25px auto;
            max-width: 600px;
            border-radius: 8px;
        }

        input {
            width: 95%;
            padding: 10px;
            margin: 8px 0 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        button {
            background-color: #263b63;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            margin-top: 25px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }

        th {
            background-color: #263b63;
            color: white;
        }

        .message {
            text-align: center;
            color: #666;
        }
    </style>
</head>

<body>

    <h1>Gestion des étudiants</h1>

    <div class="form-container">
        <h2>Ajouter un étudiant</h2>

        <form action="<%= request.getContextPath() %>/etudiants"
              method="post">

            <label>Nom :</label>
            <input type="text" name="nom" required>

            <label>Prénom :</label>
            <input type="text" name="prenom" required>

            <label>Email :</label>
            <input type="email" name="email" required>

            <label>Note (0 - 20) :</label>
            <input type="number" name="note"
                   min="0" max="20" step="0.01" required>

            <button type="submit">Ajouter</button>
        </form>
    </div>

    <h2>Liste des étudiants</h2>

<form action="<%= request.getContextPath() %>/etudiants"
      method="get">

    <input
        type="text"
        name="recherche"
        placeholder="Rechercher un étudiant par nom..."
        value="<%= request.getAttribute("recherche") != null
            ? request.getAttribute("recherche") : "" %>"
    >

    <button type="submit">Rechercher</button>

    <a href="<%= request.getContextPath() %>/etudiants">
        Afficher tous
    </a>
</form>


    <%
        List<Etudiant> etudiants =
            (List<Etudiant>) request.getAttribute("etudiants");

        if (etudiants != null && !etudiants.isEmpty()) {
    %>

    <table>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Email</th>
            <th>Note</th>
            <th>Résultat</th>
            <th>Actions</th>
        </tr>

        <% for (Etudiant e : etudiants) { %>
        <tr>
            <td><%= e.getId() %></td>
            <td><%= e.getNom() %></td>
            <td><%= e.getPrenom() %></td>
            <td><%= e.getEmail() %></td>
            <td><%= e.getNote() %></td>
            <td><%= e.estAdmis() ? "Admis" : "Non admis" %></td>

<td>
    <a href="<%= request.getContextPath() %>/modifier?id=<%= e.getId() %>">
        Modifier
    </a>

    <form action="<%= request.getContextPath() %>/supprimer"
          method="post"
          style="display:inline;"
          onsubmit="return confirm('Êtes-vous sûr de vouloir supprimer cet étudiant ?');">

        <input type="hidden" name="id" value="<%= e.getId() %>">

        <button type="submit">Supprimer</button>
    </form>
</td>

        </tr>
        <% } %>
    </table>

    <%
        } else {
    %>

        <p class="message">Aucun étudiant n'est enregistré.</p>

    <%
        }
    %>

</body>
</html>
