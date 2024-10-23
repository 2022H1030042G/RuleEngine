// src/main/java/com/example/ruleengine/model/EvaluationRequest.java
package com.example.ruleengine.model;

public class EvaluationRequest {
    private String jsonData;
    private Node ast;

    // Constructors
    public EvaluationRequest() {}

    public EvaluationRequest(String jsonData, Node ast) {
        this.jsonData = jsonData;
        this.ast = ast;
    }

    // Getters and Setters
    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public Node getAst() {
        return ast;
    }

    public void setAst(Node ast) {
        this.ast = ast;
    }
}
