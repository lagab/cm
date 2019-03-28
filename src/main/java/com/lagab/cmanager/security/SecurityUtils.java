package com.lagab.cmanager.security;

import com.lagab.cmanager.domain.User;
import com.lagab.cmanager.domain.enumeration.SourceType;
import com.lagab.cmanager.domain.enumeration.Visibility;
import com.lagab.cmanager.service.MemberService;
import com.lagab.cmanager.service.UserService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */
    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .map(authentication -> {
                if (authentication.getPrincipal() instanceof UserDetails) {
                    UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                    return springSecurityUser.getUsername();
                } else if (authentication.getPrincipal() instanceof String) {
                    return (String) authentication.getPrincipal();
                }
                return null;
            });
    }

    /**
     * Get the JWT of the current user.
     *
     * @return the JWT of the current user
     */
    public static Optional<String> getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .filter(authentication -> authentication.getCredentials() instanceof String)
            .map(authentication -> (String) authentication.getCredentials());
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise
     */
    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .map(authentication -> authentication.getAuthorities().stream()
                .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(AuthoritiesConstants.ANONYMOUS)))
            .orElse(false);
    }

    /**
     * If the current user has a specific authority (security role).
     * <p>
     * The name of this method comes from the isUserInRole() method in the Servlet API
     *
     * @param authority the authority to check
     * @return true if the current user has the authority, false otherwise
     */
    public static boolean isCurrentUserInRole(String authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .map(authentication -> authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority)))
            .orElse(false);
    }

    /**
     * If the current user Can view a Resource
     */
    public static boolean isCurentUserCanAcess(Visibility visibility){
        if( isAuthenticated() ) {
            if (isCurrentUserInRole(AuthoritiesConstants.MANAGER)
                || isCurrentUserInRole(AuthoritiesConstants.ADMIN)
                || visibility.equals(Visibility.PROTECTED) ) {
                return true;
            }
        }
        return visibility.equals(Visibility.PUBLIC);
    }

    public static boolean isCurentUserCanAcess(SourceType type,Integer source,Visibility visibility){
        boolean canAcess = isCurentUserCanAcess(visibility);
        if( !canAcess && visibility.equals(Visibility.PRIVATE) ){
            // Retrive the user by login
            //Check if the user are member of this Resource
        }
        return canAcess;
    }

}
