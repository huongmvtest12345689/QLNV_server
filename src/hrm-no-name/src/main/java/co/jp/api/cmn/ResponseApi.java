package co.jp.api.cmn;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseApi extends ResponseEntity<ObjectResponse> {

	public ResponseApi() {
		super(ObjectResponse.builder().status(HttpStatus.OK.value()).build(), HttpStatus.OK);
	}

	public ResponseApi(Object data) {
		super(new ObjectResponse(HttpStatus.OK.value(), data), HttpStatus.OK);
	}
}
