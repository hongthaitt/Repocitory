package common;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static java.sql.JDBCType.BOOLEAN;
import static java.sql.JDBCType.NUMERIC;
import static javax.management.openmbean.SimpleType.STRING;
import static jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType.BLANK;

public class Test {
    private FileInputStream fis;
    private FileOutputStream fileOut;
//    private Workbook wb;
    private Sheet sh;
    private Cell cell;
    private Row row;
    private CellStyle cellstyle;
    private Color mycolor;
    private String excelFilePath;
    //    private Map<String, Integer> columns = new HashMap<>();
    private Map<String, Integer> columns = new HashMap<>();
    public Object[][] getExcelData(String fileName, String sheetName, String x) throws Exception {
        Object[][] data = null;
        Workbook workbook = null;
        FileInputStream fis = new FileInputStream(fileName);
        workbook = new XSSFWorkbook(fis);
         sh = workbook.getSheet(sheetName);
         row = sh.getRow(0);
        //
        int noOfRows = sh.getPhysicalNumberOfRows();
        int noOfCols = row.getLastCellNum();

        System.out.println("Row: " + noOfRows + " - Column: " + noOfCols);
//        System.out.println("StartRow: " + startRow + " - EndRow: " + endRow);
        data = new Object[noOfRows][1];
        Hashtable<String, String> table = null;
        for (int rowNums = 1; rowNums <= noOfRows; rowNums++) {
            table = new Hashtable<>();
            for (int colNum = 0; colNum < noOfCols; colNum++) {
                table.put(getCellData(0, colNum), getCellData(rowNums, colNum));
            }
            data[rowNums][0] = table;
        }
        return data;
    }

    public String getCellData(int rownum, int colnum) throws Exception{
        try{
            cell = sh.getRow(rownum).getCell(colnum);
            String CellData = null;
            switch (cell.getCellType()){
                case STRING:
                    CellData = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell))
                    {
                        CellData = String.valueOf(cell.getDateCellValue());
                    }
                    else
                    {
                        CellData = String.valueOf((long)cell.getNumericCellValue());
                    }
                    break;
                case BOOLEAN:
                    CellData = Boolean.toString(cell.getBooleanCellValue());
                    break;
                case BLANK:
                    CellData = "";
                    break;
            }
            return CellData;
        }catch (Exception e){
            return"";
        }
    }
}
