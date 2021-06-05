package com.company;

import com.company.DataBase.DataBaseExecutor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String keyValue="1.0";
        String pValue="50.0";
        String viscosity="30.0";
        String keyName="t";
        Scanner in = new Scanner(System.in);
        while (true){
        try {
            DataBaseExecutor exe = new DataBaseExecutor("water");
            System.out.println("TableName:"+exe.getTableName());
            System.out.println("Names:"+exe.getNames());

            System.out.println(keyName+"="+keyValue+";"+exe.getRecordById(keyName,keyValue).getValues());
            System.out.println("Силен не тот кто силен, силен тот кто может быть силен когда он не силен. Ауф!");



       // System.out.print("Input "+keyName+".. ");
      //  keyValue=in.nextLine();
        System.out.print("Input value [p].. ");
        pValue=in.nextLine();
        //System.out.print("Input value [viscosity].. ");
        //viscosity=in.nextLine();
        exe.updateDataRec(keyName,keyValue,"p",pValue.toString());
        exe.directSQLRequest("update ");
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
