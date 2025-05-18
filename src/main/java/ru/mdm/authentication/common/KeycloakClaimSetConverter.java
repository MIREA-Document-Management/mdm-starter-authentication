package ru.mdm.authentication.common;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter;

import java.util.Collections;
import java.util.Map;

/**
 * Конвертер claims для токенов keycloak
 */
public class KeycloakClaimSetConverter implements Converter<Map<String, Object>, Map<String, Object>> {

    private final MappedJwtClaimSetConverter delegate = MappedJwtClaimSetConverter.withDefaults(Collections.emptyMap());

    /**
     * Подкладывает preferred_username claim в subject
     *
     * @param claims claims
     * @return claims
     */
    @Override
    public Map<String, Object> convert(Map<String, Object> claims) {
        Map<String, Object> convertedClaims = this.delegate.convert(claims);
        String username = (String) convertedClaims.get("preferred_username"); //NOSONAR
        convertedClaims.put("sub", username);
        return convertedClaims;
    }
}
