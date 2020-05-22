package krolik.q.ackNo;

import krolik.q.ProducerBase;

public class Producer extends ProducerBase {

    public static void main(String[] args) throws Exception {
        produceQ(NO_ACK_Q, false, null, null, 1000L);
    }
}
