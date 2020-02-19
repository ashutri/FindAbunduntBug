package com.online.service.bugfindings;

import org.springframework.stereotype.Component;

@Component
public class BugManager {

	private int mostAbunduntBug;
	private int occurrences;
	
	public BugManager() {}

	public BugManager(int mostAbunduntBug, int occurrences) {
		super();
		this.mostAbunduntBug = mostAbunduntBug;
		this.occurrences = occurrences;
	}

	public int getMostAbunduntBug() {
		return mostAbunduntBug;
	}

	public void setMostAbunduntBug(int mostAbunduntBug) {
		this.mostAbunduntBug = mostAbunduntBug;
	}

	public int getOccurrences() {
		return occurrences;
	}

	public void setOccurrences(int occurrences) {
		this.occurrences = occurrences;
	}
	
	

	
}
