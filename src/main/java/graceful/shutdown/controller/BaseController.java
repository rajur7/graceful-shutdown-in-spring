package graceful.shutdown.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;

@Slf4j
@RestController
public class BaseController {

  @GetMapping(value = "wait-for-some-sec")
  public String respondAfterFewSec() {
    log.info("request received... will wait for some sec");
    int count = 0;
    while (count <= 20){
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      count++;
    }
    log.info("sending response after some sec");
    return "Processed completely";
  }

  @GetMapping(value = "no-wait")
  public String respondImmediately(){
    log.info("request received... will send response immediately");
    log.info("sending response immediately");
    return "Processed Immediately";
  }

//  @PreDestroy
  public void preDestroy() {
    log.info("going to sleep before destroy");
    try {
      Thread.sleep(20000);
    } catch (InterruptedException e) {
      System.out.println("Got exception in preDestroy");
    }
    log.info("shutting down after preDestroy executed");
  }
}
