package com.petriuk.dao;

import com.petriuk.entity.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class HibernateRoleDao extends AbstractHibernateDao<Role>
    implements RoleDao {

    @Override
    public void create(Role role) {
        createUpdate(role);
    }

    @Override
    public void update(Role role) {
        createUpdate(role);
    }

    @Override
    public void remove(Role role) {
        deleteEntity(role);
    }

    @Override
    public List<Role> findAll() {
        TypedQuery<Role> typedQuery = getSession()
            .createQuery("from Role", Role.class);
        return typedQuery.getResultList();
    }

    @Override
    public Optional<Role> findByName(String name) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<Role> criteriaQuery = builder.createQuery(Role.class);
        Root<Role> roleRoot = criteriaQuery.from(Role.class);
        criteriaQuery.select(roleRoot)
            .where(builder.equal(roleRoot.get("name"), name));
        TypedQuery<Role> query = getSession().createQuery(criteriaQuery);
        return query.getResultList().stream().findFirst();
    }

}
