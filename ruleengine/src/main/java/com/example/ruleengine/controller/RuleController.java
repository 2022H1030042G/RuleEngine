// src/main/java/com/example/ruleengine/controller/RuleController.java
package com.example.ruleengine.controller;

import com.example.ruleengine.model.EvaluationRequest;
import com.example.ruleengine.model.Node;
import com.example.ruleengine.model.Rule;
import com.example.ruleengine.repository.RuleRepository;
import com.example.ruleengine.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rules")
public class RuleController {

    @Autowired
    private RuleRepository ruleRepository;
    @Autowired
    private RuleService ruleService;

    @PostMapping("/create")
    public Node createRule(@RequestBody String ruleString) {
        // Create AST from rule string and return it
        Node ast = ruleService.parseRuleToAST(ruleString);

        Rule rule = new Rule(ruleString);
        ruleRepository.save(rule);

        return ast;
    }

    @PostMapping("/combine")
    public Node combineRules(@RequestBody List<String> ruleStrings) {
        // Combine multiple rules into a single AST
        Node combinedAst = ruleService.combineRulesIntoAST(ruleStrings);
        return combinedAst;
    }

    @PostMapping("/evaluate")
    public boolean evaluateRule(@RequestBody EvaluationRequest evaluationRequest) {
        // Use the new EvaluationRequest object
        return ruleService.evaluateAST(evaluationRequest.getAst(), evaluationRequest.getJsonData());
    }
}
