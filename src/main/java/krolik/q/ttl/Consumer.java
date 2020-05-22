package krolik.q.ttl;

import krolik.q.ConsumerBase;

public class Consumer extends ConsumerBase {

    public static void main(String[] args) throws Exception {
        new ConsumerBase().RecevingSession(TTL_Q, false, false, null, 500L);
    }
}
