package krolik.q.reject;

import krolik.q.ProducerBase;

public class Producer extends ProducerBase {

    public static void main(String[] args) throws Exception {
        produceQ(REJECT_Q, false, null, null, 500L);
    }
}
