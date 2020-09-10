package co.jp.api.model.response;

import lombok.Data;

@Data
public class ErrorDto {
	Integer line;
	String name;
	String reason;
}
