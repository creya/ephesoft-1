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

package com.ephesoft.dcma.regex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ephesoft.dcma.batch.schema.Batch;
import com.ephesoft.dcma.batch.schema.Coordinates;
import com.ephesoft.dcma.batch.schema.DocField;
import com.ephesoft.dcma.batch.schema.DocField.AlternateValues;
import com.ephesoft.dcma.batch.schema.Document;
import com.ephesoft.dcma.batch.schema.Field;
import com.ephesoft.dcma.batch.schema.Field.CoordinatesList;
import com.ephesoft.dcma.batch.schema.HocrPages;
import com.ephesoft.dcma.batch.schema.HocrPages.HocrPage;
import com.ephesoft.dcma.batch.schema.HocrPages.HocrPage.Spans;
import com.ephesoft.dcma.batch.schema.HocrPages.HocrPage.Spans.Span;
import com.ephesoft.dcma.batch.schema.Page;
import com.ephesoft.dcma.batch.service.BatchSchemaService;
import com.ephesoft.dcma.batch.service.PluginPropertiesService;
import com.ephesoft.dcma.core.common.CategoryType;
import com.ephesoft.dcma.core.exception.DCMAApplicationException;
import com.ephesoft.dcma.da.domain.FieldType;
import com.ephesoft.dcma.da.service.FieldTypeService;
import com.ephesoft.dcma.regex.constant.RegexConstants;
import com.ephesoft.dcma.regex.util.ExtractDocField;
import com.ephesoft.dcma.util.EphesoftStringUtil;

/**
 * This class is used to extract the invoice fields. Update the extracted data to the batch.xml file.
 * 
 * @author Ephesoft
 * @version 1.0
 * @see com.ephesoft.dcma.regex.util.ExtractDocField
 * @see com.ephesoft.dcma.regex.HOCRFileReader
 */
@Component
public class Extraction {

	private static final String REGULAR_REGEX_EXTRACTION = "Regular Regex Extraction";

	private static final String REGEX_EXTRACTION_PLUGIN = "REGULAR_REGEX_EXTRACTION";

	/**
	 * LOGGER to print the logging information.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(Extraction.class);

	/**
	 * fieldService FieldService.
	 */
	@Autowired
	private FieldTypeService fieldTypeService;

	/**
	 * Reference of BatchSchemaService.
	 */
	@Autowired
	private BatchSchemaService batchSchemaService;

	/**
	 * Instance of PluginPropertiesService.
	 */
	@Autowired
	@Qualifier("batchInstancePluginPropertiesService")
	private PluginPropertiesService pluginPropertiesService;

	/**
	 * Instance of PluginPropertiesService.
	 */
	@Autowired
	@Qualifier("batchClassPluginPropertiesService")
	private PluginPropertiesService batchClassPluginPropertiesService;

	/**
	 * Search coordinate.
	 */
	private String searchCoordinate;

	/**
	 * Default name for first page.
	 */
	private boolean firstFieldValue;

	/**
	 * Reference of FieldTypeService.
	 * 
	 * @param fieldTypeService FieldService
	 */
	public final void setFieldService(final FieldTypeService fieldTypeService) {
		this.fieldTypeService = fieldTypeService;
	}

	/**
	 * Search coordinate.
	 * 
	 * @param searchCoordinate String
	 */
	public final void setSearchCoordinate(final String searchCoordinate) {
		this.searchCoordinate = searchCoordinate;
	}

	/**
	 * Keep the first field value information.
	 * 
	 * @return isFirstFieldValue boolean
	 */
	public boolean isFirstFieldValue() {
		return firstFieldValue;
	}

	/**
	 * Keep the first field value information.
	 * 
	 * @param isFirstFieldValue boolean
	 */

	public final void setFirstFieldValue(final boolean isFirstFieldValue) {
		this.firstFieldValue = isFirstFieldValue;
	}

	/**
	 * Reference fieldTypeService.
	 * 
	 * @return fieldTypeService
	 */
	public final FieldTypeService getFieldTypeService() {
		return fieldTypeService;
	}

