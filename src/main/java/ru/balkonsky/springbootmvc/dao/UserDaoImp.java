package ru.balkonsky.springbootmvc.dao;

import org.springframework.stereotype.Repository;
import ru.balkonsky.springbootmvc.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select distinct u from User u join fetch u.roles", User.class).getResultList();
    }

    public User showUserById(int id){
        return entityManager.find(User.class, id);
    }

    public void saveUser(User user) {
        entityManager.persist(user);
    }

    public void updateUser(User updatedUser, int id) {
        updatedUser.setId(id);
        entityManager.merge(updatedUser);
    }

    public void deleteUser(int id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public User getUserByUsername(String username) {
        return (User) entityManager.createQuery("select u from User u join fetch u.roles where u.username=:username")
                                    .setParameter("username", username).getSingleResult();
    }
}
