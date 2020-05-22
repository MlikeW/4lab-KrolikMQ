package krolik.q.length;

import krolik.q.ProducerBase;

import java.util.HashMap;
import java.util.Map;

public class Producer extends ProducerBase {

    public static void main(String[] args) throws Exception {
        Map<String, Object> queueArgs = new HashMap<>();
        queueArgs.put("x-max-length", 10);
        queueArgs.put("overflow", "reject-publish");

        produceQ(LENGTH_LIMIT_Q, false, queueArgs, null, 2000L);
    }
}
