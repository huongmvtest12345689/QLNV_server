package co.jp.api.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.jp.api.cmn.Constants;
import co.jp.api.dao.UserDao;
import co.jp.api.entity.User;
import co.jp.api.model.response.ErrorDto;
import co.jp.api.model.response.UserResDto;
import co.jp.api.service.ImportCSVService;
import co.jp.api.util.AppContants;

@Service
@Transactional
public class ImportCSVServiceImpl implements ImportCSVService {
	
	@Autowired
    private UserDao userDao;
	
	@Override
	public List<ErrorDto> executeFile(String partFile) throws IOException {
		List<ErrorDto> messageList = new ArrayList<ErrorDto>();
		List<UserResDto> dataFileList = getData(partFile);
		List<ErrorDto> dataErroList = new ArrayList<ErrorDto>();
		ErrorDto errorData = new ErrorDto();
		if(dataFileList.size() > 0) {
			//validate data
			dataErroList = validateDataInput(dataFileList);
			if(dataErroList.size() > 0) {
				messageList = dataErroList;
			}else {
				// insert data
				if(insertDb(dataFileList)) {
					errorData.setReason(AppContants.SUCCESSFUL);
					messageList.add(errorData);
				}else {
					errorData.setReason(AppContants.FILE_ERROR);
					messageList.add(errorData);
				}
			}
		}else {
			errorData.setReason(Constants.MESSAGE_FIELD_EMPTY);
			messageList.add(errorData);
		}
		
		return messageList;
	}
	
	private List<UserResDto> getData(String partFile) throws IOException {
		// read File
		List<UserResDto> userDataList = new ArrayList<UserResDto>();
		UserResDto data = new UserResDto();
		
		FileReader fileRead = new FileReader(partFile);
		BufferedReader br = new BufferedReader(fileRead);
		//loop line in file
		int i = 0;
		while(br.readLine() != null) {
			String[] line = br.readLine().split(",");
			
			data.setStt(Integer.parseInt(line[0])); 
			data.setName(line[1]);
			data.setEmail(line[2]);
			data.setPassword(line[3]);
			data.setRoles(line[4]); 
			data.setStatus(line[5]); 
			userDataList.add(data);
			data = new UserResDto();
		}
		
		return userDataList;
	}

	private boolean insertDb(List<UserResDto> dataList) {
		// get result insert data to table
		if(this.userDao.insertData(dataList)) {
			return true;
		}else {
			return false;
		}
	}
	
	private List<ErrorDto> validateDataInput(List<UserResDto> inputList){
		List<ErrorDto> errorList = new ArrayList<ErrorDto>();
		ErrorDto errorData = new ErrorDto();
		String role = "1,2,3,4";
		HashMap<String, User> userMapList = this.userDao.getEmailList();
		for(int i = 0; i < inputList.size(); i++) {
			UserResDto userData = inputList.get(i);
			// validate name
			if(userData.getName().trim().isEmpty()) {
				errorData = new ErrorDto();
				errorData.setLine(userData.getStt());
				errorData.setName("Name");
				errorData.setReason(AppContants.ISBLANK);
				errorList.add(errorData);
				return errorList;
			}
			// validate Email
			if(userData.getEmail().trim().isEmpty()) {
				errorData = new ErrorDto();
				errorData.setLine(userData.getStt());
				errorData.setName("Email");
				errorData.setReason(AppContants.ISBLANK);
				errorList.add(errorData);
				return errorList;
			}else {
				if(userData.getEmail().length() < 14 || userData.getEmail().length() > 50) {
					errorData = new ErrorDto();
					errorData.setLine(userData.getStt());
					errorData.setName(userData.getEmail());
					errorData.setReason(AppContants.ERROR_LENGTH);
					errorList.add(errorData);
					return errorList;
				}else {
					String regex = "^[a-zA-Z0-9_+&*-]+(?:\\\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-z0-9-]+\\\\.)+[a-z]{2,7}$";
					if(!userData.getEmail().matches(regex)) {
						errorData = new ErrorDto();
						errorData.setLine(userData.getStt());
						errorData.setName(userData.getEmail());
						errorData.setReason(AppContants.INVALID_EMAIL);
						errorList.add(errorData);
						return errorList;
					}else {
						if(userData.getPassword().length() < 1 || userData.getEmail().length() > 50) {
							errorData = new ErrorDto();
							errorData.setLine(userData.getStt());
							errorData.setName(userData.getPassword());
							errorData.setReason(AppContants.ERROR_LENGTH);
							errorList.add(errorData);
							return errorList;
						}
					}
				}
			}
			// validate password
			if(userData.getPassword().trim().isEmpty()) {
				errorData = new ErrorDto();
				errorData.setLine(userData.getStt());
				errorData.setName("password");
				errorData.setReason(AppContants.ISBLANK);
				errorList.add(errorData);
				return errorList;
			}else {
				String regexPassword = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
				if(!userData.getPassword().matches(regexPassword)) {
					errorData = new ErrorDto();
					errorData.setLine(userData.getStt());
					errorData.setName(userData.getPassword());
					errorData.setReason(AppContants.INVALID_PASSWORD);
					errorList.add(errorData);
					return errorList;
				}
			}
			// validate roles
			if(userData.getRoles().trim().isEmpty()) {
				errorData = new ErrorDto();
				errorData.setLine(userData.getStt());
				errorData.setName("roles");
				errorData.setReason(AppContants.ISBLANK);
				errorList.add(errorData);
				return errorList;
			}else {
				if(!role.contains(userData.getRoles())) {
					errorData = new ErrorDto();
					errorData.setLine(userData.getStt());
					errorData.setName(userData.getRoles());
					errorData.setReason(AppContants.INVALID_ROLES);
					errorList.add(errorData);
					return errorList;
				}
			}
			// validate status
			if(userData.getStatus().trim().isEmpty()) {
				errorData = new ErrorDto();
				errorData.setLine(userData.getStt());
				errorData.setName("status");
				errorData.setReason(AppContants.ISBLANK);
				errorList.add(errorData);
				return errorList;
			}else {
				if(!role.contains(userData.getRoles())) {
					errorData = new ErrorDto();
					errorData.setLine(userData.getStt());
					errorData.setName(userData.getRoles());
					errorData.setReason(AppContants.INVALID_STATUS);
					errorList.add(errorData);
					return errorList;
				}
			}	
			// check duplicate email
			if(i > 1) {
				String emailBefore = inputList.get(i-1).getEmail();
				String email = userData.getEmail();
				if(emailBefore.equals(email)) {
					errorData = new ErrorDto();
					errorData.setLine(inputList.get(i-1).getStt());
					errorData.setName(email);
					errorData.setReason(AppContants.DUPLICATE);
					errorList.add(errorData);
					return errorList;
				}
			}
			
			if(userMapList.size() > 0) {
				if(userMapList.containsKey(userData.getEmail())) {
					errorData = new ErrorDto();
					errorData.setLine(inputList.get(i-1).getStt());
					errorData.setName(userData.getEmail());
					errorData.setReason(AppContants.DUPLICATE_DB);
					errorList.add(errorData);
					return errorList;
				}
			}
		}
		return errorList;
	}

	

}
