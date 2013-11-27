/**
 * Project name : slyak-cms
 * File name : GameWidgets.java
 * Package name : com.slyak.cms.widgets.game
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.cms.widgets.game;

import com.slyak.cms.core.annotation.Widget;
import com.slyak.cms.core.annotation.Widgets;

@Widgets("game")
public class GameWidgets {

	@Widget
	public String chess() {
		return "chess.tpl";
	}
	
}
