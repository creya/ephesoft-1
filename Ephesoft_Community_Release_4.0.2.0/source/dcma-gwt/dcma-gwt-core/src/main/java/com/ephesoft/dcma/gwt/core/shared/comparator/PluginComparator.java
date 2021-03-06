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

package com.ephesoft.dcma.gwt.core.shared.comparator;

import java.util.Comparator;

import com.ephesoft.dcma.core.common.DomainProperty;
import com.ephesoft.dcma.core.common.Order;
import com.ephesoft.dcma.da.property.PluginProperty;
import com.ephesoft.dcma.gwt.core.shared.PluginDetailsDTO;

/**
 * @author Ephesoft Compares two BatchClassPluginDTOs based on property defined in order object
 */
public class PluginComparator implements Comparator<Object> {

	private final Order order;
	String PLUGIN_NAME = "name";
	String PLUGIN_DESCRIPTION = "description";

	public PluginComparator(final Order order) {

		this.order = order;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(final Object pluginDTOOne, final Object pluginDTOTwo) {
		int isEqualOrGreater = 0;
		final PluginDetailsDTO pluginDetailsDTOOne = (PluginDetailsDTO) pluginDTOOne;

		final PluginDetailsDTO pluginDetailsDTOTwo = (PluginDetailsDTO) pluginDTOTwo;

		final Boolean isAsc = order.isAscending();

		final String pluginDetailsPropertyOne = getProperty(order.getSortProperty(), pluginDetailsDTOOne);
		final String pluginDetailsPropertyTwo = getProperty(order.getSortProperty(), pluginDetailsDTOTwo);
		if (isAsc) {
			isEqualOrGreater = pluginDetailsPropertyOne.compareTo(pluginDetailsPropertyTwo);
		} else {
			isEqualOrGreater = pluginDetailsPropertyTwo.compareTo(pluginDetailsPropertyOne);
		}
		return isEqualOrGreater;
	}

	/**
	 * Getting the name of domain property to sort on
	 * 
	 * @param domainProperty
	 * @param pluginDetailsDTO
	 * @return
	 */
	public String getProperty(final DomainProperty domainProperty, final PluginDetailsDTO pluginDetailsDTO) {
		String property = null;
		if (domainProperty.getProperty().equals(PluginProperty.NAME.getProperty())) {
			property = pluginDetailsDTO.getPluginName();
		} else {
			if (domainProperty.getProperty().equals(PluginProperty.DESCRIPTION.getProperty())) {
				property = pluginDetailsDTO.getPluginDescription();
			} else {
				property = null;
			}
		}
		return property;
	}
}
