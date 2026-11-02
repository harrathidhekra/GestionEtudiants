<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="tn.devops.Etudiant" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Liste des étudiants</title>

    <style>
        * { box-sizing: border-box; }

        body {
            font-family: Arial, sans-serif;
            background: #F5F3FF;
            margin: 0;
            padding: 40px 20px;
            color: #312E3F;
        }

        .container {
            max-width: 1100px;
            margin: auto;
        }

        h1 {
            color: #5B21B6;
            text-align: center;
            margin-bottom: 30px;
        }

        .top-links {
            display: flex;
            justify-content: space-between;
            flex-wrap: wrap;
            gap: 12px;
            margin-bottom: 25px;
        }

        .link-button {
            display: inline-block;
            background: #7C3AED;
            color: white;
            padding: 12px 18px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: bold;
        }

        .link-button:hover { background: #5B21B6; }

        .search-container {
            background: white;
            padding: 22px;
            border-radius: 12px;
            border: 1px solid #E9DFFF;
            box-shadow: 0 5px 20px rgba(91, 33, 182, 0.06);
            margin-bottom: 25px;
        }

        .search-container form {
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
        }

        input[type="text"] {
            flex: 1;
            min-width: 220px;
            padding: 12px;
            border: 1px solid #D8C8F5;
            border-radius: 8px;
            font-size: 15px;
        }

        button {
            background: #7C3AED;
            color: white;
            padding: 12px 18px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-weight: bold;
        }

        button:hover { background: #5B21B6; }

        .table-container {
            overflow-x: auto;
            background: white;
            border-radius: 12px;
            box-shadow: 0 5px 20px rgba(91, 33, 182, 0.06);
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 14px 12px;
            text-align: center;
            border-bottom: 1px solid #EEE8F8;
        }

        th {
            background: #7C3AED;
            color: white;
        }

        tr:hover { background: #FAF8FF; }

        .edit-link {
            color: #6D28D9;
            font-weight: bold;
            text-decoration: none;
            margin-right: 8px;
        }

        .delete-button {
            background: #DC2626;
            padding: 8px 12px;
        }

        .delete-button:hover { background: #B91C1C; }

        .message {
            text-align: center;
            color: #77718B;
            background: white;
            padding: 25px;
            border-radius: 12px;
        }
    </style>
</head>

<body>
<div class="container">

    <h1>Liste des étudiants</h1>

    <div class="top-links">
        <a class="link-button"
           href="${pageContext.request.contextPath}/dashboard.jsp">
            ← Tableau de bord
        </a>

        <a class="link-button"
           href="${pageContext.request.contextPath}/ajouter.jsp">
            + Ajouter un étudiant
        </a>
    </div>

    <div class="search-container">
        <form action="${pageContext.request.contextPath}/etudiants"
              method="get">

            <input type="text"
                   name="recherche"
                   placeholder="Rechercher par nom ou prénom..."
                   value="<%= request.getAttribute("recherche") != null
                       ? request.getAttribute("recherche") : "" %>">

            <button type="submit">Rechercher</button>

            <a class="link-button"
               href="${pageContext.request.contextPath}/etudiants">
                Afficher tous
            </a>
        </form>
    </div>

    <%
        List<Etudiant> etudiants =
            (List<Etudiant>) request.getAttribute("etudiants");

        if (etudiants != null && !etudiants.isEmpty()) {
    %>

    <div class="table-container">
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
                    <a class="edit-link"
                       href="${pageContext.request.contextPath}/modifier?id=<%= e.getId() %>">
                        Modifier
                    </a>

                    <form action="${pageContext.request.contextPath}/supprimer"
                          method="post"
                          style="display:inline;"
                          onsubmit="return confirm('Êtes-vous sûr de vouloir supprimer cet étudiant ?');">

                        <input type="hidden" name="id"
                               value="<%= e.getId() %>">

                        <button class="delete-button" type="submit">
                            Supprimer
                        </button>
                    </form>
                </td>
            </tr>
            <% } %>
        </table>
    </div>

    <%
        } else {
    %>

        <p class="message">Aucun étudiant n'est enregistré.</p>

    <%
        }
    %>

</div>
</body>
</html>
