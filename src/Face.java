import com.icbc.api.IcbcApiException;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Face extends HttpServlet {//支付时的人脸一对一验证
    public Communication communication = new Communication();
    public DatabaseConnection databaseConnection = new DatabaseConnection();
    URLParser parser = new URLParser();
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        //doPost(request,response);
        String url = request.getRequestURI()+"?"+request.getQueryString();
        String account = parser.getParamByUrl(url, "account");
        take_picture();
        try {
            if (communication.verifyFace(ImgBase64.GetImageStr("result.png"),databaseConnection.getUserId(account))) {
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                String returnObject = "{ \"returnCode\":\"0\" }";
                out.println(returnObject);
            } else {
                PrintWriter out = response.getWriter();
                String returnObject = "{ \"returnCode\":\"1\" }";
                out.println(returnObject);
            }
        } catch (IcbcApiException e) {
            e.printStackTrace();
        }

    }

    void take_picture() throws IOException {
        GetPicture getPicture = new GetPicture();
        getPicture.getPicture();
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }
}
