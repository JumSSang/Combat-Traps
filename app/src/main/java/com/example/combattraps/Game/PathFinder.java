package com.example.combattraps.Game;

import com.example.combattraps.Game.UnitDirect.Unit;
import com.example.combattraps.immortal.Vec2;

import java.util.ArrayList;
import java.util.Arrays;

public class PathFinder {
    private Unit m_dest;
    private final ArrayList<Node> openNodeList = new ArrayList<Node>();
    private int map[][]; //걸을수있는 맵인지 판정
    private Node nodeMap[][];
    private int width;
    private int height;

    public PathFinder() {
    }

    public Node find(Unit origin, Unit dest) //나의 오브젝트,목표 오브젝트
    {
        m_dest = dest;

        Node firstNode = new Node(origin.Postion);
        nodeMap[origin.Postion.x][origin.Postion.y] = firstNode;

        for(int i=0;i<height;i++)
        {
            Arrays.fill(nodeMap[i], null);
        }

        openNodeList.clear();
        openNodeList.add(firstNode);

        for (; ; ) {
            if (openNodeList.isEmpty()) return null;
            Node best = getBestNode();
            //Log.i("msg", best.m_pos+" -> "+dest.Postion);
            if (best.m_pos.equals(m_dest.Postion)) return best;
            openNode(best);
        }
    }

    public void LoadMap(int maps[][]) {
        map = maps;
        width = maps.length;
        height = maps[0].length;
        nodeMap = new Node[width][height];
    }

    private Node getBestNode() {
        int minWeight = Integer.MAX_VALUE;
        int minNodeId = -1;
        for (int i = 0; i < openNodeList.size(); i++) {
            int fast = openNodeList.get(i).fast;
            if (fast >= minWeight) continue;
            minWeight = fast;
            minNodeId = i;
        }
        if (minNodeId == -1) throw new AssertionError("최소 노드를 못찾았다?");
        return openNodeList.remove(minNodeId);
    }

    private void openNode(Node parent) {
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                if (x == 0 && y == 0) continue;
                Vec2 pos = new Vec2(parent.m_pos);
                pos.add(new Vec2(x, y));

                if(pos.x < 0) continue;
                if(pos.y < 0) continue;
                if(pos.x >= width) continue;
                if(pos.y >= height) continue;

                // 가중치 구하기
                int g;
                switch (map[pos.x][pos.y]) {
                    case 0: // 초원
                    case 1: // 초원
                    case 2: // 초원
                        g = 10;
                        break;
                    case 3: // 이거
                        g = 5000;
                        break;
                    default:
                        g = 100000;
                        break;
                }
                g = x * x + y * y == 2 ? g * 1414 / 1000 : g;

                Node node = new Node(pos, g, parent);
                Node oldNode = nodeMap[pos.x][pos.y];
                if (oldNode != null) {
                    if (oldNode.fast <= node.fast) continue;
                    openNodeList.remove(oldNode);
                }
                nodeMap[pos.x][pos.y] = node;
                openNodeList.add(node);
            }
        }
    }

    public class Node {
        public final Vec2 m_pos;
        public final int fast;
        public final int G;
        public final Node parentNode;

        public Node(Vec2 pos) {
            this.m_pos = new Vec2(pos);
            this.G = 0;
            this.fast = 0;
            this.parentNode = null;
        }

        public Node(Vec2 pos, int Gplus, Node parentNode) {
            this.m_pos = new Vec2(pos);
            this.G = parentNode.G + Gplus;
            this.fast = this.G + m_dest.Postion.getDistance(pos) * 10;
            this.parentNode = parentNode;
        }

        public Node(Node copy) {
            m_pos = new Vec2(copy.m_pos);
            G = copy.G;
            fast = copy.fast;
            parentNode = copy.parentNode;
        }

    }
}
