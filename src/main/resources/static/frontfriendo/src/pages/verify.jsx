import React from 'react'
import VerifyHeader from '../components/VerifyComponents/VerifyHeader'
import VerifyInput from '../components/VerifyComponents/VerifyInput'
import VerifyResend from '../components/VerifyComponents/VerifyResend'
import { useLocation } from 'react-router-dom'
const verify = () => {
    const location = useLocation();
    const data = location.state;

    console.log(data);
  return (
    <div className="flex justify-center items-center w-full h-[100vh]">
        <div className="max-w-md mx-auto text-center bg-white px-4 sm:px-8 py-10 rounded-xl shadow">
            <VerifyHeader/>
            <VerifyInput email={data.email}/>
            <VerifyResend email={data.email}/>
        </div>
    </div>
  )
}

export default verify
