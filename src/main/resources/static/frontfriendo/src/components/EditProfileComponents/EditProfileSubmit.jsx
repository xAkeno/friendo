import axios from 'axios';
import React from 'react'

const EditProfileSubmit = (props) => {
  const api = async (e) => {
    const url = "http://localhost:8080/api/v1/extra/add";
    const form = new FormData();
    // console.log(props.profileImg)
    console.log(props)
    const json = new Blob(
      [JSON.stringify({
        bio: props.allData.bio,
        country: props.allData.country,
        city: props.allData.city,
        school: props.allData.school,
        status: props.allData.status
      })],{
        type:"application/json"
      }
    )
    form.append("body",json)
    form.append("img",props.profileImg);
    try{
      const res = await axios.post(url,form,{
        headers:{
          "Content-Type" : "multipart/form-data"  
        },
        withCredentials:true
      })
      if(res.status == 200){
        alert("Successfully changed")
      }
    }catch(err){
      console.log(err)
    }
    // axios.post(url,props.allData,{withCredentials:true,})
  }
  return (
    <div className='flex justify-end items-center w-[50%] max-lg:w-[100%]'>
        <button onClick={(e) => {api(e)}} type="button" class="w-[20%] cursor-pointer text-gray-900 bg-blue-700 text-white focus:outline-none hover:bg-blue-800  focus:ring-4 focus:ring-gray-100 font-medium rounded-lg text-md px-2 py-2.5 me-2 mb-2 ">Submit</button>
    </div>
  )
}

export default EditProfileSubmit
