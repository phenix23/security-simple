package com.example.securitysimple.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.securitysimple.security.ApplicationUserPermission.*;


public enum ApplicationUserRole {
    STUDENT(Set.of()),
    ADMIN(Set.of(STUDENT_READ,STUDENT_WRITE, COURSE_READ, COURSE_WRITE)),
    ADMINTRAINEE(Set.of(STUDENT_READ, COURSE_READ));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> customPermissions = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        customPermissions.add(new SimpleGrantedAuthority("ROLE_"+ this.name()));
        return customPermissions;
    }
}
