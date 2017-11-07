import React from 'react'
import PropTypes from 'prop-types'

const Order = ({summary}) => (
  <ul>
    { (summary.SuccessfulOrder) ?
        (summary.SuccessfulOrder.orders.map((ord, i) =>
           <li key={i}>{ord.itemType} x {ord.amount}. Total cost: {ord.totalCost}</li>
           )
         )
      : ((summary.FailedOrder) ?
          (summary.FailedOrder.errors.map((err, i) =>
           <li key={i}>{err}</li>
           )
         ) : '')
    }
  </ul>
)

Order.propTypes = {
  summary: PropTypes.object.isRequired
}

export default Order