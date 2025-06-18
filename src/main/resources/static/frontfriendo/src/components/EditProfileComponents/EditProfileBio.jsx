import React, { useEffect, useState } from 'react'

const EditProfileBio = (props) => {
  const [bio,setBio] = useState('');

  const updateBio = (e) => {
    const newBio = e.target.value;
    setBio(newBio);
    props.getBio(newBio);
  };

  useEffect(() => {
    if (props.bio !== undefined) {
      setBio(props.bio);
    }
  }, [props.bio]);

  return (
    <div className='flex flex-col justify-between w-[50%] bg-gray-100 border-1 border-gray-400 dark:bg-gray-800 px-5.5 py-5 rounded-md'>
        <label for="message" class="block mb-2 text-xl font-medium text-gray-900 dark:text-white">Bio</label>
        <textarea id="message" onChange={updateBio} value={bio} rows="2" class="min-h-15 block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Write your bio here..."></textarea>
        <span>your bio is : {bio}</span>
    </div>
  )
}

export default EditProfileBio
