//package com.example.CAPSTONE.Security;
//
//import com.example.CAPSTONE.Exeptions.UnAuthorizedException;
//import com.example.CAPSTONE.Models.User;
//import com.example.CAPSTONE.Services.UserService;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//    @Component
//    public class JwtFilter extends OncePerRequestFilter {
//        @Autowired
//        private JwtTools jwtTools;
//        @Autowired
//        private UserService userService;
//
//        @Override
//        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//            String authorization = request.getHeader("Authorization");
//
//            if (authorization == null || !authorization.startsWith("Bearer ")) {
//                throw new UnAuthorizedException("Token non presente");
//            }
//
//            String token = authorization.substring(7);
//
//            jwtTools.validateToken(token);
//
//            String username = jwtTools.extractUsernameFromToken(token);
//
//            User user = userService.getUserByUsername(username);
//
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null);
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            filterChain.doFilter(request, response);
//
//        }
//
//        @Override
//        protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//            return new AntPathMatcher().match("/auth/**", request.getServletPath());
//        }
//    }
