package com.example.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.example.entity.Permission.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {
    USER(Collections.emptySet()),
    ADMIN(Set.of(
            ADMIN_READ,
            ADMIN_CREATE,
            ADMIN_UPDATE,
            ADMIN_DELETE,
            MANAGER_READ,
            MANAGER_CREATE,
            MANAGER_UPDATE,
            MANAGER_DELETE
    )),
    MANAGER(Set.of(
            MANAGER_READ,
            MANAGER_CREATE,
            MANAGER_UPDATE,
            MANAGER_DELETE
    ))
    ;

    @Getter
    private final Set<Permission>permission;

    public List<SimpleGrantedAuthority>getAuthorities(){
        var authorities = getPermission()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+ this.name()));
        return authorities;
    }
}
