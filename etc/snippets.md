### Features

manual start

    .routeId("travelStockImport")
    .autoStartup(false)

    Thread.sleep(3000);
    context.startRoute("travelStockImport");
    Thread.sleep(3000);

    .routeId("travelStockImport")

    log4j.logger.travelStockImport=DEBUG

    context.setTracing(true)

filter by filename

    from("file:data/inbox?noop=true&include=.*csv")

    .filter(header("CamelFileName").contains("csv"))
    .filter("JavaScript", "exchange.in.headers['CamelFileName'].indexOf('csv') != -1 ")

    <dependency>
        <groupId>org.apache.camel</groupId>
        <artifactId>camel-script</artifactId>
        <version>${camel-version}</version>
    </dependency>

FTP

    <dependency>
        <groupId>org.apache.camel</groupId>
        <artifactId>camel-ftp</artifactId>
        <version>${camel-version}</version>
    </dependency>
    <dependency>
        <groupId>org.apache.ftpserver</groupId>
        <artifactId>ftpserver-core</artifactId>
        <version>1.0.0</version>
    </dependency>

    public class FtpServerCreator {

        public static FtpServer embeddedFtpServer(int port, URL userProperties) throws Exception {
            FtpServerFactory serverFactory = new FtpServerFactory();

            UserManager uman = new PropertiesUserManager(new ClearTextPasswordEncryptor(), userProperties, "admin");
            serverFactory.setUserManager(uman);

            NativeFileSystemFactory fsf = new NativeFileSystemFactory();
            fsf.setCreateHome(true);
            serverFactory.setFileSystem(fsf);

            ListenerFactory factory = new ListenerFactory();
            factory.setPort(port);
            serverFactory.addListener("default", factory.createListener());

            return serverFactory.createServer();
        }
    }

        FtpServer ftpServer = embeddedFtpServer(21000, Demo.class.getResource("/users.properties"));
        ftpServer.start();

                from("ftp:rider:secret@localhost:21000/data/inbox?noop=true&include=.*csv")

        ftpServer.stop();

        ftpserver.user.admin
        ftpserver.user.admin.userpassword=admin
        ftpserver.user.admin.homedirectory=./
        ftpserver.user.admin.writepermission=true
        ftpserver.user.rider
        ftpserver.user.rider.userpassword=secret
        ftpserver.user.rider.homedirectory=./
        ftpserver.user.rider.writepermission=true

limit the number of files processed

    from("ftp:rider:secret@localhost:21000/data/inbox?noop=true" +
            "&connectTimeout=5000&timeout=5000&maxMessagesPerPoll=2&delay=3000")

    Thread.sleep(5000);

split

    .split(body(String.class).tokenize("\n"))

aggregate (only the idea)

    .process(exchange -> exchange.getIn().setHeader("aggId", "1"))
    .aggregate(header("aggId"), flexible(String.class).accumulateInCollection(ArrayList.class)).completionSize(2)

unmarshal and marshall CSV

    <dependency>
        <groupId>org.apache.camel</groupId>
        <artifactId>camel-bindy</artifactId>
        <version>${camel-version}</version>
    </dependency>

    @CsvRecord(separator = ",", crlf = "UNIX")
    public class ArticleWeights {

        @DataField(pos = 1)
        public String sku;

        @DataField(pos = 2)
        public BigDecimal efWeight;

        @DataField(pos = 3)
        public BigDecimal mgWeight;

        @DataField(pos = 4)
        public BigDecimal gbWeight;

        @Override
        public String toString() {
            return "ArticleWeights{" +
                    "sku='" + sku + '\'' +
                    ", efWeight=" + efWeight +
                    ", mgWeight=" + mgWeight +
                    ", gbWeight=" + gbWeight +
                    '}';
        }
    }

    .unmarshal().bindy(Csv, "demos")
    .marshal().bindy(Csv, "demos")

    EK100110101,4,3,100.0
    EK100110102,4,3,100.0
    EK100110103,4,3,100.0
    EK100110104,4,3,100.0
    EK100110105,4,3,100.0
    EK100110106,4,3,100.0
    EK100110107,4,3,100.0

Spring Integration

    <dependency>
        <groupId>org.apache.camel</groupId>
        <artifactId>camel-spring</artifactId>
        <version>${camel-version}</version>
    </dependency>

    @Configuration
    public class SpringConfig {

        @Bean
        public Logger logger() { return new Logger(); }

    }

    public class Logger {

        public void log(@Header("CamelFileName") String fileName, @Body Object body) {
            System.out.println("[my service] fileName: " + fileName + ", body: " + body);
        }

    }

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
    Registry registry = new ApplicationContextRegistry(applicationContext);

    CamelContext context = new DefaultCamelContext(registry);

    .beanRef("logger", "log")

Send SQL

    <dependency>
        <groupId>org.apache.camel</groupId>
        <artifactId>camel-mustache</artifactId>
        <version>${camel-version}</version>
    </dependency>

    <dependency>
        <groupId>org.apache.camel</groupId>
        <artifactId>camel-jdbc</artifactId>
        <version>${camel-version}</version>
    </dependency>

    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-jdbc</artifactId>
        <version>4.1.6.RELEASE</version>
    </dependency>

    <dependency>
        <groupId>org.hsqldb</groupId>
        <artifactId>hsqldb</artifactId>
        <version>2.3.2</version>
    </dependency>

    @Bean
    public DataSource release_ias_db() {
        return new EmbeddedDatabaseBuilder().setType(HSQL).addScript("/createDb.sql").build();
    }

    CREATE TABLE ias_article_weight (sku VARCHAR(255), warehouse VARCHAR(255), weight DECIMAL );


      INSERT INTO ias_article_weight (sku, warehouse, weight) VALUES
      {{#body}}
        {{#demos.ArticleWeights}}
          ( '{{sku}}' , 'EF' , {{efWeight}} ),
          ( '{{sku}}' , 'MG' , {{mgWeight}} ),
          ( '{{sku}}' , 'GB' , {{gbWeight}} ),
        {{/demos.ArticleWeights}}
      {{/body}}
      ;

    .to("mustache:/updateArticleWeights.sql").setBody(body().regexReplaceAll(",(\\s+;)", "$1"))
    .log("log sql: ${body}")
    .to("jdbc:release_ias_db")

Monitoring

    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="
            http://www.springframework.org/schema/beans
            http://www.springframework.org/schema/beans/spring-beans.xsd">
        <!-- this file is required by the hawtio maven plugin -->
    </beans>

    <!-- ~/git/apache-camel-2.16.2/examples/camel-example-console$ mvn io.hawt:hawtio-maven-plugin:1.4.60:camel -->
    <plugin>
        <groupId>io.hawt</groupId>
        <artifactId>hawtio-maven-plugin</artifactId>
        <version>1.4.60</version>
    </plugin>

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
