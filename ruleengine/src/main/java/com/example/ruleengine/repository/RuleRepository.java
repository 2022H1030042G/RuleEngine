// src/main/java/com/example/ruleengine/repository/RuleRepository.java
package com.example.ruleengine.repository;

import com.example.ruleengine.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<Rule, Long> {
}
