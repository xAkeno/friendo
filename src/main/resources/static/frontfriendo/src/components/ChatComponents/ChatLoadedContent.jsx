import axios from 'axios';
import React, { useEffect, useState } from 'react'

const ChatLoadedContent = ({ senderId, content, timestamp, profileImg}) => {
  var img = <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-circle-user-icon lucide-circle-user"><circle cx="12" cy="12" r="10"/><circle cx="12" cy="10" r="3"/><path d="M7 20.662V19a2 2 0 0 1 2-2h6a2 2 0 0 1 2 2v1.662"/></svg>;
  const formattedTime = new Date(timestamp).toLocaleTimeString([], {
      hour: '2-digit',
      minute: '2-digit',
  });
  return (
    <div className="flex items-start gap-2.5 mb-2">
        <span>{profileImg != null ? <img src={profileImg} className="w-[24px] rounded-full"/> : img}</span>
        <div className="flex flex-col w-full max-w-[320px] leading-1.5 p-4 border-gray-200 bg-gray-100 rounded-e-xl rounded-es-xl dark:bg-gray-700">
            <div className="flex items-center space-x-2 rtl:space-x-reverse">
            <span className="text-sm font-semibold text-gray-900 dark:text-white">{senderId}</span>
            <span className="text-sm font-normal text-gray-500 dark:text-gray-400">{formattedTime}</span>
            </div>
            <p className="text-sm font-normal py-2.5 text-gray-900 dark:text-white">{content}</p>
            <span className="text-sm font-normal text-gray-500 dark:text-gray-400">Delivered</span>
        </div>
    </div>
  )
}

export default ChatLoadedContent
