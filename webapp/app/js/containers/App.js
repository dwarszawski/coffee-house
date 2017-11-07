import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import {fetchItems} from '../actions'
import Items from '../components/Items'
import PurchaseOrder from '../components/PurchaseOrder'

class App extends Component {
  static propTypes = {
    isFetching: PropTypes.bool.isRequired,
    items: PropTypes.array.isRequired,
    dispatch: PropTypes.func.isRequired
    }

	componentDidMount() {
		const { fetchItems } = this.props
		fetchItems()
	}

  render() {
    const { items, isFetching} = this.props
    const isEmpty = items.length === 0
    return (
      <div>
        {
            isEmpty ? (isFetching ? <h2>Loading...</h2> : <h2>Empty.</h2>)
           	    : <div style={{ opacity: isFetching ? 0.5 : 1 }}>
           	        <h3>Currently available in our Shop</h3>
           		    <Items items={items} />
           		    <PurchaseOrder items={items} />
           		  </div>
         }
      </div>
    )
  }
}

const mapStateToProps = state => {
	const { items, isFetching } = state.availableItems
	return {
		isFetching: isFetching,
		items: items
	}
}

const mapDispatchToProps = dispatch => {
	return {
		fetchItems: () => dispatch(fetchItems())
	}
 }

export default connect(mapStateToProps, mapDispatchToProps)(App)