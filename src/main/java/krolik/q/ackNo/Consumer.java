package krolik.q.ackNo;


import krolik.q.ConsumerBase;

public class Consumer extends ConsumerBase {

    public static void main(String[] args) throws Exception {
        new ConsumerBase().RecevingSession(NO_ACK_Q, true, false, null, 500L);
    }
}
