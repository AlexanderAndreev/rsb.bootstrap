package rsb.bootstrap.reactor;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;


import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

@Log4j2
public class HotStreamTest2 {

    @Test
    public void hotStream() throws InterruptedException {

        int factor = 10;
        log.info("start");
        var cdl = new CountDownLatch(2);
        var live = Flux.range(0, 10).delayElements(Duration.ofMillis(factor)).share();

        var first = new ArrayList<Integer>();
        var second = new ArrayList<Integer>();

        live.doFinally(signalTypeConsumer(cdl)).subscribe(collect(first));
        Thread.sleep(factor * 2);
        live.doFinally(signalTypeConsumer(cdl)).subscribe(collect(second));
        cdl.await(5, TimeUnit.SECONDS);
        assertTrue(first.size() > second.size());
        log.info("finish");
    }

    private Consumer<SignalType> signalTypeConsumer(CountDownLatch cdl) {

        return signalType -> {
            if (signalType.equals(SignalType.ON_COMPLETE)) {
                cdl.countDown();
                log.info("await");
            }
        };
    }

    private Consumer<Integer> collect(List<Integer> list) {

        return list::add;
    }
}
