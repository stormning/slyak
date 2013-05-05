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
