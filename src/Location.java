import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Location extends HttpServlet {//解析地址数据，访问数据库
    DatabaseConnection connection = new DatabaseConnection();
    URLParser parser = new URLParser();
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String url= request.getRequestURI()+"?"+request.getQueryString();
        String account = parser.getParamByUrl(url, "account");
        String lat = parser.getParamByUrl(url, "latitude");
        String longitude = parser.getParamByUrl(url, "longitude");
        String card = connection.paidByLocation(account,lat,longitude);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String returnObject = "{ \"returnCode\":\"0\",\"card\":\""+card+"\"}";
        out.println(returnObject);

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
        String card = "1234567890123456789";
        String returnObject = "{ \"returnCode\":\"0\",\"card\":\""+card+"\"}";
        System.out.println(" ");
    }
}
