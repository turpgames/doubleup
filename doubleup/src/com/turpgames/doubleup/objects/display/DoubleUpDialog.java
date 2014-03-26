package com.turpgames.doubleup.objects.display;

import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.doubleup.utils.R;

public class DoubleUpDialog extends Dialog {
	public DoubleUpDialog() {
		addButton(R.strings.yes, R.strings.yes);
		addButton(R.strings.no, R.strings.no);
	}
}
