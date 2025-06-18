import React from 'react'
import Menu from '../components/HomeComponents/menu'
import ChatBody from '../components/ChatComponents/ChatBody'
import ChatFriend from '../components/ChatComponents/ChatFriend'
const chat = () => {
  return (
    <div className='flex justify-between h-screen w-full'>
      <Menu Side={true}/>
      <ChatBody />
      <ChatFriend />
    </div>
  )
}

export default chat
