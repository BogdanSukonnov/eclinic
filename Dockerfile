FROM tomcat
COPY /target/eclinic.war /usr/local/tomcat/webapps/
CMD ["catalina.sh", "run"]