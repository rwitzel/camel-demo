### Features

manual start

    .routeId("route66")
    .autoStartup(false)

    Thread.sleep(3000);
    context.startRoute("route66");
    Thread.sleep(3000);



### General

#### Properties

    <properties>
      <activemq-version>5.11.2</activemq-version>
      <camel-version>2.15.5</camel-version>
      <cxf-version>3.0.6</cxf-version>
      <hawtio-version>1.4.58</hawtio-version>
      <hsqldb-version>1.8.0.10</hsqldb-version>
      <junit-version>4.11</junit-version>
      <log4j-version>1.2.17</log4j-version>
      <spring-version>4.1.7.RELEASE</spring-version>
      <slf4j-version>1.7.5</slf4j-version>
      <xbean-version>3.18</xbean-version>
      <bundle-plugin-version>2.3.7</bundle-plugin-version>
      <jetty-plugin-version>8.1.16.v20140903</jetty-plugin-version>
      <scala-version>2.11.5</scala-version>
      <scala-plugin-version>3.1.6</scala-plugin-version>
      <!-- use utf-8 encoding -->
      <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
      <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    </properties>

#### log4j configuration

    #log4j.logger.org.apache.activemq=DEBUG

    # uncomment the next line to debug Camel
    log4j.logger.org.apache.camel=DEBUG

    log4j.logger.org.apache.camel.impl.converter=INFO
    log4j.logger.org.apache.camel.util.ResolverUtil=INFO

    log4j.logger.org.springframework=WARN
    log4j.logger.org.hibernate=WARN

    # CONSOLE appender not used by default
    log4j.appender.out=org.apache.log4j.ConsoleAppender
    log4j.appender.out.layout=org.apache.log4j.PatternLayout
    log4j.appender.out.layout.ConversionPattern=[%30.30t] %-30.30c{1} %-5p %m%n
    #log4j.appender.out.layout.ConversionPattern=%d [%-15.15t] %-5p %-30.30c{1} - %m%n

    log4j.throwableRenderer=org.apache.log4j.EnhancedThrowableRenderer

#### Read resource content

    System.out.println("file content:" + new Scanner(Demo.class.getResourceAsStream("/log4j.properties")).useDelimiter("abc").next());
