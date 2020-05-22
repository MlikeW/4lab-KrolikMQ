package krolik;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

public interface Common {
    String EMPTY = "";
    String LOCALHOST = "localhost";
    Integer PORT = 5672;
    Integer DEVIL = 666;
    String SENT = "Sent - ";
    String RECEIVED = "Received - ";
    String RECEIVING = "Receiving...";

    String TOPIC_NAME = "logs";
    String TOPIC_TYPE = "fanout";

    String NO_ACK_Q = "noAckQ";
    String ACK_Q = "ackQ";
    String DURABLE_Q = "durableQ";
    String TTL_Q = "ttlQ";
    String LENGTH_LIMIT_Q = "limitQ";
    String REJECT_Q = "rejectQ";
    String REQUEST_Q = "rpcQ";


    static void ThreadSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static Channel GetChannel() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(LOCALHOST);
        factory.setPort(PORT);
        return factory.newConnection().createChannel();
    }

    static void SendMessages(Channel channel, String exchange, String routingKey, AMQP.BasicProperties props, Long sleep) throws Exception{
        for (int i = 0; i < DEVIL; i++) {
            channel.basicPublish(exchange, routingKey, props, (EMPTY + i).getBytes());
            System.out.println(SENT + i);
            Common.ThreadSleep(sleep);
        }
    }
}
