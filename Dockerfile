FROM tomcat:9.0-jre8
RUN rm -rvf /usr/local/tomcat/webapps/ROOT
ADD /target/spring-mvc-app1.war /usr/local/tomcat/webapps/ROOT.war
COPY identifier.sqlite /usr/local/tomcat/bin
COPY identifier.sqlite /usr/local/tomcat/lib
COPY identifier.sqlite /usr/local/tomcat
EXPOSE 8082
CMD ["catalina.sh", "run"]
