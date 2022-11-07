package coding.collection;

import java.util.*;

/**
 * 随机序列生成器
 */
public class RandomStringGenerator<T> implements Iterable<T> {

    private final List<T> list;


    private void swap(int[] a, int i, int i1) {
    }

    public RandomStringGenerator(List<T> list) {
        this.list = list;


    }

    @Override
    public Iterator<T> iterator() {

        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                //从List<T> list 选一个词语
                return list.get((int) (list.size() * Math.random()));
            }
        };
    }


    public static void main(String[] argv) {
        var list = Arrays.asList("List", "Tree", "Array");
        //构造一个词语产生器
        var gen = new RandomStringGenerator<String>(list);

//        for(var s: gen) {
//            System.out.println(s);
//        }

        //获取迭代器（操作迭代器）
//        var it = gen.iterator();
//        for(int i = 0; i < 100; i++) {
//            System.out.println(it.next());
//        }




    }

}
