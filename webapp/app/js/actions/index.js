export const REQUEST_ITEMS = 'REQUEST_ITEMS'
export const RECEIVE_ITEMS = 'RECEIVE_ITEMS'
export const RECEIVE_SUMMARY = 'RECEIVE_SUMMARY'

export const requestItems = () => ({
  type: REQUEST_ITEMS
})

export const receiveItems = (json) => {
 console.log(json)
 return{
  type: RECEIVE_ITEMS,
  items: json
  }
}

export const receiveSummary = (json) => {
 console.log(json)
 return{
  type: RECEIVE_SUMMARY,
  summary: json
  }
}

export const fetchItems = () => dispatch => {
  dispatch(requestItems())
  return fetch('http://localhost:8088/items')
    .then(response => response.json())
    .then(json => dispatch(receiveItems(json)))
}

export const buyItems = (order) => dispatch => {
   var itemOrders = Object.entries(order).map((a) => Object.create({}, {
    itemType: { value: a[0], enumerable: true},
    amount: { value: a[1], enumerable: true}
     }))
    var newObj = Object.assign({}, ...itemOrders)
    var body = JSON.stringify(newObj)
   return fetch('http://localhost:8088/items', {
         method: 'POST',
         headers: {
           'Accept': 'application/json',
           'Content-Type': 'application/json',
         },
         body: JSON.stringify({ orders: itemOrders})
       }).then(response => response.json())
         .then(json => dispatch(receiveSummary(json)))
}