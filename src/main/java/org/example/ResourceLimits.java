package org.example;

public record ResourceLimits(int minCpu, int maxCpu) {
    public ResourceLimits {
        if (minCpu <= 0 || maxCpu < minCpu) {
            throw new IllegalArgumentException("Invalid CPU allocation bounds.");
        }
    }
}