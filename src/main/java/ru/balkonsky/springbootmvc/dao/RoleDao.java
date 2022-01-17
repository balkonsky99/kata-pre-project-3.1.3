package ru.balkonsky.springbootmvc.dao;

import ru.balkonsky.springbootmvc.model.Role;
import java.util.List;

public interface RoleDao {
    Role getRoleByRoleName(String roleName);
    List<Role> getAllRoles();
}
