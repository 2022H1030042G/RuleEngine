// src/main/java/com/example/ruleengine/service/RuleService.java
package com.example.ruleengine.service;

import com.example.ruleengine.model.Node;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class RuleService {

    private ObjectMapper objectMapper = new ObjectMapper();

    public Node parseRuleToAST(String ruleString) {
        // Parsing logic to create AST from rule string
        // Example implementation (basic parsing for demonstration purposes)
        return parseExpression(ruleString);
    }

    private Node parseExpression(String expression) {
        expression = expression.trim();
        // Remove outer parentheses if they exist
        if (expression.startsWith("(") && expression.endsWith(")")) {
            expression = expression.substring(1, expression.length() - 1).trim();
        }

        // Check for AND and OR operators and split accordingly
        int operatorIndex = findMainOperator(expression);
        if (operatorIndex != -1) {
            String operator = expression.charAt(operatorIndex) == '&' ? "AND" : "OR";
            Node leftNode = parseExpression(expression.substring(0, operatorIndex).trim());
            Node rightNode = parseExpression(expression.substring(operatorIndex + (operator.equals("AND") ? 3 : 2)).trim());
            return new Node("operator", leftNode, rightNode, operator);
        }

        // For operands (conditions), create leaf nodes
        return new Node("operand", null, null, expression);
    }

    private int findMainOperator(String expression) {
        int depth = 0;
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == '(') depth++;
            else if (ch == ')') depth--;
            else if (depth == 0 && (expression.startsWith("AND", i) || expression.startsWith("OR", i))) {
                return i; // return the index of the operator
            }
        }
        return -1; // no operator found
    }

    public Node combineRulesIntoAST(List<String> ruleStrings) {
        // For simplicity, just combine rules using OR logic
        Node combinedNode = null;
        for (String rule : ruleStrings) {
            Node ast = parseRuleToAST(rule);
            if (combinedNode == null) {
                combinedNode = ast; // first rule
            } else {
                combinedNode = new Node("operator", combinedNode, ast, "OR");
            }
        }
        return combinedNode;
    }

    public boolean evaluateAST(Node ast, String jsonData) {
        try {
            JsonNode data = objectMapper.readTree(jsonData);
            return evaluateNode(ast, data);
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Error in evaluation
        }
    }

    private boolean evaluateNode(Node node, JsonNode data) {
        if (node.getType().equals("operand")) {
            return evaluateCondition(node.getValue(), data);
        } else if (node.getType().equals("operator")) {
            boolean leftValue = evaluateNode(node.getLeft(), data);
            boolean rightValue = evaluateNode(node.getRight(), data);
            return node.getValue().equals("AND") ? leftValue && rightValue : leftValue || rightValue;
        }
        return false;
    }

    private boolean evaluateCondition(String condition, JsonNode data) {
        String[] parts = condition.split(" ");
        String attribute = parts[0];
        String operator = parts[1];
        String value = parts[2].replace("'", "").trim(); // Remove quotes if it's a string

        switch (operator) {
            case ">":
                return data.get(attribute).asInt() > Integer.parseInt(value);
            case "<":
                return data.get(attribute).asInt() < Integer.parseInt(value);
            case "=":
                return data.get(attribute).asText().equals(value);
            // Add other operators as needed
            default:
                return false;
        }
    }
}