	/**
	 * Reference fieldTypeService.
	 * 
	 * @param fieldTypeService FieldTypeService
	 */
	public final void setFieldTypeService(final FieldTypeService fieldTypeService) {
		this.fieldTypeService = fieldTypeService;
	}

	/**
	 * Rule for search Coordinate.
	 * 
	 * @return searchCoordinate
	 */
	public final String getSearchCoordinate() {
		return searchCoordinate;
	}

	/**
	 * @return the batchSchemaService
	 */
	public BatchSchemaService getBatchSchemaService() {
		return batchSchemaService;
	}

	/**
	 * @param batchSchemaService the batchSchemaService to set
	 */
	public void setBatchSchemaService(BatchSchemaService batchSchemaService) {
		this.batchSchemaService = batchSchemaService;
	}

	/**
	 * @return the pluginPropertiesService
	 */
	public PluginPropertiesService getPluginPropertiesService() {
		return pluginPropertiesService;
	}

	/**
	 * @param pluginPropertiesService the pluginPropertiesService to set
	 */
	public void setPluginPropertiesService(PluginPropertiesService pluginPropertiesService) {
		this.pluginPropertiesService = pluginPropertiesService;
	}

	/**
	 * This method is used to extract the fields. Update the extracted data to the batch.xml file.
	 * 
	 * @param batchInstanceId String
	 * @return isSuccessful boolean
	 * @throws DCMAApplicationException Check for all the input parameters and get all the page content.
	 */
	public void extractFields(final String batchInstanceIdentifier) throws DCMAApplicationException {

		String switchValue = pluginPropertiesService.getPropertyValue(batchInstanceIdentifier, REGEX_EXTRACTION_PLUGIN,
				ExtractionProperties.REGEX_SWITCH);

		if (switchValue != null && switchValue.equalsIgnoreCase("ON")) {
			String errMsg = null;
			if (null == batchInstanceIdentifier) {
				errMsg = "Invalid batchInstanceId.";
				LOGGER.error(errMsg);
				throw new DCMAApplicationException(errMsg);
			}

			if (null == this.fieldTypeService) {
				errMsg = "Invalid intialization of FieldService.";
				LOGGER.error(errMsg);
				throw new DCMAApplicationException(errMsg);
			}

			LOGGER.info("batchInstanceId : " + batchInstanceIdentifier);

			Batch batch = batchSchemaService.getBatch(batchInstanceIdentifier);

			LOGGER.info("Initializing properties...");
			String confidenceScore = pluginPropertiesService.getPropertyValue(batchInstanceIdentifier, REGEX_EXTRACTION_PLUGIN,
					ExtractionProperties.REGEX_CONFIDENCE_SCORE);
			LOGGER.info("Properties Initialized Successfully");

			try {
				final ExtractDocField extractDocField = new ExtractDocField();

				final List<Document> docTypeList = batch.getDocuments().getDocument();

				if (null == docTypeList) {
					LOGGER.info("In valid batch documents.");
				} else {
					processDocPage(docTypeList, batchInstanceIdentifier, extractDocField, batch, confidenceScore, true, null);
				}
			} catch (DCMAApplicationException e) {
				LOGGER.error(e.getMessage());
				throw new DCMAApplicationException(e.getMessage(), e);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				throw new DCMAApplicationException(e.getMessage(), e);
			}
		} else {
			LOGGER.info("Skipping Regex extraction. Switch set as off.");
		}
	}

