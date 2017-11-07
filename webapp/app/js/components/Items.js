import React from 'react'
import PropTypes from 'prop-types'

const Items = ({items}) => (
  <ul>
    {items.map((item, i) =>
      <li key={i}>{item.itemType}: {item.availableAmount} left (cost: {item.cost})</li>
    )}
  </ul>
)

Items.propTypes = {
  items: PropTypes.array.isRequired
}

export default Items