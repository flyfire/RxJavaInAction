package com.solarexsoft.rxjavainaction;

import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
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
        Disposable disposable1 = Single.zip(first, second, new BiFunction<Integer, Object, Integer>() {

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
        Disposable disposable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("/Users/ruhouhou/wumii/AndroidAthena");
            }
        }).flatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String s) throws Exception {
                return Observable.fromArray(Objects.requireNonNull(new File(s).listFiles())).map(new Function<File, String>() {
                    @Override
                    public String apply(File file) throws Exception {
                        return file.getAbsolutePath();
                    }
                });
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String file) throws Exception {
                System.out.println(file);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
    }
}
