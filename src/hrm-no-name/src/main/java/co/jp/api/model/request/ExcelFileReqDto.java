package co.jp.api.model.request;

import lombok.Data;

@Data
public class ExcelFileReqDto {
    private String base64;
    private String filename;
}