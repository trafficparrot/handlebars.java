package com.github.jknack.handlebars.springmvc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

@Configuration
public class HandlebarsApp {

  @Bean
  public HandlebarsViewResolver viewResolver() {
    HandlebarsViewResolver viewResolver = new HandlebarsViewResolver();

    Helper<Object> helper = new Helper<Object>() {
      @Override
      public CharSequence apply(final Object context, final Options options) throws IOException {
        return "Spring Helper";
      }
    };
    viewResolver.registerHelper("spring", helper);
    viewResolver.registerHelpers(HandlebarsApp.class);
    Map<String, Helper<?>> helpers = new HashMap<String, Helper<?>>();
    helpers.put("setHelper", helper);
    viewResolver.setHelpers(helpers);
    // no cache for testing
    viewResolver.setCache(false);

    return viewResolver;
  }

  @Bean
  public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("messages");
    return messageSource;
  }

  @Bean
  public HandlebarsViewResolver viewResolverWithoutMessageHelper() {
    return new HandlebarsViewResolver().withoutMessageHelper();
  }

  public static CharSequence helperSource() {
    return "helper source!";
  }
}
