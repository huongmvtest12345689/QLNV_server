package co.jp.api.dao;

import co.jp.api.entity.Roles;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesDao {
    List<Roles> findAll();
    Roles findByRoleName(String roleName);
}
