package com.urise.webapp.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomInteger {

    public static void main(String[] args) {
        int[] values = new Random().ints(3, 1, 9).toArray();
        System.out.println("Входной массив чисел для обоих заданий: " + Arrays.toString(values));
        //////////  1st homeWork  ///////////////////////
        System.out.println(minValue(values));

        /////////   2nd homeWork  ///////////////////////
        List<Integer> list = new ArrayList<>();
        Arrays.stream(values).forEach(list::add);
        // List<Integer> list = Arrays.stream(values).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println(oddOrEven(list).toString());
    }

    static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (result, x) -> 10 * result + x);
        /*  Function<int[], Integer> valueConverter = x -> {
            int result = 0;
            for (int j : x) {
                result *= 10;
                result += j;
            }
            return result;
        };*/
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        boolean remainsZero = (integers.stream().reduce(0, Integer::sum)) % 2 == 0;
        return integers.stream().filter(x -> remainsZero ? (x % 2) > 0 : x % 2 == 0).collect(Collectors.toList());

       /* List<Integer> finalList;
        Function<List<Integer>, List<Integer>> sumFounder = list -> {
            int result = 0;
            for (int elem : list) {
                result += elem;
            }
            if (result % 2 == 0) {
                System.out.println("Сумма чисел четная, оставляем нечетные числа");
                return doFilter(list, x -> x % 2 != 0);
            } else {
                System.out.println("Сумма чисел нечетная, оставляем четные числа");
                return doFilter(list, x -> x % 2 == 0);
            }
        };
        finalList = sumFounder.apply(integers);*/
    }

    /*static List<Integer> doFilter(List<Integer> list, Predicate<? super Integer> predicate) {
        return list.stream().filter(predicate).collect(Collectors.toList());
    }*/
}