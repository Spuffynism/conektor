package com.springmvc.model.provider;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.List;
import java.util.Map;

/**
 * Provider's individual dispatcher interface
 */
public interface IProviderDispatcher<T extends IProviderArgument, U extends IProviderResponse> {
    List<U> act(Map<String, String> arguments) throws InvalidArgumentException;
    List<U> act(List<T> arguments) throws InvalidArgumentException;
}
