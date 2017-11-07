import { connect } from 'react-redux'
import React, { Component } from 'react'
import { buyItems } from '../actions'
import {fetchItems} from '../actions'
import PropTypes from 'prop-types'
import Order from '../components/Order'

class PurchaseOrder extends Component {

    constructor(props) {
        super(props)
        this.state ={ order:[] }
    }

    static propTypes = {
      items: PropTypes.array.isRequired,
      summary: PropTypes.object.isRequired,
      dispatch: PropTypes.func.isRequired
     }


  handleChangeFor = (itemType) => (event) => {
    const { order } = this.state;
    const newOrder = {
      ...order,
      [itemType]: event.target.value
    };
    this.setState({ order: newOrder });
  }

     render() {
     		const { buyItems } = this.props
     		const { fetchItems } = this.props
     		const { items } = this.props
     		const { summary } = this.props
     		const { isEmpty } = this.props

              return (
                <div>
                  <form onSubmit={e => {
                    e.preventDefault()
                    buyItems(this.state.order)
                    fetchItems()
                    this.setState({ order: [] })
                  }}>
                <br />
                <h3>Your order</h3>
                      {items.map((item, i) =>
                       <p>
                       <label>
                       {item.itemType} &nbsp;
                            <input name={item.itemType } type="number" onChange={this.handleChangeFor(item.itemType)} value={this.state.order[item.itemType]}/>
                       </label>
                       </p>
                      )}
                    <button type="submit">
                      Buy
                    </button>
                  </form>
                  {
                  isEmpty ? ''
                  : <div>
                        <h3>Summary of your order</h3>
                        <Order summary={summary} />
                    </div>
                  }
                </div>
              )
          }
}

const mapStateToProps = state => {
	const { summary, isEmpty } = state.orderSummary
	return {
		summary: summary,
		isEmpty: isEmpty
	}
}


const mapDispatchToProps = dispatch => {
	return {
		buyItems: (order) => dispatch(buyItems(order)),
		fetchItems: () => dispatch(fetchItems())
	}
 }

export default connect(mapStateToProps, mapDispatchToProps)(PurchaseOrder)