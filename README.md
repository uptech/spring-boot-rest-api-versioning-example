
## Spring Boot REST API Versioning Example

I created this repository as I needed a simple playground to explore and test which versioning practice I thought was the best when working with Spring Boot.

### Conclusion

I have concluded that I prefer using the HTTP Content negotiation headers (Content-Type and Accept) with custom MIME types to manage versioning. It follows the HTTP specification and is generally supported by out of the box by most web frameworks. Also, because it is part of the HTTP specification it already has a formal protocol defined around communicating incorrect versioning requests, etc. via well defined status codes.

* has formal protocol defined around communicating incorrect requests
    * [Accept Header](https://tools.ietf.org/html/rfc2616#page-100)
    * [Content-Type Header](https://tools.ietf.org/html/rfc2616#page-124)
    * [415 - Unsupported Media Type](https://httpstatuses.com/415)
    * [406 - Not Acceptable](https://httpstatuses.com/406)
* independently version endpoints without having to proxy unchanged endpoints
* generally handled by most web frameworks and clients

## Run

```text
mvn spring-boot:run
```