	/**
	 * This method will process for each page for each document.
	 * 
	 * @param xmlDocuments List<DocumentType>
	 * @param batchInstanceId String
	 * @param extractDocField ExtractDocField
	 * @param isBatchExecution
	 * @param folderPath
	 * @return batch Batch
	 * @throws DCMAApplicationException Check for all the input parameters and process the document page.
	 */
	private void processDocPage(List<Document> xmlDocuments, String batchInstanceId, ExtractDocField extractDocField, Batch batch,
			String confidenceScore, boolean isBatchExecution, String folderPath) throws DCMAApplicationException {

		String errMsg = null;
		if (null == xmlDocuments || xmlDocuments.isEmpty()) {
			throw new DCMAApplicationException("In valid parameters.");
		}

		List<List<DocField>> updtDocList = new ArrayList<List<DocField>>();
		List<Boolean> isValidateDataList = new ArrayList<Boolean>();

		for (Document document : xmlDocuments) {
			final List<Page> pageList = document.getPages().getPage();
			final String docTypeName = document.getType();
			LOGGER.info("docTypeName : " + docTypeName);
			// read all the extraction fields one by one to all the
			// pages document.

			boolean isValidateData = false;
			List<com.ephesoft.dcma.da.domain.FieldType> allFdTypes;
			if (isBatchExecution) {
				allFdTypes = pluginPropertiesService.getFieldTypes(batchInstanceId, docTypeName);
			} else {
				// in test extraction flow the batch instance id will contain batch class identifier
				allFdTypes = batchClassPluginPropertiesService.getFieldTypes(batchInstanceId, docTypeName);
			}

			if (null == allFdTypes || allFdTypes.isEmpty()) {
				errMsg = "No FieldType data found from data base for document type : " + docTypeName;
				LOGGER.error(errMsg);
				updtDocList.add(null);
				isValidateDataList.add(null);
				continue;
				// throw new DCMAApplicationException(errMsg);
			}

			List<DocField> updtDocFdTyList = new ArrayList<DocField>();

			LOGGER.info("FieldType data found from data base for document type : " + docTypeName);

			for (com.ephesoft.dcma.da.domain.FieldType fdType : allFdTypes) {
				String key = fdType.getName();
				String pattern = fdType.getPattern();

				int fieldOrderNumber = fdType.getFieldOrderNumber();
				String fdTypeName = null;

				if (fdType.getDataType() != null) {
					fdTypeName = fdType.getDataType().name();
				}

				final DocField updtDocFdType = new DocField();
				updtDocFdType.setConfidence(0.0f);
				updtDocFdType.setFieldOrderNumber(0);
				updtDocFdType.setForceReview(false);
				updtDocFdType.setOcrConfidence(0.0f);
				updtDocFdType.setOcrConfidenceThreshold(0.0f);
				updtDocFdType.setName(key);
				updtDocFdType.setFieldOrderNumber(fieldOrderNumber);
				updtDocFdType.setFieldValueChangeScript(fdType.isFieldValueChangeScriptEnabled());
				if(null != fdType.getCategoryName()){
					updtDocFdType.setCategory(fdType.getCategoryName());
				} else {
//					updtDocFdType.setCategory("Uncategorised");
					updtDocFdType.setCategory(CategoryType.GROUP_1.getCategoryName());
				}
				updtDocFdType.setHidden(fdType.isHidden());
				updtDocFdType.setExtractionName(REGULAR_REGEX_EXTRACTION);

				// EPHE-8996 - Priority Issue: Sticky Fields not working in 4.0.2.0
				// Fix for Read Only tag not coming in Batch.xml
				updtDocFdType.setReadOnly(fdType.getIsReadOnly());
				
				setFirstFieldValue(true);

				final AlternateValues alternateValues = new AlternateValues();

				if (EphesoftStringUtil.isNullOrEmpty(pattern)) {
					LOGGER.error("No regex pattern is defined for field type : " + key);
				} else {

					// Zoho ticket #1121 : Support for regular regex extraction to work for multiple pages in a document.
					for (Page pageType : pageList) {
						String pageID = pageType.getIdentifier();
						HocrPages hocrPages;
						if (isBatchExecution) {
							hocrPages = batchSchemaService.getHocrPages(batchInstanceId, pageType.getHocrFileName());
						} else {
							// in case of test extraction flow get hocr pages a different way.
							hocrPages = batchSchemaService.getHocrPagesForTestContent(folderPath, pageType.getHocrFileName());
						}
						extractFieldsFromHOCR(confidenceScore, pattern, fdTypeName, updtDocFdType, alternateValues, pageID, hocrPages);
					}
					sortDocFdWithConfidence(updtDocFdType);
				}

				if (!isFirstFieldValue()) {
					updtDocFdTyList.add(updtDocFdType);
				} else {
					updtDocFdType.setType(fdTypeName);
					updtDocFdTyList.add(updtDocFdType);
					isValidateData = true;
				}
			}
			updtDocList.add(updtDocFdTyList);
			isValidateDataList.add(isValidateData);
		}

		extractDocField.updateBatchXML(batchInstanceId, updtDocList, batch, isValidateDataList, batchSchemaService, false);

		LOGGER.info("All files are written.");
	}

