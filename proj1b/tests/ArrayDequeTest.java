import edu.princeton.cs.algs4.In;
import jh61b.utils.Reflection;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDequeTest {

     @Test
     @DisplayName("ArrayDeque has no fields besides backing array and primitives")
     void noNonTrivialFields() {
         List<Field> badFields = Reflection.getFields(ArrayDeque.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
     }


     @Test
    /**Test addFirst*/
    public void  addFirstTest(){
        Deque<Integer> list1 = new ArrayDeque<Integer>();
        list1.addFirst(1);
        assertThat(list1.toList()).containsExactly(1);
        list1.addFirst(2);
        assertThat(list1.toList()).containsExactly(2,1);
     }

     @Test
    /**Test addLast*/
    public void addLastTest(){
        Deque<Integer> list2 = new ArrayDeque<Integer>();
        list2.addLast(1);
        assertThat(list2.toList()).containsExactly(1);
        list2.addLast(2);
        assertThat(list2.toList()).containsExactly(1,2);
     }

     @Test
    /**Test addLast & First*/
    public void addLastAndFirstTest(){
        Deque<Integer> list3 = new ArrayDeque<Integer>();
        list3.addFirst(2);
        assertThat(list3.toList()).containsExactly(2);
        list3.addLast(3);
        assertThat(list3.toList()).containsExactly(2,3);
        list3.addLast(4);
        list3.addFirst(1);
        assertThat(list3.toList()).containsExactly(1,2,3,4);
     }

     @Test
    /**Test isEmpty and size*/
    public void isEmptyAndSize() {
        Deque<Integer> list4 = new ArrayDeque<Integer>();
        assertThat(list4.isEmpty()).isTrue();
        list4.addFirst(1);
        list4.addLast(2);
        assertThat(list4.size()).isEqualTo(2);
        assertThat(list4.isEmpty()).isFalse();
         for (int i = 0; i < 20; i++) {
             list4.addLast(i);
         }
         assertThat(list4.size()).isEqualTo(22);
         for (int i = 0; i < 22; i++) {
             list4.removeLast();
         }
         assertThat(list4.size()).isEqualTo(0);
         list4.removeLast();
         assertThat(list4.size()).isEqualTo(0);
         assertThat(list4.isEmpty()).isTrue();
     }

     @Test
     /**Test get method*/
     public void getTest() {
         Deque<Integer> list5 = new ArrayDeque<>();
         for (int i = 0; i < 20; i++) {
             list5.addLast(i);
         }
         assertThat(list5.get(3)).isEqualTo(3);
         assertThat(list5.get(-1)).isNull();
         assertThat(list5.get(21)).isNull();
     }

     @Test
    /**Test removeFirst*/
    public void removeTest(){
        Deque<Integer> list6 = new ArrayDeque<>();
        list6.addLast(1);
        list6.removeFirst();
        assertThat(list6.isEmpty()).isTrue();
        for(int i = 0; i < 10; i++) {
            list6.addLast(i);
        }
        for (int j = 0; j < 9; j++) {
            list6.removeFirst();
        }
        assertThat(list6.size()).isEqualTo(1);
        assertThat(list6.toList()).containsExactly(9);
         }

         @Test
         /** Test removeLast*/
         public void removeLast(){
             Deque<Integer> list6 = new ArrayDeque<>();
             list6.addFirst(1);
             list6.removeLast();
             assertThat(list6.isEmpty()).isTrue();
             for(int i = 0; i < 10; i++) {
                 list6.addFirst(i);
             }
             for (int j = 0; j < 9; j++) {
                 list6.removeLast();
             }
             assertThat(list6.size()).isEqualTo(1);
             assertThat(list6.toList()).containsExactly(9);
         }


    @Test
    /**test removing*/
    public void removingTest(){
        Deque<Integer> lld9 = new ArrayDeque<>();
        for (int i = 0; i < 100; i++){
            lld9.addLast(i);
        }
        for (int i = 0; i < 100; i++){
            assertThat(lld9.removeFirst()).isEqualTo(i);
        }
        assertThat(lld9.toList()).containsExactly();
        assertThat(lld9.removeFirst()).isNull();
    }

    @Test
    /**This is test for add after Remove Test*/
    public void addAfterRemoveTest(){
        Deque<Integer> lld8 = new ArrayDeque<>();
        lld8.removeFirst();
        lld8.addFirst(7);
        assertThat(lld8.removeFirst()).isEqualTo(7);
        lld8.addFirst(8);
        lld8.addLast(9);
        assertThat(lld8.toList()).containsExactly(8,9);
        lld8.removeFirst(); //test remove First to one
        assertThat(lld8.toList()).containsExactly(9);
        lld8.removeFirst();// test removeFirst to 0
        assertThat(lld8.toList()).containsExactly();
        lld8.addFirst(8); // test add after remove
        lld8.addFirst(7);
        assertThat(lld8.toList()).containsExactly(7,8);
        lld8.removeLast();
        lld8.removeLast();
        lld8.addLast(9);// test add after remove
        assertThat(lld8.toList()).containsExactly(9);

    }
    @Test
    /**This test performs test for removeFirst addFirst remove Last and addLast*/
    public void removeFirstAndLastTest(){
        Deque<Integer> lld7 = new ArrayDeque<>();
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
}
