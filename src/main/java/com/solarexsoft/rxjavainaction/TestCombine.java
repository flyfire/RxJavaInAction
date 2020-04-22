package com.solarexsoft.rxjavainaction;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

import java.util.concurrent.TimeUnit;

/**
 * Created by Solarex on 2020/4/22/7:54 PM
 * Desc:
 */
public class TestCombine {
    public static void main(String[] args) {
        Single<Integer> first = Single.just(111).doOnSuccess(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("first thread = " + Thread.currentThread().getName() +  "  " + integer + ",now = " + System.currentTimeMillis());
            }
        }).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                System.out.println("first subscribe thread = " + Thread.currentThread().getName() + ",now = " + System.currentTimeMillis());
            }
        }).delay(1000, TimeUnit.MILLISECONDS);
        Single<Object> second = Single.just(new Object()).doOnSuccess(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println("second thread = " + Thread.currentThread().getName() + "  " + o + ",now = " + System.currentTimeMillis());
            }
        }).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                System.out.println("second subscribe thread = " + Thread.currentThread().getName() + ",now = " + System.currentTimeMillis());
            }
        }).delay(2000, TimeUnit.MILLISECONDS);
        Single.zip(first, second, new BiFunction<Integer, Object, Integer>() {

            @Override
            public Integer apply(Integer integer, Object o) throws Exception {
                System.out.println("zip thread = " + Thread.currentThread().getName() +  "  " + integer + "," + o + ",now = " + System.currentTimeMillis());
                return integer;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("subscribe thread = " + Thread.currentThread().getName() +  "  " + integer + ",now = " + System.currentTimeMillis());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
