import React from 'react'
import { useNavigate } from 'react-router-dom'

const ProfileHeader = (props) => {
  const navigate = useNavigate()
  return (
    <div className="flex gap-20 justify-center items-center px-10">
        <img className="h-48 w-48 rounded-full" src={props.data.profileImg}/>
        <div className='flex flex-col gap-5'>
            <div className='flex gap-2'>
              <h1 className="text-3xl">{props.data.username}</h1>
              <a onClick={() => navigate("./EditProfile")} className="text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-100 font-medium rounded-lg text-sm px-3 py-2.5 cursor-pointer me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700">Edit Profile</a>
            </div>
            <ul className='flex text-lg gap-5'>
                <li> <span>0</span> Posts</li>
                <li> <span>0</span> Friends</li>
            </ul>
            <h1>{props.data.firstname +" "+ props.data.lastName}</h1>
        </div>
    </div>
  )
}

export default ProfileHeader
