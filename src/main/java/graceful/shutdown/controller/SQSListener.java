package graceful.shutdown.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy.ON_SUCCESS;

@Slf4j
@Component
@EnableSqs
public class SQSListener {

  @SqsListener(value = "${aws.sqs.queue.url}", deletionPolicy = ON_SUCCESS)
  public void receive(String message) {
    log.info("Incoming Message: {}", message);
    try {
      Thread.sleep(18000);
    } catch (InterruptedException e) {
      log.info("Exception Caught");
    }
    log.info("Processed Successfully : {}", message);
    log.info("Executed for 18sec");
  }
}
