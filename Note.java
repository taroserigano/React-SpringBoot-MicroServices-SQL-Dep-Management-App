eureka.instance.client.serverUrl.defaultZone=http://localhost:8761/eureka/

spring.application.name=API-GATEWAY
server.port=9191

spring.application.name=CONFIG-SERVER
server.port=8888

** all Services are connected to the port 8888:, which is their config server

spring.application.name=EMPLOYEE-SERVICE
spring.config.import=optional:configserver:http://localhost:8888

------SERVICE REGISTRY - on Eureka 

spring.application.name=SERVICE-REGISTRY
server.port=8761
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false   <-- for debugging 





--RABBIT MQ 
-shares the samr PORT number for all services 

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest




CONFIG Server - hosts all props on github repo


# Actuator endpoints for Circuit Breaker
management.health.circuitbreakers.enabled=true
management.endpoints.web.exposure.include=health
management.endpoint.health.show-details=always

# Circuit Breaker configuration
resilience4j.circuitbreaker.instances.EMPLOYEE-SERVICE.registerHealthIndicator=true
resilience4j.circuitbreaker.instances.EMPLOYEE-SERVICE.failureRateThreshold=50
resilience4j.circuitbreaker.instances.EMPLOYEE-SERVICE.minimumNumberOfCalls=5
resilience4j.circuitbreaker.instances.EMPLOYEE-SERVICE.automaticTransitionFromOpenToHalfOpenEnabled=true
resilience4j.circuitbreaker.instances.EMPLOYEE-SERVICE.waitDurationInOpenState=5s
resilience4j.circuitbreaker.instances.EMPLOYEE-SERVICE.permittedNumberOfCallsInHalfOpenState=3
resilience4j.circuitbreaker.instances.EMPLOYEE-SERVICE.slidingWindowSize=10
resilience4j.circuitbreaker.instances.EMPLOYEE-SERVICE.slidingWindowType=COUNT_BASED

# Retry configuration
resilience4j.retry.instances.EMPLOYEE-SERVICE.registerHealthIndicator=true
resilience4j.retry.instances.EMPLOYEE-SERVICE.maxRetryAttempts=5
resilience4j.retry.instances.EMPLOYEE-SERVICE.waitDuration=2s



-FEIGN CLIENTS 
-WEB CLIENT 
@EnableFeignClients
public class EmployeeServiceApplication {

//	@Bean
//	@LoadBalanced
//	public RestTemplate restTemplate(){
//		return new RestTemplate();
//	}

	@Bean
	public WebClient webClient(){
		return WebClient.builder().build();
	}

	public static void main(String[] args) {
		SpringApplication.run(EmployeeServiceApplication.class, args);
	}

}

FEIGN - how to GET from other service through API Client:
@FeignClient("DEPARTMENT-SERVICE")
public interface APIClient {
    // Build get department rest api
    @GetMapping("api/departments/{department-code}")
    DepartmentDto getDepartment(@PathVariable("department-code") String departmentCode);
}
public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employeeDto);

    APIResponseDto getEmployeeById(Long employeeId);
}


--API RESPONSE DTO -- get that DTOs from other services as APIResponseDto 

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class APIResponseDto {
    private EmployeeDto employee;
    private DepartmentDto department;
    private OrganizationDto organization;
}


When getting Employee Object, and needs to access other services, 
Use APIs and get those data, set them and return in API RESponse body 
-*using Web Client 

 //@CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Override

    public APIResponseDto getEmployeeById(Long employeeId) {

OrganizationDto organizationDto = webClient.get()
                .uri("http://localhost:8083/api/organizations/" + employee.getOrganizationCode())
                .retrieve()
                .bodyToMono(OrganizationDto.class)
                .block();


APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployee(employeeDto);
        apiResponseDto.setDepartment(departmentDto);
        apiResponseDto.setOrganization(organizationDto);
        return apiResponseDto;
    }


--CIRCUIT BREAKER (Default Response) 

public APIResponseDto getDefaultDepartment(Long employeeId, Exception exception) {

        LOGGER.info("inside getDefaultDepartment() method");

        Employee employee = employeeRepository.findById(employeeId).get();

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentName("R&D Department");
        departmentDto.setDepartmentCode("RD001");
        departmentDto.setDepartmentDescription("Research and Development Department");

        EmployeeDto employeeDto = EmployeeMapper.mapToEmployeeDto(employee);

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployee(employeeDto);
        apiResponseDto.setDepartment(departmentDto);
        return apiResponseDto;
    }


















