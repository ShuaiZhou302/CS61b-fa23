import deque.MaxArrayDeque;
import net.sf.saxon.expr.Component;
import org.junit.jupiter.api.*;
import deque.Deque;
import deque.ArrayDeque;
import deque.LinkedListDeque;

import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDequeTest {
    @Test
        public void addLastTestBasicWithoutToList() {
            Deque<String> lld1 = new MaxArrayDeque<>(); //LInkedListDeque

            lld1.addLast("front"); // after this call we expect: ["front"]
            lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
            lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
            assertThat(lld1).containsExactly("front", "middle", "back");
        }

    @Test
    public void testEqualDeques() {
        Deque<String> lld1 = new MaxArrayDeque<>();
        Deque<String> lld2 = new MaxArrayDeque<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertThat(lld1).isEqualTo(lld2);
    }


    @Test
    public void toStringTest() {
        Deque<String> lld1 = new MaxArrayDeque<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        System.out.println(lld1);
    }

    @Test
    public void maxTest() {
        Comparator<Integer> customeComparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        };

        Comparator<Integer> reverseComparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return -(o1 - o2);
            }
        };
        MaxArrayDeque<Integer> lld1 = new MaxArrayDeque<>(customeComparator);
        lld1.addLast(1);
        lld1.addLast(11);
        lld1.addLast(99);

        assertThat(lld1.max()).isEqualTo(99);
        assertThat(lld1.max(reverseComparator)).isEqualTo(1);
    }
}


