package com.example.securitysimple.security;

import java.util.Set;

public enum ApplicationUserRole {
    STUDENT(Set.of()),
    ADMIN(Set.of(ApplicationUserPermission.STUDENT_READ, ApplicationUserPermission.STUDENT_WRITE, ApplicationUserPermission.COURSE_READ, ApplicationUserPermission.COURSE_WRITE)),
    ADMINTRAINNER(Set.of(ApplicationUserPermission.STUDENT_READ, ApplicationUserPermission.COURSE_READ));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }
}
