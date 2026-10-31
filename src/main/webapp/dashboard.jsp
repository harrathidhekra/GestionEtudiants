
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Gestion des étudiants</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f6f9;
            text-align: center;
            margin: 0;
            padding: 40px;
        }

        h1 {
            color: #1e3a8a;
            margin-bottom: 10px;
        }

        p {
            color: #666;
            margin-bottom: 35px;
        }

        .container {
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            gap: 20px;
        }

        .card {
            background: white;
            width: 200px;
            padding: 30px 20px;
            border-radius: 12px;
            box-shadow: 0 3px 10px #ddd;
        }

        .card a {
            display: block;
            text-decoration: none;
            background: #2563eb;
            color: white;
            padding: 12px;
            border-radius: 7px;
            margin-top: 15px;
        }

        .card a:hover {
            background: #1e40af;
        }
    </style>
</head>

<body>

    <h1>Gestion des étudiants</h1>
    <p>Bienvenue dans votre espace de gestion</p>

    <div class="container">

        <div class="card">
            <h3>Ajouter</h3>
            <p>Ajouter un nouvel étudiant</p>
            <a href="${pageContext.request.contextPath}/etudiants">
                Ajouter un étudiant
            </a>
        </div>

        <div class="card">
            <h3>Liste</h3>
            <p>Consulter et gérer les étudiants</p>
            <a href="${pageContext.request.contextPath}/etudiants">
                Liste des étudiants
            </a>
        </div>

        <div class="card">
            <h3>Statistiques</h3>
            <p>Consulter les statistiques</p>
            <a href="${pageContext.request.contextPath}/statistiques">
                Statistiques
            </a>
        </div>

        <div class="card">
            <h3>Accueil</h3>
            <p>Retourner à la page d'accueil</p>
            <a href="${pageContext.request.contextPath}/index.jsp">
                Retour à l'accueil
            </a>
        </div>

    </div>

</body>
</html>
