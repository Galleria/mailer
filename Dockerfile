FROM java:8
COPY com.odde.mailer-1.0-SNAPSHOT.jar /usr/src
CMD ["java", "-jar", "com.odde.mailer-1.0-SNAPSHOT.jar"]
