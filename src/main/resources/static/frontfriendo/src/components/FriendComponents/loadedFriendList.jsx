import React from 'react'

const loadedFriendList = (props) => {
  return (
    <div className="w-[250px] h-auto p-2 border-1 rounded-[10px] mt-3 mb-3">
        <img src="https://images.squarespace-cdn.com/content/v1/639baf89d3544a2a17afcd6f/d59585eb-a7c6-46e1-8d50-72ba5aec90d8/0x0.jpg" className="h-[250px]"/>
        <div className="flex flex-col">
            <h1 className="my-3">{props.name}</h1>
        </div>
    </div>
  )
}

export default loadedFriendList
