/*package org.matrix.tess4j;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.spi.IIORegistry;
import javax.imageio.spi.ImageInputStreamSpi;
import javax.imageio.spi.ImageOutputStreamSpi;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.spi.ImageWriterSpi;

import net.sourceforge.tess4j.Tesseract;

import com.sun.media.imageioimpl.stream.ChannelImageInputStreamSpi;
import com.sun.media.imageioimpl.stream.ChannelImageOutputStreamSpi;

*//**
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013-12-16 下午3:07:55
 *//*
public class TessTester {

	private static void loadDLL(String libFullName) {
        try {
            String nativeTempDir = System.getProperty("java.io.tmpdir");
            InputStream in = null;
            FileOutputStream writer = null;
            BufferedInputStream reader = null;
            File extractedLibFile = new File(nativeTempDir + File.separator + libFullName);
            System.out.println(extractedLibFile.getAbsolutePath());
            if (!extractedLibFile.exists()) {
                try {
                    in = Tesseract.class.getResourceAsStream("/" + libFullName);
                    Tesseract.class.getResource(libFullName);
                    reader = new BufferedInputStream(in);
                    writer = new FileOutputStream(extractedLibFile);
                    byte[] buffer = new byte[1024];
                    while (reader.read(buffer) > 0) {
                        writer.write(buffer);
                        buffer = new byte[1024];
                    }
                    in.close();
                    writer.close();
                    System.load(extractedLibFile.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (in != null) {
                        in.close();
                    }
                    if (writer != null) {
                        writer.close();
                    }
                }
            } else {
                System.load(extractedLibFile.toString());
            }
        } catch (IOException e) {
            System.err.println("初始化 " + libFullName + " DLL错误");
        }
 }
	
	public static void main(String[] args) throws Exception {
		IIORegistry registry = IIORegistry.getDefaultInstance();
		// registry.registerServiceProvider(new ImageReadWriteSpi(), OperationRegistrySpi.class);//这个,注册不了
		registry.registerServiceProvider(new ChannelImageInputStreamSpi(), ImageInputStreamSpi.class);
		registry.registerServiceProvider(new ChannelImageOutputStreamSpi(), ImageOutputStreamSpi.class);
		registry.registerServiceProvider(new com.sun.media.imageioimpl.plugins.jpeg.CLibJPEGImageReaderSpi(), ImageReaderSpi.class);
		registry.registerServiceProvider(new com.sun.media.imageioimpl.plugins.png.CLibPNGImageReaderSpi(), ImageReaderSpi.class);
		registry.registerServiceProvider(new com.sun.media.imageioimpl.plugins.jpeg2000.J2KImageReaderSpi(), ImageReaderSpi.class);
		registry.registerServiceProvider(new com.sun.media.imageioimpl.plugins.jpeg2000.J2KImageReaderCodecLibSpi(), ImageReaderSpi.class);
		registry.registerServiceProvider(new com.sun.media.imageioimpl.plugins.wbmp.WBMPImageReaderSpi(), ImageReaderSpi.class);
		registry.registerServiceProvider(new com.sun.media.imageioimpl.plugins.bmp.BMPImageReaderSpi(), ImageReaderSpi.class);
		registry.registerServiceProvider(new com.sun.media.imageioimpl.plugins.pnm.PNMImageReaderSpi(), ImageReaderSpi.class);
		registry.registerServiceProvider(new com.sun.media.imageioimpl.plugins.raw.RawImageReaderSpi(), ImageReaderSpi.class);
		registry.registerServiceProvider(new com.sun.media.imageioimpl.plugins.tiff.TIFFImageReaderSpi(), ImageReaderSpi.class);
		registry.registerServiceProvider(new com.sun.media.imageioimpl.plugins.jpeg.CLibJPEGImageWriterSpi(), ImageWriterSpi.class);
		registry.registerServiceProvider(new com.sun.media.imageioimpl.plugins.jpeg.CLibJPEGImageWriterSpi(), ImageWriterSpi.class);
		registry.registerServiceProvider(new com.sun.media.imageioimpl.plugins.png.CLibPNGImageWriterSpi(), ImageWriterSpi.class);
		registry.registerServiceProvider(new com.sun.media.imageioimpl.plugins.jpeg2000.J2KImageWriterSpi(), ImageWriterSpi.class);
		registry.registerServiceProvider(new com.sun.media.imageioimpl.plugins.jpeg2000.J2KImageWriterCodecLibSpi(), ImageWriterSpi.class);
		registry.registerServiceProvider(new com.sun.media.imageioimpl.plugins.wbmp.WBMPImageWriterSpi(), ImageWriterSpi.class);
		registry.registerServiceProvider(new com.sun.media.imageioimpl.plugins.bmp.BMPImageWriterSpi(), ImageWriterSpi.class);
		registry.registerServiceProvider(new com.sun.media.imageioimpl.plugins.gif.GIFImageWriterSpi(), ImageWriterSpi.class);
		registry.registerServiceProvider(new com.sun.media.imageioimpl.plugins.pnm.PNMImageWriterSpi(), ImageWriterSpi.class);
		registry.registerServiceProvider(new com.sun.media.imageioimpl.plugins.raw.RawImageWriterSpi(), ImageWriterSpi.class);
		registry.registerServiceProvider(new com.sun.media.imageioimpl.plugins.tiff.TIFFImageWriterSpi(), ImageWriterSpi.class);
		
		System.out.println(System.getProperty("java.io.tmpdir"));
//		loadDLL("liblept168.dll"); 
//		loadDLL("libtesseract302.dll"); 
        BufferedImage bi = ImageIO.read(new File("/home/lf/201312161104022_908166_250x250.jpg"));
        String result = Tesseract.getInstance().doOCR(bi);
        System.out.println(result);
	}
}
*/