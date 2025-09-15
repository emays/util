package com.mays.util;

import java.util.Comparator;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.slf4j.Logger;

public class Counter<K> {

	private String name;

	private TreeMap<K, Integer> count = new TreeMap<>();

	public Counter(String name) {
		super();
		this.name = name;
	}

	public void inc(K key) {
		count.merge(key, 1, Math::addExact);
	}

	public Integer get(K key) {
		return count.get(key);
	}

	public String getName() {
		return name;
	}

	public TreeMap<K, Integer> getCount() {
		return count;
	}

	public Stream<Entry<K, Integer>> getOrderedByCount() {
		return count.entrySet().stream() //
				.sorted(Comparator.comparing(Entry<K, Integer>::getValue).reversed());
	}

	public void logByKey(Logger logger) {
		logger.info(name + " counter(by key)");
		count.entrySet() //
				.forEach(es -> logger.info("\t" + es.getKey() + "\t" + es.getValue()));
	}

	public void logByCount(Logger logger) {
		logger.info(name + " counter(by count)");
		getOrderedByCount() //
				.forEach(es -> logger.info("\t" + es.getKey() + "\t" + es.getValue()));
	}

}
