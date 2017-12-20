package com.example;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

@SpringBootApplication
public class Application {
  public static void main(String[] args) throws Exception {
    SpringApplication.run(Example.class, args);
  }
}
