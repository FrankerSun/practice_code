package annotationtest.repeatable;

import java.lang.annotation.Repeatable;

/**
 * @author f.s.
 * @date 2019/2/8
 */
@Repeatable(Persons.class)
public @interface Person {

    String role() default "人";
}

