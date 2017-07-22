package com.github.tkawachi.dddtools

trait GenericEntity[IdType] extends Entity {
  override type Id = IdType
}
