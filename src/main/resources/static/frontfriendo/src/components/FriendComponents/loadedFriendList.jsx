import React from 'react'
import { useNavigate } from 'react-router-dom';

const loadedFriendList = (props) => {
  console.log(props)
  const navigate = useNavigate();
  var img = "https://static.vecteezy.com/system/resources/previews/009/292/244/non_2x/default-avatar-icon-of-social-media-user-vector.jpg";
  return (
    <div className="w-[250px] h-auto p-2 border-1 rounded-[10px] mt-3 mb-3 hover:border-gray-400 hover:cursor-pointer bg-gray-100 dark:bg-gray-800 shadow-md" onClick={() => {navigate("/Profile/" + props.username)}}>
        {/* <img src="https://images.squarespace-cdn.com/content/v1/639baf89d3544a2a17afcd6f/d59585eb-a7c6-46e1-8d50-72ba5aec90d8/0x0.jpg" className="h-[250px]"/> */}
        <span>{props.profileImg != null ? <img src={props.profileImg} className="h-[250px]"/> : <img src={img} className="h-[250px]"/>}</span>
        <div className="flex flex-col">
          <h1 className="text-2xl font-semibold">{props.username}</h1>
          <h1 className="dark:text-gray-300 text-gray-700">{props.name}</h1>
        </div>
    </div>
  )
}

export default loadedFriendList
