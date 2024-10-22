package com.transaction.utils.roles;

import com.transaction.enums.PermissionTypeConstant;
import com.transaction.utils.etc.RolePermissionHolder;
import org.apache.commons.lang3.StringUtils;

public enum AdminRole implements RolePermissionHolder {
    ADMIN("ADMIN");

    private String displayName;
    private PermissionTypeConstant[] permissionConstant;

    AdminRole(PermissionTypeConstant... permissionConstant) {
        this.permissionConstant = permissionConstant;
    }

    AdminRole(String name, PermissionTypeConstant... permissionConstant) {
        this(permissionConstant);
        this.displayName = name;
    }

    @Override
    public String roleName() {
        return StringUtils.defaultString(displayName, name());
    }

    @Override
    public PermissionTypeConstant[] permissions() {
        return permissionConstant;
    }
}
