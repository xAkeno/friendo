import axios from 'axios';
import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';

const SubmitVerify = (event,setFail,email,setSuccessMessage,setErrorMessage,setSuccess,navigate) =>{
    event.preventDefault();
    var loc = event.target;
    var code = loc.one.value + loc.two.value + loc.three.value + loc.four.value + loc.five.value + loc.six.value;
    var json = email;
    const url = "http://localhost:8080/auth/verify";
    const data = {
        "email":json.email,
        "verificationCode":code
    }
    axios({
        method: 'post',
        url: url,
        data: data,
        withCredentials:true,
        headers: {
            'Content-Type':'application/json'
        }
    }).then(response => {
            if(response.status == 200){
                setSuccess(1)
                setSuccessMessage("Successfully verified redirecting to login")
                setTimeout(() => {
                    navigate("/");
                }, 1200);
            }else{
                setFail(1)
                setErrorMessage("Check the code carefullyee")
            }
        }).catch(err => {
            console.log(err);
            setFail(1)
            setErrorMessage("Check the code carefullyz")
            setTimeout(() => {
                setFail(0)
            }, 1200);
        })
    console.log(data)
}
const VerifyInput = (email) => {
    const [fail,setFail] = useState(0);
    const [successMessage, setSuccessMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [success, setSuccess] = useState(0);
    const navigate = useNavigate();
  return (
    <form id="otp-form" onSubmit={e => SubmitVerify(e,setFail,email,setSuccessMessage,setErrorMessage,setSuccess,navigate)}>
        <div className="flex items-center justify-center gap-3">
            <input
                name="one"
                type="text"
                className="w-14 h-14 text-center text-2xl font-extrabold text-slate-900 bg-slate-100 border border-transparent hover:border-slate-200 appearance-none rounded p-4 outline-none focus:bg-white focus:border-indigo-400 focus:ring-2 focus:ring-indigo-100"
                pattern="\d*" maxLength="1" />
            <input
                name="two"
                type="text"
                className="w-14 h-14 text-center text-2xl font-extrabold text-slate-900 bg-slate-100 border border-transparent hover:border-slate-200 appearance-none rounded p-4 outline-none focus:bg-white focus:border-indigo-400 focus:ring-2 focus:ring-indigo-100"
                maxLength="1" />
            <input
                name="three"
                type="text"
                className="w-14 h-14 text-center text-2xl font-extrabold text-slate-900 bg-slate-100 border border-transparent hover:border-slate-200 appearance-none rounded p-4 outline-none focus:bg-white focus:border-indigo-400 focus:ring-2 focus:ring-indigo-100"
                maxLength="1" />
            <input
                name="four"
                type="text"
                className="w-14 h-14 text-center text-2xl font-extrabold text-slate-900 bg-slate-100 border border-transparent hover:border-slate-200 appearance-none rounded p-4 outline-none focus:bg-white focus:border-indigo-400 focus:ring-2 focus:ring-indigo-100"
                maxLength="1" />
            <input
                name="five"
                type="text"
                className="w-14 h-14 text-center text-2xl font-extrabold text-slate-900 bg-slate-100 border border-transparent hover:border-slate-200 appearance-none rounded p-4 outline-none focus:bg-white focus:border-indigo-400 focus:ring-2 focus:ring-indigo-100"
                maxLength="1" />
            <input
                name="six"
                type="text"
                className="w-14 h-14 text-center text-2xl font-extrabold text-slate-900 bg-slate-100 border border-transparent hover:border-slate-200 appearance-none rounded p-4 outline-none focus:bg-white focus:border-indigo-400 focus:ring-2 focus:ring-indigo-100"
                maxLength="1" />
        </div>
        <div className="max-w-[260px] mx-auto mt-4">
            <button type="submit" name="verify"
                className="w-full inline-flex justify-center whitespace-nowrap rounded-lg bg-indigo-500 px-3.5 py-2.5 text-sm font-medium text-white shadow-sm shadow-indigo-950/10 hover:bg-indigo-600 focus:outline-none focus:ring focus:ring-indigo-300 focus-visible:outline-none focus-visible:ring focus-visible:ring-indigo-300 transition-colors duration-150">Verify
                Account</button>
        </div>
        {success === 1 && (
            <div className="p-4 m-4 text-sm text-green-800 rounded-lg bg-green-50 dark:bg-gray-800 dark:text-green-400" role="alert">
                <span className="font-medium">Success!</span> {successMessage}
            </div>
        )}
        {fail === 1 && (
            <div className="p-4 m-4 text-sm text-red-800 rounded-lg bg-red-50 dark:bg-gray-800 dark:text-red-400" role="alert">
                <span className="font-medium">Error!</span> {errorMessage}
            </div>
        )}
    </form>
  )
}

export default VerifyInput
