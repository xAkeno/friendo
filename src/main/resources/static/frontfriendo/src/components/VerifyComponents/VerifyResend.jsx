import axios from 'axios';
import React from 'react'
const resend = (event,json) => {
    event.preventDefault();
    const url = "http://localhost:8080/auth/resend";
    const email = json.email;
    const data = {"email":email,"verificationCode":""}
    axios({
        method:'post',
        url:url,
        data:data,
        withCredentials:true,
        headers:{
            'Content-Type':'application/json'
        }
    }).then(res => {
        if(res.status == 200){
            alert("Successfully resend try check your email again")
        }else{
            alert("something must have happen")
        }
    }).catch(err => {console.log(err)})
}
const VerifyResend = (email) => {
  return (
    <div className="text-sm text-slate-500 mt-4">
        Didn't receive code? 
        <form onSubmit={e => resend(e,email)}>
            <button type="submit" className="font-medium text-indigo-500 hover:text-indigo-600 cursor-pointer">Resend</button>
        </form>
    </div>
  )
}

export default VerifyResend
