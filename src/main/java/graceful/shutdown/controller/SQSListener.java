package graceful.shutdown.controller;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

import static org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy.ON_SUCCESS;

@Slf4j
@Component
@EnableSqs
public class SQSListener {

  @Value("${aws.sqs.another-queue.url}")
  private String anotherQueue;

  private AmazonSQSAsync amazonSQSAsync = AmazonSQSAsyncClientBuilder.defaultClient();

  @SqsListener(value = "${aws.sqs.queue.url}", deletionPolicy = ON_SUCCESS)
  public void receive(String message) {
    log.info("Incoming Message: {}", message);
    try {
      Thread.sleep(18000);
    } catch (InterruptedException e) {
      log.info("Exception Caught");
    }

    log.info("Processed Successfully : {}", message);
    log.info("Publishing Message");
    amazonSQSAsync.sendMessage(anotherQueue, "Message after receiving :" + message);
    log.info("Executed for 18sec ish");
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
