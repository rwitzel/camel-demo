package demos;

import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import static org.apache.camel.LoggingLevel.WARN;

public class Demo {

    public static void main(String args[]) throws Exception {

        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("file:data/inbox?noop=true&include=.*xml")
                        .log(WARN, "loaded ${in.header.CamelFileName}")
                        .to("file:data/outbox");
            }
        });

        context.start();
        Thread.sleep(2000);

        context.stop();
    }
}