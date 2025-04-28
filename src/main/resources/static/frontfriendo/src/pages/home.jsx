import React, { useState } from 'react'
import Menu from '../components/HomeComponents/menu'
import Feed from '../components/HomeComponents/feed'
import Trend from '../components/HomeComponents/trend'
import CreateSpark from '../components/HomeComponents/createSpark'
const home = () => {
    const[show,setShow] = useState(0);

    const updateShow = (newvalue) => {
        setShow(newvalue);
    }
  return (
    <div className="flex justify-between w-full">
        <CreateSpark show={show} updateShow={updateShow}/>
        <Menu/>
        <Feed updateShow={updateShow}/>
        <Trend/>
    </div>
  )
}

export default home
