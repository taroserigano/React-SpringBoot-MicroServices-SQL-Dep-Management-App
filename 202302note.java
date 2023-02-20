
SERVICE REGISTRY & DISCOVERY 

NETFLIX EUREKA SERVER










Resilience4J 

-Circuit Breaker - stops making requests when service is failing 
-FallBack - Alternative path for failing requests to go to 
-Retry - Makes retries when service failing 
-Rate Limit - Limits the number of calls service receives 
-BulkHead - Limits the number of outgoing requests to service to avoid overloading 
 


-Circuit Breaker 

@CircuitBreaker(name="detailsForCustomersSupportApp",
fallbackMethod="CustomerDetailsFallBack") 


CLOSED - initial state, accepting client requests 
OPEN - will make all incoming requests fail fast - after eceeding fail threshold 
HALF-OPEN - periodically, status moves to half-open and checks if fail has been resolved by allowing a few requests to check 


-Retry - attempts retry pattern when service failing 


-configure values:
--maxAttempts - max number of attempts 
--waitDuration - wait duration

# we can set up a fallback method if retry fails 
@Retry(name="defailtForCustomersSuportApp",
fallbackMethod="customerDetailsFallBack") 


-Rate Limitter - limits the number of valls to avoid overloading 

# we can set up a fallback method if retry fails

-BulkHead - limits the resources which can be used for services, in order to avoid resource exchaustion 

# we can set up a fallback method if retry fails


API GATEWAY 

-Routing 
-Security
-Logging, Auditing


SPRING SLEUTH 

-adds logger 
--Application name, Trace Id (general entire), Span Id (transation unit) 

ZIPKIN 

-visualization tool 







----------------------------------------------------------

Web Client and Open Feign - HTTP req communication
























