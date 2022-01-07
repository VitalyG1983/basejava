package com.urise.webapp.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RandomInteger {

    public static void main(String[] args) {
        int[] values = new Random().ints(9, 1, 9).toArray();
        System.out.println("Входной массив чисел для обоих заданий: "+ Arrays.toString(values));
     //////////  1st homeWork  ///////////////////////
        joinNumbers(values);

     /////////   2nd homeWork  ///////////////////////
        List<Integer> list = new ArrayList<>();
        Arrays.stream(values).forEach(list::add);
        List<Integer> lis = oddOrEven(list);
    }

    static void joinNumbers(int[] values) {
        Function<int[], Integer> valueConverter = x -> {
            int result = 0;
            for (int j : x) {
                result *= 10;
                result += j;
            }
            return result;
        };
        System.out.println(valueConverter.apply(Arrays.stream(values).sorted().distinct().toArray()));
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        List<Integer> finalList;
        Function<List<Integer>, List<Integer>> sumFounder = list -> {
            int result = 0;
            for (int elem : list) {
                result += elem;
            }
                if (result % 2 == 0) {
                System.out.println("Сумма чисел четная, убираем четные числа");
                return doFilter(list, x -> x % 2 != 0);
            } else {
                System.out.println("Сумма чисел нечетная, убираем нечетные числа");
                return doFilter(list, x -> x % 2 == 0);
            }
        };
        finalList = sumFounder.apply(integers);
        System.out.println(finalList);
        return finalList;
    }

    static List<Integer> doFilter(List<Integer> list, Predicate<? super Integer>  predicate ){
        return list.stream().filter(predicate).collect(Collectors.toList());
    }
}