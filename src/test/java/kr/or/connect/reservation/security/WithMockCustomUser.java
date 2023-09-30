package kr.or.connect.reservation.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
    long id() default 1L;
    String email() default "test@connect.or.kr";
    String password() default "test";
    String name() default "test";
    String phone() default "000-0000-0000";
    String[] authorities() default "ROLE_USER";
}
