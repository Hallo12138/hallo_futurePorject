import com.icbc.api.internal.util.internal.util.fastjson.JSON;
import com.icbc.api.internal.util.internal.util.fastjson.JSONArray;
import com.icbc.api.internal.util.internal.util.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
public class getCards extends HttpServlet {//获取该账户名下卡片信息
    DatabaseConnection connection = new DatabaseConnection();
    URLParser parser = new URLParser();
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String url = request.getRequestURI()+"?"+request.getQueryString();
        String account = parser.getParamByUrl(url, "account");
        List<String> result = connection.showCards(account);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();


        JSONArray jsonArray = new JSONArray();

        for(int i = 0; i < result.size(); i++){
            String card = "\""+result.get(i).split(",")[0]+"\"";
            String type = "\""+result.get(i).split(",")[1]+"\"";
            String jsonObj = "{\"card\":" + card + ", \"type\": " + type + "}";
            System.out.println(jsonObj);
            jsonArray.add(JSON.parseObject(jsonObj));
        }

        out.println(jsonArray);
        System.out.println(jsonArray);




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
        DatabaseConnection connection = new DatabaseConnection();
        String account = "100001";
        List<String> result = connection.showCards(account);
        //response.setContentType("application/json");
        //PrintWriter out = response.getWriter();


        JSONArray jsonArray = new JSONArray();

        for(int i = 0; i < result.size(); i++){
            String card = "\""+result.get(i).split(",")[0]+"\"";
            String type = "\""+result.get(i).split(",")[1]+"\"";
            String jsonObj = "{\"card\":" + card + ", \"type\": " + type + "}";
            System.out.println(jsonObj);
            jsonArray.add(JSON.parseObject(jsonObj));
        }
    }
}
