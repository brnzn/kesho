package com.kesho.datamart.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 2/3/14
 * Time: 7:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class ValidationResult {
    Map<String, String> violations = new HashMap<String, String>();
    public void add(String message, String fieldPath) {
        violations.put(message, fieldPath);
    }

    public Map<String, String> getViolations() {
        return violations;
    }

    public boolean isValid() {
        return violations.isEmpty();
    }

    public Set<Map.Entry<String, String>> values() {
        return violations.entrySet();
    }
}
