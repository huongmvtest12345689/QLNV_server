package co.jp.api.cmn;

import co.jp.api.entity.User;
import co.jp.api.model.CellInfoDTO;
import co.jp.api.service.ExcelApiService;
import co.jp.api.service.UserApiService;
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

    @Autowired
    private UserApiService userApiService;

    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    Map<Integer, CellInfoDTO> COLUMN_MAP = new HashMap<Integer, CellInfoDTO>() {{
        put(0, new CellInfoDTO("id", CellType.NUMERIC));
        put(1, new CellInfoDTO("name", CellType.STRING));
        put(2, new CellInfoDTO("email", CellType.STRING));
        put(3, new CellInfoDTO("password", CellType.STRING));
        put(4, new CellInfoDTO("rolesId", CellType.NUMERIC));
        put(5, new CellInfoDTO("status", CellType.NUMERIC));
    }};

    public Map<String, List<User>> mapDataObject(InputStream is) throws IOException, NoSuchFieldException, IllegalAccessException {
        MessageContants.messageImport = new HashMap<>();
        Map<String, List<User>> userMap = new HashMap<>();
        Workbook workbook = new XSSFWorkbook(is);
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        List<User> userListDb = userApiService.findAll();

        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            String sheetName = sheet.getSheetName();
            List<User> userListImport = new ArrayList<>();
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
                    if (cellIdx == 0) {
                        cellIdx++;
                        continue;
                    }
                    CellInfoDTO cellInfo = COLUMN_MAP.get(cellIdx);
                    String formatCurrentCell = dataFormatter.formatCellValue(currentCell);
                    String msgError = validateColumn(rowNumber, cellInfo.getName(), sheetName, formatCurrentCell, cellInfo.getCellType(), cellType, userListImport, userListDb);
                    if (msgError == null) {
                        /*user.setFieldValue(cellInfo, formatCurrentCell);*/
                        user = setValue(cellInfo.getName(), formatCurrentCell, user);
                    } else {
                        listMessage.add(msgError);
                    }

//                    switch (cellIdx) {
//                        case 0:
//                            String getId = dataFormatter.formatCellValue(currentCell);
//                            msgError = validateColumn(rowNumber, "Id", sheetName, getId, CellType.NUMERIC, cellType, 0, 0);
//                            if (msgError == null){
//                                user.setId(Integer.parseInt(getId));
//                            } else {
//                                listMessage.add(msgError);
//                            }
//                            break;
//
//                        case 1:
//                            String getName = dataFormatter.formatCellValue(currentCell);
//                            msgError = validateColumn(rowNumber, "Name", sheetName, getName, CellType.STRING, cellType, 0, 0);
//                            if (msgError == null){
//                                user.setName(getName);
//                            } else {
//                                listMessage.add(msgError);
//                            }
//                            break;
//
//                        case 2:
//                            String getEmail = dataFormatter.formatCellValue(currentCell);
//                            msgError = validateColumn(rowNumber, "Email", sheetName, getEmail, CellType.STRING, cellType, 0, 0);
//                            if (msgError == null){
//                                msgDuplicateError = validateDuplicateEmail(rowNumber, "Email", sheetName, getEmail, userList);
//                                if (msgDuplicateError == null) {
//                                    user.setEmail(getEmail);
//                                } else {
//                                    listMessage.add(msgDuplicateError);
//                                }
//                            } else {
//                                listMessage.add(msgError);
//                            }
//                            break;
//
//                        case 3:
//                            String getPassword = dataFormatter.formatCellValue(currentCell);
//                            msgError = validateColumn(rowNumber, "Password", sheetName, getPassword, CellType.STRING, cellType, 4, 8);
//                            if (msgError == null){
//                                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//                                String hashedPassword = passwordEncoder.encode(getPassword);
//                                user.setPassword(hashedPassword);
//                            } else {
//                                listMessage.add(msgError);
//                            }
//                            break;
//
//                        case 4:
//                            String getRole = dataFormatter.formatCellValue(currentCell);
//                            msgError = validateColumn(rowNumber, "RoleId", sheetName, getRole, CellType.NUMERIC, cellType, 1, 4);
//                            if (null == msgError) {
//                                user.setRolesId(Integer.parseInt(getRole));
//                            } else {
//                                listMessage.add(msgError);
//                            }
//                            break;
//                        case 5:
//                            String getStatus = dataFormatter.formatCellValue(currentCell);
//                            msgError = validateColumn(rowNumber, "Status", sheetName, getStatus, CellType.NUMERIC, cellType, 1, 2);
//                            if (msgError == null){
//                                user.setStatus(Integer.parseInt(getStatus));
//                            } else {
//                                listMessage.add(msgError);
//                            }
//                            break;
//
//                        default:
//                            break;
//                    }

                    cellIdx++;
                }
                userListImport.add(user);
                rowNumber++;
            }
            userMap.put(sheet.getSheetName(), userListImport);
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
    public User setValue(String nameColums ,String columnValue, User user) {
        switch (nameColums) {
            case "name":
                user.setName(columnValue);
                break;
            case "email":
                user.setEmail(columnValue);
                break;
            case "password":
                user.setPassword(AppUtils.encode(columnValue));
                break;
            case "rolesId":
                user.setRolesId(Integer.parseInt(columnValue));
                break;
            case "status":
                user.setStatus(Integer.parseInt(columnValue));
                break;
        }
        return user;
    }

    public String validateColumn(Integer rowColumn,
                                 String columnName,
                                 String sheetName,
                                 String columnValue,
                                 CellType cellTypeValidate,
                                 CellType cellType,
                                 List<User> userListImport,
                                 List<User> userListDb) {

        if (cellTypeValidate != cellType){
            return AppUtils.getMessageVariable(rowColumn, columnName, sheetName, MessageContants.MSG_021);
        }

        switch (cellType) {
            case NUMERIC:
                int value = Integer.parseInt(columnValue);
                if ("status".equals(columnName)) {
                    if (value < 1 || value > 4){
                        return AppUtils.getMessageVariable(rowColumn, columnName, sheetName, MessageContants.MSG_021);
                    }
                }
                return null;

            case STRING:
                if ("password".equals(columnName)) {
                    Pattern pattern = Pattern.compile(AppContants.PASSWORD_PATTERN);
                    if(!pattern.matcher(columnValue).matches()) {
                        return AppUtils.getMessageVariable(rowColumn, columnName, sheetName, MessageContants.MSG_021);
                    }
                }

                if ("email".equals(columnName)) {
                    Pattern pattern = Pattern.compile(AppContants.EMAIL_PATTERN);
                    if (!pattern.matcher(columnValue).matches()){
                        return AppUtils.getMessageVariable(rowColumn, columnName, sheetName, MessageContants.MSG_021);
                    }

                    if (userListDb.stream().anyMatch(u -> u.getEmail().equals(columnValue))) {
                        return AppUtils.getMessageVariable(rowColumn, columnName, sheetName, MessageContants.MSG_017);
                    }

                    if (userListImport.stream().anyMatch(u -> u.getEmail().equals(columnValue))) {
                        return AppUtils.getMessageVariable(rowColumn, columnName, sheetName, MessageContants.MSG_016);
                    }
                }
                return null;
            case BLANK:
                return AppUtils.getMessageVariable(rowColumn, columnName, sheetName, MessageContants.MSG_022);
            default:
                return null;
        }
    }
}
