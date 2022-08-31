package io.nirahtech;

import static org.junit.Assert.assertTrue;

import java.util.stream.Stream;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {

        String[] integers = new String[100_000];
        for (int i = 0; i < integers.length; i++) {
            integers[i] = new StringBuffer().append(i).toString();
        }
        long start = System.nanoTime();
        for (String i : integers) {

        }
        long end = System.nanoTime();
        System.out.println(end - start);

        Stream<String> stream = Stream.of(integers);
        start = System.nanoTime();
        stream.forEach(i -> {
        });
        end = System.nanoTime();
        System.out.println(end - start);

        assertTrue(true);
    }
}
