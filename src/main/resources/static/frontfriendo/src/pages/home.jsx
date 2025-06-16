import React, { useEffect, useState } from 'react'
import Menu from '../components/HomeComponents/menu'
import Feed from '../components/HomeComponents/feed'
import Trend from '../components/HomeComponents/trend'
import CreateSpark from '../components/HomeComponents/createSpark'
import axios from 'axios'
const home = () => {
    const[show,setShow] = useState(0);
    const[username,setUsername] = useState('');
    const updateShow = (newvalue) => {
        setShow(newvalue);
    }
    const getUsername = () => {
      const url = "http://localhost:8080/api/v1/extra/getUsername";
      axios({
        method:'get',
        url:url,
        withCredentials:true
      }).then(res => setUsername(res.data))
      .catch(err => console.log(err))
    }
    useEffect(getUsername,[])
  return (
    <div className="flex justify-between w-full">
        <CreateSpark Side={true} show={show} updateShow={updateShow}/>
        <Menu username={username}/>
        <Feed updateShow={updateShow}/>
        <Trend/>
    </div>
  )
}

export default home
