package mg.douane.intervention;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// @Configuration
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(InteventionApplication.class);
    }

    /*
     * @Bean
     *
     * @Order(Ordered.HIGHEST_PRECEDENCE) CharacterEncodingFilter
     * characterEncodingFilter() { CharacterEncodingFilter filter = new
     * CharacterEncodingFilter(); filter.setEncoding("UTF-8");
     * filter.setForceEncoding(true); return filter; }
     */

}
