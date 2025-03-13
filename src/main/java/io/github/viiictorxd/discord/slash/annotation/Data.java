package io.github.viiictorxd.discord.slash.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
public @interface Data {

  String name();

  String description() default "Sem descrição";

  boolean required() default true;
}
