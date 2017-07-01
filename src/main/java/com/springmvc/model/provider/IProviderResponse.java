package com.springmvc.model.provider;

/**
 * All provider calls made return this
 */
public interface IProviderResponse {
    boolean isSuccessful();
    int getTripTime();
    String getMessage();
}
