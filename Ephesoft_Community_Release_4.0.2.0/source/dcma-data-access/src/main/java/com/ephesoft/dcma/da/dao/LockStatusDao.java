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

package com.ephesoft.dcma.da.dao;

import java.util.List;

import com.ephesoft.dcma.core.common.FeatureName;
import com.ephesoft.dcma.core.dao.CacheableDao;
import com.ephesoft.dcma.da.domain.LockStatus;

/**
 * The <code>LockStatusDao</code> interface provides operations to be performed on LockStatus table like acquire lock, release lock
 * etc. for providing the support for locking mechanism for Ephesoft application feature.
 * 
 * @author Ephesoft
 * @version 1.0
 * @see LockStatus
 */
public interface LockStatusDao extends CacheableDao<LockStatus> {

	/**
	 * Acquires the lock on the particular application feature.
	 * 
	 * @param lockStatus {@link LockStatus} The instance of the LockStatus.
	 */
	void acquireLock(LockStatus lockStatus);

	/**
	 * Returns the instance of LockStatus for the given application feature name.
	 * 
	 * @param featureName {@link FeatureName} The name of the feature on which lock is being acquired.
	 * @return {@link LockStatus} The instance of LockStatus.
	 */
	LockStatus getLockStatus(FeatureName featureName);

	/**
	 * Releases the lock from the particular application feature.
	 * 
	 * @param featureName {@link FeatureName} The name of the feature on which lock is being acquired.
	 */
	void releaseLock(FeatureName featureName);

	/**
	 * Releases all the locks from the application features which are being locked by the userName.
	 * 
	 * @param userName {@link String} The name of the user which has taken the lock on the application features.
	 */
	void releaseAllLocks(String userName);

	/**
	 * Returns the list of all the application features which are being locked by the userName passed.
	 * 
	 * @param userName {@link String} The name of the user which has taken the lock on the application features.
	 * @return {@link List <{@link LockStatus}> The list the lockstatus object.
	 */
	List<LockStatus> getAllFeaturesLockedByUser(String userName);

}
