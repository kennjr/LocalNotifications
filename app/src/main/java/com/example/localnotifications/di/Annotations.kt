package com.example.localnotifications.di

import javax.inject.Qualifier

/**
 * This annotation class is what we'll use to identify the Basic notification's Builder
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BasicNotificationBuilder

/**
 * This annotation class is what we'll use to identify the Progress notification's Builder
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProgressNotificationBuilder