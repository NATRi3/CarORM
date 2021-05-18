package hiber.dao;

import hiber.model.User;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@AllArgsConstructor
public class UserDaoImp implements UserDao {

    private final SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User", User.class);
        return query.getResultList();
    }

    @Override
    public User getUserByCar(String model, int series) {
        Session session = sessionFactory.getCurrentSession();
        List<User> usersList = session.createQuery("from User where car = (from Car where model = :model and series = :series)", User.class)
                .setParameter("model", model)
                .setParameter("series", series)
                .getResultList();
        if (!usersList.isEmpty()) {
            return usersList.get(0);
        }
        return null;
    }
}
