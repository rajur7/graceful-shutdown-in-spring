package graceful.shutdown.controller;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.listener.Acknowledgment;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

import static org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy.NEVER;

@Slf4j
@Component
@EnableSqs
public class SQSListener {

  @Value("${aws.sqs.another-queue.url}")
  private String anotherQueue;

  private AmazonSQSAsync amazonSQSAsync = AmazonSQSAsyncClientBuilder.defaultClient();

  @SqsListener(value = "${aws.sqs.queue.url}", deletionPolicy = NEVER)
  public void receive(String message, Acknowledgment acknowledgment) {
    log.info("Incoming Message: {}", message);
    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      log.info("Exception Caught");
    }

    log.info("Processed Successfully after 10s: {}", message);
    log.info("Publishing Message to another queue");
    amazonSQSAsync.sendMessage(anotherQueue, "Message after receiving :" + message);
    try {
      log.info("Deleting Message");
      acknowledgment.acknowledge().get();
    } catch (Exception e) {
      log.info("Exception occurred while deleting message from the queue : {}", e.getMessage());
    }
  }

  //  @PreDestroy
  public void preDestroy() {
    log.info("going to sleep before destroy");
    try {
      Thread.sleep(30000);
    } catch (InterruptedException e) {
      System.out.println("Got exception in preDestroy");
    }
    log.info("shutting down after preDestroy executed");
  }
}
