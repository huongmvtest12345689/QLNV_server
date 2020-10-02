package co.jp.api.controller;

import co.jp.api.cmn.ResourceResponse;
import co.jp.api.model.request.UserReqDto;
import co.jp.api.model.request.UserUpdateReqDto;
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
        }

        return new ResourceResponse(userList);
    }
    @GetMapping("find-user-email")
    public ResourceResponse findByEmail(@RequestParam("email") String email) {
        return new ResourceResponse(userApiService.findByEmail(email));
    }
    @PutMapping("update-user")
    public ResourceResponse updateUser(@Valid @RequestBody UserUpdateReqDto userUpdateReqDto) {
        if (!userApiService.update(userUpdateReqDto)) {
            return new ResourceResponse(404,"Cập nhật không thành công!");
        }

        return new ResourceResponse(200, "Đã cập nhật thành công!");
    }
    @PutMapping("delete-user")
    public ResourceResponse deleteUser(@RequestParam("email") String email) {
        if (!userApiService.delete(email)) {
            return new ResourceResponse(404,"Xóa không thành công!");
        }

        return new ResourceResponse(200, "Đã xóa thành công!");
    }
    @PostMapping("create-user")
    public ResourceResponse createUser(@Valid @RequestBody UserReqDto userReqDto) {
        if (!userApiService.save(userReqDto)){
            return new ResourceResponse(404,"Tạo không thành công!");
        }

        return new ResourceResponse(200, "Đã tạo thành công!");
    }
    @PutMapping("delete-multi-user")
    public ResourceResponse deleteMultiUser(@Valid @RequestBody List<String> emailList) {
        if (!userApiService.deleteMulti(emailList)) {
            return new ResourceResponse(404,"Xóa không thành công!");
        }

        return new ResourceResponse(200, "Đã xóa thành công!");
    }
}
