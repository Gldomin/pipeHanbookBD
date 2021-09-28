package com.company;

import com.company.DataBase.CustomTypes.UniversalData;
import com.company.DataBase.DataBaseExecutor;
import org.firebirdsql.extern.decimal.Decimal;

import java.io.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

public class Main {
    static UniversalData testData;
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
     try {
            DataBaseExecutor DB= new DataBaseExecutor("WATER_TAB","jdbc:firebirdsql:LOCALHOST:D:/Desktop/neoFile.FDB?charSet=UTF-8");

            ArrayList fieldsNames = new ArrayList();
            fieldsNames.add("T");
            fieldsNames.add("DENSITY");
            fieldsNames.add("VISC_DNC");
            fieldsNames.add("HT_CAPACITY");
            ArrayList fieldsValues = new ArrayList();



        // Read XSL file
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File("D:/Desktop/HandBookDataOldedTest.xls"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Get the workbook instance for XLS file
        HSSFWorkbook workbook = null;
       try {
            workbook = new HSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get first sheet from the workbook
        HSSFSheet sheet = workbook.getSheetAt(0);

        // Get iterator to all the rows in current sheet
        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            // Get iterator to all cells of current row
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                // Change to getCellType() if using POI 4.x
                CellType cellType = cell.getCellType();

                switch (cellType) {
                    case _NONE:
//                        System.out.print("");
//                        System.out.print("\t");
                        break;
                    case BOOLEAN:
//                        System.out.print(cell.getBooleanCellValue());
//                        System.out.print("\t");
                        break;
                    case BLANK:
//                        System.out.print("");
//                        System.out.print("\t");
                        break;
                    case FORMULA:
                        // Formula
//                        System.out.print(cell.getCellFormula());
//                        System.out.print("\t");
//
//                        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                        // Print out value evaluated by formula
//                        System.out.print(evaluator.evaluate(cell).getNumberValue());
                        break;
                    case NUMERIC:
//                        System.out.print(cell.getNumericCellValue());
//                        System.out.print("\t");
                        break;
                    case STRING:

                        System.out.print(cell.getAddress() + " has   "+ cell.getStringCellValue());
                       if (cell.getRowIndex()>0){

                           fieldsValues.add(cell.getStringCellValue());
                        //tempData.set((String) fieldsNames.get(cell.getColumnIndex()), cell.getStringCellValue());
                        //заполняет Запись пока не дойдет до 4-го столбца, затем записывает запись в БД, а потом перезаписывает поверх старых данных новую запись. это повторяется
                        if(cell.getColumnIndex()==3) {
                            System.out.println();
                            System.out.println("I am ColumnIndex 3!");
                            System.out.println("FV__"+fieldsValues);
                            System.out.println("FN__"+fieldsNames);
                            UniversalData tempData = new UniversalData(fieldsNames,fieldsValues);
                            System.out.println();
                            System.out.println("TempDataNames:          "+tempData.getNames());
                            System.out.println("TempDataValues:          "+tempData.getValues());
                            DB.insertDataRec(tempData);
                            System.out.print( " NEW TAB ");
                            fieldsValues.clear();
                        }
                       }
                        System.out.print("\t");
                        break;
                    case ERROR:
                        System.out.print("!");
                        System.out.print("\t");
                        break;
                }

            }
            System.out.println("");
            //testBD();
        }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public static void testBD() {
        String keyValue1 = "1";
        String keyValue2 = "2";
        String keyValue3 = "3";
        String pValueString = "60.123457";
        double pValueDouble = 60.123457;
        BigDecimal pValueDecimal = BigDecimal.valueOf(60.123456);
        String viscosity = "30.0";
        String keyName = "t";
        Scanner in = new Scanner(System.in);
        while (true) { //надо попробовать с decimalType (test datatable)
            try {
                DataBaseExecutor exe = new DataBaseExecutor("test3");
                System.out.println("TableName:" + exe.getTableName());
                System.out.println("Names:" + exe.getNames());

                System.out.println("String[1]:" + keyName + "=" + keyValue1 + ";" + exe.getRecordById(keyValue1, keyName).getValues());
                System.out.println("Double[2]:" + keyName + "=" + keyValue2 + ";" + exe.getRecordById(keyValue2, keyName).getValues());
                System.out.println("Decimal[3]:" + keyName + "=" + keyValue3 + ";" + exe.getRecordById(keyValue3, keyName).getValues());


                // System.out.print("Input "+keyName+".. ");
                //  keyValue=in.nextLine();
                System.out.print("Input value [p].. ");
                pValueString = in.nextLine();
                //System.out.print("Input value [viscosity].. ");
                //viscosity=in.nextLine();
                pValueString = "60.123456";

                //To String
                exe.updateDataRec(keyValue1, keyName, "stringp", pValueString.toString());   //[1]String
                exe.updateDataRec(keyValue2, keyName, "stringp", pValueDouble);              //[2]Double
                exe.updateDataRec(keyValue3, keyName, "stringp", pValueDecimal);             //[3]Decimal
                //To double
                exe.updateDataRec(keyValue1, keyName, "floatp", pValueString.toString());   //[1]String
                exe.updateDataRec(keyValue2, keyName, "floatp", pValueDouble);              //[2]Double
                exe.updateDataRec(keyValue3, keyName, "floatp", pValueDecimal);             //[3]Double
                //To decimal 10,4 (10 размер, 4 скалирование)
                exe.updateDataRec(keyValue1, keyName, "decimalp_10_4", pValueString.toString());        //[1]String
                exe.updateDataRec(keyValue2, keyName, "decimalp_10_4", pValueDouble);                   //[2]Double
                exe.updateDataRec(keyValue3, keyName, "decimalp_10_4", pValueDecimal);                  //[2]Double
                //To decimal 5,4
                exe.updateDataRec(keyValue1, keyName, "decimalp_5_4", pValueString.toString());            //[1]String
                exe.updateDataRec(keyValue2, keyName, "decimalp_5_4", pValueDouble);                       //[2]Double
                exe.updateDataRec(keyValue3, keyName, "decimalp_5_4", pValueDecimal);                       //[2]Double
                //To decimal 3,3
                exe.updateDataRec(keyValue1, keyName, "decimalp_3_3", pValueString.toString());             //[1]String
                exe.updateDataRec(keyValue2, keyName, "decimalp_3_3", pValueDouble);                        //[2]Double
                exe.updateDataRec(keyValue3, keyName, "decimalp_3_3", pValueDecimal);                       //[2]Double
                //To numeric 10,4 (10 размер, 4 скалирование)
                exe.updateDataRec(keyValue1, keyName, "numericp_10_4", pValueString.toString());           //[1]String
                exe.updateDataRec(keyValue2, keyName, "numericp_10_4", pValueDouble);                        //[2]Double
                exe.updateDataRec(keyValue3, keyName, "numericp_10_4", pValueDecimal);                       //[2]Double
                //To numeric 5,4
                exe.updateDataRec(keyValue1, keyName, "numericp_5_4", pValueString.toString());            //[1]String
                exe.updateDataRec(keyValue2, keyName, "numericp_5_4", pValueDouble);                        //[2]Double
                exe.updateDataRec(keyValue3, keyName, "numericp_5_4", pValueDecimal);                     //[2]Double
                //To numeric 3,3
                exe.updateDataRec(keyValue1, keyName, "numericp_3_3", pValueString.toString());            //[1]String
                exe.updateDataRec(keyValue2, keyName, "numericp_3_3", pValueDouble);                       //[2]Double
                exe.updateDataRec(keyValue3, keyName, "numericp_3_3", pValueDecimal);                     //[2]Double


                //exe.directSQLRequest("update ");
            } catch (ClassNotFoundException e) {
                System.out.println("exception catched");
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
