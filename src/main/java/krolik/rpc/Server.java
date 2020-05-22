package krolik.rpc;

import com.rabbitmq.client.*;
import krolik.Common;

import java.nio.charset.StandardCharsets;

public class Server implements Common {

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(LOCALHOST);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(REQUEST_Q, false, false, false, null);
        channel.queuePurge(REQUEST_Q);

        channel.basicQos(1);

        System.out.println("Ready");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(delivery.getProperties().getCorrelationId())
                    .build();

            int n = Integer.parseInt(new String(delivery.getBody(), StandardCharsets.UTF_8));
            System.out.println("Message - " + n);
            String modified = EMPTY + modification(n);

            channel.basicPublish(EMPTY, delivery.getProperties().getReplyTo(), replyProps, modified.getBytes(StandardCharsets.UTF_8));
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };

        channel.basicConsume(REQUEST_Q, false, deliverCallback, (consumerTag -> { }));
    }

    private static int modification(int n) {
        return n + n%10 + 1;
    }
}
