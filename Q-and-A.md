

Camel Q&A
=========

Q. What is Camel? Give examples. Compare with Spring.

A. Camel is an integration and routing framework.

It integrates with

* File transer, Mail, HTTP/Websockets, Messaging, Stores,
* Apple Notifications, Atom feeds, Caches,  DNS, Dropbox, ElasticSearch, Dropbox, Facebook, Git and
  Google Calendar, Drive, Mail, Geocoder, JIRA, JMX, Printer, Slack
* Bridges to Spring Batch and Spring Integration
* many more (LINK Apache Camel components http://camel.apache.org/components.html)

Supports beyond JSON, XML, CSV, SOAP a wide range of dataformats like EDI, Zip, Syslog, RSS, Barcode, ICal, ProtoBuf.

Expression languages like xpath, Groovy, JavaScript, SpEL.

Spring connects business logic components using an (in general acyclic) directed graph.

Camel integrates

Q. Is Camel an enterprise service bus (ESB)?

No, but Apache ServiceMix (an ESB) uses Camel. But Camel can be considered
microservices extracted from a monolithic ESB. And all this microservice use
the same kind of API and language.

Q. Give the key concepts of Camel?

A. Message, Exchange, Unit of Work, InOnly, InOut,

Route, Endpoint, Producer, Consumer (Polling, Event-triggered)

EIP, Components, Processors, Data Formats, Type Converters, Expression Languages

Q. Is Camel mature? Who started Camel?

A. The project was started 2007 by James Strachan (among others) who co-founded ActiveMQ and Apache
ServiceMix as well. The maturity of the components varies but the core is battletested.

Q. Why should I use Camel instead plain Java Code?

A. In many cases the Camel DSL is more concise that the API of the underlying library.

But indeed, one should continuously check which part of the application should be managed by Camel and which not.

Camel can manage the integration to components outside of the system.
And the route definition can provide a high level view on the application.

Q. What is the difference between Camel-based applications und conventionally
designed applications (like Spring) in terms of runtime analysis (debugging)?
What is the advantage of the Camel approach?
Why is this important for the software design?

A. in Spring applications the stacktrace provides information about the flow
- apart from logs.
In Camel applications the messages must be used to trace information about the flow.
The advantage of Camel is that adding traces to messages (TODO Laufzettel) fits much more
the eCommerce domain than stacktrace-based debugging which has no equivalence in the business domain.
The more the software design resembles the business domain the easier it is to understand
the software.

Q. Which patterns is Camel using? Are Camel patterns supporting eCommerce domain? Given examples.

A. The main pattern is the flow of messagas between components and a concise vocabulary to
describe this flow.

The elements of the flow use the language of the Enterprice Integration Patterns.

Thus the main focus is not on the eCommerce domain. Example: The splitter can be written as
when().when().otherwise(), i.e. it is not made clear that a single entity (in OOP an instance of a class)
reps. a worker is acting here.

Q. Can you bundle the consumption of a JMS message and a SQL change bundle somehow in a transaction?

A. Yes, this is a well known pattern - without using XA (distributed transactions). This is how it looks in Camel:

from("activemq:queue:partners")
  .transacted()
  .beanRef("partner", "toSql")
  .to("jdbc:partnerDb")

Nothing more. It possible to use JTA as well but .. try to avoid it by all means.

Q. How can you make a route 'transactional' if the involved resources are not transactional regarding XA?

A. You register completion and undo operations for a route. Camel calls them Synchronization callbacks.

They are called when routing is finished, the 'unit of work' is done.
All of the callbacks will be executed, i.e. if one of them throws an error,
the error is caught and logged but it does not stop the execution of the other callbacks.

Q. How is concurrency resp. parallelism supported in Camel?

A. One way is is configure a threadpool for a splitter. The easiest way is to set parallelProcessing=true.

The other way is to use queues or topics (real ones or in memory) with more than one consumer.

Q. What is the meaning of streaming for a splitter? Has it something to do with concurrency?

A. No, it reduces the memory overhead but consuming and producing chunks.

Q. How do you tackle errors when you applied a split before the error?

A. Setting shareUnitOfWork = true makes sure that the entire route is considered a single unit of work.

Q. Can the resulting messages of the splitter later be combined again?

A. Of course. Use end().

Q. Can you compare Camel with implementations of JavaScript promises?

A. The promises have a clear focus on the flow of messages, the integration with other systems is done by separated libraries.

Q. How can you monitor a Camel application?

A. Message could be 'wire tapped'. You can use log component to add custom logs.

You can activate tracing by context.setTracing(true) to log details on INFO level.
You can customise the tracer to determine the extent of logged information.
rb.noTracing() deactivates tracing.

You can register notifier for Camel events.

JMX can used to monitor the flow of messages and to administrate routes.

Q. Is it possible to change camel routes at runtime?

A. Yes, the context has an API for it. Additionally, Spring's' refresh scope
allows you to replace all the Camel related beanss with newer versions.

Q. What patterns are used by Camel to deal with errors?

A. redelivery / retry policies. Error handlers, especially rollback handlers (synchronization callbacks).

Q. How can Camel routes be tested?

A. TODO

Q. How do you feed a Camel route programmically? And how do you receive messages programmatically?

A. Use the ProducerTemplate and the TODO.

Q. What do you think about Camel's ability to provide HTTP endpoints?

A. Well, ...

Q. Can Camel be combined with Hystrix, Spring Integration, Spring Batch?

A. Yes there are bridges / adapters.

Q. What happens when you stop a route and there are still message processed?
What is the difference between stop and suspend and remove? Can you configure the shutdown procedure?

A. Both Stop and Suspend allow the routes to gracefully shutdown, i.e.
this ensures messages is given time to complete. The default timeout is 5 minutes.
After that stop is forced. This means exchanges are no longer forwared but
they will be rejected. Note: I (Rod) expect that the rollback (synchronization callbacks)
are executed then.

You can configure the startupOrder and the shutdownRoute strategy to control the shutdown process
if many routes.

Q. How can I stop a route from another?

A: Use to("controlbus:route?routeId=theRouteToStop&action=stop&async=true").


Open Questions
==============

* What is the difference between to(..) and process(..) and "transform(..)"?




Let's build DAS
===============
 * delivery time predictions per dated dp - depending on context (app domain, DPs)
 * delivery product selection
 * third party dependencies
   * scheduled, with cache
   * hystrix
 * tender creation and consolidation
 * high throughput (does camel uses other threads? probably using the vm-component with concurrent consumers)
 * rest + swagger-ui + specification
 * error handling
 * camel route visualization
 * ? logbook
 * ? flow id
 * ? spring security with oauth