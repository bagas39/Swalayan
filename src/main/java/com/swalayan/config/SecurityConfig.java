package com.swalayan.config;

import com.swalayan.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(auth -> auth

                .requestMatchers("/login", "/css/**", "/js/**", "/images/**", "/api/login", "/api/me").permitAll()

                .requestMatchers("/").hasAuthority("Kasir")
                .requestMatchers("/manajemen_stok").hasAnyAuthority("Kasir", "Gudang", "Supervisor") 


                .requestMatchers("/stok_opname").hasAnyAuthority("Gudang", "Supervisor")
                .requestMatchers("/prediksi_stok").hasAnyAuthority("Gudang", "Supervisor")
                .requestMatchers("/transaksi_pembelian").hasAnyAuthority("Gudang", "Supervisor")

                .requestMatchers("/transaksi_penjualan").hasAuthority("Supervisor")

                .requestMatchers("/laporan_keuangan").hasAuthority("Owner")
                .requestMatchers("/manajemen_pengguna").hasAnyAuthority("Owner", "Supervisor")

                .requestMatchers("/api/users/**").hasAnyAuthority("Owner", "Supervisor")

                .requestMatchers("/api/laporan/**").hasAuthority("Owner")

                .requestMatchers("/api/opname/**").hasAnyAuthority("Gudang", "Supervisor")
                .requestMatchers("/api/prediksi/**").hasAnyAuthority("Gudang", "Supervisor")
                .requestMatchers("/api/pembelian/**").hasAnyAuthority("Gudang", "Supervisor")

                .requestMatchers("/api/transaksi_penjualan/**").hasAuthority("Supervisor")
                .requestMatchers("/api/transaksi_detail/**").hasAuthority("Supervisor")

                .requestMatchers(HttpMethod.PUT, "/api/transaksi/**").hasAuthority("Supervisor")

                .requestMatchers(HttpMethod.POST, "/api/transaksi").hasAuthority("Kasir")

                .requestMatchers(HttpMethod.GET, "/api/barang/**").hasAnyAuthority("Kasir", "Gudang", "Supervisor", "Owner")
                .requestMatchers(HttpMethod.GET, "/api/manajemen_stok/**").hasAnyAuthority("Kasir", "Gudang", "Supervisor", "Owner")

                .requestMatchers("/api/barang/**").hasAnyAuthority("Gudang", "Supervisor")

                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/api/login")
                .successHandler(mySuccessHandler())
                .failureHandler((request, response, exception) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"success\": false, \"message\": \"Username atau Password salah!\"}");
                })
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler mySuccessHandler() {
        return (request, response, authentication) -> {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            
            String role = authentication.getAuthorities().iterator().next().getAuthority();

            response.getWriter().write("{\"success\": true, \"role\": \"" + role + "\"}");
        };
    }
}