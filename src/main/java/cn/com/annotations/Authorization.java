package cn.com.annotations;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Authorization {
    @AliasFor("url")
    String[] value() default {};

    @AliasFor("value")
    String[] url() default {};

    RequestMethod[] method() default {};

    /**
     * 权限信息，支持：c，u，r，d
     * @return
     */
    String authority() default "";
}
