package com.github.tkawachi.dddtools

package object repository {
  type EntityMap[E <: Entity] = Map[E#Id, E]
}
