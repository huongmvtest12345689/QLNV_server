package co.jp.api.controller;

import co.jp.api.cmn.ResourceResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
public class MemberApiController {
    @GetMapping("/random")
    public ResourceResponse randomStuff(){
        return new ResourceResponse("MEMBER: JWT Hợp lệ mới có thể thấy được message này");
    }
}