	private void extractFieldsFromHOCR(final String confidenceScore, final String pattern, final String fdTypeName,
			final DocField updtDocFdType, final AlternateValues alternateValues, final String pageID, final HocrPages hocrPages)
			throws DCMAApplicationException {
		LOGGER.info("Entering method extractFieldsFromHOCR....");
		List<HocrPage> hocrPageList = hocrPages.getHocrPage();
		HocrPage hocrPage = hocrPageList.get(0);

		LOGGER.debug("HocrPage page ID : " + pageID);

		List<DataCarrier> foundData = new ArrayList<DataCarrier>();

		Spans spans = hocrPage.getSpans();

		if (null != spans) {

			List<Span> spanList = spans.getSpan();

			int index = 0;
			for (Span span : spanList) {
				try {
					LOGGER.debug("Span value : " + span.getValue());
					if (pattern.contains(RegexConstants.SEMI_COLON)) {
						String[] patternArr = pattern.split(RegexConstants.SEMI_COLON);
						List<DataCarrier> localFoundData = null;
						findPattern(span, patternArr[patternArr.length - 1], localFoundData, confidenceScore);
						if (null != localFoundData && !localFoundData.isEmpty()) {
							verifyFoundValue(spanList, index, patternArr, localFoundData);
							foundData.addAll(localFoundData);
						}
					} else {
						findPattern(span, pattern, foundData, confidenceScore);
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
				}
				index++;
			}

		}

		if (foundData != null && foundData.size() > 0) {
			processPageType(updtDocFdType, foundData, pageID, alternateValues, fdTypeName);
		}
		LOGGER.info("Exiting method extractFieldsFromHOCR....");
	}

	public List<DocField> extractFieldsAPI(final String batchClassID, final String docTypeName, final HocrPages hocrPages)
			throws DCMAApplicationException {
		LOGGER.info("Entering method extractFieldsAPI......");
		LOGGER.debug("Getting fields type for batch class : " + batchClassID + ", document : " + docTypeName);
		List<FieldType> allFdTypes = fieldTypeService.getFdTypeByDocTypeNameForBatchClass(docTypeName, batchClassID);
		String confidenceScore = batchClassPluginPropertiesService.getPropertyValue(batchClassID, REGEX_EXTRACTION_PLUGIN,
				ExtractionProperties.REGEX_CONFIDENCE_SCORE);
		LOGGER.debug("Confidence score for REGULAR_REGEX_EXTRACTION plugin : " + confidenceScore);

		List<DocField> updtDocFdTyList = null;

		if (allFdTypes == null || allFdTypes.isEmpty()) {
			LOGGER.error("No document level fields for document type : " + docTypeName);
		} else {
			updtDocFdTyList = new ArrayList<DocField>();
			for (com.ephesoft.dcma.da.domain.FieldType fdType : allFdTypes) {
				String key = fdType.getName();
				String pattern = fdType.getPattern();
				if (EphesoftStringUtil.isNullOrEmpty(pattern)) {
					LOGGER.error("No regex pattern is defined for field type : " + key);
				} else {
					int fieldOrderNumber = fdType.getFieldOrderNumber();
					LOGGER.debug("Extarcting values for field type : " + key + " , Field type pattern : " + pattern
							+ "Field type order number : " + fieldOrderNumber);

					String fdTypeName = null;

					if (fdType.getDataType() != null) {
						fdTypeName = fdType.getDataType().name();
					}

					final DocField updtDocFdType = new DocField();
					updtDocFdType.setConfidence(0.0f);
					updtDocFdType.setFieldOrderNumber(0);
					updtDocFdType.setForceReview(false);
					updtDocFdType.setOcrConfidence(0.0f);
					updtDocFdType.setOcrConfidenceThreshold(0.0f);
					updtDocFdType.setName(key);
					updtDocFdType.setFieldOrderNumber(fieldOrderNumber);
					updtDocFdType.setFieldValueChangeScript(fdType.isFieldValueChangeScriptEnabled());
					updtDocFdType.setReadOnly(fdType.getIsReadOnly());
					if(null != fdType.getCategoryName()){
						updtDocFdType.setCategory(fdType.getCategoryName());
					} else {
//						updtDocFdType.setCategory("Uncategorised");
						updtDocFdType.setCategory(CategoryType.GROUP_1.getCategoryName());
					}
					updtDocFdType.setHidden(fdType.isHidden());
					updtDocFdType.setExtractionName(REGULAR_REGEX_EXTRACTION);

					final AlternateValues alternateValues = new AlternateValues();

					setFirstFieldValue(true);
					String pageID = hocrPages.getHocrPage().get(0).getPageID();

					extractFieldsFromHOCR(confidenceScore, pattern, fdTypeName, updtDocFdType, alternateValues, pageID, hocrPages);

					if (!isFirstFieldValue()) {
						updtDocFdTyList.add(updtDocFdType);
					} else {
						updtDocFdType.setType(fdTypeName);
						updtDocFdTyList.add(updtDocFdType);
					}
				}
			}
		}
		LOGGER.info("Exiting method extractFieldsAPI......");
		return updtDocFdTyList;
	}

	/**
	 * Sort the document level fields on the basis of confidence score.
	 * 
	 * @param updtDocFdType DocFieldType
	 */
	private void sortDocFdWithConfidence(final DocField updtDocFdType) {

		if (null != updtDocFdType) {
			AlternateValues alternateValues = updtDocFdType.getAlternateValues();
			if (null != alternateValues) {

				List<Field> alternateValue = alternateValues.getAlternateValue();
				if (!alternateValue.isEmpty()) {

					Field localFdType = new Field();
					localFdType.setForceReview(Boolean.FALSE);
					localFdType.setName(updtDocFdType.getName());
					localFdType.setFieldOrderNumber(updtDocFdType.getFieldOrderNumber());
					localFdType.setValue(updtDocFdType.getValue());
					localFdType.setConfidence(updtDocFdType.getConfidence());
					localFdType.setCoordinatesList(updtDocFdType.getCoordinatesList());
					localFdType.setPage(updtDocFdType.getPage());
					localFdType.setType(updtDocFdType.getType());
					localFdType.setFieldValueChangeScript(updtDocFdType.isFieldValueChangeScript());
					localFdType.setReadOnly(updtDocFdType.isReadOnly());

					localFdType.setExtractionName(REGULAR_REGEX_EXTRACTION);

					alternateValue.add(0, localFdType);

					// sort the field type on the basis of confidence score.
					Collections.sort(alternateValue, new Comparator<Field>() {

						public int compare(Field fdType1, Field fdType2) {

							float confidenceFd = fdType1.getConfidence() - fdType2.getConfidence();

							if (confidenceFd > 0) {
								return -1;
							}
							if (confidenceFd < 0) {
								return 1;
							}

							return 0;
						}
					});

					// get the maximum confidence value.
					Field fdType = alternateValue.get(0);

					updtDocFdType.setConfidence(fdType.getConfidence());
					updtDocFdType.setCoordinatesList(fdType.getCoordinatesList());
					updtDocFdType.setName(fdType.getName());
					updtDocFdType.setFieldOrderNumber(fdType.getFieldOrderNumber());
					updtDocFdType.setPage(fdType.getPage());
					updtDocFdType.setType(fdType.getType());
					updtDocFdType.setValue(fdType.getValue());
					updtDocFdType.setExtractionName(REGULAR_REGEX_EXTRACTION);

					alternateValue.remove(0);
				}
			}
		}
	}

	/**
	 * This method will verify all the values found in the pattern matching weather they are valid or not.
	 * 
	 * @param spanList List<Span>
	 * @param index int
	 * @param patternArr String[]
	 * @param foundData List<Span>
	 */
	private void verifyFoundValue(List<Span> spanList, int index, String[] patternArr, List<DataCarrier> foundData) {

		boolean isAdd = false;
		int localIndex = index - 1;
		String preValue = null;

		for (DataCarrier dataCarrier : foundData) {
			Span span = dataCarrier.getSpan();
			for (int i = patternArr.length - 2; i >= 0 && localIndex >= 0; i--) {
				preValue = spanList.get(localIndex).getValue();
				if (patternArr[i].contains(RegexConstants.NOT_VALID)) {
					if (!preValue.contains(patternArr[i].split(RegexConstants.NOT_VALID)[1])) {
						localIndex--;
						isAdd = true;
					} else {
						isAdd = false;
						break;
					}
				} else {
					if (preValue.contains(patternArr[i])) {
						localIndex--;
						isAdd = true;
					} else {
						isAdd = false;
						break;
					}
				}
			}

			if (!isAdd) {
				foundData.remove(span);
			}
		}
	}

	/**
	 * This method will process each page of one document.
	 * 
	 * @param updtDocFdType DocFieldType
	 * @param foundData List<DataCarrier>
	 * @param key String
	 * @param pageID String
	 * @param alternateValues AlternateValues
	 * @param fdTypeName String
	 * @param fieldOrderNumber int
	 * @throws DCMAApplicationException Check for input parameters and process the document page.
	 */
	private void processPageType(final DocField updtDocFdType, final List<DataCarrier> foundData, final String pageID,
			final AlternateValues alternateValues, final String fdTypeName) throws DCMAApplicationException {

		for (DataCarrier dataCarrier : foundData) {
			Span span = dataCarrier.getSpan();
			String value = dataCarrier.getValue();
			if (isFirstFieldValue()) {
				updtDocFdType.setPage(pageID);
				updtDocFdType.setType(fdTypeName);
				updtDocFdType.setConfidence(dataCarrier.getConfidence());
				updtDocFdType.setValue(value);

				Coordinates coordinates = new Coordinates();
				Coordinates hocrCoordinates = span.getCoordinates();
				coordinates.setX0(hocrCoordinates.getX0());
				coordinates.setX1(hocrCoordinates.getX1());
				coordinates.setY0(hocrCoordinates.getY0());
				coordinates.setY1(hocrCoordinates.getY1());
				CoordinatesList coordinatesList = new CoordinatesList();
				coordinatesList.getCoordinates().add(coordinates);
				updtDocFdType.setCoordinatesList(coordinatesList);
				setFirstFieldValue(false);
			} else {

				final List<Field> alternateValue = alternateValues.getAlternateValue();

				final Field fieldType = new Field();
				fieldType.setForceReview(Boolean.FALSE);
				fieldType.setName(updtDocFdType.getName());
				fieldType.setValue(value);
				fieldType.setType(fdTypeName);
				fieldType.setConfidence(dataCarrier.getConfidence());
				fieldType.setFieldOrderNumber(updtDocFdType.getFieldOrderNumber());
				fieldType.setPage(pageID);
				fieldType.setFieldValueChangeScript(updtDocFdType.isFieldValueChangeScript());
				fieldType.setExtractionName(REGULAR_REGEX_EXTRACTION);
				fieldType.setReadOnly(updtDocFdType.isReadOnly());
				Coordinates coordinates = new Coordinates();
				Coordinates hocrCoordinates = span.getCoordinates();
				coordinates.setX0(hocrCoordinates.getX0());
				coordinates.setX1(hocrCoordinates.getX1());
				coordinates.setY0(hocrCoordinates.getY0());
				coordinates.setY1(hocrCoordinates.getY1());

				CoordinatesList coordinatesList = new CoordinatesList();
				coordinatesList.getCoordinates().add(coordinates);
				fieldType.setCoordinatesList(coordinatesList);

				alternateValue.add(fieldType);
				updtDocFdType.setAlternateValues(alternateValues);
			}
		}
	}

	/**
	 * Method is responsible for finding the patterns and returned the found data List.
	 * 
	 * @param span Span
	 * @param patternStr String
	 * @param confidenceScore String
	 * @return foundData List<DataCarrier>
	 * @throws DCMAApplicationException Check for all the input parameters and find the pattern.
	 */
	private void findPattern(Span span, String patternStr, List<DataCarrier> foundData, String confidenceScore)
			throws DCMAApplicationException {

		String errMsg = null;
		CharSequence inputStr = span.getValue();
		boolean isFound = false;
		if (null == inputStr || RegexConstants.EMPTY.equals(inputStr)) {

			errMsg = "Invalid input character sequence.";
			// throw new DCMAApplicationException(errMsg);
			LOGGER.info(errMsg);

		} else {
			// Compile and use regular
			// expression
			Pattern pattern = Pattern.compile(patternStr);
			Matcher matcher = pattern.matcher(inputStr);
			// boolean matchFound = matcher.find();
			while (matcher.find()) {
				// Get all groups for this match
				for (int i = 0; i <= matcher.groupCount(); i++) {
					String groupStr = matcher.group(i);
					if (null == foundData) {
						foundData = new ArrayList<DataCarrier>();
					}
					isFound = false;
					if (patternStr.contains(RegexConstants.NOT_SPACE)) {
						if (groupStr.contains(RegexConstants.FULL_STOP)) {
							isFound = true;
						}
					} else {
						isFound = true;
					}

					if (isFound) {
						int confidenceInt = 100;
						try {
							confidenceInt = Integer.parseInt(confidenceScore);
						} catch (NumberFormatException nfe) {
							LOGGER.error(nfe.getMessage(), nfe);
						}
						float confidence = (groupStr.length() * confidenceInt) / inputStr.length();
						DataCarrier dataCarrier = new DataCarrier(span, confidence, groupStr);
						foundData.add(dataCarrier);
						LOGGER.info(groupStr);
					}
				}
			}
		}

	}

	private class DataCarrier implements Comparable<DataCarrier> {

		private Span span;

		private float confidence;

		private String value;

		public DataCarrier(final Span span, final float confidence, final String value) {
			super();
			this.span = span;
			this.confidence = confidence;
			this.value = value;
		}

		/**
		 * Compare a given DataCarrier with this object. If confidence of this object is greater than the received object, then this
		 * object is greater than the other. As we have to finder larger confidence score value we will return -1 for this case.
		 */
		public int compareTo(DataCarrier dataCarrier) {

			int returnValue = 0;

			float confidenceData = this.getConfidence() - dataCarrier.getConfidence();

			if (confidenceData > 0) {
				returnValue = -1;
			}
			if (confidenceData < 0) {
				returnValue = 1;
			}

			return returnValue;
		}

		/**
		 * @return the span
		 */
		public Span getSpan() {
			return span;
		}

		/**
		 * @return the confidence
		 */
		public float getConfidence() {
			return confidence;
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

	}

	public void extractFieldsTestExtractionAPI(String batchClassIdentifier, String folderPath, Batch batch)
	throws DCMAApplicationException {

String errMsg = null;

if (null == this.fieldTypeService) {
	errMsg = "Invalid intialization of FieldService.";
	LOGGER.error(errMsg);
	throw new DCMAApplicationException(errMsg);
}

LOGGER.info("batch class identifier : " + batchClassIdentifier);

LOGGER.info("Initializing properties...");
String confidenceScore = batchClassPluginPropertiesService.getPropertyValue(batchClassIdentifier, REGEX_EXTRACTION_PLUGIN,
		ExtractionProperties.REGEX_CONFIDENCE_SCORE);
LOGGER.info("Properties Initialized Successfully");

try {
	final ExtractDocField extractDocField = new ExtractDocField();

	final List<Document> documentList = batch.getDocuments().getDocument();

	if (null == documentList) {
		LOGGER.info("In valid batch documents.");
	} else {
		processDocPage(documentList, batchClassIdentifier, extractDocField, batch, confidenceScore, false, folderPath);
	}
} catch (DCMAApplicationException e) {
	LOGGER.error(e.getMessage());
	throw new DCMAApplicationException(e.getMessage(), e);
} catch (Exception e) {
	LOGGER.error(e.getMessage());
	throw new DCMAApplicationException(e.getMessage(), e);
}

}

}
