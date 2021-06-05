
package com.company.DataBase.DataBaseFunctianalServlets;

import com.company.DataBase.DataBaseExecutor;
import com.company.DataBase.CustomTypes.UniversalData;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DataBaseDeleteServlet extends HttpServlet {
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {

        PrintWriter pw = httpServletResponse.getWriter();

        String keyFieldName = (httpServletRequest.getParameter("keyFieldName"));
        String keyFieldValue = (httpServletRequest.getParameter("keyFieldValue"));
        String usingDataTable = (httpServletRequest.getParameter("usingDataTable"));

        pw.println("<html>");
        pw.println("<Center> Hello, It`s " + this.getClass().getName() + "!" + "</Center>");

        try {


            DataBaseExecutor deleter = new DataBaseExecutor(usingDataTable);

            UniversalData data = deleter.getRecordById(keyFieldName, keyFieldValue);

            if (data == null)
                pw.println("Request rejected. Element with " + keyFieldName + "=[" + keyFieldValue + "] not found"); //проверка на существование элемента в БД
            else {
                pw.println("Request accepted. Element with " + keyFieldName + "=[" + keyFieldValue + "] deleted. Deleted element:");
                pw.println("<table border=\"1\">");

                pw.println("<tr>");
                for (int i = 0; i < data.getNames().size(); i++)
                    pw.println("<th>" + data.getNames().get(i) + "</th>"); //названия столбцов
                pw.println("</tr>");


                pw.println("<tr>");
                for (int i = 0; i < data.getNames().size(); i++)
                    pw.println("<th>" + data.get((String) data.getNames().get(i)) + "</th>"); //значения

                pw.println("</tr>");

                pw.println("</table>");

                deleter.deleteDataRec(keyFieldName, keyFieldValue);  //метод для записи данных в БД
            }


        } catch (Exception e) {
            pw.println("Request rejected");
            e.printStackTrace();
        }
        pw.println("                <form action=\"main\" >\n" +
                "                    <button>Go to main</button>\n" +
                "                </form>");
        pw.println("</html>");
    }

}