import React, { useState } from 'react'
import CreateSpark from '../HomeComponents/createSpark'
const spark = (props) => {
    
  return (
    <div className="w-[100%] p-5 flex justify-center items-center">
        <div className="flex justify-between items-center h-13 border-1 rounded-md border-gray-300 pt-1 pl-2 pb-1 pr-2 w-[75%]  bg-gray-100 dark:focus:ring-gray-700 dark:bg-gray-800  dark:border-gray-600  dark:hover:bg-gray-700 cursor-pointer" onClick={() => {props.updateShow(1)}}>
            <span className="text-black dark:text-white">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-circle-user-icon lucide-circle-user"><circle cx="12" cy="12" r="10"/><circle cx="12" cy="10" r="3"/><path d="M7 20.662V19a2 2 0 0 1 2-2h6a2 2 0 0 1 2 2v1.662"/></svg>
            </span>
            <button type="submit" className="cursor-pointer">What is on your mind?</button>
            <span className="cursor-pointer text-black dark:text-white">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-circle-check-big-icon lucide-circle-check-big"><path d="M21.801 10A10 10 0 1 1 17 3.335"/><path d="m9 11 3 3L22 4"/></svg>
            </span>
        </div>
    </div>
  )
}

export default spark
