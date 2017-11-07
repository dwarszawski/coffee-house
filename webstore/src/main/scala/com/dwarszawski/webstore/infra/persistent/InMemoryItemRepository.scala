package com.dwarszawski.webstore.infra.persistent

import java.util.concurrent.ConcurrentHashMap

import com.dwarszawski.webstore.domain.core.entity.{ItemPurchase, ItemStatus, OrderSummary}
import com.dwarszawski.webstore.domain.spi.ItemRepository

import scala.collection.JavaConverters._
import scala.collection._
import scalaz.syntax.validation._

class InMemoryItemRepository extends ItemRepository {

  private[persistent] val warehouse: concurrent.Map[String, ItemStatus] = new ConcurrentHashMap[String, ItemStatus]().asScala

  warehouse.put("Cappuccino", ItemStatus("Cappuccino", 30, 10))
  warehouse.put("Americano", ItemStatus("Americano", 50, 7))
  warehouse.put("Espresso", ItemStatus("Espresso", 25, 5))

  override def collectItem(itemPurchase: ItemPurchase) = synchronized {
    val snapshot = collection.mutable.Map[String, ItemStatus]() ++= warehouse

    val updateStatus = for {
     order <- itemPurchase.orders
     (itemType, amount) = (order.itemType, order.amount)
    } yield {
      warehouse.get(itemType) match {
        case Some(status) if status.availableAmount >= amount =>
          warehouse.put(itemType, status.copy(availableAmount = status.availableAmount - amount))
          OrderSummary(itemType, amount, amount * status.cost).successNel[String]
        case _ =>
          s"Cannot process purchase of item $itemType because available amount is lower than $amount"
            .failureNel[OrderSummary]
      }
    }

    if(updateStatus.exists(_.isFailure)) { // compensating action is required if no transaction available
      warehouse.clear()
      warehouse ++= snapshot
    }

    updateStatus
  }

  override def findAll() = warehouse.values.toSeq
}
