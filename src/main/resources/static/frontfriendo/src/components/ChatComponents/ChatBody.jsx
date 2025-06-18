import React from 'react'

import ChatHead from './ChatHead'
import ChatContent from './ChatContent'
import ChatInput from './ChatInput'

const ChatBody = () => {
  return (
    <div className='w-[55%] dark:bg-gray-800 flex flex-col h-full p-3 rounded-md mt-2'>
        <ChatHead/>
        <ChatContent/>
        <ChatInput/>
    </div>
  )
}

export default ChatBody
