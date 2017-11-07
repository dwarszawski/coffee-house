import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { combineReducers } from 'redux'
import {
  REQUEST_ITEMS, RECEIVE_ITEMS, RECEIVE_SUMMARY
} from '../actions'


const availableItems = (state = {
  isFetching: false,
  items: []
}, action) => {
  switch (action.type) {
    case REQUEST_ITEMS:
      return {
        ...state,
        isFetching: true,
      }
    case RECEIVE_ITEMS:
      return {
        ...state,
        isFetching: false,
        items: action.items
      }
    default:
      return state
  }
}

const orderSummary = (state = {
        summary: {},
        isEmpty: true
      }, action) => {
        switch (action.type) {
          case RECEIVE_SUMMARY:
            return {
              ...state,
              summary: action.summary,
              isEmpty: false,
            }
          default:
            return state
        }
      }


const rootReducer = combineReducers({
 availableItems,
 orderSummary
})

export default rootReducer