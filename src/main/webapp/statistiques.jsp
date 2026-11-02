
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Statistiques des étudiants</title>

    <style>
        * {
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
            background: #F5F3FF;
            margin: 0;
            padding: 40px 20px;
            color: #333;
        }

        .container {
            max-width: 900px;
            margin: auto;
            text-align: center;
        }

        h1 {
            color: #5B21B6;
            margin-bottom: 35px;
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
            padding: 25px 20px;
            width: 190px;
            border-radius: 12px;
            border: 1px solid #E9DFFF;
            box-shadow: 0 4px 12px rgba(91, 33, 182, 0.08);
            transition: transform 0.2s;
        }

        .carte:hover {
            transform: translateY(-4px);
        }

        .carte h3 {
            color: #6B5B85;
            font-size: 16px;
            margin-top: 0;
        }

        .valeur {
            font-size: 30px;
            font-weight: bold;
            color: #7C3AED;
            margin-top: 15px;
        }

        .retour {
            display: inline-block;
            margin-top: 20px;
            padding: 12px 22px;
            background: #7C3AED;
            color: white;
            text-decoration: none;
            border-radius: 7px;
            transition: background 0.2s;
        }

        .retour:hover {
            background: #5B21B6;
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

    <a class="retour"
       href="${pageContext.request.contextPath}/etudiants">
        Retour à la liste des étudiants
    </a>

</div>
</body>
</html>
