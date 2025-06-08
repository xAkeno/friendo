import React from 'react'

const ProfileContentChoose = (props) => {
  return (
    <div>
      <ul className='flex text-[20px] font-semibold text-gray-300 gap-15 py-5 [&>li]:cursor-pointer'>
        <li className={props.choosen === 1 && "text-blue-700"} onClick={() => (props.updateChoose(1))}>About</li>
        <li className={props.choosen === 2 && "text-blue-700"} onClick={() => (props.updateChoose(2))}>Post</li>
        <li className={props.choosen === 3 && "text-blue-700"} onClick={() => (props.updateChoose(3))}>Saved</li>
      </ul>
    </div>
  )
}

export default ProfileContentChoose