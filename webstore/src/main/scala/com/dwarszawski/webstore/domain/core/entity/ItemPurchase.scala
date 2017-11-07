package com.dwarszawski.webstore.domain.core.entity

case class ItemPurchase(orders: Seq[ItemOrder])
case class ItemOrder(itemType:String, amount: Long)
