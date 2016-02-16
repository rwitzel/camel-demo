

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

Expression languages like xpath,

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
