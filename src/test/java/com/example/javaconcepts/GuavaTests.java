package com.example.javaconcepts;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GuavaTests {

    @Test
    public void whenFilterWithPredicateThenFiltered() {
        /*
        * Filtering list using Predicates(Guava)
        * */
        List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        List<String> result = names.stream().filter(Predicates.containsPattern("a")::apply).collect(Collectors.toList());
        assertThat(result, containsInAnyOrder("Jane", "Adam"));
    }

    @Test
    public void whenFilterWithMultiplePredicatesUsingGuavaCollections2ThenFiltered() {
        /*
        * Filtering with multiple predicates(Guava) using Guava Collections2
        * */
        List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        List<String> result = new ArrayList<>(Collections2
                .filter(names, Predicates
                        .or(Predicates.containsPattern("J"), Predicates
                                .not(Predicates.containsPattern("a")))));
        assertEquals(3, result.size());
        assertThat(result, containsInAnyOrder("John", "Jane", "Tom"));
    }

    @Test
    public void whenRemoveNullFromCollectionTheRemoved() {
        /*
        * We can clean up the null values from a collection by filtering it with Predicates.notNull() as in the following example:
        * */
        List<String> names =
                Lists.newArrayList("John", null, "Jane", null, "Adam", "Tom");
        List<String> result = new ArrayList<>(
                Collections2.filter(names, Predicates.notNull()));

        assertEquals(4, result.size());
        assertThat(result, containsInAnyOrder("John", "Jane", "Adam", "Tom"));
    }

    @Test
    public void whenCheckingIfAllElementsMatchAConditionThenCorrect() {
        List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");

        boolean result = names.stream().allMatch(Predicates.containsPattern("n|m")::apply);
        assertTrue(result);

        result = names.stream().allMatch(Predicates.containsPattern("a")::apply);
        assertFalse(result);
    }

    @Test
    public void whenFilteringAndTransformingCollection_thenCorrect() {
        Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return input.startsWith("A") || input.startsWith("T");
            }
        };

        Function<String, Integer> func = new Function<String,Integer>(){
            @Override
            public Integer apply(String input) {
                return input.length();
            }
        };

        List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        Collection<Integer> result = FluentIterable.from(names)
                .filter(predicate)
                .transform(func)
                .toList();

        assertEquals(2, result.size());
        assertThat(result, containsInAnyOrder(4, 3));
    }
}
