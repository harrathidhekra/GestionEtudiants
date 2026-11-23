\# GestionEtudiants – Projet DevOps



\## 1. Présentation du projet



GestionEtudiants est une application web Java permettant de gérer les informations des étudiants.



Ce projet a pour objectif de mettre en pratique les outils et les concepts DevOps : la gestion du code source, l'intégration continue, l'analyse de la qualité du code, la création et la gestion des conteneurs Docker et le déploiement avec Kubernetes.



\## 2. Technologies et outils utilisés



\- \*\*Java 21\*\* : développement de l'application.

\- \*\*Maven\*\* : compilation, gestion des dépendances et tests.

\- \*\*Git et GitHub\*\* : gestion et hébergement du code source.

\- \*\*Jenkins\*\* : automatisation du pipeline CI/CD.

\- \*\*SonarCloud\*\* : analyse de la qualité du code.

\- \*\*Docker\*\* : création de l'image de l'application.

\- \*\*Docker Hub\*\* : publication de l'image Docker.

\- \*\*Minikube\*\* : environnement Kubernetes local.

\- \*\*Kubernetes\*\* : déploiement et gestion des conteneurs.



\## 3. Dépôt GitHub



\[GestionEtudiants – GitHub](https://github.com/harrathidhekra/GestionEtudiants)



\## 4. Image Docker



Image publiée sur Docker Hub :



`dhekra1/gestionetudiants:1.0`



\## 5. Compilation et tests



Pour compiler le projet et exécuter les tests :



```bash

mvn clean test

```



Pour générer le fichier WAR :



```bash

mvn clean package

```



Le fichier WAR généré se trouve dans le dossier `target`.



\## 6. Création et exécution de l'image Docker



\### Construction de l'image



```bash

docker build -t gestionetudiants:1.0 .

```



\### Exécution du conteneur



```bash

docker run -d -p 8082:8080 --name gestionetudiants gestionetudiants:1.0

```



L'application est accessible à l'adresse :



`http://localhost:8082/GestionEtudiants/`



\### Publication de l'image sur Docker Hub



```bash

docker tag gestionetudiants:1.0 dhekra1/gestionetudiants:1.0

docker push dhekra1/gestionetudiants:1.0

```



L'image est publiée sur Docker Hub sous le nom `dhekra1/gestionetudiants:1.0`.



\## 7. Déploiement avec Kubernetes et Minikube



\### Démarrage de Minikube



```bash

minikube start

```



\### Chargement de l'image Docker dans Minikube



```bash

minikube image load dhekra1/gestionetudiants:1.0

```



\### Déploiement de l'application



```bash

kubectl apply -f k8s/deployment.yaml

kubectl apply -f k8s/service.yaml

```



\### Vérification du déploiement



Vérifier les nœuds :



```bash

kubectl get nodes

```



Vérifier le déploiement :



```bash

kubectl get deployments

```



Vérifier les Pods :



```bash

kubectl get pods

```



Vérifier les Services :



```bash

kubectl get services

```



Vérifier le déploiement complet :



```bash

kubectl rollout status deployment/gestionetudiants

```



\### Accès à l'application



Pour obtenir l'adresse d'accès :



```bash

minikube service gestionetudiants-service --url

```



L'application est accessible en ajoutant `/GestionEtudiants/` à l'adresse fournie par Minikube.



\### Surveillance de l'application



Le fichier `deployment.yaml` configure :



\- \*\*Liveness Probe\*\* : vérifie que l'application fonctionne.

\- \*\*Readiness Probe\*\* : vérifie que l'application est prête à recevoir des requêtes.

\- \*\*Resources\*\* : définit les limites et les demandes de mémoire et de processeur.



\## 8. Intégration continue avec Jenkins



Jenkins est utilisé pour automatiser les différentes étapes du projet à travers un pipeline CI/CD.



Le pipeline comprend les étapes suivantes :



1\. \*\*Checkout\*\* : récupération du code source depuis GitHub.

2\. \*\*Build\*\* : compilation de l'application avec Maven.

3\. \*\*Tests\*\* : exécution des tests automatisés.

4\. \*\*Sonar Analysis\*\* : analyse de la qualité du code.

5\. \*\*Package\*\* : génération du fichier WAR.

6\. \*\*Deploy\*\* : étape de déploiement définie dans le pipeline.



Le résultat des dernières exécutions vérifiées est : SUCCESS.



\## 9. Analyse de la qualité du code avec SonarCloud



SonarCloud est utilisé pour analyser le code source et détecter les problèmes de qualité.



L'analyse permet notamment de vérifier :



\- Les bugs potentiels.

\- Les problèmes de maintenabilité.

\- Les vulnérabilités détectées par l'analyse.

\- La couverture des tests.



Le Quality Gate du projet a été validé (Passed).



\## 10. Conclusion



Ce projet a permis de mettre en pratique plusieurs outils DevOps, depuis la gestion du code source avec GitHub jusqu'au déploiement de l'application avec Docker et Kubernetes.



Il a également permis de découvrir l'intégration continue avec Jenkins et l'analyse de la qualité du code avec SonarCloud.





