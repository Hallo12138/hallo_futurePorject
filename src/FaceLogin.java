import com.icbc.api.IcbcApiException;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class FaceLogin extends HttpServlet {//人脸登录类
    public Communication communication = new Communication();
    public DatabaseConnection databaseConnection = new DatabaseConnection();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        //doPost(request,response);
        take_picture();
        String result = new String();
        String faceId;
        String account;
        String password;
        try {
            result = communication.face_login(ImgBase64.GetImageStr("result.png"));
        } catch (IcbcApiException e) {
            e.printStackTrace();
        }
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        if(result.equals("无匹配项目,非法用户")) {
            String returnObject = "{ \"returnCode\":\"3\",\"Mes\":}";
            out.println(returnObject);
        }
        else {
            faceId = result.split("\"faceId\":")[1].split(",")[0];
            account = databaseConnection.loadByFace(faceId).split(",")[0];
            password = databaseConnection.loadByFace(faceId).split(",")[1];
            String return_code = databaseConnection.loadAccount(account, password);

            if (return_code.equals("0")) {
                //登陆成功
                String returnObject = "{ \"returnCode\":\"0\",\"account\":\""+account+"\"}";
                System.out.println(returnObject);
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
