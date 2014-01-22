/**
 * Project name : slyak-core
 * File name : IndexSeacherFactoryBean.java
 * Package name : com.slyak.core.lucene
 * Date : 2013年12月24日
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.core.lucene;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.BytesRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndexSeacherHelper {
	
	static final Logger LOGGER = LoggerFactory.getLogger(IndexSeacherHelper.class);
	
	private static Directory directory;
	
	private static DirectoryReader directoryReader;
	
	private static IndexSearcher indexSearcher;
	
	public void setDirectory(Directory directory) {
		IndexSeacherHelper.directory = directory;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	public static IndexSearcher getIndexSeacher() throws IOException {
		if(indexSearcher == null || !directoryReader.isCurrent()){
			directoryReader = DirectoryReader.open(directory);
			indexSearcher = new IndexSearcher(directoryReader);
		}else{
			if(!directoryReader.isCurrent()){
				directoryReader = DirectoryReader.openIfChanged(directoryReader);
				indexSearcher = new IndexSearcher(directoryReader);
			}
		}
		return indexSearcher;
	}
	
	public static int docCount() throws IOException{
		return getIndexSeacher().getIndexReader().numDocs();
	}
	
	public List<String> freqWords(String field){
		try {
			Terms terms = getIndexSeacher().getIndexReader().getTermVector(0, field);
			TermsEnum termsEnum = terms.iterator(null);
			BytesRef text ;
			Map<String, Integer> frequencies = new HashMap<String, Integer>();
			while ((text = termsEnum.next()) != null) {
			    String term = text.utf8ToString();
			    int freq = (int) termsEnum.totalTermFreq();
			    frequencies.put(term, freq);
//			    terms.add(term);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	} 
	

}
