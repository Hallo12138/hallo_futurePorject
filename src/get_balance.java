import com.icbc.api.IcbcApiException;
import com.icbc.api.internal.util.internal.util.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class get_balance extends HttpServlet {//相应前端查询余额请求

    URLParser parser = new URLParser();
    Communication communication = new Communication();
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String url = request.getRequestURI()+"?"+request.getQueryString();
        String card = parser.getParamByUrl(url, "card");
        String result = new String();
        try {
            result = communication.look_up_balance(card);
        } catch (IcbcApiException e) {
            e.printStackTrace();
        }
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        if(!result.equals("error")) {
            String balance = result.split("balance:")[1].split("\n")[0];
            String unusable_balance = result.split("unusableBalance:")[1].split("\n")[0];
            String returnObject = "{ \"returnCode\":\"0\",\"balance\":\""+balance+"\", \"unusableBalance\":\""+unusable_balance+"\"}";
            out.println(returnObject);
        }
        else {
            String returnObject = "{ \"returnCode\":\"1\"}";
            out.println(returnObject);
        }
        /*response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset="+"GBK"+">");
        out.println("<title>Return code:</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h3>"+result+"</h3>");
        out.println("</body>");
        out.println("</html>");*/
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
        String result = "Time:20181123\nbalance:0\nunusableBalance:0\n";
        String balance = result.split("balance:")[1].split("\n")[0];
        String unusable_balance = result.split("unusableBalance:")[1].split("\n")[0];
        String returnObject = "{ \"returnCode\":\"0\",\"balance\":\""+balance+"\", \"unusableBalance\":\""+unusable_balance+"\"}";
        JSON json = JSON.parseObject(returnObject);
        System.out.println("he");
    }
}
