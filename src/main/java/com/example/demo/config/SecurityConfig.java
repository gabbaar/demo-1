package com.example.demo.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;

// @EnableWebSecurity
// public class SecurityConfig extends WebSecurityConfigurerAdapter {

// 	// protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
// 	// 	auth.inMemoryAuthentication().withUser("user1").password(passwordEncoder().encode("user1Pass")).roles("USER")
// 	// 			.and().withUser("user2").password(passwordEncoder().encode("user2Pass")).roles("USER");
// 	// }

// 	// @Override
// 	// protected void configure(HttpSecurity http) throws Exception {
// 	// 	http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/login")
// 	// 			.defaultSuccessUrl("/home").permitAll().and().logout().logoutSuccessUrl("/login").and().csrf();
// 	// 	http.sessionManagement().maximumSessions(1).expiredUrl("/login");

// 	// }

// 	// @Bean
// 	// public PasswordEncoder passwordEncoder() {
// 	// 	return new BCryptPasswordEncoder();
// 	// }
// }
