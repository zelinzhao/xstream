package com.thoughtworks.xstream;

import java.util.Collection;
import java.util.HashSet;

import com.thoughtworks.xstream.core.JVM;

public class IgnoreTypes {
	private static HashSet<String> ignoreNames = new HashSet<String>();
	
	public static void addIgnore(String name) {
		ignoreNames.add(name);
	}
	public static void addAllIgnore(Collection<String> names) {
		ignoreNames.addAll(names);
	}
	public static boolean ignore(Object obj) {
		String name1 = obj.getClass().getName();
		String name2 = obj.getClass().getCanonicalName();
		if(ignoreNames.contains(name1)||ignoreNames.contains(name2))
			return true;
		return false;
	}
}
