
Roadmap
=======

* What is Camel?
* Key abstractions

Coding
* move files to another folder
* explain the logs (JMX, routeId, component properties)
* log filename instead of everything
* filter - subsequently and before transfer
  * expression languages and predicates (lambdas usable), predicates builder
* move files from FTP server to local folder
* configure timeouts
* only one property must be configured
* bind CSV rows to Object

* split
* aggregation

* unit of work

* dataformats? gzip?
* filter, choice
* ?testing?
* my experience: the book and JMS and spring security

* rollback (unit of work) / error handling

Open Questions
==============

* What is the difference between to(..) and process(..) and "transform(..)"?

To is changing the body


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
