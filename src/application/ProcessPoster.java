package application;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class ProcessPoster {
	public static ImageView processPoster(String posterUrl) {
		/* Convert BufferedImage from ImageIO to image because
		 * the API for TMDB I'm using does not support buffered image
		 */
		
		BufferedImage bf = null;
		try {
			URL posterAccess = new URL(posterUrl);
			bf = ImageIO.read(posterAccess);
		} catch (IOException e) {
			e.printStackTrace();
		}
		WritableImage wr = null;
		if (bf != null) {
			wr = new WritableImage(bf.getWidth(), bf.getHeight());
			PixelWriter pw = wr.getPixelWriter();
			for (int x = 0; x < bf.getWidth(); x++) {
				for (int y = 0; y < bf.getHeight(); y++) {
					pw.setArgb(x,  y,  bf.getRGB(x,  y));
				}
			}
		}
		ImageView actualPoster = new ImageView(wr);
		return actualPoster;
		
	}
}
