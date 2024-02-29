package kth.iv1201.gohire.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * Configures the authentication entry point
 */
@Component
public class DelegatedAuthenticationEntryPoint implements AuthenticationEntryPoint {


    private final HandlerExceptionResolver resolver;

    /**
     * Creates a new instance of DelegatedAuthenticationEntryPoint
     * @param resolver the HandlerExceptionResolver
     */
    @Autowired
    public DelegatedAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    /**
     * Configures errors to be resolved by the ErrorHandler
     * @param request the HTTP request
     * @param response the HTTP response
     * @param authException the exception that should be handled by the ErrorHandler
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) {
        resolver.resolveException(request, response, null, authException);
    }
}