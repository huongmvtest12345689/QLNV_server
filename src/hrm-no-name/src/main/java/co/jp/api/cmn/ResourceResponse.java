package co.jp.api.cmn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public class ResourceResponse extends ResponseEntity<ResourceResponse.Payload> {

    public ResourceResponse(Object object) {
        super(new Payload(null, null, object), HttpStatus.OK);
        // TODO Auto-generated constructor stub
    }

    public ResourceResponse(Integer status, String message) {
        super(new Payload(status, message, null), HttpStatus.OK);
        // TODO Auto-generated constructor stub
    }

    public ResourceResponse(Integer status, String message, Object object) {
        super(new Payload(status, message, object), HttpStatus.OK);
        // TODO Auto-generated constructor stub
    }

    public ResourceResponse(Integer status, String message, Map<String, List<String>> messageImport) {
        super(new Payload(status, message, messageImport), HttpStatus.OK);
    }

    public ResourceResponse(int status, String message, Map<String, Object> map) {
        super(new Payload(status, message, map), HttpStatus.OK);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Payload {
        private Integer status;
        private String message;
        private Object object;
    }
}
