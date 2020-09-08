package rsb.bootstrap.reactor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;


import reactor.core.publisher.EmitterProcessor;

public class HotStreamTest1 {

    @Test
    public void hotStream() {
        var first = new ArrayList<Integer>();
        var second = new ArrayList<Integer>();

        var emittor = EmitterProcessor.<Integer>create(2);
        var sink = emittor.sink();

        emittor.subscribe(collect(first));
        sink.next(1);
        sink.next(2);

        emittor.subscribe(collect(second));
        sink.next(3);
        sink.complete();

        assertEquals(3, first.size());
        assertEquals(1, second.size());
    }

    private Consumer<Integer> collect(List<Integer> list) {
        return list::add;
    }
}
