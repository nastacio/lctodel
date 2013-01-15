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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Link;
import org.apache.abdera.parser.Parser;

/**
 * @author root
 * 
 */
public class LcBookmarkReader {

	private String lcUrl;
	private String user;
	private String password;

	/**
	 * @param lcUrl
	 * @param user
	 * @param password
	 */
	public LcBookmarkReader(String lcUrl, String user, String password) {
		this.lcUrl = lcUrl;
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
	public List<Entry> getBookmarks() throws LcToDelException {
		List<Entry> result = new ArrayList<Entry>();
		Abdera abdera = new Abdera();
		Parser abderaParser = abdera.getParser();

		
		String nextUrl = lcUrl;
		while (nextUrl != null) {
			System.out.println(nextUrl);
			InputStream inFeed;
			try {
				URL inputUrl = new URL(nextUrl);
				URLConnection conn = inputUrl.openConnection();
				conn.setRequestProperty(
						"User-Agent",
						"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.11) Gecko/20101012 (CK-IBM) Firefox/3.6.11 ( .NET CLR 3.5.30729)");

				inFeed = conn.getInputStream();
			} catch (IOException e) {
				throw new LcToDelException("Unable to load data from url: " + nextUrl + " due to: " + e.getMessage(), e);
			}

			Document<Feed> doc = abderaParser.parse(inFeed);
			Feed feed = doc.getRoot();
			List<Entry> feedEntries = feed.getEntries();
			result.addAll(feedEntries);

			// Is there a link to follow ?
			Link feedNextLink = feed.getLink(Link.REL_NEXT);
			nextUrl = (feedNextLink == null ? null : feedNextLink.getResolvedHref()
					.toString());
			nextUrl = null; 
		} 

		return result;
	}
}
