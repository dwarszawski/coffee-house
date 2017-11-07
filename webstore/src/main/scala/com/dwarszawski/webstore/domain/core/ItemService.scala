package com.dwarszawski.webstore.domain.core

import com.dwarszawski.webstore.domain.api.StoreService
import com.dwarszawski.webstore.domain.core.entity._
import com.dwarszawski.webstore.domain.spi.ItemRepository

import scalaz.ValidationNel
import scalaz.syntax.applicative._
import scalaz.syntax.validation._

class ItemService(itemRepository: ItemRepository) extends StoreService {

  override def getAvailableItems(): Seq[ItemStatus] = itemRepository.findAll()

  override def handleOrder(itemPurchase: ItemPurchase): OrderStatus = {

    val appliedOrders = itemRepository.collectItem(itemPurchase)

    val status = purchaseStatusOrAccumulateErrors(appliedOrders)
    status.fold(e => FailedOrder(e.list.toList), s => SuccessfulOrder(s))
  }

  private def purchaseStatusOrAccumulateErrors(appliedOrders: Seq[ValidationNel[String, OrderSummary]]) = {
    appliedOrders.foldLeft(Seq.empty[OrderSummary].successNel[String]) {
      case (acc, v) => (acc |@| v) (_ :+ _)
    }
  }
}

