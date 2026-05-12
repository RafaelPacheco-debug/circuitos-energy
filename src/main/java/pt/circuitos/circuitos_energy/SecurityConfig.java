package pt.circuitos.circuitos_energy;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CsrfException;

@Configuration
public class SecurityConfig {

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService userDetailsService)
                        throws Exception {
                LoginUrlAuthenticationEntryPoint loginEntryPoint = new LoginUrlAuthenticationEntryPoint("/login");
                AccessDeniedHandlerImpl accessDeniedHandler = new AccessDeniedHandlerImpl();

                http
                                .exceptionHandling(exception -> exception
                                                .accessDeniedHandler((request, response, accessDeniedException) -> {
                                                        if (accessDeniedException instanceof CsrfException
                                                                        || isSolarQuoteSubmit(request)) {
                                                                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                                                return;
                                                        }
                                                        accessDeniedHandler.handle(request, response,
                                                                        accessDeniedException);
                                                })
                                                .authenticationEntryPoint((request, response, authException) -> {
                                                        if (authException.getCause() instanceof CsrfException
                                                                        || isSolarQuoteSubmit(request)) {
                                                                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                                                return;
                                                        }
                                                        loginEntryPoint.commence(request, response, authException);
                                                }))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/", "/sobre", "/servicos", "/servicos/**",
                                                                "/produtos", "/produtos/**",
                                                                "/orcamento/paineis-solares/**", "/contactos",
                                                                "/agendamento/**", "/ferramentas/**", "/login",
                                                                "/registro", "/css/**", "/js/**", "/images/**",
                                                                "/webjars/**", "/favicon.ico")
                                                .permitAll()
                                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                                .anyRequest().authenticated())
                                .userDetailsService(userDetailsService)
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .defaultSuccessUrl("/", true)
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutSuccessUrl("/")
                                                .permitAll());

                return http.build();
        }

        private boolean isSolarQuoteSubmit(jakarta.servlet.http.HttpServletRequest request) {
                return "POST".equals(request.getMethod())
                                && "/orcamento/paineis-solares/submeter".equals(request.getServletPath());
        }
}
