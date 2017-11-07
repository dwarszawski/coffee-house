package com.dwarszawski.webstore.domain.core.entity

sealed trait OrderStatus

case class SuccessfulOrder(orders: Seq[OrderSummary]) extends OrderStatus
case class OrderSummary(itemType: String, amount: Long, totalCost: Long)

case class FailedOrder(errors: Seq[String]) extends OrderStatus


