package co.jp.api.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.jp.api.cmn.Constants;
import co.jp.api.cmn.ResourceResponse;
import co.jp.api.exception.UserHandleException;
import co.jp.api.model.request.ImportFileCSVResDto;
import co.jp.api.model.response.ErrorDto;
import co.jp.api.service.ImportCSVService;
import co.jp.api.util.AppContants;

@RestController
@RequestMapping("/api/importCSV/")
public class ImportFileCSVApiController {
	@Autowired
    private ImportCSVService importCSVService;
	
	@PostMapping("import")
    @ExceptionHandler(UserHandleException.class)
	public ResourceResponse importFile(@RequestBody ImportFileCSVResDto importFileCSVResDto) {
		String partFile = importFileCSVResDto.getPartFile();
		List<String> messageList = new ArrayList<String>();
    	try {
			List<ErrorDto> messageListOutput = this.importCSVService.executeFile(partFile);
			if(messageListOutput.size() > 0) {
				if(messageListOutput.get(0).getReason().equals(AppContants.INVALID_EMAIL)) {
					messageList.add("Line ".concat(messageListOutput.get(0).getLine().toString()).concat(" ").concat(AppContants.INVALID_EMAIL));
				}else if(messageListOutput.get(0).getReason().equals(AppContants.ISBLANK)) {
					messageList.add("Line ".concat(messageListOutput.get(0).getLine().toString()).concat(" ").concat(messageListOutput.get(0).getName()).concat(AppContants.ISBLANK));
				}else if(messageListOutput.get(0).getReason().equals(AppContants.ERROR_LENGTH)) {
					messageList.add("Line ".concat(messageListOutput.get(0).getLine().toString()).concat(" ").concat(messageListOutput.get(0).getName()).concat(AppContants.ERROR_LENGTH));
				}else if (messageListOutput.get(0).getReason().equals(AppContants.INVALID_PASSWORD)) {
					messageList.add("Line ".concat(messageListOutput.get(0).getLine().toString()).concat(" ").concat(AppContants.INVALID_PASSWORD));
				}else if (messageListOutput.get(0).getReason().equals(AppContants.INVALID_STATUS)) {
					messageList.add("Line ".concat(messageListOutput.get(0).getLine().toString()).concat(" ").concat(AppContants.INVALID_STATUS));
				}else if (messageListOutput.get(0).getReason().equals(AppContants.DUPLICATE)) {
					messageList.add("Line ".concat(messageListOutput.get(0).getLine().toString()).concat(" ").concat(messageListOutput.get(0).getName()).concat(AppContants.DUPLICATE));
				}else if (messageListOutput.get(0).getReason().equals(AppContants.DUPLICATE_DB)) {
					messageList.add("Line ".concat(messageListOutput.get(0).getLine().toString()).concat(" ").concat(messageListOutput.get(0).getName()).concat(AppContants.DUPLICATE_DB));
				}else if (messageListOutput.get(0).getReason().equals(Constants.MESSAGE_FIELD_EMPTY)) {
					messageList.add(Constants.MESSAGE_FIELD_EMPTY);
				}else if (messageListOutput.get(0).getReason().equals(AppContants.FILE_ERROR)) {
					messageList.add(AppContants.FILE_ERROR);
				}else {
					messageList.add(AppContants.SUCCESSFUL);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResourceResponse(messageList);
		
	}
}
