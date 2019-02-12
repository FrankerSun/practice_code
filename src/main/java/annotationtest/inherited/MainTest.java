package annotationtest.inherited;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author f.s.
 * @date 2019/2/8
 * @link <a href="https://blog.csdn.net/zmx729618/article/details/78060333">参考</a>
 */
@Slf4j
public class MainTest {

    public static void main(String[] args) {

        Class<SubClass> subClassClass = SubClass.class;
        log.info("============================Field===========================");
        // 自身的公共字段 + 父类的公共字段
        log.info(Arrays.toString(subClassClass.getFields()));
        // 自身的所有字段
        log.info(Arrays.toString(subClassClass.getDeclaredFields()));


        log.info("============================Method===========================");
        // 自身的公共方法 + 父类的公共方法[也包括Object的]
        log.info(Arrays.toString(subClassClass.getMethods()));
        // 自身的所有方法
        log.info(Arrays.toString(subClassClass.getDeclaredMethods()));


        log.info("============================Constructor===========================");
        // 同上
        log.info(Arrays.toString(subClassClass.getConstructors()));
        log.info(Arrays.toString(subClassClass.getDeclaredConstructors()));


        log.info("============================AnnotatedElement===========================");
        // NoInheritedTest --> output: true
        log.info("{}", subClassClass.isAnnotationPresent(NoInheritedTest.class));
        // NoInheritedTest --> output: @annotationtest.inherited.NoInheritedTest(name=)
        log.info("{}", subClassClass.getAnnotation(NoInheritedTest.class));
        // 继承的InheritedTest + NoInheritedTest
        log.info(Arrays.toString(subClassClass.getAnnotations()));
        // 自身的注解，不包含继承的
        log.info(Arrays.toString(subClassClass.getDeclaredAnnotations()));
    }
}