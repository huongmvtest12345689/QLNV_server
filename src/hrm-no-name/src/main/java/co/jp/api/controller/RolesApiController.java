package co.jp.api.controller;

import co.jp.api.cmn.ResourceResponse;
import co.jp.api.model.response.RolesResDto;
import co.jp.api.service.RolesApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles/")
public class RolesApiController {

    @Autowired
    private RolesApiService rolesApiService;

    @GetMapping("all")
    public ResourceResponse getAll(){
        List<RolesResDto> rolesResDtoList = rolesApiService.findAll();
        if (CollectionUtils.isEmpty(rolesResDtoList)) {
            return new ResourceResponse(null);
        } else {
            return new ResourceResponse(rolesResDtoList);
        }
    }

    @GetMapping("find-role-name")
    public ResourceResponse findByRoleName(@RequestParam("roleName") String roleName) {
        return new ResourceResponse(rolesApiService.findByRoleName(roleName));
    }
}
