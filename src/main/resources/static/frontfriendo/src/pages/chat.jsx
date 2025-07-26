import React, { useEffect, useState } from 'react'
import Menu from '../components/HomeComponents/menu'
import ChatBody from '../components/ChatComponents/ChatBody'
import ChatFriend from '../components/ChatComponents/ChatFriend'
import axios from 'axios'
const chat = () => {
  const [target,setTarget] = useState();
  const updateTarget = (val) => {
    setTarget(val);
  }
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
  useEffect(getUsername,[])
  return (
    <div className='flex justify-between h-screen w-full'>
      <Menu Side={true} username={username}/>
      <ChatBody target={target} username={username}/>
      <ChatFriend updateTarget={updateTarget}/>
    </div>
  )
}

export default chat
