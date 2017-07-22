package com.github.tkawachi.dddtools.repository

import com.github.tkawachi.dddtools.Entity

import scala.language.higherKinds

trait StandardRepository[E <: Entity, F[_]]
    extends BasicRepository[E, F]
    with DeleteRepository[E, F]
    with BasicMultiRepository[E, F]
    with DeleteMultiRepository[E, F]
