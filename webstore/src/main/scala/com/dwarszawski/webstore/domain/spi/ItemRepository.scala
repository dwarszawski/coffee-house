package com.dwarszawski.webstore.domain.spi

import com.dwarszawski.webstore.domain.core.entity.{ItemPurchase, ItemStatus, OrderSummary}

import scalaz.ValidationNel

trait ItemRepository {

  def collectItem (itemPurchase: ItemPurchase): Seq[ValidationNel[String, OrderSummary]]
  
  def findAll(): Seq[ItemStatus]
}
