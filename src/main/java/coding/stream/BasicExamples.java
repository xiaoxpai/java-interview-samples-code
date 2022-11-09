package coding.stream;


import org.junit.Test;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BasicExamples {


    @Test
    public void test_mapfilter() {
        Stream.of(1,2,3,4,5,6)
                .map(Object::toString)
                .map(x -> x + x)
                .map(x -> x + x + x)
//                .map(x -> Integer.parseInt(x))
                .map(Integer::parseInt)
                .forEach(System.out::println);
        // function reference operator
        // lambda expression
    }

    
   /**
     *   <R> Stream<R> map(Function<? super T, ? extends R> mapper);
     *      Stream<T> filter(Predicate<? super T> predicate);
     *      Optional<T> reduce(BinaryOperator<T> accumulator);
     *           - 把BinaryOperator 把lambda表达式 --> interface 用的
     *           -   R apply(T t, U u); //两个一样的类型 T、U 产生一个一样类型的结果R
     *        @HotSpotIntrinsicCandidate
     *        public static int max(int a, int b) { //max
     *             return (a >= b) ? a : b;
     *        }
     *
     *            提供流计算：
     *             有状态： sorted(把乱序，顺序输出)、skip、limit、任何触发状态变化的程序
     *             无状态： map（一个值映射成另一个值，）、reduce
     *             副作用：纯函数(pure function)、非纯函数;多写纯函数
     */
    @Test
    public void test_mapfilterreduce(){
        //var result = Stream.of(1,2,3,4,5,)
        var result = IntStream.of()
                    .map(x -> x * x)
                    .filter(x -> x < 20)
                    .reduce( Math::max);
//                    .orElse(0);
                    //.reduce(0, Integer::min);
        System.out.println(result.isPresent());
        System.out.println(result.orElseGet(() -> 0));
    }

    @Test
    public void test_mutation() {
        var stream = Stream.of(1,3,5,2,3,4,5,6).sorted();
        stream.forEach(System.out::println);
    }

  /**
     * 字符串按照字母去重，留下字母，myine
     *
     *
     * <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);
     *  -  Function<? super T  入参
     *  -  ? extends Stream<? extends R>  出参
     *  可以看出是一个   T    -> Stream<R> 的一个映射
     *  也就是        String -> Stream<R> 的一个映射
     */
    @Test
    public void test_flatMap(){
        // String -> Stream<R>
        var set = Stream.of("My", "Mine")
                .flatMap(str -> str.chars().mapToObj(i -> (char)i)) //int流->mapToObj流->转字符流
                .collect(Collectors.toSet());
        System.out.println(new ArrayList<>(set));
//        System.out.println(set.stream().collect(Collectors.toList()));
    }

    @Test
    public void test_parallel() throws ExecutionException, InterruptedException {

        var r = new Random();
        var list = IntStream.range(0, 1_000_000)
                .map(x -> r.nextInt(10_000_000))
                .boxed()
                .collect(Collectors.toList());

        var t0 = System.currentTimeMillis();
        System.out.println(list.stream().max(Comparator.comparingInt(x -> x)));
        System.out.println("time:" + (System.currentTimeMillis() - t0));

        // 1000
        var pool = new ForkJoinPool(2);
        var t1 = System.currentTimeMillis();
        var max = pool.submit(() -> list.parallelStream().max(Comparator.naturalOrder())).get();

//        list.stream().parallel().max((a , b) -> a -b);

        // 15
        // Spliter 1024 -> Thread0 1024 -> Thread1

        System.out.println("time:" + (System.currentTimeMillis() - t1) + ",max:" + max);
    }




}
