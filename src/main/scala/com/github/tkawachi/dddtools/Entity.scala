package com.github.tkawachi.dddtools

trait Entity {
  type Id
  def id: Id

  def canEqual(other: Entity): Boolean = getClass == other.getClass

  override def equals(obj: Any): Boolean =
    obj match {
      case oe: Entity if canEqual(oe) => id == oe.id
      case _ => false
    }

  override def hashCode(): Int = id.hashCode()
}
