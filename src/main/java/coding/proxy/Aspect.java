package coding.proxy;

import basic.monad.Try;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.stream.Collectors;

public interface Aspect {
    void before();
    void after();

    /**
     * static : 像静态方法调用一样，
     * @Param Class<T> <T>：不知道传的是什么类型的，
     * @Param String ...  可变参数
     * 
     * 
     * 
     */
    static <T> T getProxy(Class<T> cls, String ... aspects) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {


        var aspectInsts = Arrays.stream(aspects).map(name -> Try.ofFailable(() -> {
            //查找类的实例
            var clazz = Class.forName(name);
            return (Aspect)clazz.getConstructor().newInstance();
        }))
                .filter(aspect -> aspect.isSuccess()) //是否成功拿到了类的实例
                .collect(Collectors.toList());//收集起来类的实例
        
        //上面获取到了类的实例，这里创建实例
        var inst = cls.getConstructor().newInstance();
        return (T) Proxy.newProxyInstance(
                cls.getClassLoader(),
                cls.getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable { //method 接口的方法名 args 接口函数的参数
                        for(var aspect : aspectInsts) {
                            aspect.get().before();
                        }
                        var result = method.invoke(inst);
                        for(var aspect : aspectInsts) {
                            aspect.get().after();
                        }
                        return result;
                    }
                }
        );
    }
}
