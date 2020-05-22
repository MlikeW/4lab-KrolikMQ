package krolik.rpc;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import krolik.Common;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class Client implements AutoCloseable  {

    private Connection connection;
    private Channel channel;

    public Client() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Common.LOCALHOST);
        factory.setPort(Common.PORT);

        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public static void main(String[] argv) {
        try (Client modRpc = new Client()) {
            for (int i = 0; i < 32; i++) {
                System.out.println("_______\n" + Common.SENT + i);
                String i_str = Integer.toString(i);
                String response = modRpc.call(i_str);
                System.out.println("Modified - " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String call(String message) throws IOException, InterruptedException {
        final String correlationId = UUID.randomUUID().toString();

        String replyQueueName = channel.queueDeclare().getQueue();
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(correlationId)
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish(Common.EMPTY, Common.REQUEST_Q, props, message.getBytes(StandardCharsets.UTF_8));

        final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

        String finalConsTag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
            if (delivery.getProperties().getCorrelationId().equals(correlationId)) {
                response.offer(new String(delivery.getBody(), StandardCharsets.UTF_8));
            }
        }, consumerTag -> { });

        String result = response.take();
        channel.basicCancel(finalConsTag);
        return result;
    }

    public void close() throws IOException {
        connection.close();
    }
}
