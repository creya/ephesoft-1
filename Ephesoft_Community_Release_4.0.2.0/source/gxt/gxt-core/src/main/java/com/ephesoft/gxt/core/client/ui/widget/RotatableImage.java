/********************************************************************************* 
* Ephesoft is a Intelligent Document Capture and Mailroom Automation program 
* developed by Ephesoft, Inc. Copyright (C) 2015 Ephesoft Inc. 
* 
* This program is free software; you can redistribute it and/or modify it under 
* the terms of the GNU Affero General Public License version 3 as published by the 
* Free Software Foundation with the addition of the following permission added 
* to Section 15 as permitted in Section 7(a): FOR ANY PART OF THE COVERED WORK 
* IN WHICH THE COPYRIGHT IS OWNED BY EPHESOFT, EPHESOFT DISCLAIMS THE WARRANTY 
* OF NON INFRINGEMENT OF THIRD PARTY RIGHTS. 
* 
* This program is distributed in the hope that it will be useful, but WITHOUT 
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
* FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more 
* details. 
* 
* You should have received a copy of the GNU Affero General Public License along with 
* this program; if not, see http://www.gnu.org/licenses or write to the Free 
* Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 
* 02110-1301 USA. 
* 
* You can contact Ephesoft, Inc. headquarters at 111 Academy Way, 
* Irvine, CA 92617, USA. or at email address info@ephesoft.com. 
* 
* The interactive user interfaces in modified source and object code versions 
* of this program must display Appropriate Legal Notices, as required under 
* Section 5 of the GNU Affero General Public License version 3. 
* 
* In accordance with Section 7(b) of the GNU Affero General Public License version 3, 
* these Appropriate Legal Notices must retain the display of the "Ephesoft" logo. 
* If the display of the logo is not reasonably feasible for 
* technical reasons, the Appropriate Legal Notices must display the words 
* "Powered by Ephesoft". 
********************************************************************************/ 

package com.ephesoft.gxt.core.client.ui.widget;

import com.ephesoft.dcma.batch.schema.Direction;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Image;

public class RotatableImage extends Image {

	public RotatableImage() {
		super();
		sinkEvents(Event.ONCONTEXTMENU);
	}

	public RotatableImage(String url) {
		super(url);
	}

	public void setUrl(String url, Direction direction) {
		super.setUrl(url + "?" + direction);
	}

	public void setUrl(String url, String direction) {
		super.setUrl(url + "?" + direction);
	}

	@Override
	public void onBrowserEvent(Event theEvent) {
		super.onBrowserEvent(theEvent);
		int anEventType = DOM.eventGetType(theEvent);
		if (anEventType == Event.ONCONTEXTMENU) {
			theEvent.stopPropagation();
			theEvent.preventDefault();
		}
	}

	public int getNaturalWidth() {
		return getNaturalWidth(this.getElement());
	}

	private native int getNaturalWidth(Element element)
	/*-{
	return element.naturalWidth;
	}-*/;

	private native int getNaturalHeight(Element element)
	/*-{
	return element.naturalHeight;
	}-*/;

	public int getNaturalHeight() {
		return getNaturalHeight(this.getElement());
	}
}
