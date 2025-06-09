import React, { useState } from 'react'
import EditProfilePhoto from './EditProfilePhoto'
import EditProfileBio from './EditProfileBio'
import EditProfileExtra from './EditProfileExtra'
import EditProfileSubmit from './EditProfileSubmit'

const EditProfileContent = () => {
    const jsonPass = {
        "img":"",
        "bio":"",
        "country":"",
        "city":"",
        "school":"",
        "status":""
    }
    const [data,setData] = useState(jsonPass)
    const api = () => {

    }
    const getAllExtra = (country, city, school, status) => {
        setData(prev => ({
            ...prev,
            country,
            city,
            school,
            status
        }));
    };

    // Update bio
    const getBio = (bio) => {
        setData(prev => ({
            ...prev,
        bio}));
    };
    const getImg = (img) => {
        setData(prev => ({
            ...prev,
            img
        }))
    }
  return (
    <div className='w-full mt-5 flex justify-center items-center flex flex-col gap-4'>
      <EditProfilePhoto getImg={getImg}/>
      <EditProfileBio getBio={getBio}/>
      <EditProfileExtra getAllExtra={getAllExtra}/>
      <EditProfileSubmit allData={data}/>
    </div>
  )
}

export default EditProfileContent
