import React from 'react'
import { useNavigate } from 'react-router-dom';

const loadedSearcch = (props) => {
  const navigate = useNavigate();

  var img = "https://static.vecteezy.com/system/resources/previews/009/292/244/non_2x/default-avatar-icon-of-social-media-user-vector.jpg";
  return (
    <div className='flex items-center gap-2 p-1 rounded-xl hover:bg-gray-200 hover:cursor-pointer' key={props.username} onClick={() => {navigate("/Profile/"+props.username)}}>
        <span>{props.profileImg != null ? <img src={props.profileImg} className="h-12 rounded-full"/> : <img src={img} className="h-12 rounded-full"/>}</span>
        <div className="flex flex-col">
            <span className="font-bold text-xl">{props.username}</span>
            <span>{props.name}</span>
        </div>
    </div>
  )
}

export default loadedSearcch
