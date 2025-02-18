package edu.whut.cs.javaee.spring.ai.sql;

import java.util.List;
import java.util.Map;

public record Answer(String sqlQuery, List<Map<String, Object>> results) { }
