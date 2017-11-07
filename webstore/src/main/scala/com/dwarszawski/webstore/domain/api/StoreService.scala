package com.dwarszawski.webstore.domain.api

import com.dwarszawski.webstore.domain.core.entity._

trait StoreService {
  def getAvailableItems(): Seq[ItemStatus]

  def handleOrder(order: ItemPurchase): OrderStatus

}
