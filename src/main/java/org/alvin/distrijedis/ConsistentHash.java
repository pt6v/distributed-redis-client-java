package org.alvin.distrijedis;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.CRC32;

/**
 * Created by zhangshuang on 15/11/19.
 */
@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class ConsistentHash {

    private int virtualNode;

    private List<Integer> nodes;

    private Map<Long, Integer> virtualNodes;

    private Map<Integer, List> rltVirtualNodes;

    public ConsistentHash() {
        this.virtualNode = 64;
        this.nodes = new ArrayList<Integer>();
        this.virtualNodes = new TreeMap<Long, Integer>();
        this.rltVirtualNodes = new TreeMap<Integer, List>();
    }

    public ConsistentHash addNode(int node) {
        if (nodes.size() > node && nodes.get(node) != null) {
            return this;
        }
        List<Long> nodeVirtualNodes = new ArrayList<Long>();
        for (int i = 0; i < this.virtualNode; i++) {
            CRC32 crc32 = new CRC32();
            String key = "" + node + i;
            crc32.update(key.getBytes());
            long hash = crc32.getValue();
            this.virtualNodes.put(hash, node);
            nodeVirtualNodes.add(hash);

        }
        this.rltVirtualNodes.put(node, nodeVirtualNodes);

        return this;
    }

    public ConsistentHash addNodes(List<Integer> nodes) {
        for (Integer node : nodes) {
            this.addNode(node);
        }
        return this;
    }

    public int getNode(String key) {
        if (this.virtualNodes == null || this.virtualNodes.size() == 0) {
            return -1;
        }

        CRC32 crc32 = new CRC32();
        crc32.update(key.getBytes());
        long hash = crc32.getValue();
        for (Map.Entry<Long, Integer> entry : this.virtualNodes.entrySet()) {
            long virtualNode = entry.getKey();
            int node = entry.getValue();
            if (virtualNode > hash) {
                return node;
            }
        }

        return 0;

    }

    public static void main(String[] args) {
        ConsistentHash con = new ConsistentHash();
        List<Integer> nodes = new ArrayList<Integer>();
        nodes.add(100);
        nodes.add(101);
        nodes.add(102);
        con.addNodes(nodes);
        int a = con.getNode("abcdefghijklm");
        System.out.println(a);
    }

}

