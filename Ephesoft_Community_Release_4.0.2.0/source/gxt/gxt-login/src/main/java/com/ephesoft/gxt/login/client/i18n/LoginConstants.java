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

package com.ephesoft.gxt.login.client.i18n;

import com.ephesoft.gxt.core.client.i18n.LocaleCommonConstants;

/**
 * Interface for Login Constants.
 * 
 * @author Ephesoft
 * 
 *         <b>created on</b> 13-Apr-2015 <br/>
 * @version 1.0.0 <br/>
 *          $LastChangedDate:$ <br/>
 *          $LastChangedRevision:$ <br/>
 */
public interface LoginConstants extends LocaleCommonConstants {

	String login_title = "login_title";
	String login_username = "login_username";
	String login_password = "login_password";
	/*
	 * String login_remember_me = "login_remember_me"; String login_forgot_pwd = "login_forgot_pwd";
	 */
	String login_button_text = "login_button_text";
	String version_text = "version_text";
	String UNABLE_TO_RETRIVE_VERSION_INFO = "unable_to_retrive_version_info";

	/**
	 * Constant used for displaying the title of the Login Page
	 */
	String LOGIN_TITLE = "login_title";

	/**
	 * Constant used for ID of the tag where INVALID username/password is to be displayed
	 */
	String INVALID_CREDENTIALS_ID = "error";

	/**
	 * Constant used for displaying Message of Invalid credentials to the user
	 */
	String INVALID_CREDENTIALS = "invalid_login_credentials";
}
