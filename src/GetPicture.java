import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;


/**
 * Example of how to take single picture.
 *
 * @author Bartosz Firyn (SarXos)
 */
public class GetPicture {//≈ƒ’’

    public  void getPicture() throws IOException {

        // get default webcam and open it
        Webcam webcam = Webcam.getDefault();
        webcam.open();

        // get image
        BufferedImage image = webcam.getImage();

        // save image to PNG file
        ImageIO.write(image, "PNG", new File("result.png"));
        webcam.close();
    }

    public static void main(String[] args) throws IOException {
        GetPicture getPicture = new GetPicture();
        getPicture.getPicture();
    }
}
