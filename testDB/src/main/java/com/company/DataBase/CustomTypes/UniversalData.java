package com.company.DataBase.CustomTypes;

import java.util.*;


public class UniversalData {
    private Map data = new LinkedHashMap(); //хешмеп через который происходит Связка полей и их имен



    public UniversalData(List fieldsNames, List fieldsValues) { //конструктор задает первоначальные данные


        for (int i = 0; i < fieldsNames.size(); i++) {

            data.put((String) fieldsNames.get(i), (String) fieldsValues.get(i)); //все поля хранятся в виде Хешмепа. Имена полей это ключи, значения полей - объекты хешмепа\
            //если в таблице данных 5 полей, то будет и 5 элементов Hasmap
        }
    }

    public UniversalData(List fieldsNames) { //конструктор задает первоначальные данные //OVERLOAD


        for (int i = 0; i < fieldsNames.size(); i++) {

            data.put((String) fieldsNames.get(i), "value has not set");
        }
    }

    public UniversalData() { //конструктор задает первоначальные данные //OVERLOAD
    }

    public String get(String fieldName) { //метод получения элемента по его названию
    return (String) data.get(fieldName);
    }

    public void set(String fieldName, String fieldValue) { //метод занесение нового элемента по его названию
       data.put(fieldName,fieldValue);
    }

    public ArrayList getNames() {
        ArrayList<Integer> keyList = new ArrayList<Integer>(data.keySet());

        return keyList;
    }

    public ArrayList getValues() {
        ArrayList<Integer> dataList = new ArrayList<Integer>(data.values());
        return dataList;
        } //TODO сделать так же как получение имен
}
