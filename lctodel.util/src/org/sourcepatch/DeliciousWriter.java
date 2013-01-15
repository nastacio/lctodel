/********************************************************* {COPYRIGHT-TOP} ***
 * IBM Confidential
 * OCO Source Materials
 * BlueGenome
 *
 * (C) Copyright IBM Corp. 2012  All Rights Reserved.
 *
 * The source code for this program is not published or otherwise  
 * divested of its trade secrets, irrespective of what has been 
 * deposited with the U.S. Copyright Office.
 ********************************************************* {COPYRIGHT-END} **/

package org.sourcepatch;

import java.util.Date;
import java.util.List;

import org.apache.abdera.model.Category;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Link;

import del.icio.us.Delicious;

/**
 * @author Denilson Nastacio
 *
 */
public class DeliciousWriter {

	private String user;
	private String password;

	/**
	 * @param lcUrl
	 * @param user
	 * @param password
	 */
	public DeliciousWriter(String user, String password) {
		this.user = user;
		this.password = password;
	}

	/**
     * 
     */
	public void init() throws LcToDelException {

	}
	
	/**
	 * 
	 * @return
	 */
	public void writeBookmarks(List<Entry> entries) throws LcToDelException {

		Delicious d = new Delicious(user, password);
		for (Entry entry : entries) {
			String url = entry.getAlternateLink().getHref().toASCIIString();
			String description = entry.getTitle();
			String extended = entry.getText();
			List<Category> categories = entry.getCategories();
			String tags = "";
			for (Category category : categories) {
				String catTerm = category.getTerm();
				if ("bookmark".equals(catTerm)) {
					continue;
				}
				tags = tags + " " + catTerm;
			}
			Date date = entry.getUpdated();
			boolean replace = true;
			boolean shared = ! url.contains("ibm.com") && !url.contains("www.ibm.com");
			d.addPost(url, description, extended, tags, date, replace, shared);
		}
	}
	
}
