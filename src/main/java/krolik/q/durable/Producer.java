package krolik.q.durable;

import krolik.q.ProducerBase;

public class Producer extends ProducerBase {

    public static void main(String[] args) throws Exception {
        produceQ(DURABLE_Q, false, null, null, 100L);
    }
}
