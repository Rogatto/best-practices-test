package diferentesniveis;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.channels.Channel;
import co.paralleluniverse.strands.channels.Channels;
import com.google.common.collect.Sets;
import com.pinterest.jbender.JBender;
import com.pinterest.jbender.events.TimingEvent;
import com.pinterest.jbender.executors.RequestExecutor;
import com.pinterest.jbender.intervals.ConstantIntervalGenerator;
import com.pinterest.jbender.intervals.IntervalGenerator;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Set;

import static org.junit.Assert.assertEquals;


public class TestPerformance {
    private static final class FakeRequestExecutor implements RequestExecutor<Integer, Integer> {
        @Override
        public Integer execute(long nanoTime, Integer request) {
            return request;
        }
    }

    private void assertEvents(Channel<TimingEvent<Integer>> eventCh, int start, int end) throws SuspendExecution, InterruptedException {
        Set<Integer> actual = Sets.newHashSetWithExpectedSize(end - start);
        Set<Integer> expected = Sets.newHashSetWithExpectedSize(end - start);

        for (int i = start; i < end; ++i) {
            expected.add(i);
        }

        int cur = start;
        while (true) {
            TimingEvent<Integer> t = eventCh.receive();

            if (t == null) {
                break;
            }

            Integer i = t.response;
            actual.add(i);
            cur++;
        }

        assertEquals(cur, end);
        assertEquals(actual, expected);
    }

    private void requests(Channel<Integer> requestCh, int count) {
        new Fiber<Void>(() -> {
            for (int i = 0; i < count; ++i) {
                requestCh.send(i);
            }
            requestCh.close();
        }).start();
    }

    @Test
    @Category(PerformanceTest.class)
    public void testLoadTestThroughputNoRequests() throws SuspendExecution, InterruptedException {
        IntervalGenerator intervalGen = new ConstantIntervalGenerator(0);
        Channel<Integer> requestCh = Channels.newChannel(-1);
        Channel<TimingEvent<Integer>> eventCh = Channels.newChannel(-1);
        RequestExecutor<Integer, Integer> executor = new FakeRequestExecutor();

        requests(requestCh, 0);
        JBender.loadTestThroughput(intervalGen, 0, requestCh, executor, eventCh);
        assertEvents(eventCh, 0, 0);
    }

    @Test
    @Category(PerformanceTest.class)
    public void testLoadTestThroughputOneRequest() throws SuspendExecution, InterruptedException {
        IntervalGenerator intervalGen = new ConstantIntervalGenerator(0);
        Channel<Integer> requestCh = Channels.newChannel(-1);
        Channel<TimingEvent<Integer>> eventCh = Channels.newChannel(-1);
        RequestExecutor<Integer, Integer> executor = new FakeRequestExecutor();

        requests(requestCh, 1);
        JBender.loadTestThroughput(intervalGen, 0, requestCh, executor, eventCh);
        assertEvents(eventCh, 0, 1);
    }

    @Test
    @Category(PerformanceTest.class)
    public void testLoadTestThroughputTenRequests() throws SuspendExecution, InterruptedException {
        IntervalGenerator intervalGen = new ConstantIntervalGenerator(0);
        Channel<Integer> requestCh = Channels.newChannel(-1);
        Channel<TimingEvent<Integer>> eventCh = Channels.newChannel(-1);
        RequestExecutor<Integer, Integer> executor = new FakeRequestExecutor();

        requests(requestCh, 10);
        JBender.loadTestThroughput(intervalGen, 0, requestCh, executor, eventCh);
        assertEvents(eventCh, 0, 10);
    }

    @Test
    @Category(PerformanceTest.class)
    public void testLoadTestThroughputWarmup() throws SuspendExecution, InterruptedException {
        IntervalGenerator intervalGen = new ConstantIntervalGenerator(0);
        Channel<Integer> requestCh = Channels.newChannel(-1);
        Channel<TimingEvent<Integer>> eventCh = Channels.newChannel(-1);
        RequestExecutor<Integer, Integer> executor = new FakeRequestExecutor();

        requests(requestCh, 10);
        JBender.loadTestThroughput(intervalGen, 5, requestCh, executor, eventCh);
        assertEvents(eventCh, 5, 10);
    }

    @Test
    @Category(PerformanceTest.class)
    public void testLoadTestConcurrencyNoRequests() throws SuspendExecution, InterruptedException {
        Channel<Integer> requestCh = Channels.newChannel(-1);
        Channel<TimingEvent<Integer>> eventCh = Channels.newChannel(-1);
        RequestExecutor<Integer, Integer> executor = new FakeRequestExecutor();

        requests(requestCh, 0);
        JBender.loadTestConcurrency(1, 0, requestCh, executor, eventCh);
        assertEvents(eventCh, 0, 0);
    }

    @Test
    @Category(PerformanceTest.class)
    public void testLoadTestConcurrencyOneRequest() throws SuspendExecution, InterruptedException {
        Channel<Integer> requestCh = Channels.newChannel(-1);
        Channel<TimingEvent<Integer>> eventCh = Channels.newChannel(-1);
        RequestExecutor<Integer, Integer> executor = new FakeRequestExecutor();

        requests(requestCh, 1);
        JBender.loadTestConcurrency(1, 0, requestCh, executor, eventCh);
        assertEvents(eventCh, 0, 1);
    }

    @Test
    @Category(PerformanceTest.class)
    public void testLoadTestConcurrencyTenRequests() throws SuspendExecution, InterruptedException {
        Channel<Integer> requestCh = Channels.newChannel(-1);
        Channel<TimingEvent<Integer>> eventCh = Channels.newChannel(-1);
        RequestExecutor<Integer, Integer> executor = new FakeRequestExecutor();

        requests(requestCh, 10);
        JBender.loadTestConcurrency(1, 0, requestCh, executor, eventCh);
        assertEvents(eventCh, 0, 10);
    }

    @Test
    @Category(PerformanceTest.class)
    public void testLoadTestConcurrencyWarmup() throws SuspendExecution, InterruptedException {
        Channel<Integer> requestCh = Channels.newChannel(-1);
        Channel<TimingEvent<Integer>> eventCh = Channels.newChannel(-1);
        RequestExecutor<Integer, Integer> executor = new FakeRequestExecutor();

        requests(requestCh, 10);
        JBender.loadTestConcurrency(1, 5, requestCh, executor, eventCh);
        assertEvents(eventCh, 5, 10);
    }
}