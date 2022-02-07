package com.example.client.clientservice.Filter;

import com.example.client.clientservice.Entity.Client;
import com.example.client.clientservice.Exception.JwtException;
import com.example.client.clientservice.Repository.ClientRepositry;
import com.example.client.clientservice.Utility.JwtUtility;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final ClientRepositry clientRepositry;

    private final JwtUtility jwtUtility;

    private final HandlerExceptionResolver resolver;

    @Autowired
    public JwtFilter(ClientRepositry agentRepository, JwtUtility jwtUtility, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.clientRepositry = agentRepository;
        this.jwtUtility = jwtUtility;
        this.resolver = resolver;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if(Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }

        String token = authorizationHeader.replace("Bearer ", "");
        try {

            Integer agentid = Integer.parseInt(jwtUtility.extractSubject(token));

            Client client = clientRepositry.findById(agentid)
                    .orElseThrow(JwtException::new);

            if (!jwtUtility.validateToken(token,client)) {
                throw new JwtException();
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    client,
                    null,
                    client.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            resolver.resolveException(request, response, null, new JwtException());
        }
    }
}
