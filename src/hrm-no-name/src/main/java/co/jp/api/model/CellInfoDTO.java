package co.jp.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.ss.usermodel.CellType;

@Data
@AllArgsConstructor
public class CellInfoDTO {
    public String name;
    public CellType cellType;
}
