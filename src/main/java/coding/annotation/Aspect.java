package coding.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//@Deprecated 代表用户不要去用它了
@Retention(RetentionPolicy.RUNTIME) //注解代表存活时间(编译，运行时，只在源代码中)
@Target(ElementType.TYPE) //这个注解表示可以用于什么类型上：类型本身
public @interface Aspect {
    public Class type();
}
