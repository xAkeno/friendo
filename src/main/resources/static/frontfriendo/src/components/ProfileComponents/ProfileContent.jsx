import React, { useState } from 'react'
import ProfileAbout from './ProfileAbout';
import ProfilePost from './ProfilePost';
import ProfileSaved from './ProfileSaved';
const ProfileContent = (props) => {
    return (
        <div className='w-[60%]'>
            {props.choosen === 1 ? <ProfileAbout data={props.data}/> : (props.choosen === 2 ? <ProfilePost data={props.data}/> : (props.choosen === 3 ? <ProfileSaved data={props.data}/> : null))}
        </div>
    )
}

export default ProfileContent
