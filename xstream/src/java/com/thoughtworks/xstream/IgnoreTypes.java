package com.thoughtworks.xstream;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

public class IgnoreTypes {
	private static HashSet<String> ignoreNames = new HashSet<String>();
	private static List<Pattern> ignorePatterns = new ArrayList<Pattern>();
	
	private static OutputStream log = new BufferedOutputStream(System.out);

	public static void addIgnoreName(String name) {
		if (name != null)
			ignoreNames.add(name);
	}

	public static void addAllIgnoreNames(Collection<String> names) {
		if (names != null)
			ignoreNames.addAll(names);
	}

	public static void addIgnorePattern(String pattern) {
		if (pattern != null) {
			Pattern p = Pattern.compile(pattern);
			ignorePatterns.add(p);
		}
	}

	public static void addAllIgnorePatterns(Collection<String> patterns) {
		if (patterns != null)
			for (String str : patterns)
				addIgnorePattern(str);
	}

	public static boolean ignore(Class cla) {
		String name1 = cla.getName();
		String name2 = cla.getCanonicalName();
		try {
			for (Pattern p : ignorePatterns) {
				if ((name1 != null && p.matcher(name1).matches()) 
						|| (name2 != null && p.matcher(name2).matches())) {
					log.write(("[DSU] Omit type: " + name1 + "\n").getBytes());
					log.flush();
					return true;
				}
			}
			if (ignoreNames.contains(name1) || ignoreNames.contains(name2)) {
				log.write(("[DSU] Omit type: " + name1 + "\n").getBytes());
				log.flush();
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean ignore(Object obj) {
		if(obj==null)
			return false;
		return ignore(obj.getClass());
	}
}
