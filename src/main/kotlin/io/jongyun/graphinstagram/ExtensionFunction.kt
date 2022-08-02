package io.jongyun.graphinstagram

import java.util.concurrent.CompletableFuture

fun <T> T.toFuture(): CompletableFuture<T> = CompletableFuture.completedFuture(this)

fun <T> asyncInIo(func: () -> T): CompletableFuture<T> = CompletableFuture.supplyAsync(func)