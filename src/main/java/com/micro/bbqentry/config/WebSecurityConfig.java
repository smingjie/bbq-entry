package com.micro.bbqentry.config;

import com.micro.bbqentry.security.JwtAuthenticationFilter;
import com.micro.bbqentry.security.JwtLoginFilter;
import com.micro.bbqentry.security.under.MyCustomAuthenticationProvider;
import com.micro.bbqentry.security.under.MyCustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * web spring security configuration
 *
 * @author jockeys
 * @since 2020/2/5
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 为spring security 框架定制的user service
     */
    @Autowired
    private MyCustomUserService userService;

    /**
     * BCrypt密码编码器，用来加密密码的
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 需要放行的URL
     */
    private static final String[] AUTH_WHITELIST = {
            // -- register url
            //"/users/signup",
            //"/users/addTask",

            // -- swagger ui
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- 测试使用,暂时放行
            "/dicts/**",
            "/test/**",
            "/captcha/**",
            "/manage/**"
            // -- other public endpoints of your API may be appended to this array
    };

    /**
     * 设置 HTTP 验证规则
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()  // 所有请求需要身份认证
                .and()
                .exceptionHandling()
//                .authenticationEntryPoint(
//                        new Http401AuthenticationEntryPoint("Basic realm=\"MyApp\""))
//                .and()
//                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler) // 自定义访问失败处理器
                .and()
                .formLogin()
                .permitAll().and()
                .addFilter(new JwtLoginFilter(authenticationManager()))
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .logout() // 默认注销行为为logout，可以通过下面的方式来修改
                .logoutUrl("/logout")
                // 设置注销成功后跳转页面，默认是跳转到登录页面
                .logoutSuccessUrl("/login")
                .permitAll();
    }

    /**
     * 该方法是登录的时候会进入
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        // 使用自定义身份验证组件
        auth.authenticationProvider(new MyCustomAuthenticationProvider(userService, bCryptPasswordEncoder()));
        // 设置UserDetailsService
        // auth.userDetailsService(myUserDetailsService)
        // 使用BCrypt进行密码的hash
        // .passwordEncoder(bCryptPasswordEncoder());
    }


}
