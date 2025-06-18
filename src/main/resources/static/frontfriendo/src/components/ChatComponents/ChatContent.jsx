import React from 'react'
import ChatLoadedContent from './ChatLoadedContent'
const ChatContent = () => {
  return (
    <div className='flex-1 overflow-y-auto py-5'>
        <ChatLoadedContent/>
        <ChatLoadedContent/>
        <ChatLoadedContent/>
    </div>
  )
}

export default ChatContent
