// src/main/java/com/example/ruleengine/model/Node.java
package com.example.ruleengine.model;

public class Node {
    private String type; // "operator" or "operand"
    private Node left; // left child
    private Node right; // right child for operators
    private String value; // value for operand nodes (e.g., number for comparisons)

    public Node(String type, Node left, Node right, String value) {
        this.type = type;
        this.left = left;
        this.right = right;
        this.value = value;
    }

    // Getters and Setters
    // ...

    public String getType() {
        return type;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public String getValue() {
        return value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
