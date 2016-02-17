
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
* limit the number of files processed
* split and aggregate (weird java syntax? a look at the XML syntax helps!)
* bind CSV rows to Object
* bean method binding: parameter binding is good (service activator pattern)
* create sql with template and send it
* monitoring: hawtio (add a choice to show a tree)

* rollback (unit of work) / error handling
* testing

Summary
=======

* excellent concise and powerful syntax to easily integrate with the world
* unit of work helps in handling error scenarios (components support this already)
* my limited experience: the book and JMS and spring security
* Java later than XML makes finding the right DSL a bit difficult ... but verbose works always