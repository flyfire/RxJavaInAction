package com.solarexsoft.rxjavainaction

import io.reactivex.Observable
import io.reactivex.rxkotlin.zipWith
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Solarex on 2020/4/22/4:45 PM
 * Desc:
 */

fun delayOperations() {
    println("start ${Date()}")
    Observable.create<String> { s ->
        println("subscribing ${Date()}")
        s.onNext("a")
        s.onNext("b")
        s.onNext("c")
    }.doOnNext {
        println(">$it ${Date()}")
    }.delay(1000, TimeUnit.MILLISECONDS)
        .zipWith(
        Observable.range(1, 7).delay(2000, TimeUnit.MILLISECONDS).doOnNext { println(">$it ${Date()}") }.doOnComplete { println("done") }
    ).subscribe { println(it) }
}


fun main() {
    delayOperations()
}