package com.booksscout.service;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple in-memory implementation to keep the demo self-contained.
 */
final class InMemoryUserService implements UserService {
    private final Map<String, char[]> users = new ConcurrentHashMap<>();
    private final Map<String, String> hints = new ConcurrentHashMap<>();

    InMemoryUserService() {
        addSeedUser("reader@example.com", "books123".toCharArray(), "Hint: favorite hobby plus numbers");
    }

    @Override
    public boolean authenticate(String email, char[] password) {
        Objects.requireNonNull(email, "email");
        Objects.requireNonNull(password, "password");

        var normalized = normalize(email);
        var stored = users.get(normalized);
        var matches = stored != null && Arrays.equals(stored, password);
        Arrays.fill(password, '\0');
        return matches;
    }

    @Override
    public void register(String email, char[] password) {
        Objects.requireNonNull(email, "email");
        Objects.requireNonNull(password, "password");

        var normalized = normalize(email);
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException("Email is required.");
        }
        if (password.length < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters.");
        }
        var previous = users.putIfAbsent(normalized, Arrays.copyOf(password, password.length));
        if (previous != null) {
            throw new IllegalArgumentException("Email already registered.");
        }
        hints.put(normalized, "Hint: keep your new password safe.");
        Arrays.fill(password, '\0');
    }

    @Override
    public String resetHint(String email) {
        var normalized = normalize(email);
        if (normalized.isEmpty()) {
            return "We do not have this reader on file.";
        }
        return hints.getOrDefault(normalized, "We sent a reset link if the account exists.");
    }

    private void addSeedUser(String email, char[] password, String hint) {
        var normalized = normalize(email);
        users.put(normalized, Arrays.copyOf(password, password.length));
        hints.put(normalized, hint);
        Arrays.fill(password, '\0');
    }

    private String normalize(String email) {
        return email.trim().toLowerCase();
    }
}
