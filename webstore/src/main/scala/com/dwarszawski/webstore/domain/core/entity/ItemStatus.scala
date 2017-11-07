package com.dwarszawski.webstore.domain.core.entity

case class ItemAvailability(items: Seq[ItemStatus])

case class ItemStatus(itemType: String, availableAmount: Long, cost: Long)

