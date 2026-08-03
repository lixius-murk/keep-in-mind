package com.example.keep_in_mind.controllers;

// @param <T> type of the result (use Void for operations with no return value)


public interface DatabaseCallback<T> {
    void onResult(T result);
}