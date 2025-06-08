import React, { useState } from 'react'
import ProfileHeader from './ProfileHeader'
import ProfileMyDay from './ProfileMyDay'
import ProfileContentChoose from './ProfileContentChoose'
import ProfileContent from './ProfileContent'
const ProfileBody = () => {
    const [choosen,setChoosen] = useState(1);
    const updateChoose = (updated) => {
        setChoosen(updated);
    }
  return (
    <div className='w-full mt-5 flex justify-center items-center flex flex-col'>
      <div className='w-[60%] pb-10 px-10 border-b-1 flex flex-col gap-5'>
        <ProfileHeader/>
        <ProfileMyDay/>
      </div>
        <ProfileContentChoose updateChoose={updateChoose} choosen={choosen}/>
        <ProfileContent choosen={choosen}/>
    </div>
  )
}

export default ProfileBody
