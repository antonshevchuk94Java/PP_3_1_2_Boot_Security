package ru.kata.spring.boot_security.demo.dao;


import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u FROM User u", User.class).getResultList();
    }


    @Override
    public void updateUser(User user) {
        entityManager.merge(user);

    }

    @Override
    public void removeUserById(long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);

    }

    @Override
    public User findByLogin(String userLogin) {
       try { return entityManager.createQuery
                       ("select u from User u where u.login = :login", User.class)
               .setParameter("login",userLogin)
               .getSingleResult();
       } catch (NoResultException e) {
           throw new UsernameNotFoundException("User с таким login не найден");
       }

    }
}

