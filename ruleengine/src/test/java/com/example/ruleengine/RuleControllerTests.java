// src/test/java/com/example/ruleengine/RuleControllerTests.java
package com.example.ruleengine;

import com.example.ruleengine.controller.RuleController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RuleControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RuleController ruleController;

    @BeforeEach
    public void setUp() {
        // Additional setup if needed
    }

    @Test
    public void testCreateRule() throws Exception {
        String ruleString = "((age > 30 AND department = 'Sales') OR (age < 25 AND department = 'Marketing')) AND (salary > 50000 OR experience > 5)";
        mockMvc.perform(post("/api/rules/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"" + ruleString + "\"")) // Wrap in quotes to match JSON format
                .andExpect(status().isOk());
    }

    @Test
    public void testCombineRules() throws Exception {
        String rule1 = "((age > 30 AND department = 'Sales'))";
        String rule2 = "((age < 25 AND department = 'Marketing'))";

        mockMvc.perform(post("/api/rules/combine")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"" + rule1 + "\", \"" + rule2 + "\"]")) // JSON array
                .andExpect(status().isOk());
    }

    @Test
    public void testEvaluateRule() throws Exception {
        String ruleString = "((age > 30 AND department = 'Sales') OR (age < 25 AND department = 'Marketing')) AND (salary > 50000 OR experience > 5)";
        String jsonData = "{\"age\": 35, \"department\": \"Sales\", \"salary\": 60000, \"experience\": 3}";

        // First, create the rule to get the AST
        mockMvc.perform(post("/api/rules/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"" + ruleString + "\""))
                .andExpect(status().isOk());

        // Now, evaluate the rule against the JSON data
        mockMvc.perform(post("/api/rules/evaluate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"jsonData\": " + jsonData + ", \"ast\": {\"type\": \"operator\", ...}}")) // Replace with actual AST structure
                .andExpect(status().isOk());
    }
}
