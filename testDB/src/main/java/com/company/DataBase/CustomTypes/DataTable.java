package com.company.DataBase.CustomTypes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class DataTable { //объект для хранения таблиц данных. по-сути хешмеп с хранящимеся в нем UniversalData
    private Map data = new LinkedHashMap<String, UniversalData>(); //хешмеп через который происходит Связка полей и их имен



    public DataTable(List fieldsNames, List fieldsValues) { //конструктор задает первоначальные данные


        for (int i = 0; i < fieldsNames.size(); i++) {

            data.put((String) fieldsNames.get(i), (UniversalData) fieldsValues.get(i)); //все поля хранятся в виде Хешмепа. Имена полей это ключи, значения полей - объекты хешмепа\
            //если в таблице данных 5 полей, то будет и 5 элементов Hasmap
        }
    }

    public DataTable(List fieldsNames) { //конструктор задает первоначальные данные //OVERLOAD


        for (int i = 0; i < fieldsNames.size(); i++) {

            data.put((String) fieldsNames.get(i), null);
        }
    }

    public DataTable() { //пустой конструктор //OVERLOAD
    }

    public UniversalData get(String fieldName) { //метод получения элемента по его названию
    return (UniversalData) data.get(fieldName);
    }

    public void set(String fieldName, UniversalData fieldValue) { //метод занесение нового элемента по его названию
       data.put(fieldName,fieldValue);
    }

    public ArrayList getNames() {
        ArrayList<Integer> keyList = new ArrayList<Integer>(data.keySet());

        return keyList;
    }

    public ArrayList getDataTableFields() {
       UniversalData tempus = (UniversalData) data.get(data.keySet().toArray()[0]);


        return tempus.getNames();
    }


    public ArrayList getValues() {return (ArrayList) data.values();}
}
