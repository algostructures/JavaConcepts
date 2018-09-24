package com.example.javaconcepts;


import com.google.common.collect.Lists;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class Java8Tests {

    @Test
    public void whenFilterWithCustomPredicateThenFiltered() {
        /*
        * Filtering Using Custom Predicate With Lambda
        * */
        Predicate<String> predicate = input -> {
            assert input != null;
            return input.startsWith("A") || input.startsWith("J");
        };

        List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        List<String> result = names.stream().filter(predicate).collect(Collectors.toList());

        assertEquals(3, result.size());
        assertThat(result, containsInAnyOrder("John", "Jane", "Adam"));
    }

    @Test
    public void whenTransformWithFunctionThenTransformed() {
        /*
        * transform a collection using a Function.
        * */

        /*
        Function<String, Integer> function = input -> input.length();

        Function<String, Integer> function = new Function<String, Integer>() {
            @Override
            public Integer apply(String input) {
                return input.length();
            }
        };*/

        Function<String, Integer> function = String::length;

        List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        List<Integer> result = names.stream().map(function).collect(Collectors.toList());

        assertThat(result, Matchers.contains(4, 4, 4, 3));
    }

    @Test
    public void whenCheckingIfAllElementsMatchAConditionThenCorrect() {
        /*
        * let’s check if all elements in a Collection match a certain condition.
        * */
        List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        Predicate<String> predicate = input -> {
            assert input != null;
            return input.contains("n") || input.contains("m");
        };

        boolean result = names.stream().allMatch(predicate);
        assertTrue(result);

        result = names.stream().allMatch((value)-> value.contains("a"));
        assertFalse(result);
    }

    @Test
    public void whenConvertingOneArrayTypeToAnotherThenCorrect() {
        /*
        * Transforming Array of one type to another type
        * */

        int a[] = {1,2,3,4};

        Node nodes[] = IntStream.range(0,a.length).mapToObj(i -> new Node(i, a[i])).toArray(Node[]::new);

        Node expected[] = {new Node(0,1), new Node(1,2), new Node(2,3), new Node(3,4)};

        assertArrayEquals(expected, nodes);
    }

    @Test
    public void whenSortingArrayOfCustomTypeInLineFunctionThenCheck(){
        /*
         * Sorting An Array of custom class Type With Using Comparator and Inline Function
         * */
        Node ar[] = { new Node(1,2), new Node(0,1), new Node(3,4), new Node(2,3)};

        Arrays.sort(ar, Comparator.comparing(Node::getVal));

        Node expected[] = {new Node(0,1), new Node(1,2), new Node(2,3), new Node(3,4)};

        assertArrayEquals(expected, ar);

    }

    @Test
    public void whenSortingArrayOfCustomTypeWithFunctionThenCheck(){
        /*
        * Sorting An Array of custom class Type With Using Comparator and Function
        * */
        Function<Node, Integer> func = Node::getVal;

        Node ar[] = { new Node(1,2), new Node(0,1), new Node(3,4), new Node(2,3)};

        Arrays.sort(ar, Comparator.comparing(func));

        Node expected[] = {new Node(0,1), new Node(1,2), new Node(2,3), new Node(3,4)};

        assertArrayEquals(expected, ar);

    }

    @Test
    public void whenConvertingStringToStreamOfCharactersThenCheck(){
        /*
        * TODO: Use Assert
        * */
        String testString = "Str";

        IntStream intStream = testString.chars();

        Stream<Character> characterStream = testString.chars()
                .mapToObj(c -> (char) c);

        characterStream.forEach(System.out::print);
        intStream.forEach(i-> System.out.print((char)i));
    }

    @Test
    public void givenTwoCollections_whenStreamedInParallel_thenCheckOutputDifferent() {
        List<String> list = Arrays.asList("B", "A", "C", "D", "F");
        Set<String> set = new TreeSet<>(list);

        Object[] listOutput = list.stream().parallel().toArray();
        Object[] setOutput = set.stream().parallel().toArray();

        assertEquals("[B, A, C, D, F]", Arrays.toString(listOutput));
        assertEquals("[A, B, C, D, F]", Arrays.toString(setOutput));
    }

    @Test
    public void givenUnsortedStreamInput_whenStreamSorted_thenCheckOrderChanged() {
        List<Integer> list = Arrays.asList(-3, 10, -4, 1, 3);

        Object[] listOutput = list.stream().toArray();
        Object[] listOutputSorted = list.stream().sorted().toArray();

        assertEquals("[-3, 10, -4, 1, 3]", Arrays.toString(listOutput));
        assertEquals("[-4, -3, 1, 3, 10]", Arrays.toString(listOutputSorted));
    }


    class Node {
        private int ind;
        private int val;

        private Node(int ind, int val) {
            this.ind = ind;
            this.val = val;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node)) return false;

            Node node = (Node) o;

            return ind == node.ind && val == node.val;
        }

        int getVal() {
            return val;
        }
    }

}
