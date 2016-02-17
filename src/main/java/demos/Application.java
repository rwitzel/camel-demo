package demos;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.Registry;
import org.apache.camel.spring.spi.ApplicationContextRegistry;
import org.apache.ftpserver.FtpServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static demos.FtpServerCreator.embeddedFtpServer;
import static org.apache.camel.model.dataformat.BindyType.Csv;

public class Application {

    public static void main(String args[]) throws Exception {

        FtpServer ftpServer = embeddedFtpServer(21000, Application.class.getResource("/users.properties"));
        ftpServer.start();

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        Registry registry = new ApplicationContextRegistry(applicationContext);

        CamelContext context = new DefaultCamelContext(registry);

        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("ftp:rider:secret@localhost:21000/data/inbox?noop=true&connectTimeout=5000&timeout=5000")
                        .routeId("travelStockImport")
                        .unmarshal().bindy(Csv, "demos")
                        .to("mustache:/updateArticleWeights.sql").setBody(body().regexReplaceAll(",(\\s+;)", "$1"))
                        .log("log sql: ${body}")
                        .to("jdbc:release_ias_db")
                ;
            }
        });

        context.start();
        Thread.sleep(2000);

        context.stop();

        ftpServer.stop();
    }

}