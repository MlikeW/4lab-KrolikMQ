package krolik.topic;

import com.rabbitmq.client.Channel;
import krolik.Common;

public class Pub implements Common {

    public static void main(String[] args) throws Exception {
        Channel channel = Common.GetChannel();
        channel.exchangeDeclare(TOPIC_NAME, TOPIC_TYPE);
        Common.SendMessages(channel, TOPIC_NAME, EMPTY, null, (long)DEVIL);
    }
}
