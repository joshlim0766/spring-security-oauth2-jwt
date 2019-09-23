package josh0766.oauth2jwtexample.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Value("${resource.id:resource-server}")
    private String resourceId;

    @Value("${security.oauth2.resource.jwt.key-value}")
    private String authorizationServerPublicKey;

    @Bean(name = "resourceServerTokenStore")
    public TokenStore tokenStore () {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean(name = "resourceServerAccessTokenConverter")
    public JwtAccessTokenConverter accessTokenConverter () {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

        converter.setVerifierKey(authorizationServerPublicKey);

        return converter;
    }

    @Bean(name = "resourceServerTokenService")
    public DefaultTokenServices tokenService () {
        DefaultTokenServices defaultTokenService = new DefaultTokenServices();

        defaultTokenService.setTokenStore(tokenStore());
        defaultTokenService.setSupportRefreshToken(true);

        return defaultTokenService;
    }

    @Override
    public void configure (HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/api/v1/login").permitAll()
                    .antMatchers("/api/v1/signup").permitAll()
                    .antMatchers("/api/v1/test").hasAnyRole("ADMIN", "USER");
    }

    @Override
    public void configure (ResourceServerSecurityConfigurer configurer) throws Exception {
        configurer.resourceId(resourceId);
    }
}
