package com.company.DataBase;


import com.company.DataBase.CustomTypes.DataTable;
import com.company.DataBase.CustomTypes.UniversalData;
import org.firebirdsql.extern.decimal.Decimal;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataBaseExecutor {

    int numOfDataTableFields; //мета
    ArrayList<String> names = new ArrayList(); //мета
    ResultSet primaryKeyNames; //мета
    String primaryKeyName; //мета

    public ResultSet getPrimaryKeyNames() { //метод для получения меты
        return primaryKeyNames;
    }

    public String getDatabaseURL() {  //метод для получения меты
        return databaseURL;
    }

    public String getPrimaryKeyName() { //метод для получения меты
        return primaryKeyName;
    }

    public String getTableName() { //метод для получения меты
        return tableName;
    }

    public int getNumOfDataTableFields() { //get-метод для получения мета-данных таблицы
        return numOfDataTableFields;
    }

    public ArrayList<String> getNames() { //get-метод для получения мета-данных таблицы
        return names;
    }

    String databaseURL; //мета
    String user; //мета
    String password; //мета
    String driverName; //мета
    String tableName; //мета


    public DataBaseExecutor() throws ClassNotFoundException, SQLException { //если не указано иначе, класс будет работать с трубами со стандартными настройками

        numOfDataTableFields = 99; //количество столбцов определяется само, 99 стоит что бы проверить (редко в таблицах 99 столбцов) алгоритм определения количества столбцов
        databaseURL = "jdbc:firebirdsql:LOCALHOST:D:/DataCrypt/DataTube/neoFile.FDB?charSet=UTF-8"; //путь к базе данных
        user = "sysdba";
        password = "masterkey";
        driverName = "org.firebirdsql.jdbc.FBDriver";
        tableName = "PIPE"; //таблица данных, с которой будем общаться

        metaUpdate(); //метаданные на момент создания включали только названия столбцов и их количество - эти параметры не меняются в процессе работы с базой данных
    }

    public DataBaseExecutor(String dataTableName) throws ClassNotFoundException, SQLException { //стандартные настройки, но для разных таблиц данных


        numOfDataTableFields = 99; //количество столбцов определяется само, 99 стоит что бы проверить (редко в таблицах 99 столбцов) алгоритм определения количества столбцов
        databaseURL = "jdbc:firebirdsql:LOCALHOST:D:/DataCrypt/DataTube/neoFile.FDB?charSet=UTF-8"; //путь к базе данных
        user = "sysdba";
        password = "masterkey";
        driverName = "org.firebirdsql.jdbc.FBDriver";


        tableName = dataTableName; //другое название таблицы данных

        metaUpdate(); //метаданные на момент создания включали только названия столбцов и их количество - эти параметры не меняются в процессе работы с базой данных
    }

    public DataBaseExecutor(String dataTableName, String databaseURL) throws ClassNotFoundException, SQLException { //стандартные настройки, но для разных таблиц данных


        numOfDataTableFields = 99; //количество столбцов определяется само, 99 стоит что бы проверить (редко в таблицах 99 столбцов) алгоритм определения количества столбцов
        this.databaseURL = databaseURL; //путь к базе данных
        user = "sysdba";
        password = "masterkey";
        driverName = "org.firebirdsql.jdbc.FBDriver";


        tableName = dataTableName; //другое название таблицы данных

        metaUpdate(); //метаданные на момент создания включали только названия столбцов и их количество - эти параметры не меняются в процессе работы с базой данных
    }


    void DataBaseExecutor(int numOfDataTableFields, String databaseURL, String dataTableName, String user, String password, String driverName) { //полная кастомизация класса

    }

    private void metaUpdate() throws ClassNotFoundException, SQLException { //обновление метаданных таблицы. на момент создания обновляла лишь названия полей и их количество
        //+++метаданные+++
        Class.forName(driverName);
        Connection connection = DriverManager.getConnection(databaseURL, user, password);
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM " + tableName);
        ResultSetMetaData metaRes = result.getMetaData(); //метаданные базы данных
        numOfDataTableFields = metaRes.getColumnCount(); //определение количества столбцов

        DatabaseMetaData metaData = connection.getMetaData();
        primaryKeyNames = metaData.getPrimaryKeys(null, null, tableName);  //todo надо автоматизировать получение ключевого поля, что бы не передавать его для сортировки в getDataTableAttribute
        primaryKeyName = primaryKeyNames.toString();
        //System.out.println("lalalallala" + primaryKeyName);

        names.clear();
        for (int i = 0; i < numOfDataTableFields; i++) { //пробежка по всем колонкам
            names.add((metaRes.getColumnName(i + 1))); //получение имен столбцов //todo ЛоверКейс панацея ли? //удалил Ловеркейс
        }
        statement.close();
        connection.close();
    }

    public String compositeInsertRequest(ArrayList<String> fields, String tableName) { //собирает строку-запрос для добавления новой записи. создан для универсальности
        String mask = "";
        String request = "Insert into " + tableName + "(";
        for (int i = 0; i < fields.size(); i++) {
            request += fields.get(i); //строка запроса
            mask += "?"; //маска это та часть запроса, что состоит из вопросов

            if (i + 1 != fields.size()) {
                request += ",";
                mask += ",";
            }
        }
        request += ")values(" + mask + ");";


        return request;
    }

    public void insertDataRec(UniversalData record) //добавление в базу данных одной строки (новой записи)

            throws IOException {

        try {
            ArrayList recordedData = new ArrayList<String>();


            Class.forName(driverName);
            Connection connection = DriverManager.getConnection(databaseURL, user, password);
            Statement statement = connection.createStatement();


            String query = compositeInsertRequest(names, tableName); //универсальный запрос
           // String query = "Insert INTO " + tableName + "(T, DENSITY, VISC_DNC, HT_CAPACITY)"+ "VALUES" + "('1','1','1','1')";
            System.out.println();
            System.out.println("Names is __________"+names);
            System.out.println("Values is __________"+record.getValues());
            PreparedStatement pstmt = connection.prepareStatement(query);
            for (int i = 0; i < record.getNames().size(); i++) { //создание связи используя каждый столбец
                System.out.println("Tempus="+names.get(i));
                String tempus =record.get(names.get(i));

              //  System.out.println("TempusValue="+tempus ); //проверка на Темпус, если вылазит NullPointerException, важно что бы все названия полей были капсом в мета-аптейдете.
              //   System.out.println("Test="+record.get("id"));

                pstmt.setString(i + 1, tempus.toString());
                recordedData.add(record.get(names.get(i)).toString());
            }

            int x = pstmt.executeUpdate();

            if (x == 1) {

                System.out.println("Data upload successfully " + names + " = " + recordedData);
            }

            statement.close();
            connection.close();


        } catch (Exception e) {

            System.out.println("Request rejected");

            e.printStackTrace();
        }


    }


    public HttpServletRequest getDataTableAttribute(HttpServletRequest req, String attributeName, String sortBy, String primaryKey) {

        DataTable allPipes = new DataTable(); //хешмеп для хранения данных базы данных (таблицы)
        try {

            Class.forName(driverName);
            Connection connection = DriverManager.getConnection(databaseURL, user, password);
            Statement statement = connection.createStatement();


            ResultSet result = statement.executeQuery("SELECT * FROM " + tableName + " ORDER BY " + sortBy);


            while (result.next()) { //проход по всем строкам
                UniversalData pipe = new UniversalData(names); // объект-строка таблицы
                for (int i = 0; i < numOfDataTableFields; i++) { //пробежка по всем колонкам
                    pipe.set(names.get(i), result.getString(i + 1));
                }
                allPipes.set(result.getString(primaryKey), pipe); //пробежались по всей строке - закидываем строку в таблицу и так с каждой строкой //todo надо как-то определять ключевое поле, а не брать первый столбец
                //System.out.println(pipe.get(names.get(2)));
            }
            statement.close();
            connection.close();
            req.setAttribute(attributeName, allPipes); //закидывание данных в Атрибуты для последующего использования данных в другом сервлете

        } catch (Exception e) {
            System.out.println("Request rejected");
            e.printStackTrace();
        }

        return req;
        // return (HashMap<Integer, UniversalData>) allPipes;
    }


    public LinkedHashMap<Integer, UniversalData> getFiltredDataTable(String fieldName, String value) { //простой метод для отображения отфильтрованной таблицы данных (можно использовать как отображение единичной записи, если использовать id)

        Map allPipes = new LinkedHashMap<Integer, UniversalData>(); //хешмеп для хранения данных базы данных (таблицы)
        try {
            Class.forName(driverName);
            Connection connection = DriverManager.getConnection(databaseURL, user, password);
            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery("SELECT * FROM " + tableName + " WHERE " + fieldName + " = " + value);


            while (result.next()) { //проход по всем строкам
                UniversalData pipe = new UniversalData(names); // объект-строка таблицы
                for (int i = 0; i < numOfDataTableFields; i++) { //пробежка по всем колонкам
                    pipe.set(names.get(i), result.getString(i + 1));
                }
                allPipes.put(result.getString(1), pipe); //пробежались по всей строке - закидываем строку в таблицу и так с каждой строкой
                //  System.out.println(pipe.get(names.get(2)));
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Request rejected");
            e.printStackTrace();
        }

        return (LinkedHashMap<Integer, UniversalData>) allPipes;
        // return (HashMap<Integer, UniversalData>) allPipes;
    }

    public UniversalData getRecordById(String keyFieldName, String keyFieldValue) { //простой метод для отображения отфильтрованной таблицы данных (можно использовать как отображение единичной записи, если использовать id)

        UniversalData pipe = new UniversalData(names); // объект-строка таблицы

        try {
            Class.forName(driverName);
            Connection connection = DriverManager.getConnection(databaseURL, user, password);
            Statement statement = connection.createStatement();

            String SQL = "SELECT * FROM " + tableName + " WHERE " + keyFieldName + " = " + keyFieldValue;
            System.out.println(SQL);
            ResultSet result = statement.executeQuery(SQL); //поиск по id

            while (result.next()) { //проход по всем строкам
                for (int i = 0; i < numOfDataTableFields; i++) { //поиск нужной колонки
                    pipe.set(names.get(i), result.getString(i + 1));
                }

                //  System.out.println(pipe.get(names.get(2)));
            }
            statement.close();
            connection.close();

            if (pipe.get(keyFieldName) == "value has not set") //если данные не вернулись (точнее вернулось "value has not set", то мы это дело обрабатываем как ошибку
                throw new Exception();

        } catch (Exception e) {
            System.out.println("Request rejected" + e.toString());
            System.out.println(e.getMessage());
            //e.printStackTrace();
            return null;

        }
        return pipe;

        // return (HashMap<Integer, UniversalData>) allPipes;
    }


    public void updateDataRec(String keyFirldValue, String keyFieldName, String fieldToUpdate, String newValue) //изменение в базе данных одной строки (записи)
            throws IOException {
        try {

            Class.forName(driverName);
            Connection connection = DriverManager.getConnection(databaseURL, user, password);
            Statement statement = connection.createStatement();

            String SQL = "UPDATE " + tableName + " SET " + fieldToUpdate + " = '" + newValue + "' WHERE " + keyFieldName + " = " + keyFirldValue;
            System.out.println(SQL);
            int x = statement.executeUpdate(SQL);
            if (x == 1) {

                System.out.println("Data update successfully " + keyFieldName + "=[" + keyFirldValue + "]");
            }

            statement.close();
            connection.close();


        } catch (Exception e) {

            System.out.println("Request rejected. " + keyFieldName + "[" + keyFirldValue + "] can`t be updated");
            e.printStackTrace();
        }


    }

    public void updateDataRec(String keyFirldValue, String keyFieldName, String fieldToUpdate, BigDecimal newValue) //изменение в базе данных одной строки (записи)
            throws IOException {
        try {

            Class.forName(driverName);
            Connection connection = DriverManager.getConnection(databaseURL, user, password);
            Statement statement = connection.createStatement();

            String SQL = "UPDATE " + tableName + " SET " + fieldToUpdate + " = '" + newValue + "' WHERE " + keyFieldName + " = " + keyFirldValue;
            System.out.println(SQL);
            int x = statement.executeUpdate(SQL);
            if (x == 1) {

                System.out.println("Data update successfully " + keyFieldName + "=[" + keyFirldValue + "]");
            }

            statement.close();
            connection.close();


        } catch (Exception e) {

            System.out.println("Request rejected. " + keyFieldName + "[" + keyFirldValue + "] can`t be updated");
            e.printStackTrace();
        }


    }

    public void updateDataRec(String keyFirldValue, String keyFieldName, String fieldToUpdate, Double newValue) //изменение в базе данных одной строки (записи)
            throws IOException {
        try {

            Class.forName(driverName);
            Connection connection = DriverManager.getConnection(databaseURL, user, password);
            Statement statement = connection.createStatement();

            String SQL = "UPDATE " + tableName + " SET " + fieldToUpdate + " = '" + newValue + "' WHERE " + keyFieldName + " = " + keyFirldValue;
            System.out.println(SQL);
            int x = statement.executeUpdate(SQL);
            if (x == 1) {

                System.out.println("Data update successfully " + keyFieldName + "=[" + keyFirldValue + "]");
            }

            statement.close();
            connection.close();


        } catch (Exception e) {

            System.out.println("Request rejected. " + keyFieldName + "[" + keyFirldValue + "] can`t be updated");
            e.printStackTrace();
        }


    }

    public void updateDataRec(int keyFirldValue, String keyFieldName, String fieldToUpdate, double newValue) throws ClassNotFoundException, SQLException //изменение в базе данных одной строки (записи)
    {

        Class.forName(driverName);
        Connection connection = DriverManager.getConnection(databaseURL, user, password);
        Statement statement = connection.createStatement();

        String SQL = "UPDATE " + tableName + " SET " + fieldToUpdate + " = '" + newValue + "' WHERE " + keyFieldName + " = " + keyFirldValue;
        System.out.println(SQL);
        int x = statement.executeUpdate(SQL);
        if (x == 1) {

            System.out.println("Data update successfully " + keyFieldName + "=[" + keyFirldValue + "]");
        }
    }

    public void deleteDataRec(String keyFieldName, String keyFieldValue) //удаление в базе данных одной строки (записи)
            throws IOException {
        try {

            Class.forName(driverName);
            Connection connection = DriverManager.getConnection(databaseURL, user, password);
            Statement statement = connection.createStatement();

            String SQL = "DELETE FROM " + tableName + " WHERE " + keyFieldName + " = " + keyFieldValue;

            int x = statement.executeUpdate(SQL);
            if (x == 1) {

                System.out.println("Data delete successfully " + keyFieldName + "=[" + keyFieldValue + "]");
            }
            statement.close();
            connection.close();


        } catch (Exception e) {

            System.out.println("Request rejected. " + keyFieldName + "[" + keyFieldValue + "] not found");
            e.printStackTrace();
        }


    }

    public void directSQLRequest(String SQL) //прямой запрос к базе данных
            {
                try {
                    Class.forName(driverName);

                    Connection connection = DriverManager.getConnection(databaseURL, user, password);
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(SQL);
                    statement.close();
                    connection.close();


                } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }


}
