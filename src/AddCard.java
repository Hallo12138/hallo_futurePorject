
import com.icbc.api.internal.util.internal.util.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AddCard extends HttpServlet {//º”»Î–¬ø®

    URLParser parser = new URLParser();
    Communication communication = new Communication();
    DatabaseConnection databaseConnection = new DatabaseConnection();
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String url = request.getRequestURI()+"?"+request.getQueryString();
        String card = parser.getParamByUrl(url, "cardNumber");
        String type = parser.getParamByUrl(url, "cardType");
        String password = parser.getParamByUrl(url, "password");
        String account = parser.getParamByUrl(url, "account");
        boolean result = false;
        result = databaseConnection.insertCards(card,password,account,type);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        if(result) {
            String returnObject = "{ \"returnCode\":\"0\"}";
            out.println(returnObject);
        }
        else {
            String returnObject = "{ \"returnCode\":\"1\"}";
            out.println(returnObject);
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        doGet(request, response);
    }

    public static void main(String[] args) {
        String result = "Time:20181123\nbalance:0\nunusableBalance:0\n";
        String balance = result.split("balance:")[1].split("\n")[0];
        String unusable_balance = result.split("unusableBalance:")[1].split("\n")[0];
        String returnObject = "{ \"returnCode\":\"0\",\"balance\":\""+balance+"\", \"unusableBalance\":\""+unusable_balance+"\"}";
        JSON json = JSON.parseObject(returnObject);
        System.out.println("he");
    }
}
