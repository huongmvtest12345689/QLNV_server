package co.jp.api.service.impl;

import co.jp.api.dao.RolesDao;
import co.jp.api.entity.Roles;
import co.jp.api.model.response.RolesResDto;
import co.jp.api.service.RolesApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RolesApiServiceImpl implements RolesApiService {

    @Autowired
    private RolesDao rolesDao;

    @Override
    public List<RolesResDto> findAll() {
        List<RolesResDto> rolesResDtoList = new ArrayList<>();
        List<Roles> rolesList = rolesDao.findAll();
        if (rolesList.size() > 0) {
            for (Roles roles : rolesList) {
                RolesResDto rolesResDto = new RolesResDto();
                rolesResDto.setRoleName(roles.getRolesName());
                rolesResDto.setRoleId(roles.getRolesId());
                rolesResDtoList.add(rolesResDto);
            }
        }
        return rolesResDtoList;
    }

    @Override
    public RolesResDto findByRoleName(String roleName) {
        Roles roles = rolesDao.findByRoleName(roleName);
        RolesResDto rolesResDto = new RolesResDto();
        rolesResDto.setRoleName(roles.getRolesName());
        rolesResDto.setRoleId(roles.getRolesId());
        return rolesResDto;
    }
}
