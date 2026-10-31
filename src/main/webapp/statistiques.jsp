
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Statistiques des étudiants</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f6f9;
            text-align: center;
            padding: 30px;
        }
        .container {
            max-width: 800px;
            margin: auto;
        }
        .cartes {
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            gap: 20px;
            margin: 30px 0;
        }
        .carte {
            background: white;
            padding: 25px;
            width: 150px;
            border-radius: 10px;
            box-shadow: 0 2px 8px #ddd;
        }
        .valeur {
            font-size: 28px;
            font-weight: bold;
            color: #2563eb;
        }
        a {
            color: #2563eb;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Statistiques des étudiants</h1>

    <div class="cartes">
        <div class="carte">
            <h3>Total</h3>
            <div class="valeur">${total}</div>
        </div>

        <div class="carte">
            <h3>Admis</h3>
            <div class="valeur">${admis}</div>
        </div>

        <div class="carte">
            <h3>Non admis</h3>
            <div class="valeur">${nonAdmis}</div>
        </div>

        <div class="carte">
            <h3>Moyenne générale</h3>
            <div class="valeur">${moyenne}</div>
        </div>
    </div>

    <a href="${pageContext.request.contextPath}/etudiants">
        Retour à la liste des étudiants
    </a>
</div>
</body>
</html>
