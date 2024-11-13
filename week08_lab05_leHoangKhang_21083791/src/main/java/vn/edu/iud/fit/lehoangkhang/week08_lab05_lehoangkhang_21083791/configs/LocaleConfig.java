package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import java.util.Locale;

@Configuration
public class LocaleConfig {

    @Bean
    public LocaleResolver localeResolver() {
        // Thiết lập Locale mặc định là tiếng Việt tại Việt Nam
        return new FixedLocaleResolver(new Locale("vi", "VN"));
    }
}
