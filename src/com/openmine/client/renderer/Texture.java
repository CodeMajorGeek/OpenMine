package com.openmine.client.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.openmine.client.util.FileUtils;

public class Texture {

	private static final String TEXTURES_PATH = "/textures/";

	private int textureHandle;

	public Texture(String textureFileName) throws IOException {

		InputStream assetStream = FileUtils.getInputStream(TEXTURES_PATH + textureFileName.replace(".", "/") + ".png");
		BufferedImage img = ImageIO.read(assetStream);

		int width = img.getWidth();
		int height = img.getHeight();

		int[] pixels = new int[width * height];
		img.getRGB(0, 0, width, height, pixels, 0, width);

		int[] data = new int[pixels.length];
		for (int i = 0; i < pixels.length; i++) {
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = pixels[i] & 0xff;
			
			data[i] = a << 24 | b << 16 | g << 8 | r;
		}

		textureHandle = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureHandle);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		
		assetStream.close();
	}

	public void cleanup() {
		glDeleteTextures(textureHandle);
	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, textureHandle);
	}
}
