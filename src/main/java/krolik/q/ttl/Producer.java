package krolik.q.ttl;

import com.rabbitmq.client.AMQP;
import krolik.q.ProducerBase;

public class Producer extends ProducerBase {

    public static void main(String[] args) throws Exception {
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .contentType("text/plain")
                .expiration("10")
                .build();
        produceQ(TTL_Q, false, null, properties, 200L);
    }
}
