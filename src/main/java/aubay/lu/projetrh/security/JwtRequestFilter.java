package aubay.lu.projetrh.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    //TODO : Mettre en place le logging dans cette classe et les autres components de sécurité où ça peut être utile
    private SecurityUserDetailsService securityUserDetailsService;
    private JwtUtil jwtUtil;

    @Autowired
    JwtRequestFilter(SecurityUserDetailsService securityUserDetailsService, JwtUtil jwtUtil){
        this.securityUserDetailsService = securityUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    protected boolean shouldNotFilter(HttpServletRequest request){
        return request.getServletPath().startsWith("/authentification");
    }

    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);

            try {
                String pseudo = jwtUtil.getTokenBody(jwt).getSubject();

                if(pseudo != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    UserDetails userDetails = securityUserDetailsService.loadUserByUsername(pseudo);

                    if(jwtUtil.valideToken(jwt, userDetails)){

                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                        usernamePasswordAuthenticationToken
                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                        filterChain.doFilter(httpServletRequest,httpServletResponse);
                    } else {
                        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        httpServletResponse.setCharacterEncoding("UTF-8");
                        httpServletResponse.getWriter().write("Le token est corrompu ou expiré");
                        httpServletResponse.getWriter().flush();
                    }
                }

            } catch (ExpiredJwtException e) {
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.getWriter().write("Le token est expiré");
                httpServletResponse.getWriter().flush();
            }
        } else {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.getWriter().write("Le token est inexistant ou malformé");
            httpServletResponse.getWriter().flush();
        }

    }

}
