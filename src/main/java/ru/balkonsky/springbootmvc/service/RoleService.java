package ru.balkonsky.springbootmvc.service;

import org.springframework.stereotype.Component;
import ru.balkonsky.springbootmvc.model.Role;

import java.util.HashSet;
import java.util.List;

@Component
public interface RoleService {

    Role getRoleByRoleName(String roleName);
    List<Role> getAllRoles();
    HashSet<Role> getSetOfListRoles (List<String> listRoleNames);
}
