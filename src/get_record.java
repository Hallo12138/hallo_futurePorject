
import com.icbc.api.IcbcApiException;
import com.icbc.api.internal.util.internal.util.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class get_record extends HttpServlet {//响应前端查询流水
    URLParser parser = new URLParser();
    Communication communication = new Communication();
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String url = request.getRequestURI();
        String card = parser.getParamByUrl(url, "card");
        String date_from = parser.getParamByUrl(url, "from");
        String date_to = parser.getParamByUrl(url, "to");
        String result = new String();
        try {
            result = communication.look_up_record(card,date_from,date_to);
        } catch (IcbcApiException e) {
            e.printStackTrace();
        }
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        if(!result.equals("error")) {
            String detail = result.split("The sum of the orders:")[1].split("\n")[0];
            String returnObject = "{ \"returnCode\":\"0\",\"ordersum\":\""+detail+"\"}";
            out.println(returnObject);
        }
        else {
            String returnObject = "{ \"returnCode\":\"1\"}";
            out.println(returnObject);
        }
        /*
         * We are going to perform the same operations for POST requests
         * as for GET methods, so this method just sends the request to
         * the doGet method.
         */
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        doGet(request, response);
    }

    public static void main(String[] args) {
        String result = "Time:20181123\n" +
                "The sum of the orders:0\n" +
                "The selected time has 0 orders";
        String detail = result.split("The sum of the orders:")[1].split("\n")[0];
        String returnObject = "{ \"returnCode\":\"0\",\"orderSum\":\""+detail+"\"}";
        JSON json = JSON.parseObject(returnObject);
        System.out.println(" ");
    }
}
