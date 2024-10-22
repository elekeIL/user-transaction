package com.transaction.utils.etc;

import com.transaction.enums.PermissionTypeConstant;

public interface RolePermissionHolder {
    String roleName();

    PermissionTypeConstant[] permissions();
}