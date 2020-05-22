package krolik.q.reject;

import krolik.q.ConsumerBase;

public class Consumer extends ConsumerBase {

    public static void main(String[] args) throws Exception {
        new ConsumerBase().RecevingSession(REJECT_Q, false, false, null, 2000L);
    }
}
