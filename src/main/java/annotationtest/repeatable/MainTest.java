package annotationtest.repeatable;

import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

/**
 * @author f.s.
 * @date 2019/2/8
 */
@Slf4j
public class MainTest {

    public static void main(String[] args) {

        if (ConcretePerson.class.isAnnotationPresent(Persons.class)) {
            Annotation[] annotations = ConcretePerson.class.getAnnotations();
            log.info("annotations.length = {}", annotations.length);
            Persons p = ConcretePerson.class.getAnnotation(Persons.class);
            for (Person t : p.value()) {
                log.info("role = {}", t.role());
            }
        }
    }
}
