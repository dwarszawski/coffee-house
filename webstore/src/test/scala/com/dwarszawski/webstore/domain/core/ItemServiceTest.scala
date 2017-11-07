package com.dwarszawski.webstore.domain.core

import com.dwarszawski.webstore.domain.core.entity._
import com.dwarszawski.webstore.domain.spi.ItemRepository
import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec

import scalaz.syntax.validation._

class ItemServiceTest extends FlatSpec with MockFactory {
  it should "return the amount of different items" in {
    val itemRepository = stub[ItemRepository]
    (itemRepository.findAll _).when().returns(List.fill(10)(ItemStatus("A", 10, 2000)))

    val itemService = new ItemService(itemRepository)
    val result = itemService.getAvailableItems()
    assert(result.head.itemType === "A")
    assert(result.head.availableAmount === 10)
    assert(result.head.cost === 2000)
  }

  it should "handle the order and return purchase summary" in {
    val itemRepository = stub[ItemRepository]
    (itemRepository.collectItem _).when(ItemPurchase(Seq(ItemOrder("A", 10), ItemOrder("B", 15)))).returns(
      Seq(
        OrderSummary("A", 10, 10 * 20).successNel[String],
        OrderSummary("B", 15, 15 * 100).successNel[String]
      )
    )

    val itemService = new ItemService(itemRepository)
    val result = itemService.handleOrder(ItemPurchase(Seq(ItemOrder("A", 10), ItemOrder("B", 15))))
    result match {
      case SuccessfulOrder(orders) =>
        assert(orders.length == 2)
        assert(orders.head.itemType == "A")
        assert(orders.head.amount == 10)
        assert(orders.head.totalCost == 200)
        assert(orders(1).itemType == "B")
        assert(orders(1).amount == 15)
        assert(orders(1).totalCost == 1500)
      case _ => assert(false)
    }
  }

  it should "fail to process request if ordered amount exceed the available amount" in {
    val itemRepository = stub[ItemRepository]

    (itemRepository.collectItem _).when(ItemPurchase(Seq(ItemOrder("A", 10), ItemOrder("B", 15))))
      .returns(
        Seq(
          "item A is not available in 10 amount".failureNel[OrderSummary],
          "item B is not available in 15 amount".failureNel[OrderSummary]
        )
      )

    val itemService = new ItemService(itemRepository)
    val result = itemService.handleOrder(ItemPurchase(Seq(ItemOrder("A", 10), ItemOrder("B", 15))))
    result match {
      case FailedOrder(errors) =>
        assert(errors.length == 2)
        assert(errors.head == "item A is not available in 10 amount")
        assert(errors(1) == "item B is not available in 15 amount")
      case _ => assert(false)
    }
  }

  it should "fail to process whole order if one of the item is not available" in {
    val itemRepository = stub[ItemRepository]

    (itemRepository.collectItem _).when(ItemPurchase(Seq(ItemOrder("A", 10), ItemOrder("B", 15), ItemOrder("C", 20))))
      .returns(
        Seq(
          "item A is not available in 10 amount".failureNel[OrderSummary],
          OrderSummary("B", 15, 15 * 100).successNel[String],
          "item C is not available in 20 amount".failureNel[OrderSummary],
        )
      )

    val itemService = new ItemService(itemRepository)
    val result = itemService.handleOrder(ItemPurchase(Seq(ItemOrder("A", 10), ItemOrder("B", 15), ItemOrder("C", 20))))
    result match {
      case FailedOrder(errors) =>
        assert(errors.length == 2)
        assert(errors.head == "item A is not available in 10 amount")
        assert(errors(1) == "item C is not available in 20 amount")
      case _ => assert(false)
    }
  }

}
