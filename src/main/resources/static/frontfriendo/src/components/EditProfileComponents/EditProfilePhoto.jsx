import React, { useRef, useState } from 'react'

const EditProfilePhoto = (props) => {
    const imgVal = useRef();
    const [img,setImg] = useState('');
    const updateImg = () =>{
        const files = imgVal.current.files[0];
        props.getImg(files);

        const reader = new FileReader();
        reader.onload = (event) => {
            setImg(event.target.result)
        };
        reader.readAsDataURL(files)
    }
    console.log(props.image)
  return (
    <div className='flex justify-between items-center w-[50%] bg-gray-100 border-1 border-gray-400 dark:bg-gray-800 px-5.5 py-5 rounded-md'>
        <div className='flex items-center gap-5'>
            <img className="h-28 w-28 rounded-full" src={img || props.image}/>
            <div className='flex flex-col '>
                <span className='text-2xl font-semibold'>Akeno</span>
                <span className='text-md text-gray-800 dark:text-gray-400'>Clark kent Raguhos</span>
            </div>
        </div>
        <div>
            <label htmlFor='img' class="cursor-pointer text-gray-900 bg-blue-700 text-white focus:outline-none hover:bg-blue-800  focus:ring-4 focus:ring-gray-100 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 ">Change Photo</label>
            <input ref={imgVal} onChange={updateImg} type="file" id='img' className='hidden'/>
        </div>
    </div>
  )
}

export default EditProfilePhoto
