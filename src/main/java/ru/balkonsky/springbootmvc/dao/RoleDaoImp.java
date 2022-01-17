package ru.balkonsky.springbootmvc.dao;

import org.springframework.stereotype.Repository;

import ru.balkonsky.springbootmvc.model.Role;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RoleDaoImp implements RoleDao{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role getRoleByRoleName(String roleName) {
        TypedQuery<Role> query = (TypedQuery<Role>) entityManager.createNativeQuery("SELECT * FROM roles where name= :roleName", Role.class);
        query.setParameter("roleName", roleName);
        return query.getSingleResult();
    }

    @Override
    public List<Role> getAllRoles() {
        TypedQuery<Role> query = (TypedQuery<Role>) entityManager.createNativeQuery("SELECT * FROM roles", Role.class);

        return query.getResultList();
    }

}
