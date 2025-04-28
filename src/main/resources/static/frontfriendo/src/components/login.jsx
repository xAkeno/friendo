import React, { useState } from 'react'
import axios from 'axios'
import { useNavigate } from "react-router-dom";

const activateLogin = async (event,setFail,navigate) => {
    event.preventDefault();

    const url = "http://localhost:8080/api/v1/account/login";
    const data = {
        email: event.target.email.value,
        password: event.target.password.value
    }

    console.log(data)
    axios({
        method:'post',
        url: url,
        data: data,
        headers:{
            'Content-Type':'application/json'
        }
    }).then(response => {
        if(response.status == 200){
            console.log("nice");
            document.cookie = "userId="+ response.data.id+"; path=/; max-age=3600; secure; samesite=Strict";
            navigate("/home");
        }else{
            setFail(1);
            setTimeout(() => {
                setFail(0)
            }, 1200);
        }
    }).catch(err => {
        console.log(err); 
        setFail(1);
        setTimeout(() => {
            setFail(0);
        }, 1200);
    })
    event.target.reset();
}
const login = props => {
    const[fail,setFail] = useState(0);
    const navigate = useNavigate()
  return (
    <React.Fragment>
        <form onSubmit={(e) => activateLogin(e,setFail,navigate)}>
            <label htmlFor="website-admin" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Username</label>
            <div className="flex">
                <span className="inline-flex items-center px-3 text-sm text-blue-600 bg-gray-200 border rounded-e-0 border-gray-300 border-e-0 rounded-s-md dark:bg-gray-600 dark:text-gray-400 dark:border-gray-600">
                    <svg className="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                    <path d="M10 0a10 10 0 1 0 10 10A10.011 10.011 0 0 0 10 0Zm0 5a3 3 0 1 1 0 6 3 3 0 0 1 0-6Zm0 13a8.949 8.949 0 0 1-4.951-1.488A3.987 3.987 0 0 1 9 13h2a3.987 3.987 0 0 1 3.951 3.512A8.949 8.949 0 0 1 10 18Z"/>
                    </svg>
                </span>
                <input key="username"type="text" id="website-admin" name="email" className="rounded-none rounded-e-lg bg-gray-50 border text-gray-900 focus:ring-blue-500 focus:border-blue-500 block flex-1 min-w-0 w-full text-sm border-gray-300 p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 h-[50px]" placeholder="Username" />
            </div>

            <label htmlFor="website-admin" className="block mb-1.5 text-sm font-medium text-gray-900 dark:text-white">Password</label>
                <div className="flex">
                    <span className="inline-flex items-center px-3 text-sm text-gray-900 bg-gray-200 border rounded-e-0 border-gray-300 border-e-0 rounded-s-md dark:bg-gray-600 dark:text-gray-400 dark:border-gray-600">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-lock-icon lucide-lock"><rect width="18" height="11" x="3" y="11" rx="2" ry="2"/><path d="M7 11V7a5 5 0 0 1 10 0v4"/></svg>
                    </span>
                <input key="password" type="password" name="password" className="rounded-none rounded-e-lg bg-gray-50 border text-gray-900 focus:ring-blue-500 focus:border-blue-500 block flex-1 min-w-0 w-full text-sm border-gray-300 p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 h-[50px]" placeholder="Username" />
            </div><br />
            {fail === 1 && (
                <div className="p-4 mb-4 text-sm text-red-800 rounded-lg bg-red-50 dark:bg-gray-800 dark:text-red-400" role="alert">
                    <span className="font-medium">Failed to log in</span>
                </div>
            )}
            <button type="Submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800 w-full h-[50px] mt-24">Sign In</button>
        </form>
    
    </React.Fragment>
  )
}

export default login
