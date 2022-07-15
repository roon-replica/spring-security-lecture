- [baeldung / circular view path error](https://www.baeldung.com/spring-circular-view-path-error)

> By default, the Spring MVC framework applies the InternalResourceView class as the view resolver. As a result, if the @GetMapping value is the same as the view, the request will fail with the Circular View path error.


> implementation group: 'org.thymeleaf', name: 'thymeleaf', version: '3.0.14.RELEASE' 를 쓰면 안 됨
> <br/>
> implementation group: 'org.springframework.boot', name: 'spring-boot-starter-thymeleaf', version: '2.6.4'를 써야 함
<br/>
> 그러면 request path랑 view name 같아도 상관없음
