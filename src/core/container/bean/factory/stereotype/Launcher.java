package core.container.bean.factory.stereotype;

import core.container.constant.LaunchType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Launcher {
    LaunchType launchType() default LaunchType.CONSOLE;
}
