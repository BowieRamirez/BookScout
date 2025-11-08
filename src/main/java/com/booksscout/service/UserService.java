package com.booksscout.service;

/**
 * Contract for authenticating and registering readers.
 */
interface UserService {
    boolean authenticate(String email, char[] password);

    void register(String email, char[] password);

    String resetHint(String email);
}
