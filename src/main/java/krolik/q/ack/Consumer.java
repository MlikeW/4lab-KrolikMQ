package krolik.q.ack;

import krolik.q.ConsumerBase;

public class Consumer extends ConsumerBase {

    public static void main(String[] args) throws Exception {
        new ConsumerBase().RecevingSession(ACK_Q, false, false, null, 30L);
    }
}
