/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusementpark;


import java.awt.Point;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
/**
 *
 * @author sukap
 */

class Node {
    // `(x, y)` represents matrix cell coordinates, and
    // `dist` represents their minimum distance from the source
    int x, y, dist;
    
    Node parent;
 
    Node(int x, int y, int dist, Node parent) {
        this.x = x;
        this.y = y;
        this.dist = dist;
        this.parent = parent;
    }
}

public class BFS {
    
    private static final int M = 14;
    private static final int N = 38;
 
    // Below arrays detail all four possible movements from a cell
    private static final int[] row = { -1, 0, 0, 1 };
    private static final int[] col = { 0, -1, 1, 0 };
    
    
    public BFS () {
        
    }
    
    
    /**
     * Function to check if it is possible to go to position `(row, col)`
     * from the current position. The function returns false if `(row, col)`
     * is not a valid position or has a value other than 1 or already visited.
     * 
     * 
     * @param mat
     * @param visited
     * @param row
     * @param col
     * @param x
     * @param y
     * @return
     */
    
    public static boolean isValid(int mat[][], boolean visited[][], int row, int col, int x, int y) {   
        return (row >= 0) && (row < M) && (col >= 0) && (col < N)
                && mat[row][col] == 1 && !visited[row][col];
    }
 

    public static ArrayList<Point> useBFS(int mat[][], int i, int j, int x, int y) {
        
        //2 for Restaurants, 3 for Games, 4 for Gardens, 5 for TrashCans, 1 for Path
        int temp = mat [x][y];
        
        if (temp != 1) {
            mat [x] [y] = 1;
            mat [x] [y+1] = 1;
            mat [x+1] [y] = 1;
            mat [x+1] [y+1] = 1;
        }
        
        
        
        boolean[][] visited = new boolean[M][N];
 
        Queue<Node> q = new ArrayDeque<>();
 
        visited[i][j] = true;
        q.add(new Node(i, j, 0, null));
 
        // stores length of the longest path from source to destination
        int min_dist = Integer.MAX_VALUE;
        Node node = null;
        
        while (!q.isEmpty()) {
            
            node = q.poll();
 
            i = node.x;
            j = node.y;
            int dist = node.dist;
 
            // if the destination is found, update `min_dist` and stop
            if (i == x && j == y)
            {
                min_dist = dist;
                break;
            }
 
            // check for all four possible movements from the current cell
            // and enqueue each valid movement
            for (int k = 0; k < 4; k++)
            {
                // check if it is possible to go to position
                // `(i + row[k], `j` + col[k])` from current position
                if (isValid(mat, visited, i + row[k], j + col[k], x, y))
                {
                    // mark next cell as visited and enqueue it
                    visited[i + row[k]][j + col[k]] = true;
                    q.add(new Node(i + row[k], j + col[k], dist + 1, node));
                }
            }
        }
        
        if (temp != 1) {
            mat [x] [y] = temp;
            mat [x] [y+1] = temp;
            mat [x+1] [y] = temp;
            mat [x+1] [y+1] = temp;
        }
 
        if (min_dist != Integer.MAX_VALUE){
            
            /*System.out.println("The shortest path from source to destination " +
                                    "has length " + min_dist);*/
            
            return returnPath(node, mat);
        }
        else {
            //System.out.print("Destination can't be reached from a given source");
        }
        
        return null;
    }
    
    private static ArrayList<Point> returnPath(Node node, int[][] mat) {
        ArrayList<Point> p = new ArrayList<Point>();;
        
        while (node != null) {
            p.add (new Point(node.x, node.y));
            System.out.println(node.x + ", " + node.y);
            node = node.parent;
        }

        return p;
    }
}
