
package practice;

public class Node(){
    private int value;
    private Node parent;
    private Node child;

    public Node(){
        value = 0;
    }

    public Node(int val){
        value = val;
    }

    public Node(int value, Node parent, Node child){
        this.value = value;
        this.parent = parent;
        this.child = child;
    }

    public void setValue(int val){
        value = val;
    }

    public int getValue(){
        return this.value;
    }

    public void setParent(Node rent){
        this.parent = rent;
    }

    public Node getParent(){
        return this.parent;
    }

    public void setChild(Node child){
        this.child = child;
    }

    public Node getChild(){
        return this.child;
    }

}