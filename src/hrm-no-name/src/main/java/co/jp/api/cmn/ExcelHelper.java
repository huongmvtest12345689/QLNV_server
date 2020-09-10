package co.jp.api.cmn;

import co.jp.api.entity.User;
import co.jp.api.service.ExcelApiService;
import co.jp.api.util.AppContants;
import co.jp.api.util.AppUtils;
import co.jp.api.util.MessageContants;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    @Autowired
    private ExcelApiService excelApiService;

    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }
    public Map<String, List<User>> mapDataObject(InputStream is) throws IOException {
        MessageContants.messageImport = new HashMap<>();
        Map<String, List<User>> userMap = new HashMap<>();
        Workbook workbook = new XSSFWorkbook(is);
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            String sheetName = sheet.getSheetName();
            List<User> userList = new ArrayList<>();
            List<String> listMessage = new ArrayList<String>();
            DataFormatter dataFormatter = new DataFormatter();
            Iterator<Row> rowIterator = sheet.rowIterator();

            int rowNumber = 0;
            while (rowIterator.hasNext()) {
                Row currentRow = rowIterator.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                User user = new User();

                int cellIdx = 0;

                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    CellType cellType = currentCell.getCellTypeEnum();
                    String msgError = null;
                    String msgDuplicateError = null;

                    switch (cellIdx) {
                        case 0:
                            String getId = dataFormatter.formatCellValue(currentCell);
                            msgError = validateColumn(rowNumber, "Id", sheetName, getId, CellType.NUMERIC, cellType, 0, 0);
                            if (msgError == null){
                                user.setId(Integer.parseInt(getId));
                            } else {
                                listMessage.add(msgError);
                            }
                            break;

                        case 1:
                            String getName = dataFormatter.formatCellValue(currentCell);
                            msgError = validateColumn(rowNumber, "Name", sheetName, getName, CellType.STRING, cellType, 0, 0);
                            if (msgError == null){
                                user.setName(getName);
                            } else {
                                listMessage.add(msgError);
                            }
                            break;

                        case 2:
                            String getEmail = dataFormatter.formatCellValue(currentCell);
                            msgError = validateColumn(rowNumber, "Email", sheetName, getEmail, CellType.STRING, cellType, 0, 0);
                            if (msgError == null){
                                msgDuplicateError = validateDuplicateEmail(rowNumber, "Email", sheetName, getEmail, userList);
                                if (msgDuplicateError == null) {
                                    user.setEmail(getEmail);
                                } else {
                                    listMessage.add(msgDuplicateError);
                                }
                            } else {
                                listMessage.add(msgError);
                            }
                            break;

                        case 3:
                            String getPassword = dataFormatter.formatCellValue(currentCell);
                            msgError = validateColumn(rowNumber, "Password", sheetName, getPassword, CellType.STRING, cellType, 4, 8);
                            if (msgError == null){
                                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                                String hashedPassword = passwordEncoder.encode(getPassword);
                                user.setPassword(hashedPassword);
                            } else {
                                listMessage.add(msgError);
                            }
                            break;

                        case 4:
                            String getRole = dataFormatter.formatCellValue(currentCell);
                            msgError = validateColumn(rowNumber, "RoleId", sheetName, getRole, CellType.NUMERIC, cellType, 1, 4);
                            if (null == msgError) {
                                user.setRolesId(Integer.parseInt(getRole));
                            } else {
                                listMessage.add(msgError);
                            }
                            break;
                        case 5:
                            String getStatus = dataFormatter.formatCellValue(currentCell);
                            msgError = validateColumn(rowNumber, "Status", sheetName, getStatus, CellType.NUMERIC, cellType, 1, 2);
                            if (msgError == null){
                                user.setStatus(Integer.parseInt(getStatus));
                            } else {
                                listMessage.add(msgError);
                            }
                            break;

                        default:
                            break;
                    }

                    cellIdx++;
                }
                userList.add(user);
                rowNumber++;
            }
            userMap.put(sheet.getSheetName(), userList);
            MessageContants.messageImport.put(sheet.getSheetName(), listMessage);
        }
        workbook.close();
//      for (Map.Entry<String, List<String>> entry : MessageContants.messageImport.entrySet()) {
//            String key = entry.getKey();
//            List<String> values = entry.getValue();
//            System.out.println("Key = " + key);
//            for (String msg : values) {
//                System.out.println(msg);
//            }
//        }
        return userMap;
    }
    public String validateDuplicateEmail(Integer rowColumn,
                                         String columnName,
                                         String sheetName,
                                         String email,
                                         List<User> userList){
        String msg = null;
        if (!"".equals(email)) {
            User userCheck = excelApiService.findByEmail(email);
            if (userCheck != null) {
                msg = AppUtils.getMessageVariable(rowColumn, columnName, sheetName, MessageContants.MSG_017);
            }
            for (User user : userList){
                if (email.equals(user.getEmail())) {
                    msg = AppUtils.getMessageVariable(rowColumn, columnName, sheetName, MessageContants.MSG_016);
                    break;
                }
            }
        }
        return msg;
    }
    public String validateColumn(Integer rowColumn,
                                 String columnName,
                                 String sheetName,
                                 String columnValue,
                                 CellType cellTypeValidate,
                                 CellType cellType,
                                 Integer minValue,
                                 Integer maxValue) {
        String msg = null;
        switch (cellType) {
            case BOOLEAN:
                if (cellTypeValidate != cellType){
                    msg = AppUtils.getMessageVariable(rowColumn, columnName, sheetName, MessageContants.MSG_021);
                }
                return msg;
            case NUMERIC:
                if (cellTypeValidate != cellType){
                    msg = AppUtils.getMessageVariable(rowColumn, columnName, sheetName, MessageContants.MSG_021);
                } else {
                    int value = Integer.parseInt(columnValue);
                    if (maxValue != 0 && minValue != 0) {
                        if (value < minValue || value > maxValue){
                            msg = AppUtils.getMessageVariable(rowColumn, columnName, sheetName, MessageContants.MSG_021);
                        }
                    }
                }
                return msg;
            case STRING:
                if (cellTypeValidate != cellType){
                    msg = AppUtils.getMessageVariable(rowColumn, columnName, sheetName, MessageContants.MSG_021);
                } else if (minValue != 0 && maxValue != 0) {
                    Pattern pattern = Pattern.compile(AppContants.PASSWORD_PATTERN);
                    if(!pattern.matcher(columnValue).matches()){
                        msg = AppUtils.getMessageVariable(rowColumn, columnName, sheetName, MessageContants.MSG_021);
                    }
                } else if ("Email".equals(columnName)) {
                    Pattern pattern = Pattern.compile(AppContants.EMAIL_PATTERN);
                    if(!pattern.matcher(columnValue).matches()){
                        msg = AppUtils.getMessageVariable(rowColumn, columnName, sheetName, MessageContants.MSG_021);
                    }
                }
                return msg;
            case BLANK:
                msg = AppUtils.getMessageVariable(rowColumn, columnName, sheetName, MessageContants.MSG_022);
                return msg;
            default:
                return null;
        }
    }
}
