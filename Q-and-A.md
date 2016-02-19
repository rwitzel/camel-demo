

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