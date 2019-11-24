FROM tomcat
RUN rm -rf /usr/local/tomcat/webapps/ROOT
COPY /target/eclinic.war /usr/local/tomcat/webapps/ROOT.war
CMD ["catalina.sh", "run"]