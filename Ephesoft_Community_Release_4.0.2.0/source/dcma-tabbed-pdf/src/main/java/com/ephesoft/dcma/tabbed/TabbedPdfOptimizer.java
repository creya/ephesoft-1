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

package com.ephesoft.dcma.tabbed;

import java.io.File;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ephesoft.dcma.core.component.ICommonConstants;
import com.ephesoft.dcma.core.exception.DCMAApplicationException;
import com.ephesoft.dcma.core.threadpool.BatchInstanceThread;
import com.ephesoft.dcma.core.threadpool.EphesoftProcessExecutor;
import com.ephesoft.dcma.tabbed.constant.TabbedPdfConstant;
import com.ephesoft.dcma.util.ApplicationConfigProperties;
import com.ephesoft.dcma.util.OSUtil;

/**
 * Performs the optimization while creating the tabbed pdf using GhostScript.
 * 
 * @author Ephesoft
 * 
 *         <b>created on</b> Jun 20, 2013 <br/>
 * @version $LastChangedDate:$ <br/>
 *          $LastChangedRevision:$ <br/>
 */
public class TabbedPdfOptimizer implements ICommonConstants {

	private final String outputFolderPath;
	private final String inputFolderPath;
	private final BatchInstanceThread thread;
	private final String tabbedPDFName;
	private final String pdfOptimizerParam;
	private final String gsCommand;
	/**
	 * Logger instance for logging using slf4j for logging information.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TabbedPdfOptimizer.class);

	/**
	 * Constructor Initialization.
	 * 
	 * @param outputFolderPath {@link String}
	 * @param inputFolderPath {@link String}
	 * @param thread
	 * @param tabbedPDFName
	 * @param pdfOptimizerParam
	 * @param gsCommand
	 * @throws DCMAApplicationException
	 */
	public TabbedPdfOptimizer(String outputFolderPath, String inputFolderPath, BatchInstanceThread thread, String tabbedPDFName,
			String pdfOptimizerParam, String gsCommand) throws DCMAApplicationException {
		this.outputFolderPath = outputFolderPath;
		this.inputFolderPath = inputFolderPath;
		this.thread = thread;
		this.tabbedPDFName = tabbedPDFName;
		this.pdfOptimizerParam = pdfOptimizerParam;
		this.gsCommand = gsCommand;
		run();
	}

	/**
	 * Method to initiate the command for performing the tabbed pdf optimization.
	 * 
	 * @throws DCMAApplicationException
	 */
	public void run() throws DCMAApplicationException {
		try {
			String pdfOptimizerParams[] = pdfOptimizerParam.split(" ");
			ArrayList<String> commandList = new ArrayList<String>();
			if (OSUtil.isWindows()) {
				commandList.add("cmd");
				commandList.add("/c");
			}
			commandList.add(gsCommand);
			for (String param : pdfOptimizerParams) {
				commandList.add(param);
			}
			// commandList.add("-q");
			// commandList.add("-dNODISPLAY");
			// commandList.add("-P-");
			// commandList.add("-dSAFER");
			// commandList.add("-dDELAYSAFER");
			// commandList.add("--");
			// commandList.add("pdfopt.ps");

			if (OSUtil.isWindows()) {
				commandList.add("\"" + inputFolderPath + File.separator + tabbedPDFName + "\"");
				commandList.add("\"" + outputFolderPath + File.separator + tabbedPDFName + "\"");
			} else {
				commandList.add(inputFolderPath + File.separator + tabbedPDFName);
				commandList.add(outputFolderPath + File.separator + tabbedPDFName);

			}
			String[] cmds = new String[commandList.size()];
			for (int i = 0; i < commandList.size(); i++) {
				if (commandList.get(i).contains("cmd")) {
					LOGGER.info("inside cmd");
					cmds[i] = commandList.get(i);
				} else if (commandList.get(i).contains("/c")) {
					LOGGER.info("inside /c");
					cmds[i] = commandList.get(i);
				} else {
					LOGGER.info("inside remaining params");
					cmds[i] = commandList.get(i);
				}
			}

			LOGGER.info("command formed is :");
			for (int i = 0; i < cmds.length; i++) {
				LOGGER.info(cmds[i]);
			}
			LOGGER.info("command formed Ends.");
			// Replacing ProcessExecutor with EphesoftProcessExecutor
			thread.add(new EphesoftProcessExecutor(cmds, new File(System.getenv(TabbedPdfConstant.GHOSTSCRIPT_HOME)),
					ApplicationConfigProperties.getWaitTimeProperty(GS_WAIT_TIME_PROPERTY)));

		} catch (Exception e) {
			LOGGER.error("Exception while generating Tabbed PDF. " + e.getMessage());
			throw new DCMAApplicationException("Exception while generating Tabbed PDF." + e.getMessage(), e);
		}
	}
}
