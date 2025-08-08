import axios from 'axios';
import React, { useEffect, useState } from 'react'
import LoadedTrend from './LoadedTrend';
import TrendTable from './TrendTable';
const TrendBody = () => {
  return (
    <div className='w-full p-5 max-md:pb-[75px]'>
        {/* <h1 className="text-2xl font-semibold mt-3 dark:text-white">Trending Now :</h1> */}
        <TrendTable />
    </div>
  )
}

export default TrendBody
