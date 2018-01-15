package com.example.matan.library;


public interface Callback<T> {

    //callback if success or if failed<Generic>
    void onSuccess(T object);

    void onError(T object);

}
