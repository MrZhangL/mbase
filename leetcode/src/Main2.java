import java.util.*;
public class Main2 {
    public static void main(String[] args) {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();

        treeMap.put(3, 1);
        treeMap.put(3, 1);
        treeMap.put(5, 2);
        treeMap.put(1, 3);

        SortedMap<Integer, Integer> sortedMap = treeMap.tailMap(4);
        System.out.println(sortedMap.firstKey());
        System.out.println(treeMap.tailMap(4, false).firstKey());
        System.out.println(treeMap.floorEntry(5));
        System.out.println(treeMap.ceilingEntry(5));
    }
}