package org.tx.anno;

import java.lang.annotation.*;

/**
 * @author WangChao
 * @create 2020/11/27 7:29
 */
@Target({ElementType.METHOD, ElementType.TYPE}) //标记方法和类,接口使用
@Retention(RetentionPolicy.RUNTIME)
@Inherited      //类继承关系中，子类会继承父类使用的注解中被@Inherited修饰的注解
@Documented  //给标记注解生成api文档
public @interface TxTransaction {
}
