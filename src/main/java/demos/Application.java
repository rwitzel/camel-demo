package demos;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import static java.util.Arrays.asList;

public class Application {

    public static void main(String args[]) throws Exception {

        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("direct:start")
                        .routeId("playground")
                        .split(body())
                        .log("body: ${body}")
                        .end()
                        .to("seda:end");
            }
        });

        context.start();

        ProducerTemplate producer = context.createProducerTemplate();
        producer.sendBody("direct:start", asList("abc", "def"));

        ConsumerTemplate consumer = context.createConsumerTemplate();
        Exchange received = consumer.receive("seda:end");
        System.out.println("received.getIn(): " + received.getIn());

        context.stop();
    }

}