import React from 'react'
import Theme from '../theme'
const buttonGroup = (props) => {

  return (
    <div class="flex rounded-md shadow-xs justify-between items-center">
        <div></div>
        <div>
            <a href="#" aria-current="page" onClick={() => {props.updateChoice(0);props.updateWord(0)}} class="px-4 py-2 text-sm font-medium text-blue-700 bg-white border border-black rounded-s-lg hover:bg-gray-100 focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700 dark:bg-gray-800 dark:border-gray-700 dark:text-white dark:hover:text-white dark:hover:bg-gray-700 dark:focus:ring-blue-500 dark:focus:text-white">
            Trending
            </a>
            {/* <a href="#" class="px-4 py-2 text-sm font-medium text-gray-900 bg-white border-t border-b border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700 dark:bg-gray-800 dark:border-gray-700 dark:text-white dark:hover:text-white dark:hover:bg-gray-700 dark:focus:ring-blue-500 dark:focus:text-white">
            Settings
            </a> */}
            <a href="#" onClick={() => {props.updateChoice(1);props.updateWord(1)}} class="px-4 py-2 text-sm font-medium text-gray-900 bg-white border border-black rounded-e-lg hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-blue-700 focus:text-blue-700 dark:bg-gray-800 dark:border-gray-700 dark:text-white dark:hover:text-white dark:hover:bg-gray-700 dark:focus:ring-blue-500 dark:focus:text-white">
            Friends
            </a>
        </div>
        <Theme/>
      </div>
  )
}

export default buttonGroup
