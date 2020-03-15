package ru.vood.property.server.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@Column
public @interface Pk {

    @AliasFor(annotation = Column.class)
    String name() default "";

    @AliasFor(annotation = Column.class)
    int colId() default 0;
}
