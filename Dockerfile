FROM tomcat:10.1-jre21-temurin

COPY target/GestionEtudiants.war /usr/local/tomcat/webapps/GestionEtudiants.war

EXPOSE 8080