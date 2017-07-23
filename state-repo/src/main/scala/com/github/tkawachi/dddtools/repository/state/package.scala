package com.github.tkawachi.dddtools.repository

import com.github.tkawachi.dddtools.Entity

package object state {
  type EntityMap[E <: Entity] = Map[E#Id, E]
}
