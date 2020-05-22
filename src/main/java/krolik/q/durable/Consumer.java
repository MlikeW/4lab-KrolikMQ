package krolik.q.durable;

import krolik.q.ConsumerBase;

public class Consumer extends ConsumerBase {

    public static void main(String[] args) throws Exception {
        new ConsumerBase().RecevingSession(DURABLE_Q, false, true, null, 500L);
    }
}
