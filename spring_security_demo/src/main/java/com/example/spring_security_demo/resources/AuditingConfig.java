package com.example.spring_security_demo.resources;

import com.example.spring_security_demo.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuditingConfig implements AuditorAware<String> {
    private final TokenService tokenService;
    private final HttpServletRequest httpServletRequest;


    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            return tokenService.getUsername(httpServletRequest) == null ?
                    Optional.empty() : Optional.of(tokenService.getUsername(httpServletRequest));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
