import React from 'react'

const EditProfileSubmit = (props) => {
  return (
    <div className='flex justify-end items-center w-[50%]'>
        <button onClick={() => {console.log(props.allData)}} type="button" class="w-[20%] cursor-pointer text-gray-900 bg-blue-700 text-white focus:outline-none hover:bg-blue-800  focus:ring-4 focus:ring-gray-100 font-medium rounded-lg text-md px-2 py-2.5 me-2 mb-2 ">Submit</button>
    </div>
  )
}

export default EditProfileSubmit
