import React from 'react'

const ProfileHeader = () => {
  return (
    <div className="flex gap-20 justify-center items-center px-10">
        <img class="h-48 w-48 rounded-full" src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Kanye_West_at_the_2009_Tribeca_Film_Festival_%28crop_2%29.jpg/250px-Kanye_West_at_the_2009_Tribeca_Film_Festival_%28crop_2%29.jpg"/>
        <div className='flex flex-col gap-5'>
            <h1 className="text-3xl">Akeno</h1>
            <ul className='flex text-lg gap-5'>
                <li> <span>0</span> Posts</li>
                <li> <span>0</span> Friends</li>
            </ul>
            <h1>Clark Kent B. Raguhos</h1>
        </div>
    </div>
  )
}

export default ProfileHeader
