package com.example.tokenbucket;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sayHi")
public class Controller {
  @GetMapping
  public String hello(@RequestParam String name){
    return "hello " + name;
  }
}
