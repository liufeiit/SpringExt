package org.matrix.zxing;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class ZxingEAN13DecoderHandler {

    /**
     * @param imgPath
     * @return String
     */
    public String decode(String imgPath) {
        BufferedImage image = null;
        Result result = null;
        try {
            image = ImageIO.read(new File(imgPath));
            if (image == null) {
                System.out.println("the decode image may be not exit.");
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            result = new MultiFormatReader().decode(bitmap, null);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String imgPath = "/home/lf/tmp/zxing_EAN13.png";
        ZxingEAN13DecoderHandler handler = new ZxingEAN13DecoderHandler();
        String decodeContent = handler.decode(imgPath);
        System.out.println("解码内容如下：");
        System.out.println(decodeContent);
        System.out.println("Michael ,you have finished zxing EAN-13 decode.");

    }
}
