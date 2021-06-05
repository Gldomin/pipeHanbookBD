
package com.company.DataBase.DataBaseFunctianalServlets;

import com.company.DataBase.DataBaseExecutor;
import com.company.DataBase.CustomTypes.UniversalData;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DataBaseUpdateServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        PrintWriter pw = res.getWriter();
        String usingDataTable = req.getParameter("usingDataTable"); //usingDataTable - имя атрибута хранящего данные, с которыми нужно работать

        String keyFieldName = req.getParameter("keyFieldName");


        String fieldToUpdateName = req.getParameter("fieldToUpdateName");
        String fieldToUpdateValue = req.getParameter("fieldToUpdateValue");

        pw.println("<html>");
        pw.println("<Center> Hello, It`s " + this.getClass().getName() + "!" + "</Center>");
        try {


            DataBaseExecutor updater = new DataBaseExecutor(usingDataTable);

            UniversalData newData = new UniversalData();

           /* for (String i :
                    updater.getNames()) {
                newData.set((String)i,req.getParameter((String)i));
                System.out.println("Parameter getted:" + i +" = " +req.getParameter((String)i));
            }*/
            String keyFieldValue = req.getParameter(keyFieldName);
            System.out.println(keyFieldName + "=" + keyFieldValue);
            UniversalData tempusData = updater.getRecordById(keyFieldName, keyFieldValue); //данные из БД
            if (tempusData == null) { //проверка на существование элемента в БД
                pw.println("Request rejected. Element with " + keyFieldName + "=[" + keyFieldValue + "] don`t created");


            } else {

                pw.println("<table border=\"1\">");
                pw.println("<tr>");
                for (int i = 0; i < tempusData.getNames().size(); i++)
                    pw.println("<th>" + tempusData.getNames().get(i) + "</th>"); //названия столбцов
                pw.println("</tr>");


                pw.println("<tr>");
                for (int i = 0; i < tempusData.getNames().size(); i++)
                    pw.println("<th>" + tempusData.get((String) tempusData.getNames().get(i)) + "</th>"); //значения

                pw.println("</tr>");
                pw.println("</table>");


                updater.updateDataRec(keyFieldValue, keyFieldName, fieldToUpdateName, fieldToUpdateValue);  //метод для записи данных в БД

                UniversalData updatedData = updater.getRecordById(keyFieldName, keyFieldValue); //необходимо обновить данные из бд, что бы отобразить их

                pw.println("Request accepted. Element with keyField=[" + keyFieldValue + "] updated. new element:");

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