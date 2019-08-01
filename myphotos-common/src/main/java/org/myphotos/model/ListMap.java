package org.myphotos.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.myphotos.infra.util.CommonUtils;

public class ListMap<K, V> {
	private final Map<K, List<V>> map = new LinkedHashMap<>();
	
	public void add(K key, V value) {
		map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
	}
	
	public Map<K, List<V>> toMap() {
		return CommonUtils.getSafeMap(map);
	}
}
