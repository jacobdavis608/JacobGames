package practice;

import java.util.*;

public class DoublyLinkedList(){
    private Node head;
    private int size;

    public DoublyLinkedList(){
        size = 0;
    }
    public DoublyLinkedList(int headVal){
        this.head = new Node(headVal);
        size = 1;
    }

    public void insert(int value){
        Node newNode = new Node(value);
        

        Node temp = head;
        newNode.setChild(head);
        
        head = newNode;
        head.set
    }
}