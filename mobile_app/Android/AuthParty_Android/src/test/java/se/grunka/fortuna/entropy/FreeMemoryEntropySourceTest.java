package se.grunka.fortuna.entropy;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import se.grunka.fortuna.accumulator.EventAdder;
import se.grunka.fortuna.accumulator.EventScheduler;

import static org.junit.Assert.assertEquals;

public class FreeMemoryEntropySourceTest {

    private FreeMemoryEntropySource target;
    private int schedules;
    private int adds;

    @Before
    public void before() throws Exception {
        target = new FreeMemoryEntropySource();
        schedules = 0;
        adds = 0;
    }

    @Test
    public void shouldReadFreeMemory() throws Exception {
        target.event(
                new EventScheduler() {
                    @Override
                    public void schedule(long delay, TimeUnit timeUnit) {
                        assertEquals(100, timeUnit.toMillis(delay));
                        schedules++;
                    }
                },
                new EventAdder() {
                    @Override
                    public void add(byte[] event) {
                        assertEquals(2, event.length);
                        adds++;
                    }
                }
        );
        assertEquals(1, schedules);
        assertEquals(1, adds);
    }
}
