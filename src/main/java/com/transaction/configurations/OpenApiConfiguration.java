package com.transaction.configurations;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.RedirectView;


/**
 * @author Eleke Iheanyi
 * email: iheanyi.eleke@gmail.com
 * May 2024
 **/

@Configuration
public class OpenApiConfiguration {

    public static final String SEC_SCHEME_NAME = "ApiKey";

    static {
        SpringDocUtils.getConfig().addResponseTypeToIgnore(RedirectView.class);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(SEC_SCHEME_NAME,
                                new SecurityScheme().type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .name("Authorization"))
                )
                .info(new Info().title("STL API").version("1.0")
                        .contact(contact())
                        .description(
                                "The STL RESTful service using springdoc-openapi"));
    }

    private Contact contact() {
        Contact contact = new Contact();
        contact.setName("Oasis Bot");
        contact.setEmail("ieleke@oasismgt.net");
        return contact;
    }

    @Bean
    public GroupedOpenApi stlAppOpenApi() {
        String[] paths = {
                "/**"};
        return GroupedOpenApi.builder().group("stlAppOpenApi").pathsToMatch(paths).build();
    }

}
