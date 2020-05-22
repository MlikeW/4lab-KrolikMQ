package krolik.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import krolik.Common;

import java.nio.charset.StandardCharsets;

public class Sub implements Common{

    public static void main(String[] args) throws Exception {
        Channel channel = Common.GetChannel();
        channel.exchangeDeclare(TOPIC_NAME, TOPIC_TYPE);
        String qName = channel.queueDeclare().getQueue();
        channel.queueBind(qName, TOPIC_NAME, EMPTY);

        System.out.println(RECEIVING);
        DeliverCallback deliverCallback = (consumerTag, delivery)
                -> System.out.println(RECEIVED + new String(delivery.getBody(), StandardCharsets.UTF_8));

        channel.basicConsume(qName, true, deliverCallback, consumerTag -> { });
    }

}
