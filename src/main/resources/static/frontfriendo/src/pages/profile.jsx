import React from 'react'
import Menu from '../components/HomeComponents/menu'
import Friend from '../components/FriendComponents/friend'
import ProfileBody from '../components/ProfileComponents/ProfileBody'
import Trend from '../components/HomeComponents/trend'
const profile = () => {
  return (
    <div className='flex justify-between w-full'>
        <Menu Side={true}/> 
        <ProfileBody/>
    </div>
  )
}

export default profile
