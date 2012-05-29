package cge.simple;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RawMaker {

	public static void convertToRaw(String inFile, String outFile) throws IOException{
		BufferedImage inImage = ImageIO.read(new File(inFile));
		int width = inImage.getWidth();
		int height = inImage.getHeight();
		BufferedImage outImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

		for (int x=0;x<width;x++){
			for (int y=0;y<height;y++){
				int col = inImage.getRGB(x, y);
				int r = col & 0xFF << 16;
				r = r | r >> 8| r >> 16;
				int g = col & 0xFF << 8;
				g = g | g >> 8 | g << 8; 
				int b = col & 0xFF;
				b = b | b << 16 | b << 8; 

				if (x % 2 == 0){
					if (y%2 == 0){
						// Blue
						outImage.setRGB(x, y, b);
					} else {
						// Green
						outImage.setRGB(x, y, g);
					}
				} else {
					if (y%2 == 0){
						outImage.setRGB(x, y, g);

					} else {
						outImage.setRGB(x, y, r);
					}
				}
			}
		}
		ImageIO.write(outImage, "PNG", new File(outFile));
	}


	public static void main(String[] args) throws IOException {
		convertToRaw("res/rgb.png", "rawConvTest.png");
	}
}
