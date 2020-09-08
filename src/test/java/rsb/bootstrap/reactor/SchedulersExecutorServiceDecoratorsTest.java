package rsb.bootstrap.reactor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import org.aopalliance.intercept.MethodInterceptor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;


import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

@Log4j2
public class SchedulersExecutorServiceDecoratorsTest {

    private final AtomicInteger methodInvocationCounts = new AtomicInteger();
    private String rsb = "rsb";

    @BeforeEach
    public void before() {

        Schedulers.resetFactory();
        Schedulers.addExecutorServiceDecorator(this.rsb,
                ((scheduler, scheduledExecutorService) -> this.decorate(scheduledExecutorService)));
    }

    @Test
    public void changeDefaultDecorator() {
        var numbers = Flux.just(1).delayElements(Duration.ofMillis(1));
        StepVerifier.create(numbers).thenAwait(Duration.ofMillis(10)).expectNextCount(1).verifyComplete();
        assertEquals(1, this.methodInvocationCounts.get());
    }

    @AfterEach
    public void after() {
        Schedulers.removeExecutorServiceDecorator(this.rsb);
    }

    private ScheduledExecutorService decorate(ScheduledExecutorService scheduledExecutorService) {

        var pfb = new ProxyFactoryBean();
        try {
            pfb.setProxyInterfaces(new Class[]{ScheduledExecutorService.class});
            pfb.addAdvice((MethodInterceptor) methodInvocation -> {
                var methodName = methodInvocation.getMethod().getName().toLowerCase();
                this.methodInvocationCounts.incrementAndGet();
                log.info("method: {}", methodName);
                return methodInvocation.proceed();
            });
            pfb.setSingleton(true);
            pfb.setTarget(scheduledExecutorService);
            return (ScheduledExecutorService) pfb.getObject();
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }
}
