package coupons.filters;

import coupons.binding.Secured;
import coupons.enums.Privilege;
import coupons.models.User;
import coupons.services.UserService;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Secured({Privilege.OPERATOR})
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final String REALM = "Access to privileged data";
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    private UserService userService = new UserService();

    @Context
    private ResourceInfo resourceInfo;

    private User user = null;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(requestContext);
            return;
        }

        String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

        try {
            validateToken(token);
        } catch (Exception e) {
            abortWithUnauthorized(requestContext);
        }

        if (user != null) {
            requestContext.setProperty("user", user);
        }

        Class<?> resourceClass = resourceInfo.getResourceClass();
        List<Privilege> classPrivileges = extractPrivileges(resourceClass);

        Method resourceMethod = resourceInfo.getResourceMethod();
        List<Privilege> methodPrivileges = extractPrivileges(resourceMethod);

        try {

            if (methodPrivileges.isEmpty()) {
                checkPermissions(classPrivileges);
            } else {
                checkPermissions(methodPrivileges);
            }
        } catch (Exception e) {
            requestContext.abortWith(
                    Response.status(Response.Status.FORBIDDEN).build()
            );
        }
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                .header(HttpHeaders.WWW_AUTHENTICATE, AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"")
                .build()
        );
    }

    private List<Privilege> extractPrivileges(AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return new ArrayList<Privilege>();
        } else {
            Secured secured = annotatedElement.getAnnotation(Secured.class);
            if (secured == null) {
                return new ArrayList<Privilege>();
            } else {
                Privilege[] allowedPrivileges = secured.value();
                return Arrays.asList(allowedPrivileges);
            }
        }
    }

    private void checkPermissions(List<Privilege> allowedPrivileges) throws Exception {
        // Check if the user contains one of the allowed roles
        // Throw an Exception if the user has not permission to execute the method
        if (user == null || !allowedPrivileges.contains(user.getPrivilege())) {
            throw new Exception();
        }
    }

    private void validateToken(String token) throws Exception {
        // Check if the token was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid
        this.user = userService.parseJwt(token);
        if(user == null) {
            throw new Exception();
        }
    }
}
