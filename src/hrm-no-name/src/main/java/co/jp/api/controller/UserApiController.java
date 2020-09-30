package co.jp.api.controller;

import co.jp.api.cmn.ResourceResponse;
import co.jp.api.model.request.UserUpdateResDto;
import co.jp.api.model.response.UserAllResDto;
import co.jp.api.service.UserApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user/")
public class UserApiController {
    @Autowired
    private UserApiService userApiService;
    @GetMapping("all")
    public ResourceResponse getAll(){
        List<UserAllResDto> userList = userApiService.findAll();
        if (CollectionUtils.isEmpty(userList)) {
            return new ResourceResponse(null);
        } else {
            return new ResourceResponse(userList);
        }
    }
    @GetMapping("find-user-email")
    public ResourceResponse findByEmail(@RequestParam("email") String email) {
        return new ResourceResponse(userApiService.findByEmail(email));
    }
    @PutMapping("update-user")
    public ResourceResponse updateUser(@Valid @RequestBody UserUpdateResDto userUpdateResDto) {
        if (userApiService.update(userUpdateResDto)) {
            return new ResourceResponse(200, "Đã cập nhật thành công!");
        } else {
            return new ResourceResponse(404,"Cập nhật không thành công!");
        }
    }
    @PutMapping("delete-user")
    public ResourceResponse deleteUser(@RequestParam("userId") Integer userId) {
        if (!userApiService.delete(userId)) {
            return new ResourceResponse(404,"Xoa không thành công!");
        }

        return new ResourceResponse(200, "Đã xoa thành công!");
    }
}
