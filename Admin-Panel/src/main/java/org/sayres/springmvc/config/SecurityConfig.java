package org.sayres.springmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * @author Aliaksei Karabelnikau
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .authorizeRequests()
                .antMatchers("/users/**", "/games/**", "/detailed_info/**", "/participation/**").hasRole("ADMIN")
                .antMatchers("/**", "/logout").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/", true)
                .permitAll().and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true);
    }

    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:C:\\\\Users\\\\Aliaksei\\\\Desktop\\\\admin-panel\\\\Admin-Panel\\\\src\\\\main\\\\java\\\\org\\\\sayres\\\\springmvc\\\\dao\\\\identifier.sqlite");
        return dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource())
                .usersByUsernameQuery("select player_name, password, 1 from Players where player_name=?")
                .authoritiesByUsernameQuery("select player_name, 'ROLE_' || role from Players where player_name=?")
                .passwordEncoder(noOpPasswordEncoder());
    }

    @SuppressWarnings("deprecation")
    @Bean
    public PasswordEncoder noOpPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}


