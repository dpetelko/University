package com.petelko.university.config.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@PropertySource("classpath:swagger.properties")
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfigProperties {

    @Value("${description.field.response.message.200}")
    private String message200;
    @Value("${description.field.response.message.201}")
    private String message201;
    @Value("${description.field.response.message.400}")
    private String message400;
    @Value("${description.field.response.message.404}")
    private String message404;
    @Value("${description.field.response.message.500}")
    private String message500;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.petelko.university.controller.rest"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .produces(getProduces())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, getCustomizedGetResponseMessages())
                .globalResponseMessage(RequestMethod.POST, getCustomizedPostResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, getCustomizedPutResponseMessages());
    }

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder
                .builder()
                .operationsSorter(OperationsSorter.ALPHA)
                .tagsSorter(TagsSorter.ALPHA)
                .defaultModelRendering(ModelRendering.MODEL)
                .displayRequestDuration(true)
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("University REST API")
                .description("Written in sweat and blood by Petelko Dmitry")
                .version("2.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
                .contact(new Contact("Dmitry Petelko", "", "dpetelko@gmail.com"))
                .build();
    }


    private List<ResponseMessage> getCustomizedGetResponseMessages() {
        List<ResponseMessage> responseMessages = new ArrayList<>();
        responseMessages.add(new ResponseMessageBuilder().code(200).message(message200).build());
        responseMessages.add(new ResponseMessageBuilder().code(404).message(message404).build());
        responseMessages.add(new ResponseMessageBuilder().code(500).message(message500).build());
        return responseMessages;
    }

    private List<ResponseMessage> getCustomizedPostResponseMessages() {
        List<ResponseMessage> responseMessages = new ArrayList<>();
        responseMessages.add(new ResponseMessageBuilder().code(201).message(message201).build());
        responseMessages.add(new ResponseMessageBuilder().code(400).message(message400).build());
        responseMessages.add(new ResponseMessageBuilder().code(500).message(message500).build());
        return responseMessages;
    }

    private List<ResponseMessage> getCustomizedPutResponseMessages() {
        List<ResponseMessage> responseMessages = new ArrayList<>();
        responseMessages.add(new ResponseMessageBuilder().code(200).message(message200).build());
        responseMessages.add(new ResponseMessageBuilder().code(400).message(message400).build());
        responseMessages.add(new ResponseMessageBuilder().code(404).message(message404).build());
        responseMessages.add(new ResponseMessageBuilder().code(500).message(message500).build());
        return responseMessages;
    }

    private Set<String> getProduces() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json");
        return produces;
    }
}
