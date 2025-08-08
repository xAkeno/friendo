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

  const [isWideScreen, setIsWideScreen] = useState(window.innerWidth > 767);

  useEffect(() => {
    const handleResize = () => {
      setIsWideScreen(window.innerWidth > 767);
    };

    // Attach listener
    window.addEventListener('resize', handleResize);

    // Cleanup
    return () => window.removeEventListener('resize', handleResize);
  }, []);
  return (
    <div className='flex justify-between h-screen w-full max-md:pb-[65px] overflow-hidden'>
      <Menu Side={true} username={username}/>
      {
        isWideScreen ? (
          <>
            <ChatBody target={target} username={username}/>
            <ChatFriend updateTarget={updateTarget}/>
          </>
        )  :<div className='flex flex-col w-full pl-3 max-md:pb-[139px]'>
          <ChatFriend updateTarget={updateTarget} isWideScreen={isWideScreen}/>
          <ChatBody target={target} username={username} isWideScreen={isWideScreen}/>
        </div> 
      }
    </div>
  )
}

export default chat
