import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.vavr.Function1
import io.vavr.Function2
import io.vavr.Tuple
import io.vavr.Tuple2
import io.vavr.collection.List
import io.vavr.concurrent.Promise
import io.vavr.control.Either
import io.vavr.control.Try
import org.junit.Assert
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.mockito.plugins.MockMaker

import java.time.Duration
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import java.util.function.Supplier

import static org.mockito.Mockito.when

class ResilienceTest {
    @Test
    public void test3(){
        // Create a custom configuration for a CircuitBreaker
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .permittedNumberOfCallsInHalfOpenState(2)
                .slidingWindowSize(2)
                .recordExceptions(IOException, TimeoutException)
                .build();

        // Create a CircuitBreakerRegistry with a custom global configuration
        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig)
        def breaker = circuitBreakerRegistry.circuitBreaker("name")
        Supplier<String> decoratedSupplier = CircuitBreaker
                .decorateSupplier(breaker, ResilienceTest::doSomething);

        String result = Try.ofSupplier(decoratedSupplier)
                .recover(throwable -> "Hello from Recovery").get();


    }

    @Test
    public void test49(){
        Either.right(5).map(x -> x + 1);
    }


    static def doSomething(){
        return "hello"
    }

    @Test
    public void test41(){
        def of = Try.of(() -> 5 / 0)
        println of.isFailure()
    }

    @Test
    public void test47(){
        def list = List.of(1, 2, 3)
        def java8 = Tuple.of("java", 8)
        def _1 = java8._1

        Function2<Integer, Integer, Integer> sum = (a, b) -> a + b;
        when(sum.apply(5)).thenReturn()
    }

    @Test
    public void test76() {
        ThreadLocal<String> tl = new ThreadLocal<>();
        tl.set("hello")
        println tl.get()
        new Thread(() -> {
            println tl.get()
        }).start()
        TimeUnit.SECONDS.sleep(1)
    }

    @Test
    public void test88(){
        InheritableThreadLocal<String> tl = new InheritableThreadLocal<>();
        tl.set("hello")
        println tl.get()
        new Thread(() -> {
            println tl.get()
        }).start()
        TimeUnit.SECONDS.sleep(1)
    }
}