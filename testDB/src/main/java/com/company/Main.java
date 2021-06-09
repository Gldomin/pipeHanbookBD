package com.company;

import com.company.DataBase.DataBaseExecutor;
import org.firebirdsql.extern.decimal.Decimal;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String keyValue1="1";
        String keyValue2="2";
        String keyValue3="3";
        String pValueString="60.123456";
        double pValueDouble=60.123456;
        BigDecimal pValueDecimal= BigDecimal.valueOf(60.123456);
        String viscosity="30.0";
        String keyName="t";
        Scanner in = new Scanner(System.in);
        while (true){ //надо попробовать с decimalType (test datatable)
        try {
            DataBaseExecutor exe = new DataBaseExecutor("test3");
            System.out.println("TableName:"+exe.getTableName());
            System.out.println("Names:"+exe.getNames());

            System.out.println("String[1]:"+keyName+"="+keyValue1+";"+exe.getRecordById(keyValue1,keyName).getValues());
            System.out.println("Double[2]:"+keyName+"="+keyValue2+";"+exe.getRecordById(keyValue2,keyName).getValues());
            System.out.println("Decimal[3]:"+keyName+"="+keyValue3+";"+exe.getRecordById(keyValue3,keyName).getValues());




       // System.out.print("Input "+keyName+".. ");
      //  keyValue=in.nextLine();
        System.out.print("Input value [p].. ");
            pValueString=in.nextLine();
        //System.out.print("Input value [viscosity].. ");
        //viscosity=in.nextLine();
            pValueString="60.123456";

            //To String
            exe.updateDataRec(keyValue1,keyName,"stringp",pValueString.toString());   //[1]String
            exe.updateDataRec(keyValue2,keyName,"stringp",pValueDouble);              //[2]Double
            exe.updateDataRec(keyValue3,keyName,"stringp",pValueDecimal);             //[3]Decimal
            //To double
            exe.updateDataRec(keyValue1,keyName,"floatp",pValueString.toString());   //[1]String
            exe.updateDataRec(keyValue2,keyName,"floatp",pValueDouble);              //[2]Double
            exe.updateDataRec(keyValue3,keyName,"floatp",pValueDecimal);             //[3]Double
            //To decimal 10,4 (10 размер, 4 скалирование)
            exe.updateDataRec(keyValue1,keyName,"decimalp_10_4",pValueString.toString());        //[1]String
            exe.updateDataRec(keyValue2,keyName,"decimalp_10_4",pValueDouble);                   //[2]Double
            exe.updateDataRec(keyValue3,keyName,"decimalp_10_4",pValueDecimal);                  //[2]Double
            //To decimal 5,4
            exe.updateDataRec(keyValue1,keyName,"decimalp_5_4",pValueString.toString());            //[1]String
            exe.updateDataRec(keyValue2,keyName,"decimalp_5_4",pValueDouble);                       //[2]Double
            exe.updateDataRec(keyValue3,keyName,"decimalp_5_4",pValueDecimal);                       //[2]Double
            //To decimal 3,3
            exe.updateDataRec(keyValue1,keyName,"decimalp_3_3",pValueString.toString());             //[1]String
            exe.updateDataRec(keyValue2,keyName,"decimalp_3_3",pValueDouble);                        //[2]Double
            exe.updateDataRec(keyValue3,keyName,"decimalp_3_3",pValueDecimal);                       //[2]Double
            //To numeric 10,4 (10 размер, 4 скалирование)
            exe.updateDataRec(keyValue1,keyName,"numericp_10_4",pValueString.toString());           //[1]String
            exe.updateDataRec(keyValue2,keyName,"numericp_10_4",pValueDouble);                        //[2]Double
            exe.updateDataRec(keyValue3,keyName,"numericp_10_4",pValueDecimal);                       //[2]Double
            //To numeric 5,4
            exe.updateDataRec(keyValue1,keyName,"numericp_5_4",pValueString.toString());            //[1]String
            exe.updateDataRec(keyValue2,keyName,"numericp_5_4",pValueDouble);                        //[2]Double
            exe.updateDataRec(keyValue3,keyName,"numericp_5_4",pValueDecimal);                     //[2]Double
            //To numeric 3,3
            exe.updateDataRec(keyValue1,keyName,"numericp_3_3",pValueString.toString());            //[1]String
            exe.updateDataRec(keyValue2,keyName,"numericp_3_3",pValueDouble);                       //[2]Double
            exe.updateDataRec(keyValue3,keyName,"numericp_3_3",pValueDecimal);                     //[2]Double


        //exe.directSQLRequest("update ");
        } catch (ClassNotFoundException e) {	System.out.println("exception catched");
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

    }
}
