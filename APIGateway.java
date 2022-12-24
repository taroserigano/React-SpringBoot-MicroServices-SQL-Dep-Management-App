1. Gateway sits on Eureka, 
2. Whenever Request sent to A{I Gateway, 
3. It talks to Eureka and Eureka finds the requested directory (routes)
and redirect to that route through load balancer ie. lb:/ACCOUNTS




Gateway Responsibilities 

-Routing 

-Security

-Rate Limiting 

-Monitoring / Logging 

-Blue / Green Deployments 

-Caching 

-Monolith Strangling 



properties file 

management.info.env.enabled = true  // for actuator 

## fetch all the registry info from Eureka 
* can connect to other services on registry on Eureka 
* like allow and routing to accessing Dept microservice 

spring.cloug.getaway.discovery.locator.enabled = true 



port 8072 

if you send POST req to localhost:8072/ACCOUNTS/myaccounts 

















