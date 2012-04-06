package ballpuzzle.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;


public class Resource {
	public static Image loadImage(String filename) {
		Image image = null;
		String location = "/ballpuzzle/resources/";
		URL url = Resource.class.getResource(location + filename);
		try {
			if (url == null)
				throw new IOException("Couldn't load " + filename);
			
			image = Toolkit.getDefaultToolkit().createImage(url);
		} catch (SecurityException e) {
		  e.printStackTrace();
			System.err.println(filename);
		} catch (IOException e) {
			e.printStackTrace();
		  System.err.println(filename);
		}

		return image;
	}
}
