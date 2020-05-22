package krolik.q;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Delivery;
import krolik.Common;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ConsumerBase implements Common {

    Channel channel;
    Long sleep;

    public ConsumerBase(){

    }

    public void RecevingSession(String queueName, Boolean autoAck, Boolean durable,
                                Map<String, Object> queueArgs, Long sleep) throws Exception {

        this.sleep = sleep;
        channel = Common.GetChannel();

        channel.queueDeclare(queueName, durable, false, false, queueArgs);
        System.out.println(RECEIVING);

        switch(queueName) {
            case REJECT_Q:
                channel.basicConsume(queueName, autoAck, this::processMessageReject, consumerTag -> {});
                break;
            case NO_ACK_Q:
                channel.basicConsume(queueName, autoAck, this::processMessageNoAck, consumerTag -> {});
                break;
            default:
                channel.basicConsume(queueName, autoAck, this::processMessage, consumerTag -> {});
                break;
        }
    }

    private void processMessage(String consumerTag, Delivery delivery) throws IOException {
        channel.basicQos(1);
        processMessageNoAck(consumerTag, delivery);
        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
    }

    private void processMessageReject(String consumerTag, Delivery delivery) throws IOException {
        channel.basicQos(1);
        ReceiveAndSleep(delivery);
        System.out.print("\n--Rejected :(");
        channel.basicReject(delivery.getEnvelope().getDeliveryTag(), true);
    }

    private void processMessageNoAck(String consumerTag, Delivery delivery){
        ReceiveAndSleep(delivery);
        System.out.print("--END--\n");
    }

    private void ReceiveAndSleep(Delivery delivery){
        System.out.print("\n" + RECEIVED + new String(delivery.getBody(), StandardCharsets.UTF_8));
        Common.ThreadSleep(sleep);
    }
}
