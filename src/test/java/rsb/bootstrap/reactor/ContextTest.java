package rsb.bootstrap.reactor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;


import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;
import reactor.util.context.Context;

@Log4j2
public class ContextTest {

    @Test
    public void context() throws InterruptedException {

        var contextValues = new ConcurrentHashMap<String, AtomicInteger>();
        var max = 3;
        var key = "key1";
        var cdl = new CountDownLatch(max);
        var context = Context.of(key, "value1");

        Flux.range(0, max).delayElements(Duration.ofMillis(1))
                .doOnEach(integerSignal -> {
                    var currentContext = integerSignal.getContext();
                    if (integerSignal.getType().equals(SignalType.ON_NEXT)) {
                        var key1 = currentContext.get(key);
                        assertNotNull(key1);
                        assertEquals("value1", key1);

                        contextValues.computeIfAbsent(key, s -> new AtomicInteger(0)).incrementAndGet();
                    }
                })
                .subscriberContext(context).subscribe(integer -> {
                    log.info(integer);
                    cdl.countDown();
                });

        cdl.await();
        assertEquals(3, contextValues.get(key).get());
    }
}
