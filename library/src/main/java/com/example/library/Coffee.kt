@file:Suppress("CanSealedSubClassBeObject")

package com.example.library

import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class Water @Inject constructor()

sealed class CoffeeMaker

@Singleton
class DripCoffeeMaker @Inject constructor(
//    val water: Water
): CoffeeMaker()

@Singleton
class PourOverCoffeeMaker @Inject constructor(
//    val water: Water
): CoffeeMaker()

@Singleton
class EspressoCoffeeMaker @Inject constructor(
//    val water: Water
): CoffeeMaker()

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class CoffeeMakerKey(val value: KClass<out CoffeeMaker>)

@Module
@InstallIn(ApplicationComponent::class)
abstract class CoffeeMakerModule {

    @[Binds IntoMap CoffeeMakerKey(DripCoffeeMaker::class)]
    abstract fun bindDripCoffeeMaker(dripCoffeeMaker: DripCoffeeMaker): CoffeeMaker

    @[Binds IntoMap CoffeeMakerKey(PourOverCoffeeMaker::class)]
    abstract fun pourOverCoffeeMaker(pourOverCoffeeMaker: PourOverCoffeeMaker): CoffeeMaker

    @[Binds IntoMap CoffeeMakerKey(EspressoCoffeeMaker::class)]
    abstract fun espressoCoffeeMaker(espressoCoffeeMaker: EspressoCoffeeMaker): CoffeeMaker
}
