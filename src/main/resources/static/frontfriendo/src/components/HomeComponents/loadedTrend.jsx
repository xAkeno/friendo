import React, { useEffect } from 'react'

const loadedTrend = props => {
  return (
    <ul className="flex flex-col gap-2 border-1 dark:focus:ring-gray-700 dark:bg-gray-800  dark:border-gray-600  dark:hover:bg-gray-700 dark:text-white rounded-[5px] mt-2">
        <li className="p-3">
            <div className="flex justify-between">
                <h1>{props.title}</h1>
                <h1>{props.date}</h1>
                <h1>{props.category}</h1>
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
