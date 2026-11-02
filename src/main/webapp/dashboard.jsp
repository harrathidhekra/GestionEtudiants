<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Dashboard - Gestion des étudiants</title>

    <style>
        * {
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
            background: #F5F3FF;
            margin: 0;
            padding: 50px 20px;
            color: #312E3F;
        }

        header {
            text-align: center;
            margin-bottom: 45px;
        }

        h1 {
            color: #5B21B6;
            font-size: 32px;
            margin-bottom: 10px;
        }

        .subtitle {
            color: #77718B;
            font-size: 16px;
        }

        .container {
            max-width: 1000px;
            margin: auto;
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(230px, 1fr));
            gap: 25px;
        }

        .card {
            background: white;
            padding: 35px 25px;
            border-radius: 16px;
            text-align: center;
            box-shadow: 0 5px 20px rgba(91, 33, 182, 0.08);
            border: 1px solid #E9DFFF;
            transition: transform 0.2s, box-shadow 0.2s;
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 25px rgba(91, 33, 182, 0.15);
        }

        .icon {
            font-size: 42px;
            margin-bottom: 15px;
        }

        h2 {
            color: #5B21B6;
            font-size: 21px;
        }

        .card p {
            color: #77718B;
            min-height: 40px;
            line-height: 1.5;
        }

        .card a {
            display: block;
            text-decoration: none;
            background: #7C3AED;
            color: white;
            padding: 13px;
            border-radius: 8px;
            margin-top: 20px;
            font-weight: bold;
            transition: background 0.2s;
        }

        .card a:hover {
            background: #5B21B6;
        }
    </style>
</head>

<body>

<header>
    <h1>Gestion des étudiants</h1>
    <p class="subtitle">Tableau de bord de gestion</p>
</header>

<div class="container">

    <div class="card">
        <div class="icon">👨‍🎓</div>
        <h2>Ajouter un étudiant</h2>
        <p>Enregistrer un nouvel étudiant.</p>
        <a href="${pageContext.request.contextPath}/ajouter.jsp">
            Ajouter
        </a>
    </div>

    <div class="card">
        <div class="icon">📋</div>
        <h2>Liste des étudiants</h2>
        <p>Consulter, rechercher et gérer les étudiants.</p>
        <a href="${pageContext.request.contextPath}/etudiants">
            Consulter la liste
        </a>
    </div>

    <div class="card">
        <div class="icon">📊</div>
        <h2>Statistiques</h2>
        <p>Consulter les statistiques des étudiants.</p>
        <a href="${pageContext.request.contextPath}/statistiques">
            Voir les statistiques
        </a>
    </div>

</div>

</body>
</html>
