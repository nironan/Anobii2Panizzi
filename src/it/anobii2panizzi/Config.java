package it.anobii2panizzi;

import java.util.HashMap;
import java.util.List;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class Config {

	// TODO replace with http://commons.apache.org/proper/commons-configuration/
	// TODO: add ordering of favourite libraries
	// TODO: order by availability instead of library
	public static final String DEFAULT_LIBRARY = "PANIZZI";

	public static BiMap<String, String> LIBRARIES_SYNONYMS;
	
	public static final boolean ORDER_BY_LIBRARY = true;
	
	public static final String INPUT_FILE_LOCATION = "D:/Git_repository/Anobii2Panizzi/wishlist.xlsx";
	public static final String OUTPUT_FILE_LOCATION = "D:/Git_repository/Anobii2Panizzi/wishlist_CH.xlsx";

	static {
		LIBRARIES_SYNONYMS = HashBiMap.create();
		LIBRARIES_SYNONYMS.put("PANIZZI", "Biblioteca Panizzi sezione moderna");
		LIBRARIES_SYNONYMS.put("Ospizio", "Decentrata Ospizio");
	}
	
	public static final boolean areLibrariesEquivalent(String libraryName1, String libraryName2) {
		if (LIBRARIES_SYNONYMS.containsKey(libraryName1)) {
			return LIBRARIES_SYNONYMS.get(libraryName1).equalsIgnoreCase(libraryName2);
					
		} else if (LIBRARIES_SYNONYMS.containsValue(libraryName1)) {
			return LIBRARIES_SYNONYMS.inverse().get(libraryName1).equalsIgnoreCase(libraryName2);
			
		} else {
			return false;
		}
	}
	
}
