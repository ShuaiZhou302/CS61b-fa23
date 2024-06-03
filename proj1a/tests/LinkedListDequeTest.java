import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

     @Test
     @DisplayName("LinkedListDeque has no fields besides nodes and primitives")
     void noNonTrivialFields() {
         Class<?> nodeClass = NodeChecker.getNodeClass(LinkedListDeque.class, true);
         List<Field> badFields = Reflection.getFields(LinkedListDeque.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(nodeClass) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not nodes or primitives").that(badFields).isEmpty();
     }

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque<String> lld1 = new LinkedListDeque<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         Deque<Integer> lld2 = new LinkedListDeque<>();
         lld2.addLast(2);
         assertThat(lld2.toList()).containsExactly(2);
         lld2.addFirst(1);
         assertThat(lld2.toList()).containsExactly(1,2);
         lld2.addLast(3);
         assertThat(lld2.toList()).containsExactly(1,2,3);

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque<String> lld1 = new LinkedListDeque<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque<Integer> lld1 = new LinkedListDeque<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque.
    @Test
    /**This test performs test for size.*/
    public void sizeTest(){
        Deque<Integer> lld2 = new LinkedListDeque<>();
        assertThat(lld2.size()).isEqualTo(0);
        lld2.addFirst(1);
        lld2.addLast(3);
        lld2.addLast(4);
        assertThat(lld2.size()).isEqualTo(3);
        lld2.removeLast();
        lld2.removeFirst();
        lld2.removeFirst();
        assertThat(lld2.size()).isEqualTo(0);
        lld2.removeFirst();
        assertThat(lld2.size()).isEqualTo(0);
    }

    @Test
    /**This test performs test for isEmpty*/
    public void isEmptyTest(){
        Deque<Integer> lld3 = new LinkedListDeque<>();
        assertThat(lld3.isEmpty()).isTrue();
        lld3.addLast(1);
        assertThat(lld3.isEmpty()).isFalse();
    }

    @Test
    /**This test is for get Method*/
    public void getTest() {
        Deque<Integer> lld4 = new LinkedListDeque<>();
        assertThat(lld4.get(2)).isNull();
        lld4.addFirst(3);
        lld4.addLast(4);
        assertThat(lld4.get(28723)).isNull();
        assertThat(lld4.get(-1)).isNull();
        assertThat(lld4.get(1)).isEqualTo(4);
    }


    @Test
    /**This test is for getRecursive Method*/
    public void getRecursiveTest() {
        Deque<Integer> lld4 = new LinkedListDeque<>();
        assertThat(lld4.getRecursive(2)).isNull();
        lld4.addFirst(3);
        lld4.addLast(4);
        assertThat(lld4.getRecursive(28723)).isNull();
        assertThat(lld4.getRecursive(-1)).isNull();
        assertThat(lld4.getRecursive(1)).isEqualTo(4);
    }



    @Test
    /**This test performs test for removeFirst*/
    public void removeFirstTest(){
        Deque<Integer> lld5 = new LinkedListDeque<>();
        assertThat(lld5.removeFirst()).isNull();
        lld5.addFirst(1);
        lld5.addLast(2);
        lld5.addLast(3);
        assertThat(lld5.removeFirst()).isEqualTo(1); //check removeFirst work
        assertThat(lld5.toList()).containsExactly(2,3);
        assertThat(lld5.removeFirst()).isEqualTo(2);
        lld5.removeFirst();
        assertThat(lld5.toList()).containsExactly();//check remove all of them
    }


    @Test
    /**This test performs test for removeLast*/
    public void removeLastTest(){
        Deque<Integer> lld5 = new LinkedListDeque<>();
        assertThat(lld5.removeLast()).isNull();
        lld5.addFirst(1);
        lld5.addLast(2);
        lld5.addLast(3);
        assertThat(lld5.removeLast()).isEqualTo(3);//check removeLast work
        assertThat(lld5.toList()).containsExactly(1,2);
        lld5.removeLast();
        assertThat(lld5.removeLast()).isEqualTo(1);
        assertThat(lld5.toList()).containsExactly();//check remove all of them
    }

    @Test
    /**This test performs test for removeFirst addFirst remove Last and addLast*/
    public void removeFirstAndLastTest(){
        Deque<Integer> lld7 = new LinkedListDeque<>();
        lld7.addFirst(1);
        lld7.addLast(2);
        lld7.addLast(3);
        lld7.removeFirst();
        lld7.removeLast();
        assertThat(lld7.toList()).containsExactly(2);
        lld7.removeLast();
        assertThat(lld7.toList()).containsExactly();
        lld7.addFirst(3);
        assertThat(lld7.toList()).containsExactly(3);
        lld7.removeFirst();
        assertThat(lld7.toList()).containsExactly();
        lld7.addLast(4);
        assertThat(lld7.toList()).containsExactly(4);
    }

    @Test
    /**This is test for add after Remove Test*/
    public void addAfterRemoveTest(){
        Deque<Integer> lld8 = new LinkedListDeque<>();
        lld8.removeFirst();
        lld8.addFirst(7);
        assertThat(lld8.removeFirst()).isEqualTo(7);
        lld8.addFirst(8);
        lld8.addLast(9);
        assertThat(lld8.toList()).containsExactly(8,9);
        lld8.removeFirst(); //test remove First to one
        assertThat(lld8.toList()).containsExactly(9);
        lld8.removeFirst();// test removeFirst to 0
        assertThat(lld8.toList()).isNull();
        lld8.addFirst(8); // test add after remove
        lld8.addFirst(7);
        assertThat(lld8.toList()).containsExactly(7,8);
        lld8.removeLast();
        lld8.removeLast();
        lld8.addLast(9);// test add after remove
        assertThat(lld8.toList()).containsExactly(9);

    }

    @Test
    /**test removing*/
    public void removingTest(){
        Deque<Integer> lld9 = new LinkedListDeque<>();
        for (int i = 0; i < 100; i++){
            lld9.addLast(i);
        }
        for (int i = 0; i < 100; i++){
            assertThat(lld9.removeFirst()).isEqualTo(i);
        }
        assertThat(lld9.toList()).containsExactly();
        assertThat(lld9.removeFirst()).isNull();
    }

}