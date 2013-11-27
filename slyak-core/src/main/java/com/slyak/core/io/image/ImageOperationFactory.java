/**
 * Project name : slyak-core
 * File name : ImageOperationFactory.java
 * Package name : com.slyak.core.io.image
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
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
