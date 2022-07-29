package avada.media.usainua_api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.regex.Pattern;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "security.jwt")
@Data
public class AppConst {

    private String secret;

    private final String[] SWAGGER_PATHS_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    private final Pattern EMAIL_REGEX_RFC822 =
            Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
                    Pattern.CASE_INSENSITIVE);

    public static final Double EXCHANGE_RATE = 34.5d;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";
    public static final Double COMMISSION_RATE = 0.05;
    public static final Double INSURANCE_RATE = 0.1;
    public static final Double CLEARANCE_PER_PRODUCT = 0.99;
    public static final Double SEA_DELIVERY_RATE = 1.98;
    public static final Double AIR_DELIVERY_RATE = 2.98;

}
