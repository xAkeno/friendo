import React from 'react'
import Menu from '../components/HomeComponents/menu'
import EditProfileContent from '../components/EditProfileComponents/EditProfileContent'
const EditProfile = () => {
  return (
    <div className='flex'>
      <Menu Side={true}/>
      <EditProfileContent/>
    </div>
  )
}

export default EditProfile