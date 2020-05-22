package krolik.q;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import krolik.Common;
import java.util.Map;

public class ProducerBase implements Common {

    public static void produceQ(String queueName, Boolean durable,
                                Map<String, Object> queueArgs, AMQP.BasicProperties properties,
                                Long sleep) throws Exception {
            Channel channel = Common.GetChannel();
            channel.queueDeclare(queueName, durable, false, false, queueArgs);
            Common.SendMessages(channel, EMPTY, queueName, properties, sleep);
    }
}
