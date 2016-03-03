package demos;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.BindyType;
import org.apache.camel.spi.Registry;
import org.apache.camel.spring.spi.ApplicationContextRegistry;
import org.apache.commons.net.ftp.FTP;
import org.apache.ftpserver.FtpServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

public class Application {

    public static void main(String args[]) throws Exception {

        BindyCsvDataFormat bindy = new BindyCsvDataFormat(ArticleWeights.class);
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        Registry registry = new ApplicationContextRegistry(applicationContext);

        CamelContext context = new DefaultCamelContext(registry);

//        context.addRoutes(new RouteBuilder() {
//            public void configure() {
//                from("file:data/inbox?noop=true")
//                        .routeId("travelStockImport")
//                        .autoStartup(false)
//                        .to("file:data/outbox");
//
//            }
//        });

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
               from("timer://stoppingTravelStockImportFtp?repeatCount=1&delay=1000")
                       .routeId("travelStockImportFtpTimer")
                       .autoStartup(false)
                       .log(LoggingLevel.INFO, "Stopping route travelStockImportFtp")
                       .toF("controlbus:route?routeId=%s&action=stop&async=true", "travelStockImportFtp");
            }
        });

        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("ftp:rider:secret@localhost:21000/data/inbox?noop=true&include=message.*csv&sendEmptyMessageWhenIdle=true")
                        .routeId("travelStockImportFtp")
                        .autoStartup(false)
                        .choice()
                            .when(body().isNull())
                                .log(LoggingLevel.INFO, "No new File Found")
                                .toF("controlbus:route?routeId=%s&action=start&async=true", "travelStockImportFtpTimer")
                            .otherwise()
                                .log(LoggingLevel.INFO, String.format("File Found: %s", "${in.headers.CamelFileName}"))
                                //.split(body(String.class).tokenize("\n"))
                                .unmarshal(bindy)
                                .toF("controlbus:route?routeId=%s&action=stop&async=true", "travelStockImportFtpTimer")
                                .log(LoggingLevel.INFO, String.format("Item: %s", "${in.body}"))
                                .to("mustache:/updateArticleWeights.sql").setBody(body().regexReplaceAll(",(\\s+;)", "$1"))
                                .log("log sql: ${body}")
                                .to("jdbc:release_ias_db");
            }
        });

//        context.addRoutes(new RouteBuilder() {
//            public void configure() {
//                from("sql:SELECT * FROM zias_data.article_export?dataSource=dataSource")
//                        .routeId("exportFtp")
//                        .autoStartup(false)
//                        .process(new Processor() {
//                                     @Override
//                                     public void process(Exchange exchange) throws Exception {
//                                         log.error(exchange.toString());
//
//                                     }
//                                 }
//                        )
//                        .to("file:data/outbox/output.csv");
//            }
//        });

        FtpServer ftpServer = FtpServerCreator.embeddedFtpServer(21000,
                Application.class.getResource("/users.properties"));
        ftpServer.start();

        context.start();
        context.startRoute("travelStockImportFtp");


        ServiceStatus status = context.getRouteStatus("travelStockImportFtp");
        while(status.isStarted()) {
            status = context.getRouteStatus("travelStockImportFtp");
            Thread.sleep(1000);
        }

        context.stop();
        ftpServer.stop();
    }

}