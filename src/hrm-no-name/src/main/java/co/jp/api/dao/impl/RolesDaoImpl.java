package co.jp.api.dao.impl;

import co.jp.api.dao.RolesDao;
import co.jp.api.entity.Roles;
import co.jp.api.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RolesDaoImpl implements RolesDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Roles> findAll() {
        String sql = AppUtils.sqlExcute("cmn/ROLES_01_SELECT_ALL.sql");
        List<Roles> rolesList = this.entityManager.createNativeQuery(sql, Roles.class).getResultList();
        return rolesList;
    }
    @Override
    public Roles findByRoleName(String roleName) {
        String sql = AppUtils.sqlExcute("cmn/ROLES_02_FIND_ROLE_NAME.sql");
        Query query = this.entityManager.createNativeQuery(sql, Roles.class);
        query.setParameter("roleName", roleName);
        List<Roles> rolesList = query.getResultList();
        if (!CollectionUtils.isEmpty(rolesList)) {
            return rolesList.get(0);
        }
        return null;
    }
}
