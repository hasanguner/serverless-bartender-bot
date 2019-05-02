package net.hasanguner.di

import dagger.MapKey
import net.hasanguner.domain.lex.DialogState

@MapKey
annotation class DialogStateMapKey(val value: DialogState)
