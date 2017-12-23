package pl.com.bottega.hrs.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.hrs.application.UserFinder;
import pl.com.bottega.hrs.application.UserSearchResults;
import pl.com.bottega.hrs.application.UserSearchCriteria;
import pl.com.bottega.hrs.application.dtos.BasicUserDto;
import pl.com.bottega.hrs.application.dtos.DetailedUserDto;
import pl.com.bottega.hrs.application.users.Role;
import pl.com.bottega.hrs.application.users.User;
import pl.com.bottega.hrs.application.users.User_;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by freszczypior on 2017-11-29.
 */
@Component
public class JPACriteriaUserFinder implements UserFinder {

    private EntityManager entityManager;

    public JPACriteriaUserFinder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public UserSearchResults search(UserSearchCriteria criteria){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BasicUserDto> cq = cb.createQuery(BasicUserDto.class);
        Root<User> user = cq.from(User.class);
        cq.select(cb.construct(BasicUserDto.class,
                user.get(User_.id),
                user.get(User_.login)));
        Predicate predicate = bulitPredicate(criteria, cb, user);
        cq.where(predicate);
        Query query = entityManager.createQuery(cq);
        query.setMaxResults(criteria.getPageSize());
        query.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize());
        List<BasicUserDto> results = query.getResultList();
        UserSearchResults userSerchResults = new UserSearchResults();
        userSerchResults.setResults(results);
        int total = searchTotalCount(criteria);
        userSerchResults.setTotalCount(total);
        userSerchResults.setPageNumber(criteria.getPageNumber());
        userSerchResults.setPagesCount(total / criteria.getPageSize() +
                (total % criteria.getPageSize() == 0 ? 0 : 1));
        return userSerchResults;
    }

    @Override
    @Transactional
    public DetailedUserDto getUserDetails(Integer id) {
        User user = entityManager.find(User.class, id);
        if (user == null){
            throw new NoSuchEntityException();
        }
        return new DetailedUserDto(user);
    }

    private int searchTotalCount(UserSearchCriteria criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<User> user = cq.from(User.class);
        cq.select(cb.count(user));
        Predicate predicate = bulitPredicate(criteria, cb, user);
        cq.where(predicate);
        Query query = entityManager.createQuery(cq);
        return ((Long) query.getSingleResult()).intValue();
    }

    private Predicate bulitPredicate(UserSearchCriteria criteria, CriteriaBuilder cb, Root<User> user) {
        Predicate predicate = cb.conjunction();
        predicate = addLoginPredicate(criteria, cb, user, predicate);
        predicate = addRolePredicate(criteria, cb, user, predicate);
        return predicate;
    }

    private Predicate addLoginPredicate(UserSearchCriteria criteria, CriteriaBuilder cb, Root user, Predicate predicate){
        if (criteria.getLogin() != null){
            predicate = cb.and(predicate, cb.equal(user.get(User_.login), criteria.getLogin()));
        }
        return predicate;
    }
    private Predicate addRolePredicate(UserSearchCriteria criteria, CriteriaBuilder cb, Root user, Predicate predicate){
        if (criteria.getRole() != null){
            predicate = cb.and(predicate, user.get(User_.roles).in(criteria.getRole()));
        }
        return predicate;
    }
}
