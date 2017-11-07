package com.dwarszawski.webstore.infra.app.rest.json

import com.dwarszawski.webstore.domain.core.entity._
import io.circe._
import io.circe.generic.semiauto._

object codecs {
  implicit val itemStatusEncoder: Encoder[ItemStatus] = deriveEncoder[ItemStatus]
  implicit val orderStatusEncoder: Encoder[OrderStatus] = deriveEncoder[OrderStatus]
  implicit val successStatusEncoder: Encoder[SuccessfulOrder] = deriveEncoder[SuccessfulOrder]
  implicit val orderSummaryEncoder: Encoder[OrderSummary] = deriveEncoder[OrderSummary]
  implicit val failedStatusEncoder: Encoder[FailedOrder] = deriveEncoder[FailedOrder]

  implicit val itemPurchaseDecoder: Decoder[ItemPurchase] = deriveDecoder[ItemPurchase]
  implicit val itemDecoder: Decoder[ItemOrder] = deriveDecoder[ItemOrder]
}
