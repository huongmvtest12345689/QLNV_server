package co.jp.api.controller;

import co.jp.api.cmn.ResourceResponse;
import co.jp.api.model.request.ExcelFileReqDto;
import co.jp.api.service.ExcelApiService;
import co.jp.api.util.MessageContants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Base64;


@RestController
@RequestMapping("/api/import/")
public class ExcelApiController {
    @Autowired
    private ExcelApiService excelApiService;

    @RequestMapping(value = { "excel" }, method = RequestMethod.POST)
    public ResourceResponse getListCountry(@RequestBody ExcelFileReqDto uploadFileDTO){
        if (!uploadFileDTO.getBase64().equals("")) {
            byte[] decodeBase64 = Base64.getDecoder().decode(uploadFileDTO.getBase64().getBytes());
            if (excelApiService.saveFileImport(decodeBase64)){
                return new ResourceResponse(200,MessageContants.MSG_018);
            } else {
                return new ResourceResponse(400, MessageContants.MSG_023, MessageContants.messageImport);
            }
        } else {
            return new ResourceResponse(400,MessageContants.MSG_019);
        }
    }
}
