package co.jp.api.controller;

import co.jp.api.cmn.ExcelHelper;
import co.jp.api.cmn.ResourceResponse;
import co.jp.api.entity.User;
import co.jp.api.model.request.ExcelFileResDto;
import co.jp.api.service.ExcelApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/import/")
public class ExcelApiController {
    @Autowired
    private ExcelApiService excelApiService;

    @RequestMapping(value = { "excel" }, method = RequestMethod.POST)
    public ResourceResponse getListCountry(@RequestBody ExcelFileResDto uploadFileDTO){
        byte[] decodeBase64 = Base64.getDecoder().decode(uploadFileDTO.getBase64().getBytes());
        excelApiService.saveFileImport(decodeBase64);
//        if (ExcelHelper.hasExcelFormat(file)) {
//            try {
//                userMap = excelApiService.saveFileImport(file);
//            } catch (Exception e) {
//                return new ResourceResponse("Doc File loi");
//            }
//        }
        return new ResourceResponse("Import File thanh cong");
    }
}
