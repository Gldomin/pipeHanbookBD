
package com.company.DataBase.DataBaseFunctianalServlets;

import com.company.DataBase.DataBaseExecutor;
import com.company.DataBase.CustomTypes.UniversalData;

import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class DataBaseInsertServlet extends HttpServlet {
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {

        PrintWriter pw = httpServletResponse.getWriter();
        String usingDataTable = httpServletRequest.getParameter("usingDataTable"); //usingDataTable - имя атрибута хранящего данные, с которыми нужно работать

        String keyFieldName = httpServletRequest.getParameter("keyFieldName");
        String keyFieldValue; // = httpServletRequest.getParameter("keyFieldValue");


        pw.println("<html>");
        pw.println("<Center> Hello, It`s " + this.getClass().getName() + "!" + "</Center>");
        try {


            DataBaseExecutor inserter = new DataBaseExecutor(usingDataTable);

            UniversalData newData = new UniversalData();

            for (String i :
                    inserter.getNames()) {
                newData.set((String)i,httpServletRequest.getParameter((String)i));
                System.out.println("Parameter getted:" + i +" = " +httpServletRequest.getParameter((String)i));
            }

            keyFieldValue=httpServletRequest.getParameter(keyFieldName);
            UniversalData tempusData = inserter.getRecordById(keyFieldName, keyFieldValue); //данные из БД
            if (tempusData != null) { //проверка на существование элемента в БД
                pw.println("Request rejected. Element with keyField=[" + keyFieldValue + "] has was created");


            } else {
                inserter.insertDataRec(newData);  //метод для записи данных в БД

                UniversalData updatedData = inserter.getRecordById(keyFieldName, keyFieldValue); //необходимо обновить данные из бд, что бы отобразить их

                pw.println("Request accepted. Element with keyField=[" + keyFieldValue + "] added. new element:");

                pw.println("<table border=\"1\">");
                pw.println("<tr>");
                for (int i = 0; i < updatedData.getNames().size(); i++)
                    pw.println("<th>" + updatedData.getNames().get(i) + "</th>"); //названия столбцов
                pw.println("</tr>");


                pw.println("<tr>");
                for (int i = 0; i < updatedData.getNames().size(); i++)
                    pw.println("<th>" + updatedData.get((String) updatedData.getNames().get(i)) + "</th>"); //значения

                pw.println("</tr>");

                pw.println("</table>");


            }


        } catch (Exception e) {
            pw.println("<p>Upload data decline. You can check your values in table upper there &#8593; &#8593; &#8593;</p>");
            System.out.println("Data upload diceline");
            pw.println("<center><font color = \"red\">" + "Wrong parameters. Check it." + "</font></center>");

            e.printStackTrace();
        }
        pw.println("                <form action=\"main\" >\n" +
                "                    <button>Go to main</button>\n" +
                "                </form>");
        pw.println("</html>");
    }

}