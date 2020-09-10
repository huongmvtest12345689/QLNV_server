package co.jp.api.service;

import java.io.IOException;
import java.util.List;

import co.jp.api.model.response.ErrorDto;

public interface ImportCSVService {
	List<ErrorDto> executeFile(String partFile) throws IOException;
}
