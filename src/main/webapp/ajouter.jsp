<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Ajouter un étudiant</title>
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
            max-width: 550px;
            margin: 30px auto;
            background: white;
            padding: 35px;
            border-radius: 16px;
            box-shadow: 0 5px 20px rgba(91, 33, 182, 0.08);
            border: 1px solid #E9DFFF;
        }
        h1 {
            text-align: center;
            color: #5B21B6;
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
            border: none;
            border-radius: 8px;
            background: #7C3AED;
            color: white;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
        }
        button:hover { background: #5B21B6; }
        .links {
            display: flex;
            justify-content: space-between;
            margin-top: 22px;
        }
        a {
            color: #6D28D9;
            text-decoration: none;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Ajouter un étudiant</h1>

    <form action="${pageContext.request.contextPath}/etudiants" method="post">
        <label for="nom">Nom</label>
        <input type="text" id="nom" name="nom" required>

        <label for="prenom">Prénom</label>
        <input type="text" id="prenom" name="prenom" required>

        <label for="email">Email</label>
        <input type="email" id="email" name="email" required>

        <label for="note">Note (/20)</label>
        <input type="number" id="note" name="note"
               min="0" max="20" step="0.01" required>

        <button type="submit">Enregistrer l'étudiant</button>
    </form>

    <div class="links">
        <a href="${pageContext.request.contextPath}/dashboard.jsp">← Tableau de bord</a>
        <a href="${pageContext.request.contextPath}/etudiants">Liste des étudiants</a>
    </div>
</div>
</body>
</html>
