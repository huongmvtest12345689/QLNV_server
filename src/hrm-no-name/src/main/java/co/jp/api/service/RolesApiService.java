package co.jp.api.service;

import co.jp.api.model.response.RolesResDto;

import java.util.List;

public interface RolesApiService {
    List<RolesResDto> findAll();
    RolesResDto findByRoleName(String roleName);
}
