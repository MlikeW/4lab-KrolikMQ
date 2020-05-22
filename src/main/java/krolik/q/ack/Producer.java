package krolik.q.ack;

import krolik.q.ProducerBase;

public class Producer extends ProducerBase {

    public static void main(String[] args) throws Exception {
        produceQ(ACK_Q, false, null, null, 2000L);
    }

}
