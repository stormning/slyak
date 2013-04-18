/*
 * @(#)ImageOperation.java 2011
 *
 * Copyright 2011 Slyak SoftWare, Inc. All rights reserved.
 * SLYAK Limited Company/CONFIDENTIAL. Use is subject to license terms.
 */
package com.slyak.core.io.image;

import java.awt.image.BufferedImage;

/**
 * @author StormNing
 * 
 */
public interface ImageOperation {
    BufferedImage resize(BufferedImage src, double ratio);

    BufferedImage resizeWithContainer(BufferedImage src, int containerWidth, int containerHeight);

    BufferedImage crop(BufferedImage src, int left, int top, int width, int height);
    // TODO marking
    
    BufferedImage cropWithContainer(BufferedImage src, int containerWidth, int containerHeight);
}
