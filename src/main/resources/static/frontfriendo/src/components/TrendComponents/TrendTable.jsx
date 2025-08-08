import React, { useEffect, useState } from 'react'
import axios from 'axios';

import TrendTableChild from './TrendTableChild';
const TrendTable = () => {
    const [data,setData] = useState([]);
    const api = () => {
        const url = "http://localhost:8080/api/v1/trending/trend";
        axios.get(
            url,
            {
                withCredentials:true
            }
        ).then(res => {
            if(res.status == 200){
                setData(res.data)
            }
        }).catch(err => console.log(err))
    }
    useEffect(() => (api()),[])
  return (
    <div className="flex shadow-md sm:rounded-lg w-[85%] max-lg:w-[100%]">
        <div className="w-full rounded-2xl overflow-y-auto">
            <table className="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                <caption className="p-5 text-2xl font-semibold text-left rtl:text-right text-gray-900 bg-white dark:text-white dark:bg-gray-800">
                    Trending Now
                    <p className="mt-1 text-sm font-normal text-gray-500 dark:text-gray-400">
                    Browse a list of Flowbite products designed to help you work and play, stay organized, get answers, keep in touch, grow your business, and more.
                    </p>
                </caption>
                <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                    <tr>
                        <th scope="col" className="px-6 py-3">Title</th>
                        <th scope="col" className="px-6 py-3">Search Volume</th>
                        <th scope="col" className="px-6 py-3">Category</th>
                        <th scope="col" className="px-6 py-3">Date</th>
                        <th scope="col" className="px-6 py-3">Trend Breakdown</th>
                        {/* <th scope="col" className="px-6 py-3"><span className="sr-only">Edit</span></th> TrendTableChild */}
                    </tr>
                </thead>
                <tbody>
                    {
                        data.map((item,index) => (
                            <TrendTableChild title={item.title} active={item.active} volume={item.volume} date={item.data} category={item.category} link={item.newslink}/>
                        ))
                    }
                </tbody>
            </table>
        </div>
        </div>


  )
}

export default TrendTable
