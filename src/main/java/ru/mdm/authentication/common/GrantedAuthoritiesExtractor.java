package ru.mdm.authentication.common;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;

/**
 * Экстрактор ролей пользователя
 */
public class GrantedAuthoritiesExtractor implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final String ROLES_CLAIM = "realm_access";
    private static final String ROLES_KEY = "roles";
    public static final String GROUPS_CLAIM = "groups";

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims()
                .getOrDefault(ROLES_CLAIM, Collections.emptyMap());
        var roles = ((List<Object>) realmAccess.getOrDefault(ROLES_KEY, Collections.emptyList()));
        var groups = ((List<Object>) jwt.getClaims().getOrDefault(GROUPS_CLAIM, Collections.emptyList()));

        List<? extends GrantedAuthority> collectRoles = roles.stream()
                .map(Object::toString)
                .map(SimpleGrantedAuthority::new)
                .toList();

        List<? extends GrantedAuthority> collectGroups = groups.stream()
                .map(Object::toString)
                .map(GroupBasedGrantedAuthority::new)
                .toList();

        List<GrantedAuthority> result = new ArrayList<>();
        result.addAll(collectRoles);
        result.addAll(collectGroups);
        return result;
    }
}
