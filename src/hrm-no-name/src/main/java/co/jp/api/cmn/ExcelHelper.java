package co.jp.api.cmn;

import co.jp.api.dao.UserDao;
import co.jp.api.entity.User;
import co.jp.api.service.ExcelApiService;
import co.jp.api.util.MessageContants;
import lombok.experimental.Helper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
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
        Map<String, List<User>> userMap = new HashMap<>();
        List<String> listMessage = new ArrayList<String>();
        Workbook workbook = new XSSFWorkbook(is);
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            String sheetName = sheet.getSheetName();
            List<User> userList = new ArrayList<>();
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
                    switch (cellIdx) {
                        case 0:
                            String getId = dataFormatter.formatCellValue(currentCell);
                            msgError = validateColumn(rowNumber, cellIdx, getId, CellType.NUMERIC, cellType, 0, 0);
                            if (msgError == null){
                                int id = Integer.parseInt(getId);
                                user.setId(id);
                            } else {
                                listMessage.add(msgError);
                            }
                            break;

                        case 1:
                            String getName = dataFormatter.formatCellValue(currentCell);
                            msgError = validateColumn(rowNumber, cellIdx, getName, CellType.STRING, cellType, 0, 0);
                            if (msgError == null){
                                user.setName(getName);
                            } else {
                                listMessage.add(msgError);
                            }
                            break;

                        case 2:
                            String getEmail = dataFormatter.formatCellValue(currentCell);
                            msgError = validateColumn(rowNumber, cellIdx, getEmail, CellType.STRING, cellType, 0, 0);
                            if (msgError == null){
                                user.setEmail(getEmail);
                            } else {
                                listMessage.add(msgError);
                            }
                            break;

                        case 3:
                            String getPassword = dataFormatter.formatCellValue(currentCell);
                            msgError = validateColumn(rowNumber, cellIdx, getPassword, CellType.STRING, cellType, 0, 0);
                            if (msgError == null){
                                user.setPassword(getPassword);
                            } else {
                                listMessage.add(msgError);
                            }
                            break;

                        case 4:
                            String getRole = dataFormatter.formatCellValue(currentCell);
                            msgError = validateColumn(rowNumber, cellIdx, getRole, CellType.NUMERIC, cellType, 1, 4);
                            if (msgError == null){
                                int role = Integer.parseInt(getRole);
                                user.setRolesId(role);
                            } else {
                                listMessage.add(msgError);
                            }
                            break;
                        case 5:
                            String getStatus = dataFormatter.formatCellValue(currentCell);
                            msgError = validateColumn(rowNumber, cellIdx, getStatus, CellType.NUMERIC, cellType, 1, 2);
                            if (msgError == null){
                                int status = Integer.parseInt(getStatus);
                                user.setRolesId(status);
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
            }
            userMap.put(sheet.getSheetName(), userList);
            MessageContants.messageImport.put(sheet.getSheetName(), listMessage);
        }
        workbook.close();
//        for (Map.Entry<String, List<User>> entry : userMap.entrySet()) {
//            String key = entry.getKey();
//            List<User> values = entry.getValue();
//            System.out.println("Key = " + key);
//            for (User user : values) {
//                System.out.println("Name = " + user.getName() + "n");
//                System.out.println("Email = " + user.getEmail() + "n");
//            }
//        }

      for (Map.Entry<String, List<String>> entry : MessageContants.messageImport.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();
            System.out.println("Key = " + key);
            for (String msg : values) {
                System.out.println(msg);
            }
        }
        return userMap;
    }
    public void validateDataObject(List<User> userList){
        for (User user : userList) {
            User userCheck = excelApiService.findByEmail(user.getEmail());
//            if (userCheck != null) {
//                MessageContants.messageImport.add(userList.indexOf(user) + "bi trung du lieu trong DB.");
//                return MessageContants.messageImport;
//            }
        }
    }
    public String validateColumn(Integer rowColumn,
                                 Integer colColumn,
                                 String columnValue,
                                 CellType cellTypeValidate,
                                 CellType cellType,
                                 Integer maxValue,
                                 Integer minValue) {
        String msg = null;
        switch (cellType) {
            case BOOLEAN:
                if (cellTypeValidate != cellType){
                    msg = rowColumn+"_"+colColumn+": Du lieu Boolean khong dung dinh dang.";
                }
                return msg;
            case NUMERIC:
                if (cellTypeValidate != cellType){
                    msg = rowColumn+"_"+colColumn+": Du lieu So khong dung dinh dang.";
                } else if(columnValue == "") {
                    msg = rowColumn+"_"+colColumn+": Du lieu dang bi trong.";
                } else {
                    int value = Integer.parseInt(columnValue);
                    if (maxValue != 0 && minValue != 0) {
                        if (value < minValue || value > maxValue){
                            msg = rowColumn+"_"+colColumn+": Gia tri khong dung dinh dang.";
                        }
                    }
                }
                return msg;
            case STRING:
                if (cellTypeValidate != cellType){
                    msg = rowColumn+"_"+colColumn+": Du lieu String khong dung dinh dang.";
                } else if(maxValue != 0 && minValue != 0) {
                    if(Pattern.matches(MessageContants.PASSWORD_PATTERN, columnValue)){
                        msg = rowColumn+"_"+colColumn+": Mat khau khong dung dinh dang.";
                    }
                }
                return msg;
            case BLANK:
                msg = rowColumn+"_"+colColumn+": Du lieu dang bi trong.";
                return msg;
            default:
                return null;
        }
    }
}
