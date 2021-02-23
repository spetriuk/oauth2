package com.petriuk.configuration;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(final ServletContext sc) throws ServletException {
        var ctx = new AnnotationConfigWebApplicationContext();
        ctx.setServletContext(sc);
        ctx.scan("com.petriuk");
        sc.addListener(new ContextLoaderListener(ctx));
        sc.setInitParameter("contextConfigLocation", "<NONE>");

        sc.addFilter("securityFilter",
            new DelegatingFilterProxy("springSecurityFilterChain"))
            .addMappingForUrlPatterns(null, false, "/*");
    }




}
