package io.github.viiictorxd.discord.slash.annotation;

import net.dv8tion.jda.api.Permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Slash {

  String name();

  String description() default "Sem descrição";

  String[] alias() default { };

  Permission permission() default Permission.UNKNOWN;
}
