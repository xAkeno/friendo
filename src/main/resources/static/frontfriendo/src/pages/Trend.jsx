import React from 'react'
import Menu from '../components/HomeComponents/menu'
import TrendBody from '../components/TrendComponents/TrendBody'
const Trend = () => {
  return (
    <div className='flex w-full'>
      <Menu Side={true}/>
      <TrendBody />
    </div>
  )
}

export default Trend
