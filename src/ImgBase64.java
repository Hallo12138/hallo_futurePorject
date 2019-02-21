
import Decoder.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImgBase64 {//图片转base64串
    public static String GetImageStr(String file)
    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = file;//待处理的图片
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try
        {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }
    static String getImageBinary(String file) {
        File f = new File(file);
        BufferedImage bi;
        try {
            bi = ImageIO.read(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);  //经测试转换的图片是格式这里就什么格式，否则会失真
            byte[] bytes = baos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encodeBuffer(bytes).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) throws IOException {
        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("result.txt"))));
        wr.write(GetImageStr("face.jpeg"));
        wr.flush();
    }
}
