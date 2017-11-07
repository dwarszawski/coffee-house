package com.dwarszawski.webstore.infra.app.rest

import com.dwarszawski.webstore.domain.core.ItemService
import com.dwarszawski.webstore.domain.core.entity.ItemPurchase
import com.dwarszawski.webstore.infra.persistent.InMemoryItemRepository
import org.http4s._
import org.http4s.headers._
import org.http4s.MediaType._
import org.http4s.dsl._
import org.http4s.circe._
import io.circe.syntax._
import org.http4s.server.middleware.CORS

object WebStoreController {
  private val itemRepository = new InMemoryItemRepository()
  private val itemService = new ItemService(itemRepository)

  import com.dwarszawski.webstore.infra.app.rest.json.codecs._

  private val service = HttpService {
    case GET -> Root / "items" =>
      Ok(itemService.getAvailableItems().asJson)
        .putHeaders(
          `Content-Type`(`application/json`)
        )

    case req @ POST -> Root / "items" =>
      for {
        order <- req.as(jsonOf[ItemPurchase])
        status = itemService.handleOrder(order)
        resp <- Ok(status.asJson)
      } yield resp
  }

  val corsService = CORS(service)
}
