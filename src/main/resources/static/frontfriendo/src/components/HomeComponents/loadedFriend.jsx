import axios from 'axios';
import React, { useEffect, useState } from 'react'

const loadedFriend = () => {
  const [data,setData] = useState([]);
  const img = <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-circle-user-icon lucide-circle-user"><circle cx="12" cy="12" r="10"/><circle cx="12" cy="10" r="3"/><path d="M7 20.662V19a2 2 0 0 1 2-2h6a2 2 0 0 1 2 2v1.662"/></svg>;
  const api = () => {
    const url ="http://localhost:8080/api/v1/friend/view";
    const userId = document.cookie.split('; ').find(row => row.startsWith('userId='))?.split('=')[1];

    axios({
      method:'get',
      url:url,
      withCredentials:true
    }).then(res => {
      if(res.status == 200){
        setData(res.data)
        console.log(res.data)
      }
    }).catch(err => console.log(err))
    console.log("dsds")
  }
  useEffect(() => {api();},[])
  return (
    <div>
        <ul className="flex flex-col ">
          {
            data.map((item,index) => (
              <li key={index} className="flex gap-2 dark:hover:bg-gray-500 p-2 rounded-[15px] hover:bg-gray-200 cursor-pointer">
                <span>{img}</span>
                <h1>{item.username} </h1>
              </li>
            ))
          }
          
        </ul>
    </div>
  )
}

export default loadedFriend
