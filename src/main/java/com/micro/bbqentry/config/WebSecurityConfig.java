package com.micro.bbqentry.config;

import com.micro.bbqentry.security.JwtAuthenticationFilter;
import com.micro.bbqentry.security.UsernamePasswordLoginFilter;
import com.micro.bbqentry.security.SmsCodeLoginFilter;
import com.micro.bbqentry.security.provider.SmsCodeAuthenticationProvider;
import com.micro.bbqentry.security.provider.UsernamePasswordAuthenticationProvider;
import com.micro.bbqentry.security.service.LoginService;
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
    @Autowired
    private LoginService loginService;

    @Bean
    public UsernamePasswordLoginFilter getUsrPwdLoginFilter() throws Exception {
        return new UsernamePasswordLoginFilter(authenticationManager());
    }

    @Bean
    public SmsCodeLoginFilter getSmsCodeLoginFilter() throws Exception {
        return new SmsCodeLoginFilter(authenticationManager());
    }

    @Bean
    public JwtAuthenticationFilter getJwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(authenticationManager());
    }

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 需要放行的URL
     */
    private static final String[] AUTH_WHITELIST = {
            // -- register url

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
            "/**",
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
                .permitAll()
                .and()
                .addFilter(getUsrPwdLoginFilter())
                .addFilterAfter(getSmsCodeLoginFilter(), UsernamePasswordLoginFilter.class)
                .addFilter(getJwtAuthenticationFilter())

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
        auth.authenticationProvider(new UsernamePasswordAuthenticationProvider(loginService));
        auth.authenticationProvider(new SmsCodeAuthenticationProvider(loginService));
    }


}
