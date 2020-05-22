package krolik.q.length;

import krolik.q.ConsumerBase;

import java.util.Map;

public class Consumer extends ConsumerBase {

    public static void main(String[] args) throws Exception {
        new ConsumerBase().RecevingSession(LENGTH_LIMIT_Q, false, false,
                Map.of("x-max-length", 10), 500L);
    }
}
