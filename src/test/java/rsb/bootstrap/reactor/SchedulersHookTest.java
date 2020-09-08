package rsb.bootstrap.reactor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;


import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

@Log4j2
public class SchedulersHookTest {

    @Test
    public void onSchedulersHook() {

        var couter = new AtomicInteger();
        Schedulers.onScheduleHook("my hook", runnable -> () -> {
            var threadName = Thread.currentThread().getName();
            couter.incrementAndGet();
            log.info("Before execution: {}", threadName);
            runnable.run();
            log.info("After execution: {}", threadName);
        });

        var data = Flux.just(1, 2, 3).delayElements(Duration.ofMillis(1)).subscribeOn(Schedulers.immediate());

        StepVerifier.create(data).expectNext(1, 2, 3).verifyComplete();
        assertEquals(3, couter.get());
    }
}
