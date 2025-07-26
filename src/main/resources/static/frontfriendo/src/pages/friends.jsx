import React, { useEffect, useState } from 'react'
import Menu from '../components/HomeComponents/menu'
import Friend from '../components/FriendComponents/friend'
import Trend from '../components/HomeComponents/trend'
import axios from 'axios'

const Friends = () => {
  const[username,setUsername] = useState('');
  const getUsername = () => {
  const url = "http://localhost:8080/api/v1/extra/getUsername";
    axios({
      method:'get',
      url:url,
      withCredentials:true
    }).then(res => setUsername(res.data))
    .catch(err => console.log(err))
  }
  useEffect(getUsername,[]);
  return (
    <div className="flex w-full">
        <Menu username={username}/>
        <Friend/>
        <Trend/>
    </div>
  )
}

export default Friends
