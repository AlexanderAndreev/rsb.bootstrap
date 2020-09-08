package rsb.bootstrap.reactor;

import org.junit.jupiter.api.Test;


import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.ReplayProcessor;
import reactor.test.StepVerifier;

public class ReplayProcessorTest {

    @Test
    public void replayProcessor() {

        ReplayProcessor<String> processor = ReplayProcessor.create(2, false);
        produce(processor.sink());
        consume(processor);
    }

    private void produce(FluxSink<String> sink) {
        sink.next("1");
        sink.next("2");
        sink.next("3");
        sink.complete();
    }

    private void consume(Flux<String> processor) {
        for (int i = 0; i < 5; i++) {
            StepVerifier.create(processor).expectNext("2").expectNext("3").verifyComplete();
        }
    }
}
