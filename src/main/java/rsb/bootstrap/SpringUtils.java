package rsb.bootstrap;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.StringUtils;

public class SpringUtils {

    public static ConfigurableApplicationContext run(Class<?> source, String profile) {

        var context = new AnnotationConfigApplicationContext();
        if (StringUtils.hasText(profile)) {
            context.getEnvironment().setActiveProfiles(profile);
        }
        context.register(source);
        context.refresh();
        context.start();
        return context;
    }
}
