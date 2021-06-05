package com.company.DataBase;

import com.company.DataBase.CustomTypes.DataTable;
import com.company.DataBase.CustomTypes.UniversalData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class DataTableWriter { //класс строющий таблицу данных html. данные берет из Реквеста
    public static void writeTable(HttpServletRequest req, HttpServletResponse resp, String dataAttrName) { //dataAttrName - имя атрибута хранящего данные полученные DBE


        try {
            resp.getWriter().println(dataAttrName + " data view:");

            //System.out.println("allPipesAttribute= " + (HashMap)req.getAttribute("pipesData"));

            DataTable allPipes = (DataTable) req.getAttribute(dataAttrName);

            // System.out.println(allPipes.values());
            if (req.getAttribute(dataAttrName) != null) {

                ArrayList fields = allPipes.getDataTableFields();
                System.out.println("Table \""+dataAttrName+"\" writing... fields = " + fields);

                ArrayList names = fields;


                resp.getWriter().println("<table border=\"1\">");
                resp.getWriter().println("<tr>");
                for (int i = 0; i < names.size(); i++)
                    resp.getWriter().println("<th> " + names.get(i) + " </th>"); //шапка таблицы

                resp.getWriter().println("</tr>");


                for (Object i : allPipes.getNames()) {
                    //System.out.println("object " + i.toString());
                    UniversalData pipe = (UniversalData) allPipes.get((String) i);
                    resp.getWriter().println("<tr>");

                    for (int j = 0; j < ((UniversalData) allPipes.get((String) i)).getNames().size(); j++) { //каждое поле заполняем. количество полей = количество имен полей
                        resp.getWriter().println("<th>");
                        resp.getWriter().print(pipe.get((String) fields.get(j)));
                        //resp.getWriter().print(pipe.get((String) names.get(0)));
                        resp.getWriter().println("</th>");
                    }
                    resp.getWriter().println("</tr>");


                }

                resp.getWriter().println("</table>");
            }
            System.out.println("Table \""+dataAttrName+"\" writed sucsesfull.");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}


