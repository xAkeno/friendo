import axios from 'axios';
import React, { useEffect, useRef, useState } from 'react'

const ChatFriend = (props) => {
  const [data,setData] = useState([]);
  var img = <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-circle-user-icon lucide-circle-user"><circle cx="12" cy="12" r="10"/><circle cx="12" cy="10" r="3"/><path d="M7 20.662V19a2 2 0 0 1 2-2h6a2 2 0 0 1 2 2v1.662"/></svg>;
  
  var imgBig = <svg xmlns="http://www.w3.org/2000/svg" width="69" height="69" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-circle-user-icon lucide-circle-user"><circle cx="12" cy="12" r="10"/><circle cx="12" cy="10" r="3"/><path d="M7 20.662V19a2 2 0 0 1 2-2h6a2 2 0 0 1 2 2v1.662"/></svg>;
  const api = () => {
    const url ="http://localhost:8080/api/v1/friend/view";

    axios({
      method:'get',
      url:url,
      withCredentials:true
    }).then(res => {
      if(res.status == 200){
        setData(res.data)
      }
    }).catch(err => console.log(err))
  }
  useEffect(() => {api();},[])

  const [scrollLevel,setScrollLevel] = useState(100);

  const scrollRef = useRef(null);
  return (
    <div className=' w-[20%] max-[767px]:w-[98%]'>
      <div>
        <span className="text-2xl ml-2">Friends</span>
      </div>
        <ul className="flex flex-col max-[767px]:flex-row overflow-auto scroll-hidden" ref={scrollRef}>
          {
            data.map((item,index) => (
              <li key={index} onClick={() => props.updateTarget(item.username)} className="flex max-[767px]:flex-col max-[767px]:gap-0 max-[767px]:items-center gap-2 dark:hover:bg-gray-500 p-2 rounded-[15px] hover:bg-gray-200 cursor-pointer">
                <span>{item.profileImg != null ? <img src={item.profileImg} className="w-[24px] max-[767px]:min-w-[69px] max-[767px]:min-h-[69px] rounded-full"/> : props.isWideScreen ? img : imgBig}</span>
                <h1 className=''>{item.username} </h1>
              </li>
            ))
          }
          <div className={
            `absolute h-[100px] flex justify-center items-center cursor-pointer ${scrollLevel > 100 ? "" : "hidden"}`
          } onClick={() => {
            console.log(scrollLevel)
            setScrollLevel(100)
            scrollRef.current?.scrollTo({
            top:0,
            left:0,
            behavior: 'smooth'})
          }}>
            <span className='w-10 h-10 rounded-full flex justify-center items-center bg-gray-500'>
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-arrow-big-left-icon lucide-arrow-big-left"><path d="M13 9a1 1 0 0 1-1-1V5.061a1 1 0 0 0-1.811-.75l-6.835 6.836a1.207 1.207 0 0 0 0 1.707l6.835 6.835a1 1 0 0 0 1.811-.75V16a1 1 0 0 1 1-1h6a1 1 0 0 0 1-1v-4a1 1 0 0 0-1-1z"/></svg>
            </span>
          </div>

          <div className="absolute h-[100px] flex justify-center items-center right-2 cursor-pointer hidden max-[757px]:flex" onClick={() => {
            const newScroll = scrollLevel + 100;
            setScrollLevel(newScroll)
            scrollRef.current?.scrollTo({
            top:0,
            left:scrollLevel,
            behavior: 'smooth'})
          }}>
            <span className='w-10 h-10 rounded-full flex justify-center items-center bg-gray-500'>
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-arrow-big-right-icon lucide-arrow-big-right"><path d="M11 9a1 1 0 0 0 1-1V5.061a1 1 0 0 1 1.811-.75l6.836 6.836a1.207 1.207 0 0 1 0 1.707l-6.836 6.835a1 1 0 0 1-1.811-.75V16a1 1 0 0 0-1-1H5a1 1 0 0 1-1-1v-4a1 1 0 0 1 1-1z"/></svg>
            </span>
          </div>
        </ul>
    </div>
  )
}

export default ChatFriend
