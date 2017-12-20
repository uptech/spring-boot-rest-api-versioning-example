package com.example;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

@RestController
public class Example {
  @RequestMapping(value="/",
    method = RequestMethod.POST,
    produces = {"application/vnd.example.v3+json", "application/vnd.example.v2+json"},
    consumes = {"application/vnd.example.v3+json", "application/vnd.example.v2+json"})
  ResponseEntity<String> home(@RequestHeader(value="Content-Type") String contentType, @RequestHeader(value="Accept") String accept, @RequestBody String body) {

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set("Content-Type", "application/vnd.example.v8+json");
    return new ResponseEntity<String>(String.format("{ \"accept\": \"%s\", \"content-type\": \"%s\", \"body\": \"%s\" }", accept, contentType, body), responseHeaders, HttpStatus.OK);
  }

  @RequestMapping(value="/hello",
    method = RequestMethod.POST,
    produces = {"application/json", "application/vnd.example.v1+json"}, // NOTE: order does matter for Accept: */* and application/*
    consumes = {"application/json", "application/vnd.example.v1+json"})
  HelloV1Message helloV1(@RequestBody HelloV1Payload body) {
    return new HelloV1Message(String.format("Hello, %s %s", body.first_name, body.last_name));
  }

  @RequestMapping(value="/hello",
    method = RequestMethod.POST,
    produces = {"application/vnd.example.v2+json"},
    consumes = {"application/vnd.example.v2+json"})
  HelloV2Message helloV2(@RequestBody HelloV2Payload body) {
    return new HelloV2Message(String.format("Hello, %s", body.name));
  }
}
