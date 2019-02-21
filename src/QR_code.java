import com.google.zxing.WriterException;
import com.google.zxing.qrcode.encoder.QRCode;
import com.icbc.api.IcbcApiException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class QR_code  extends HttpServlet{//二维码相关类

    URLParser parser = new URLParser();
    Communication communication = new Communication();
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String url = request.getRequestURI() + "?" + request.getQueryString();
        String card = parser.getParamByUrl(url, "card");

        String order = null;
        try {
            order = communication.generate_QR_code();
        } catch (IcbcApiException e) {
            e.printStackTrace();
        }

        try {
            generate_QR(card,order);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<body>");
            out.println("<head>");
            out.println("<title>Return code:</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<img src=" + "order.jpg" + "></img>");
            out.println("</body>");
            out.println("</html>");
            /*
             * We are going to perform the same operations for POST requests
             * as for GET methods, so this method just sends the request to
             * the doGet method.
             */
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        doGet(request, response);
    }
    public void generate_QR(String card,String order) throws IOException, WriterException {
        int[] size = new int[]{600, 600};
        QRCodeFactory QR_generator = new QRCodeFactory();
        QR_generator.CreatQrImage(order + "," + card, "jpeg", "order.jpeg", "src/logo.jpeg", size);
    }
}
