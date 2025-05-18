package ru.mdm.authentication.common;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@RequiredArgsConstructor
public class GroupBasedGrantedAuthority implements GrantedAuthority {

    private final String group;

    @Override
    public String getAuthority() {
        return group;
    }
}
