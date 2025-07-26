import axios from 'axios';
import React, { useEffect, useState } from 'react'

const ChatHead = (props) => {
  const [profileImg,setProfileImg] = useState("");
  var img = <svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-circle-user-icon lucide-circle-user"><circle cx="12" cy="12" r="10"/><circle cx="12" cy="10" r="3"/><path d="M7 20.662V19a2 2 0 0 1 2-2h6a2 2 0 0 1 2 2v1.662"/></svg>;
  useEffect(() => {
    const api =  async () => {
      const url = "http://localhost:8080/auth/profile";
      const res = await axios.get(url,{withCredentials:true,params:{username:props.target},headers:{'Content-Type':'application/json'}});

      setProfileImg(res.data.profileImg);
    }
    if (props.target) {
      api();
    }
  },[props.target])
  return (
    <div className=''>
        <div className='flex items-center dark:bg-gray-700 px-3 py-4 rounded-md gap-2'>
            <span>{profileImg != null ? <img src={profileImg} className="h-16 w-16 rounded-full"/> : img}</span>
            <span>{props.target || "Choose a friend from the right to view your conversation. ➡️➡️➡️"}</span>
        </div>
    </div>
  )
}

export default ChatHead
