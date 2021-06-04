package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import principal.CustomUtilisateurDetailsService;

@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	

	@Autowired
	private CustomUtilisateurDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.err.println("auth " + auth);
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
		System.out.println(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("config");
		http.csrf().disable().authorizeRequests().antMatchers("/admin/**").hasAnyRole("ROLE_FORMATEUR")
				.antMatchers("/webjars/**", "/static/**", "/peritable/**", "/public/**", "/assets/**", "/css/**",
						"/public/connexion/**", "/inscription/**", "/verification-code/**", "/images/**", "/admin/**",
						"/protected/**", "/fontawesome/**", "/logout")
				.permitAll().anyRequest().authenticated().and().formLogin().loginPage("/public/connexion")
				.defaultSuccessUrl("/protected/home").usernameParameter("email").passwordParameter("password").and()
				.logout().logoutSuccessUrl("/public/connexion");

	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
