import React, { useState } from 'react'
import Theme from '../theme'
const buttonGroup = (props) => {
  const [cur,setCur] = useState(1)
  const baseStyle = "px-4 py-2 text-sm font-medium border focus:z-10 focus:ring-2 ";
  const selectedStyle = "bg-white text-blue-500 border-gray-500 hover:bg-gray-100 dark:bg-gray-800 dark:border-gray-700 dark:hover:bg-gray-700 ";
  const unselectedStyle = "text-gray-900 bg-white border-gray-200 hover:bg-gray-100 hover:text-blue-700 dark:bg-gray-800 dark:border-gray-700  dark:hover:text-white dark:hover:bg-gray-700 dark:text-white";
  
  return (
    <div class="flex  justify-between items-center">
        <div></div>
        <div className=''>
            <a href="#" aria-current="page" onClick={() => {props.updateChoice(0);props.updateWord(0);setCur(1)}}  className={`rounded-l-2xl border-gray-300 ${baseStyle} ${cur === 1 ? selectedStyle : unselectedStyle}`}>
            Trending
            </a>
            <a href="#" onClick={() => {props.updateChoice(1);props.updateWord(1);setCur(0)}}  className={`rounded-r-2xl border-gray-300  ${baseStyle} ${cur === 0 ? selectedStyle : unselectedStyle}`}>
            Friends
            </a>
        </div>
        <Theme/>
      </div>
  )
}

export default buttonGroup
