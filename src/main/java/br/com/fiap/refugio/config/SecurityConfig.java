package br.com.fiap.refugio.config;
import br.com.fiap.refugio.auth.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;
import org.springframework.web.filter.CorsFilter;
import java.util.*;
@Configuration
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.SERVLET)
public class SecurityConfig {
    @Bean
    SecurityFilterChain seguranca(HttpSecurity http, JwtFilter jwt, SecurityErrors errors, CorsFilter cors) throws Exception {
        return http.csrf(c->c.disable()).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .requestCache(c->c.disable()).httpBasic(c->c.disable()).formLogin(c->c.disable()).logout(c->c.disable())
                .exceptionHandling(e->e.authenticationEntryPoint((r,s,x)->errors.escrever(r,s,401))
                        .accessDeniedHandler((r,s,x)->errors.escrever(r,s,403)))
                .authorizeHttpRequests(a->a
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/auth/cadastro","/api/auth/login").permitAll()
                        .requestMatchers("/swagger-ui.html","/swagger-ui/**","/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/actuator/health","/actuator/info").permitAll()
                        .requestMatchers("/actuator/metrics","/actuator/metrics/**").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.GET,"/api/pontos","/api/pontos/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/ocorrencias/minhas").hasAnyRole("CIDADAO","GESTOR")
                        .requestMatchers(HttpMethod.POST,"/api/ocorrencias").hasAnyRole("CIDADAO","GESTOR")
                        .requestMatchers("/api/pontos","/api/pontos/**","/api/recursos","/api/recursos/**","/api/medicoes","/api/ocorrencias/*/status").hasRole("GESTOR")
                        .anyRequest().denyAll())
                .addFilterBefore(cors, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwt, UsernamePasswordAuthenticationFilter.class).build();
    }
    @Bean
    CorsFilter corsFilter(@Value("${app.cors.allowed-origins}") String origins, SecurityErrors errors) {
        var configuration = new CorsConfiguration();
        var allowed = Arrays.stream(origins.split(",")).map(String::trim).filter(s->!s.isEmpty()).toList();
        if (allowed.stream().anyMatch(s->s.contains("*"))) throw new IllegalStateException("CORS exige origens explícitas");
        configuration.setAllowedOrigins(allowed);
        configuration.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type","Accept"));
        configuration.setAllowCredentials(false);
        var source = new UrlBasedCorsConfigurationSource(); source.registerCorsConfiguration("/**", configuration);
        var filter = new CorsFilter(source);
        filter.setCorsProcessor((config,request,response)-> {
            var processor = new DefaultCorsProcessor() {
                @Override protected void rejectRequest(org.springframework.http.server.ServerHttpResponse out) throws java.io.IOException {
                    errors.escrever(request,response,403);
                }
            };
            return processor.processRequest(config,request,response);
        });
        return filter;
    }
}
