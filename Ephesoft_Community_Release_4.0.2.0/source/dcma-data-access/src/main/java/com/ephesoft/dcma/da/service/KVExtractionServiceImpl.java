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

package com.ephesoft.dcma.da.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ephesoft.dcma.core.common.LocationType;
import com.ephesoft.dcma.core.service.DataAccessService;
import com.ephesoft.dcma.da.dao.KVExtractionDao;
import com.ephesoft.dcma.da.domain.FieldType;
import com.ephesoft.dcma.da.domain.KVExtraction;

/**
 * This is a database service to read data required by KV Extraction Service .
 * 
 * @author Ephesoft
 * @version 1.0
 * @see com.ephesoft.dcma.da.service.KVExtractionService
 */
@Service
public class KVExtractionServiceImpl extends DataAccessService implements KVExtractionService {

	/**
	 * LOGGER to print the logging information.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(KVExtractionServiceImpl.class);

	@Autowired
	private KVExtractionDao kvExtractionDao;

	/**
	 * An api to fetch all KV Extraction by field type.
	 * 
	 * @param fieldType FieldType
	 * @return List<KVExtraction>
	 */
	@Transactional(readOnly = true)
	@Override
	public List<KVExtraction> getKVExtractionByFieldType(FieldType fieldType) {
		List<KVExtraction> kvExtraction = null;
		if (null == fieldType) {
			LOGGER.info("Field Type object is null.");
		} else {
			kvExtraction = kvExtractionDao.getKVExtractionByFieldType(fieldType);
		}
		return kvExtraction;
	}

	/**
	 * An api to insert the kvExtraction object.
	 * 
	 * @param kvExtraction KVExtraction
	 */
	@Transactional
	@Override
	public void insertKVExtraction(KVExtraction kvExtraction) {
		if (null == kvExtraction) {
			LOGGER.info("KVExtraction object is null.");
		} else {
			kvExtractionDao.insertKVExtraction(kvExtraction);
		}
	}

	/**
	 * An api to update the kvExtraction object.
	 * 
	 * @param kvExtraction KVExtraction
	 */
	@Transactional
	@Override
	public void updateKVExtraction(KVExtraction kvExtraction) {
		if (null == kvExtraction) {
			LOGGER.info("KVExtraction object is null.");
		} else {
			kvExtractionDao.updateKVExtraction(kvExtraction);
		}
	}

	/**
	 * An api to remove the kvExtraction object.
	 * 
	 * @param kvExtraction KVExtraction
	 */
	@Transactional
	@Override
	public void removeKVExtraction(KVExtraction kvExtraction) {
		if (null == kvExtraction) {
			LOGGER.info("KVExtraction object is null.");
		} else {
			kvExtractionDao.removeKVExtraction(kvExtraction);
		}
	}

	/**
	 * An api to check whether KVExtraction field already exists for a field type.
	 *
	 * @param fieldType
	 * @param kvExtractionField
	 * @return
	 */
	@Override
	public List<KVExtraction> getDuplicateKVFields(FieldType fieldType, KVExtraction kvExtractionField) {
		List<KVExtraction> kvExtraction = null;
		if (null == fieldType) {
			LOGGER.info("Field Type object is null.");
		} else if (kvExtractionField == null) {
			LOGGER.info("Field Type object is null.");
		} else {
			LocationType locationType = kvExtractionField.getLocationType();
			String keyPattern = kvExtractionField.getKeyPattern();
			String valuePattern = kvExtractionField.getValuePattern();
			if (locationType == null || keyPattern == null || valuePattern == null) {
				LOGGER.info("Couldn't check for duplicate fields. locationType=" + locationType + " , keyPattern=" + keyPattern
						+ ",valuePattern" + valuePattern);
			} else {
				kvExtraction = kvExtractionDao.getDuplicateKVFields(fieldType, locationType, keyPattern, valuePattern);
			}
		}
		return kvExtraction;
	}
}
