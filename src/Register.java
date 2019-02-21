import com.icbc.api.IcbcApiException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Register extends HttpServlet {//响应前端注册新卡请求
    Communication communication = new Communication();
    DatabaseConnection connection = new DatabaseConnection();
    URLParser parser = new URLParser();
    String url;
    String account;
    String nickname;
    String password;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        url = request.getRequestURI()+"?"+request.getQueryString();
        account = parser.getParamByUrl(url, "account");
        nickname = parser.getParamByUrl(url,"accname");
        password = parser.getParamByUrl(url, "password");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if(check_input()) {
            connection.insertAccount(account,nickname,password);
            String returnObject = "{ \"status\":\"0\", }";
            out.println(returnObject);
            GetPicture getPicture = new GetPicture();
            getPicture.getPicture();
            try {
                communication.addFaceGroup(ImgBase64.GetImageStr("result.png"),String.valueOf(Integer.parseInt(connection.getMaxuserid())+1));
                communication.addFace(ImgBase64.GetImageStr("result.png"),String.valueOf(Integer.parseInt(connection.getMaxuserid())+1));
            } catch (IcbcApiException e) {
                e.printStackTrace();
            }

        }
        else {
            String returnObject = "{ \"status\":\"1\", }";
            out.println(returnObject);
        }

        /*
         * We are going to perform the same operations for POST requests
         * as for GET methods, so this method just sends the request to
         * the doGet method.
         */
    }
    boolean check_input() {
        if(checkNumber(password)&&checkNumber(account)) {
            return true;
        }
        else {
            return false;
        }
    }
    public static boolean checkNumber(String value){
        String regex = "^\\d{6}$";
        return value.matches(regex);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        doGet(request, response);
    }
}
