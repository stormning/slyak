/*
 * @(#)CommonImage.java 2011
 *
 * Copyright 2011 Slyak SoftWare, Inc. All rights reserved.
 * SLYAK Limited Company/CONFIDENTIAL. Use is subject to license terms.
 */
package com.slyak.core.io.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FileUtils;

import com.slyak.core.io.FileOperation;

/**
 * @author StormNing
 * 
 */
public class CommonImage implements FileOperation {
    private BufferedImage bufferedImage = null;

    private String formatName = null;

    private ImageOperation imageOperation = ImageOperationFactory.getImageOperation();

    public CommonImage(InputStream inputStream) throws IOException {
        convertToBufferedImage(inputStream);
    }

    public CommonImage(String fileLocation) throws IOException {
        this(new File(fileLocation));
    }

    public CommonImage(File imageFile) throws IOException {
        this(FileUtils.openInputStream(imageFile));
    }

    public CommonImage(URI uri) throws IOException {
        this(getInputStreamByUri(uri));
    }

    private static InputStream getInputStreamByUri(URI uri) throws MalformedURLException, IOException {
        InputStream inputStream = null;
        String scheme = uri.getScheme();
        if (SCHEME_HTTP.equalsIgnoreCase(scheme) || SCHEME_HTTPS.equalsIgnoreCase(scheme)) {
            inputStream = uri.toURL().openStream();
        } else {
            File file = new File(uri);
            inputStream = FileUtils.openInputStream(file);
        }
        return inputStream;
    }

    private CommonImage(BufferedImage bufferedImage, String formatName) {
        this.bufferedImage = bufferedImage;
        this.formatName = formatName;
    }

    private void convertToBufferedImage(InputStream inputStream) throws IOException {
        if (inputStream != null) {
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
            initFormatName(imageInputStream);
            this.bufferedImage = ImageIO.read(imageInputStream);
        }
    }

    public CommonImage resize(double ratio) {
        return new CommonImage(imageOperation.resize(bufferedImage, ratio), this.formatName);
    }

    public CommonImage crop(int left, int top, int width, int height) {
        return new CommonImage(imageOperation.crop(bufferedImage, left, top, width, height), this.formatName);
    }

    public CommonImage resizeWithContainer(int containerWidth, int containerHeight) {
        return new CommonImage(imageOperation.resizeWithContainer(bufferedImage, containerWidth, containerHeight),
                this.formatName);
    }
    
    public CommonImage cropWithContainer(int containerWidth, int containerHeight){
        return new CommonImage(imageOperation.cropWithContainer(bufferedImage, containerWidth, containerHeight),this.formatName);
    }
    
    public CommonImage resizeWithMaxWidth(int maxWidth){
    	return new CommonImage(imageOperation.resizeWithMaxWidth(bufferedImage, maxWidth),this.formatName);
    }
    
    public CommonImage resizeWithMaxHeight(int maxHeight){
    	return new CommonImage(imageOperation.resizeWithMaxHeight(bufferedImage, maxHeight),this.formatName);
    }
    
    private void initFormatName(ImageInputStream imageInputStream) {
        try {
            Iterator<ImageReader> ir = ImageIO.getImageReaders(imageInputStream);
            if (ir.hasNext()) {
                formatName = ir.next().getFormatName();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFormatName() {
        return this.formatName;
    }

    public boolean isImageInType(String... types) {
        if (types == null || types.length == 0) {
            return true;
        }
        String formateName = getFormatName();
        for (String type : types) {
            if (type.equalsIgnoreCase(formateName)) {
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.slyak.base.io.FileOperation#save(java.lang.String)
     */
    public void save(String destination) throws IOException {
    	save(new File(destination));
    }

    @Override
    public void save(File file) throws IOException {
    	ImageIO.write(bufferedImage, formatName, file);
    }
    /*
     * (non-Javadoc)
     * 
     * @see com.slyak.base.io.FileOperation#send(java.io.OutputStream)
     */
    public void send(OutputStream outputStream) throws IOException {
        ImageIO.write(bufferedImage, formatName, outputStream);
    }
    
    public static void main(String[] args) {
        try {
        	  new CommonImage(new URI("http://www.baidu.com/img/baidu_jgylogo3.gif")).save("E:\\baidu.jpg");
        	
//            CommonImage commonImage = new CommonImage("E:\\aaa.jpg");
//            commonImage.cropWithContainer(120, 40).save("E:\\headtest-middle-cut.jpg");
//            commonImage.cropWithContainer(30, 40).save("E:\\headtest-middle-cut2.jpg");
//            commonImage.cropWithContainer(220, 220).save("E:\\headtest-middle-cut3.jpg");
//            commonImage.cropWithContainer(200, 80).save("E:\\aaa-middle-cut1.jpg");
//            commonImage.cropWithContainer(110, 220).save("E:\\aaa-middle-cut4.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
