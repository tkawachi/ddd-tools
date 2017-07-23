package com.github.tkawachi.dddtools

trait Entity {
  type Id
  def id: Id
}
