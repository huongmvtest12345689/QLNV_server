package co.jp.api.service;

import co.jp.api.model.EmailDTO;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

public interface ThymeleafApiService {
    static TemplateEngine emailTemplateEngine() {
        return null;
    }

    static ResourceBundleMessageSource emailMessageSource() {
        return null;
    }

    static ITemplateResolver htmlTemplateResolver() {
        return null;
    }

    String getContent(EmailDTO emailDto);
}
