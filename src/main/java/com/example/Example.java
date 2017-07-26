package com.example;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

// Client Sends
//    Content-Type: application/vnd.example.dms.v2+json
//    Accept: application/vnd.example.dms.v2+json,application/vnd.example.dms.v1+json;q=0.5
//

// things to vet
// - how does parsing of payloads work with custom mime-types? Does it properly
// detect the custom mime-types are JSON based and parse them into the POJO
// - how does parsing of payloads work with versioning as the POJO has to be
// different dependending on the version.
// - how does generation of POJO work for response based version?
// - does POJO to JSON work wiht custom mime-types?

// Idea: use multiple request mappings, for each version as it would in theory
// allow for the PROJO and @RequestBody annotations to be used.



@RestController
@EnableAutoConfiguration
public class Example {

    @RequestMapping(value="/",
      method = RequestMethod.POST,
      produces = {"application/vnd.example.v3+json", "application/vnd.example.v2+json"},
      consumes = {"application/vnd.example.v3+json", "application/vnd.example.v2+json"})
    ResponseEntity<String> home(@RequestHeader(value="Content-Type") String contentType, @RequestHeader(value="Accept") String accept, @RequestBody String body) {

      // get some number of Accepts header entries (eg. v1, v2, v3)
      // has some number of custom mime-types it Produces (eg. v2, v3)
      // produce the version that should respond with
      // - this should take into consideration multiple accepts being specified,
      // and their priorities as specified in the HTTP specification

      // GAP: lib to attempt to determine which supported mime-type to build and return

      // processes the data received
      // case contentType:
      //    "application/vnd.example.v3+json": 
      //      ...
      //    "application/vnd.example.v2+json": 
      //      ...

      // builds the data to send, sorted by most recent to least recent
      // case accept:
      //    "application/vnd.example.v3+json": 
      //      ...
      //      set content-type header of response
      //    "application/vnd.example.v2+json": 
      //      ...

      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.set("Content-Type", "application/vnd.example.v8+json");
      return new ResponseEntity<String>(String.format("{ \"accept\": \"%s\", \"content-type\": \"%s\", \"body\": \"%s\" }", accept, contentType, body), responseHeaders, HttpStatus.OK);
    }


    // To migrate existing non-versioned endpoints over we simply need to
    // declare them as follows as it will support clients that are currently
    // explicitly Accept: application/json as well as ones that are Accept: */*.
    // It also correctly supports clients that explicitly Accept: application/vnd.example.v1

    // Content-Type: application/json
    // Content-Type: application/json, Accept: */*
    // Content-Type: application/json, Accept: application/*
    // Content-Type: application/json, Accept: application/vnd.example.v1+json
    // Content-Type: application/vnd.example.v1+json, Accept: application/vnd.example.v1+json

    // Requeste Payload: { "first_name": "Bob", "last_name": "Villa" }
    // Response Payload: { "message": "Hello, Bob Villa" }
    @RequestMapping(value="/hello",
      method = RequestMethod.POST,
      produces = {"application/json", "application/vnd.example.v1+json"}, // NOTE: order does matter for Accept: */* and application/*
      consumes = {"application/json", "application/vnd.example.v1+json"})
    HelloV1Message helloV1(@RequestBody HelloV1Payload body) {
      return new HelloV1Message(String.format("Hello, %s %s", body.first_name, body.last_name));
    }

    // Content-Type: application/vnd.example.v2+json
    // Content-Type: application/vnd.example.v2+json, Accept: */*
    // Content-Type: application/vnd.example.v2+json, Accept: application/vnd.example.v2+json

    // Requeste Payload: { "name": "Bob Villa" }
    // Response Payload: { "message": "Hello, Bob Villa" }
    @RequestMapping(value="/hello",
      method = RequestMethod.POST,
      produces = {"application/vnd.example.v2+json"},
      consumes = {"application/vnd.example.v2+json"})
    HelloV2Message helloV2(@RequestBody HelloV2Payload body) {
      return new HelloV2Message(String.format("Hello, %s", body.name));
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Example.class, args);
    }

}
