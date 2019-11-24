FROM tomcat
COPY /target/eClinic.war /usr/local/tomcat/webapps/
CMD ["catalina.sh", "run"]