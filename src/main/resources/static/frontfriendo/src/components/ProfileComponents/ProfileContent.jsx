import React, { useState } from 'react'
import ProfileAbout from './ProfileAbout';
import ProfilePost from './ProfilePost';
import ProfileSaved from './ProfileSaved';
const ProfileContent = (props) => {
    return (
        <div className='w-[60%]'>
            {props.choosen === 1 ? <ProfileAbout/> : (props.choosen === 2 ? <ProfilePost/> : (props.choosen === 3 ? <ProfileSaved/> : null))}
        </div>
    )
}

export default ProfileContent
