package com.company;

import com.company.DataBase.DataBaseExecutor;
import org.firebirdsql.extern.decimal.Decimal;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String keyValue1="1.0";
        String keyValue2="2.0";
        String keyValue3="3.0";
        String pValueString="60.123400";
        double pValueDouble=60.123400;
        BigDecimal pValueDecimal= BigDecimal.valueOf(60.123400);
        String viscosity="30.0";
        String keyName="t";
        Scanner in = new Scanner(System.in);
        while (true){ //надо попробовать с decimalType (test datatable)
        try {
            DataBaseExecutor exe = new DataBaseExecutor("water");
            System.out.println("TableName:"+exe.getTableName());
            System.out.println("Names:"+exe.getNames());

            System.out.println("String[1]:"+keyName+"="+keyValue1+";"+exe.getRecordById(keyName,keyValue1).getValues());
            System.out.println("Double[2]:"+keyName+"="+keyValue2+";"+exe.getRecordById(keyName,keyValue2).getValues());
            System.out.println("Decimal[3]:"+keyName+"="+keyValue3+";"+exe.getRecordById(keyName,keyValue3).getValues());




       // System.out.print("Input "+keyName+".. ");
      //  keyValue=in.nextLine();
        System.out.print("Input value [p].. ");
            pValueString=in.nextLine();
        //System.out.print("Input value [viscosity].. ");
        //viscosity=in.nextLine();
        exe.updateDataRec(keyName,keyValue1,"p2",pValueString.toString());   //String
        exe.updateDataRec(keyName,keyValue2,"p2",pValueDouble);                    //Double
        exe.updateDataRec(keyName,keyValue3,"p2",pValueDecimal);                    //Decimal
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
