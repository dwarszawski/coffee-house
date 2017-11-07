package com.dwarszawski.webstore.infra.app

import com.dwarszawski.webstore.infra.app.rest.WebStoreController
import fs2.{Stream, Task}
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.util.StreamApp

object WebStoreApp extends StreamApp {
  override def stream(args: List[String]): Stream[Task, Nothing] =
    BlazeBuilder
      .bindHttp(8088, "0.0.0.0")
      .mountService(WebStoreController.corsService)
      .serve
}
