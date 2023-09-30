package kr.or.connect.reservation.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import kr.or.connect.reservation.domain.user.Role;
import kr.or.connect.reservation.domain.user.User;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        User user = new User(customUser.email(), customUser.password(), customUser.name(), customUser.phone());
        for (String authority: customUser.authorities()) {
            user.addRole(new Role(authority));
        }

        CustomUserDetails principal = new CustomUserDetails(user);
        Authentication auth = UsernamePasswordAuthenticationToken.authenticated(principal, "password", principal.getAuthorities());
        context.setAuthentication(auth);
        return context; 
    }
    
    
}
