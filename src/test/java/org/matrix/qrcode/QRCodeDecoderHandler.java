package org.matrix.qrcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;
import jp.sourceforge.qrcode.exception.DecodingFailedException;

public class QRCodeDecoderHandler {

	/**
	 * 解码二维码
	 * 
	 * @param imgPath
	 * @return String
	 */
	public String decoderQRCode(String imgPath) {

		// QRCode 二维码图片的文件
		File imageFile = new File(imgPath);

		BufferedImage bufImg = null;
		String decodedData = null;
		try {
			bufImg = ImageIO.read(imageFile);

			QRCodeDecoder decoder = new QRCodeDecoder();
			decodedData = new String(decoder.decode(new J2SEImage(bufImg)));

			// try {
			// System.out.println(new String(decodedData.getBytes("gb2312"),
			// "gb2312"));
			// } catch (Exception e) {
			// // TODO: handle exception
			// }
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		} catch (DecodingFailedException dfe) {
			System.out.println("Error: " + dfe.getMessage());
			dfe.printStackTrace();
		}
		return decodedData;
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main0(String[] args) {
		QRCodeDecoderHandler handler = new QRCodeDecoderHandler();
		// String imgPath = "/home/lf/tmp/QRCode.png";
		String imgPath = "/home/lf/201312161104022_908166_250x250.jpg";
		String decoderContent = handler.decoderQRCode(imgPath);
		System.out.println("解析结果如下：");
		System.out.println(decoderContent);
		System.out.println("========decoder success!!!");
	}

	public static void main(String[] args) throws Exception {
		BufferedImage bi = (BufferedImage) ImageIO.read(new File("/home/lf/201312161104022_908166_250x250.jpg"));
		int width = bi.getWidth();
		int height = bi.getHeight();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {// 行扫描
				int dip = bi.getRGB(j, i);
				if (dip == -1) {
//					System.out.print(" ");
					System.out.print("0");
				} else {
//					System.out.print("♦");
					System.out.print("1");
				}
			}
			System.out.println();// 换行
		}

	}

	class J2SEImage implements QRCodeImage {
		BufferedImage bufImg;

		public J2SEImage(BufferedImage bufImg) {
			this.bufImg = bufImg;
		}

		public int getWidth() {
			return bufImg.getWidth();
		}

		public int getHeight() {
			return bufImg.getHeight();
		}

		public int getPixel(int x, int y) {
			return bufImg.getRGB(x, y);
		}

	}
}
