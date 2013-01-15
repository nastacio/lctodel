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

import java.util.List;

import org.apache.abdera.model.Entry;
import org.junit.Test;

/**
 * @author root
 *
 */
public class DeliciousWriterTest {

	/**
	 * Test method for {@link org.sourcepatch.DeliciousWriter#writeBookmarks(java.util.List)}.
	 */
	@Test
	public void testWriteBookmarks() throws Exception {
		String user = "dnastaci@us.ibm.com";
		String lcBookmarkUrl = "https://w3-connections.ibm.com/dogear/atom?sort=date&sortOrder=desc&ps=50&email="+user;
		String password = "";
		LcBookmarkReader lbr = new LcBookmarkReader(lcBookmarkUrl, user, password);
		lbr.init();
		List<Entry> fe = lbr.getBookmarks();

		String delUser = "nastacio";
		String delPassword = "r2d2c3po";
		
		DeliciousWriter dw = new DeliciousWriter(delUser, delPassword);
		dw.init();
		dw.writeBookmarks(fe);
	}

}
