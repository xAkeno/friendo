import React from 'react'

const TrendTableChild = (props) => {

  return (
    <tr className="bg-white border-b dark:bg-gray-800 dark:border-gray-700 border-gray-200">
        <th scope="row" className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
            {props.title}
        </th>
        <td className="px-6 py-4">
            {props.volume}
        </td>
        <td className="px-6 py-4">
            {props.category}
        </td>
        <td className="px-6 py-4">
            {props.date}
        </td>
        <td className="px-6 py-4">
            <span><a href={props.link} className='text-blue-500 cursor-pointer' target="_blank">Explore</a></span>
        </td>
    </tr>
  )
}

export default TrendTableChild
