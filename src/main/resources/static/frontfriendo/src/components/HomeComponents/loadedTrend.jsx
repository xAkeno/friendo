import React, { useEffect } from 'react'

const loadedTrend = props => {
  return (
    <ul className="flex flex-col gap-2 border-1 dark:focus:ring-gray-700 dark:bg-gray-800  dark:border-gray-600  dark:hover:bg-gray-700 dark:text-white rounded-[5px] mt-2">
        <li className="p-3">
            <div className="flex justify-between">
                <span className="bg-blue-100 text-blue-800 text-[15px] font-medium me-2 px-2 py-0.5 rounded-sm dark:bg-blue-900 dark:text-blue-300">{props.title}</span>
                <h1>{props.date}</h1> 
                <span className="bg-green-100 text-green-800 text-sm font-medium me-2 px-2.5 py-0.5 rounded-sm dark:bg-green-900 dark:text-green-300">{props.category}</span>
            </div>
            <div className="flex justify-between">
                <h1>{props.volume} Searches</h1>
                <h1>{props.active}</h1>
            </div>
        </li>
    </ul>
  )
}

export default loadedTrend
