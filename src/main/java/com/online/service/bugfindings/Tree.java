package com.online.service.bugfindings;

import java.util.LinkedList;
import java.util.Queue;

import org.springframework.stereotype.Component;

@Component
public class Tree {

	Node root; 

	// Tree Node 
	static class Node { 
		int data; 
		Node left, right; 
		Node(int data) 
		{ 
			this.data = data; 
			this.left = null; 
			this.right = null; 
		} 
	} 

	// Function to insert nodes in level order 
	public Node insert(Node temp, int key) 
    { 
        Queue<Node> q = new LinkedList<Node>(); 
        q.add(temp); 
       
        // Do level order traversal until we find 
        // an empty place.  
        while (!q.isEmpty()) { 
            temp = q.peek(); 
            q.remove(); 
       
            if (temp.left == null && temp.data>key) { 
                temp.left = new Node(key); 
                break; 
            } else
                q.add(temp.left); 
       
            if (temp.right == null && temp.data<key) { 
                temp.right = new Node(key); 
                break; 
            } else
                q.add(temp.right); 
        } 
        return temp;
    } 

	// Function to print tree nodes in InOrder fashion 
	public void inOrder(Node root) 
	{ 
		if (root != null) { 
			inOrder(root.left); 
			System.out.print(root.data + " "); 
			inOrder(root.right); 
		} 
	}
}
