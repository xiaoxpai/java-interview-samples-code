package coding.stream;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 *  Monad设计模式：
 *      目标是：构造流计算
 *      三个特点：
 *      1.一个泛型的构造函数 。比如Optional<T>
 *      2.不改变泛型的运算操作，内容是非泛型计算。例如：Optional<R>map(T->R)
 *      3.泛型类型不变。比如可以是Optional<Integer>到Optional<String>，但还是Optional<T>类型
 */
public class Event<T>{

    T data;
    public Event(T data){
        this.data = data;
    }

    static class EventData {
        Integer id;
        String msg;
        public EventData(Integer id, String msg) {
            this.id = id;
            this.msg = msg;
        }

        @Override
        public String toString() {
            return "EventData{" +
                    "id=" + id +
                    ", msg='" + msg + '\'' +
                    '}';
        }
    }
    
   /**
     *
     * 将整数转成EventData 对象
     *
     */
    static class Transforms {
        static EventData transform(Integer id) {
            switch(id) {
                case 0:
                    return new EventData(id, "Start");
                case 1:
                    return new EventData(id, "Running");
                case 2:
                    return new EventData(id, "Done");
                case 3:
                    return new EventData(id, "Fail");
                default:
                    return new EventData(id, "Error");
            }
        }
    }

    
    @FunctionalInterface
    interface FN<A, B> {
        B apply(A a);
    }

    <B> Event<?> map(FN<T, B> f) {
        return new Event<>(f.apply(this.data));
    }

    public static void main(String[] args) {
        Stream<Event<Integer>> s = Stream.of(
                new Event<>(1),
                new Event<>(2),
                new Event<>(0),
                new Event<>(10)
        );

        s.map(event -> event.map(Transforms::transform))
                .forEach(e ->
                    System.out.println(e.data)
                );

    }
}
