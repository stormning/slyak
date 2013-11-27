/**
 * Project name : slyak-core
 * File name : AwtImageOperation.java
 * Package name : com.slyak.core.io.image
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.core.io.image;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

/**
 * @author StormNing
 * 
 */
public class AwtImageOperation implements ImageOperation {

    public BufferedImage resize(BufferedImage src, double ratio) {
        int iw = src.getWidth();
        int ih = src.getHeight();
        int width = (int) (iw * ratio);
        int height = (int) (ih * ratio);
        ColorModel dstCM = src.getColorModel();
        BufferedImage dst = new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(width, height),
                dstCM.isAlphaPremultiplied(), null);
        Image scaleImage = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        Graphics2D g = dst.createGraphics();
        g.drawImage(scaleImage, 0, 0, width, height, null);
        g.dispose();
        return dst;
    }

    public BufferedImage crop(BufferedImage src, int left, int top, int width, int height) {
        int iw = src.getWidth();
        int ih = src.getHeight();
        if (left + width > iw) {
            width = iw - left;
        }
        if (top + height > ih) {
            height = ih - top;
        }
        ColorModel dstCM = src.getColorModel();
        BufferedImage dst = new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(width, height),
                dstCM.isAlphaPremultiplied(), null);
        Graphics2D g = dst.createGraphics();
        g.drawRenderedImage(src, AffineTransform.getTranslateInstance(-left, -top));
        g.dispose();
        return dst;
    }

    public BufferedImage resizeWithContainer(BufferedImage src, int containerWidth, int containerHeight) {
        double iw = src.getWidth();
        double ih = src.getHeight();
        if (containerWidth > iw && containerHeight > ih) {
            return src;
        }
        double t1 = iw * containerHeight;
        double t2 = ih * containerWidth;
        double ratio;
        if (t1 > t2) {
            ratio = containerWidth / iw;
        } else {
            ratio = containerHeight / ih;
        }
        return resize(src, ratio);
    }

    public BufferedImage cropWithContainer(BufferedImage src, int containerWidth, int containerHeight) {
        double iw = src.getWidth();
        double ih = src.getHeight();
        double t1 = iw * containerHeight;
        double t2 = ih * containerWidth;
        int left = 0, top = 0, width = 0, height = 0;
        // 图大框小，则缩图后再截取
        if (containerWidth <= iw && containerHeight <= ih) {
            if (t1 == t2) {
                return resize(src, containerHeight / ih);
            } else {
                if (t1 > t2) {
                    // 图较宽
                    src = resize(src, containerHeight / ih);
                    left = (int) ((iw * containerHeight / ih - containerWidth) / 2);
                    top = 0;
                } else {
                    // 图较高
                    src = resize(src, containerWidth / iw);
                    left = 0;
                    top = (int) ((ih * containerWidth / iw - containerHeight) / 2);
                }
                width = containerWidth;
                height = containerHeight;
            }
        } else {
            // 框大图小，则缩框后截取
            if (t1 == t2) {
                return src;
            } else {
                if (t1 > t2) {
                    // 框较高
                    width = (int) (containerWidth * ih / containerHeight);
                    height = (int) ih;
                    left = (int) ((iw - width) / 2);
                    top = 0;
                } else {
                    // 框较长
                    width = (int) iw;
                    height = (int) (containerHeight * iw / containerWidth);
                    left = 0;
                    top = (int) ((ih - height) / 2);
                }
            }
        }
        return crop(src, left, top, width, height);
    }

	@Override
	public BufferedImage resizeWithMaxWidth(BufferedImage src, int maxWidth) {
		
		if(src.getWidth()>maxWidth){
			return resize(src, (double)maxWidth/(double)src.getWidth());
		}
		return src;
	}

	@Override
	public BufferedImage resizeWithMaxHeight(BufferedImage src, int maxHeight) {
		if(src.getHeight()>maxHeight){
			return resize(src, (double)maxHeight/(double)src.getHeight());
		}
		return src;
	}
}
