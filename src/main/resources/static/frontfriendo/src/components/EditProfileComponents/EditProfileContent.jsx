    import React, { useEffect, useState } from 'react'
    import EditProfilePhoto from './EditProfilePhoto'
    import EditProfileBio from './EditProfileBio'
    import EditProfileExtra from './EditProfileExtra'
    import EditProfileSubmit from './EditProfileSubmit'
    import axios from 'axios'

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
        const [profileImg,setProfileImg] = useState(null);
        const api = () => {
            const url = "http://localhost:8080/auth/profile";
            axios({
                url:url,
                method:"get",
                withCredentials:true
            }).then(res => {
                if(res.status == 200){
                    const result = res.data;
                    setData({
                        img: result.profileImg || '',
                        bio: result.bio || '',
                        country: result.country || '',
                        city: result.city || '',
                        school: result.school || '',
                        status: result.status || ''
                    });
                }
            }).catch(err => console.log(err))
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
            setProfileImg(img)
        }
        useEffect(api,[])
        console.log(data)
    return (
        <div className='w-full mt-5 flex justify-center items-center flex flex-col gap-4'>
        <EditProfilePhoto getImg={getImg} image={data.img}/>
        <EditProfileBio getBio={getBio} bio={data.bio} />
        <EditProfileExtra getAllExtra={getAllExtra} country={data.country} city={data.city} school={data.school} status={data.status}/>
        <EditProfileSubmit allData={data} profileImg={profileImg}/>
        </div>
    )
    }

    export default EditProfileContent
