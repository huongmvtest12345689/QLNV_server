package co.jp.api.controller;

import co.jp.api.cmn.ResourceResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/")
public class AdminApiController {
    @GetMapping("random")
    public ResourceResponse randomStuff(){
        return new ResourceResponse("ADMIN: JWT Hợp lệ mới có thể thấy được message này");
    }
}
