import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends HttpServlet{//登陆

    DatabaseConnection connection = new DatabaseConnection();
    URLParser parser = new URLParser();
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String url= request.getRequestURI()+"?"+request.getQueryString();
        String account = parser.getParamByUrl(url, "account");
        String password = parser.getParamByUrl(url, "password");
        String return_code = connection.loadAccount(account, password);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (return_code.equals("0")) {
            //登陆成功
            String returnObject = "{ \"returnCode\":\"0\" }";
            out.println(returnObject);
        }
        if (return_code.equals("1")) {
            //没账户，先注册
            out.println(" { "+"\"returnCode\":\"1\"" + " }");

        }
        if (return_code.equals("2")) {
            //密码错误
            out.println(" { "+"\"returnCode\":\"2\"" + " }"
            );
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
}