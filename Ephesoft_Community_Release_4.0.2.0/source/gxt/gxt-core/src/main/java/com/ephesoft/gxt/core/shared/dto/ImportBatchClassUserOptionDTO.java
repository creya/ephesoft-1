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

package com.ephesoft.gxt.core.shared.dto;

import com.ephesoft.gxt.core.shared.importTree.Node;
import com.google.gwt.user.client.rpc.IsSerializable;

public class ImportBatchClassUserOptionDTO implements IsSerializable {

	private String zipFileLocation;
	private String uncFolder;
	private boolean isImportExisting;
	private boolean isWorkflowDeployed;
	private boolean isWorkflowExistsInBatchClass;
	private boolean isWorkflowEqual;
	private String name;
	private String description;
	private int priority;
	private boolean isUseSource;
	private Node uiConfigRoot;
	private String encryptionAlgo;
	private boolean importConnections;

	public ImportBatchClassUserOptionDTO() {
		super();
		this.zipFileLocation = "";
		this.name = "";
		this.description = "";
		this.priority = 0;
		this.uiConfigRoot = new Node();
	}

	public ImportBatchClassUserOptionDTO(String zipFileName, boolean isImportExisting, String name, String description,
			int priority, boolean isUseSource, Node uiConfigRoot, String encryptionAlgo) {
		super();
		this.zipFileLocation = zipFileName;
		this.isImportExisting = isImportExisting;
		this.name = name;
		this.description = description;
		this.priority = priority;
		this.isUseSource = isUseSource;
		this.uiConfigRoot = uiConfigRoot;
		this.setEncryptionAlgo(encryptionAlgo);
	}

	public Node getUiConfigRoot() {
		return uiConfigRoot;
	}

	public void setUiConfigRoot(Node uiConfigRoot) {
		this.uiConfigRoot = uiConfigRoot;
	}

	public String getZipFileName() {
		return zipFileLocation;
	}

	public void setZipFileName(String zipFileName) {
		this.zipFileLocation = zipFileName;
	}

	public boolean isImportExisting() {
		return isImportExisting;
	}

	public void setImportExisting(boolean isImportExisting) {
		this.isImportExisting = isImportExisting;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean isUseSource() {
		return isUseSource;
	}

	public void setUseSource(boolean isUseSource) {
		this.isUseSource = isUseSource;
	}

	public String getUncFolder() {
		return uncFolder;
	}

	public void setUncFolder(String uncFolder) {
		this.uncFolder = uncFolder;
	}
	
	
	public void setWorkflowDeployed(boolean isWorkflowDeployed) {
		this.isWorkflowDeployed = isWorkflowDeployed;
	}
	
	public boolean isWorkflowDeployed() {
		return isWorkflowDeployed;
	}
	
	public void setWorkflowExistsInBatchClass(boolean isWorkflowExistsInBatchClass) {
		this.isWorkflowExistsInBatchClass = isWorkflowExistsInBatchClass;
	}
	
	public boolean isWorkflowExistsInBatchClass() {
		return isWorkflowExistsInBatchClass;
	}
	
	
	public void setWorkflowEqual(boolean isWorkflowEqual) {
		this.isWorkflowEqual = isWorkflowEqual;
	}
	
	public boolean isWorkflowEqual() {
		return isWorkflowEqual;
	}

	/**
	 * @param encryptionAlgo the encryptionAlgo to set
	 */
	public void setEncryptionAlgo(String encryptionAlgo) {
		this.encryptionAlgo = encryptionAlgo;
	}

	/**
	 * @return the encryptionAlgo
	 */
	public String getEncryptionAlgo() {
		return encryptionAlgo;
	}

	/**
	 * 
	 * @return the importConnections
	 */
	public boolean isImportConnections() {
		return importConnections;
	}

	/**
	 * 
	 * @param importConnections
	 */
	public void setImportConnections(boolean importConnections) {
		this.importConnections = importConnections;
	}

}
