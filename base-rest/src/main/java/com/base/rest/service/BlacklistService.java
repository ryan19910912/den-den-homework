package com.base.rest.service;

public interface BlacklistService {

    void add(String email);

    void cleanUpBlacklist();

    boolean isBlacklisted(String email);
}
