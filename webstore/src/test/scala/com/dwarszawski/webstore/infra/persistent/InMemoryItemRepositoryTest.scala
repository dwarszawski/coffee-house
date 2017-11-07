package com.dwarszawski.webstore.infra.persistent

import com.dwarszawski.webstore.domain.core.entity.{ItemOrder, ItemPurchase, ItemStatus}
import org.scalatest.FlatSpec

class InMemoryItemRepositoryTest extends FlatSpec {
  it should "succesfully update item store and return summary" in {
    val repository = new InMemoryItemRepository
    repository.warehouse.put("A", ItemStatus("A", 20, 20))
    repository.warehouse.put("D", ItemStatus("D", 60, 60))

    val status = repository.collectItem(
      ItemPurchase(orders = Seq(
        ItemOrder("A", 19),
        ItemOrder("D", 33)
      ))
    )

    assert(status.length == 2)
    assert(status.head.isSuccess)
    assert(status.head.forall(_.itemType == "A"))
    assert(status.head.forall(_.amount == 19))
    assert(status.head.forall(_.totalCost == 19 * 20))
    assert(status(1).isSuccess)
    assert(status(1).forall(_.itemType == "D"))
    assert(status(1).forall(_.amount == 33))
    assert(status(1).forall(_.totalCost == 33 * 60))
  }

  it should "compensate all applied updated to item store if one of the update cannot be applied" in {
    val repository = new InMemoryItemRepository
    repository.warehouse.put("A", ItemStatus("A", 20, 20))
    repository.warehouse.put("D", ItemStatus("D", 60, 60))

    val status = repository.collectItem(
      ItemPurchase(orders = Seq(
        ItemOrder("A", 21),
        ItemOrder("D", 33)
      ))
    )

    assert(status.length == 2)
    assert(status.head.isFailure)
    status.head.leftMap(errs =>
      assert(errs.head == "Cannot process purchase of item A because available amount is lower than 21")
    )
    assert(status(1).isSuccess)
    assert(status(1).forall(_.itemType == "D"))
    assert(status(1).forall(_.amount == 33))
    assert(status(1).forall(_.totalCost == 33 * 60))

    assert(repository.warehouse.get("A").nonEmpty)
    assert(repository.warehouse("A").itemType == "A")
    assert(repository.warehouse("A").availableAmount == 20)
    assert(repository.warehouse.get("D").nonEmpty)
    assert(repository.warehouse("D").itemType == "D")
    assert(repository.warehouse("D").availableAmount == 60)
  }
}
