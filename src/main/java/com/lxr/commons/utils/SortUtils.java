package com.lxr.commons.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortUtils {

	  public static List<String> asciiSort(List<String> keys)
      {
		  List<String> infoIds = new ArrayList<>(keys);
			// 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
			Collections.sort(infoIds, new Comparator<String>() {

				public int compare(String o1, String o2) {
					return (o1.toString().compareTo(o2));
				}
			});
			
			return infoIds;

      }
	
	  
	  public static void main(String[] args) {
		  List<String> list = new ArrayList<>();
		  list.add("qewf[0]");
		  list.add("qewf[6]");
		  list.add("qewf[3]");
		  list.add("qewf[1]"); 
		  list.add("qewf[2]");
		  
		System.out.println(asciiSort(list));
	}
	
}
