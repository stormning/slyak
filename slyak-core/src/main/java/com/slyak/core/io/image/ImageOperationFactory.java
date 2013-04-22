/*
 * Project:  any
 * Module:   slyak-core
 * File:     ImageOperationFactory.java
 * Modifier: zhouning
 * Modified: 2012-12-9 下午8:11:02 
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.slyak.core.io.image;

import com.slyak.core.web.util.SpringUtils;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:stormning@163.com">stormning</a>
 * @version V1.0, 2012-12-9
 */
public class ImageOperationFactory {

	private static ImageOperation imageOperation;	
	
	public static ImageOperation getImageOperation() {
		if( imageOperation == null){
			try{
				imageOperation = SpringUtils.getBean(ImageOperation.class);
			}catch(Exception e){
				if(imageOperation == null){
					imageOperation = new AwtImageOperation();
				}
			}
			if(imageOperation == null){
				imageOperation = new AwtImageOperation();
			}
		}
		return imageOperation;
	}

}
