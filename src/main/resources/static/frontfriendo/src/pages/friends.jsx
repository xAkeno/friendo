import React from 'react'
import Menu from '../components/HomeComponents/menu'
import Friend from '../components/FriendComponents/friend'
import Trend from '../components/HomeComponents/trend'

const Friends = () => {
  return (
    <div className="flex w-full">
        <Menu/>
        <Friend/>
        <Trend/>
    </div>
  )
}

export default Friends
