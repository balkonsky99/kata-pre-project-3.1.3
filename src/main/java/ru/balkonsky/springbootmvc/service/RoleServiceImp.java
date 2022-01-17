package ru.balkonsky.springbootmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.balkonsky.springbootmvc.dao.RoleDao;
import ru.balkonsky.springbootmvc.model.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImp implements RoleService{

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImp(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role getRoleByRoleName(String roleName) {
        return roleDao.getRoleByRoleName(roleName);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    public HashSet<Role> getSetOfListRoles (List<String> listRoleNames) {
        HashSet<Role> roleSet = new HashSet<>();
        for (String role : listRoleNames) {
            roleSet.add(getRoleByRoleName(role));
        }
        return roleSet;
    }
}
