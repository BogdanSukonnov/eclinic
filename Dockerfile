FROM tomcat
# set timezone
ENV TZ=Europe/Moscow
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# delete default ROOT application
RUN rm -rf /usr/local/tomcat/webapps/ROOT
# deploy as root application
COPY /target/eclinic.war /usr/local/tomcat/webapps/ROOT.war
# start tomcat
CMD ["catalina.sh", "run"]