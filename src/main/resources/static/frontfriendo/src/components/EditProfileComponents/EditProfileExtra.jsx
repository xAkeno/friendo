import React, { useEffect, useState } from 'react'
import CountryCity from './CountryCity.json';
const statusOptions = ['Single','In a relationship','Married', 'Divorced', 'Widowed','None'];
const EditProfileExtra = (props) => {
    
    const [country,setCountry] = useState('Philippines');
    const [city, setCity] = useState('');
    const [school,setSchool] = useState('');
    const [status,setStatus] = useState('Single');
    const handleChange = (e) => {
        setCountry(e.target.value)
        setCity('')
    }
    const handleCityChange = (e) => {
        setCity(e.target.value);
    };
    const handleSchoolChange = (e) => {
        setSchool(e.target.value)
    }
    const handleStatusChange = (e) => {
        setStatus(e.target.value)
    }

    const selectedCountry = CountryCity.find((item) => item.country === country);
    useEffect(() => {
        setCountry(props.country || 'Philippines');
        setCity(props.city || '');
        setSchool(props.school || '');
        setStatus(props.status || 'Single');
    }, [props.country, props.city, props.school, props.status]);

  return (
    <div  className="className='flex flex-col [&>div]:mt-2 justify-between w-[50%] bg-gray-100 border-1 border-gray-400 dark:bg-gray-800 px-5.5 py-5 rounded-md">
        <div>
            <label for="Country" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Country</label>
            {
                country === "Others" ? <input type="text" id="Country" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Type your Country" required /> 
                : <select value={country} onChange={handleChange} className='bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500'>
                    {
                        CountryCity.map((item,index) => (<option key={index} value={item.country}>{item.country}</option>))
                    }
                </select> 
            }
        </div>
        <div>
            <label for="city" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">City</label>
            {
                country === "Others" ? <input type="text" id="city" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Type your City" required /> 
                :<select value={city} onChange={handleCityChange} required className='bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500'>
                    <option>Select a city</option>
                    {selectedCountry &&
                        selectedCountry.cities.map((cityName, i) => (
                        <option key={i} value={cityName}>
                            {cityName}
                        </option>
                    ))}
                </select>
            } 
        </div>
        <div>
            <label for="School" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">School</label>
            <input type="text" value={school} onChange={handleSchoolChange} id="School" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Type your school" required />
        </div>
        <div>
            <label for="status" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Status</label>
            <select value={status} onChange={handleStatusChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Type your school">
                {
                    statusOptions.map((item,index) => (<option value={item}>{item}</option>))
                }
            </select>
        </div>
        <div>
            <p>
                <span className="text-gray-700 dark:text-gray-400">Current country selected: </span>
                <span className="font-semibold text-gray-900 dark:text-white">{country || 'None'}</span>
            </p>
            <p>
                <span className="text-gray-700 dark:text-gray-400">Current city selected: </span>
                <span className="font-semibold text-gray-900 dark:text-white">{city || 'None'}</span>
            </p>
            <p>
                <span className="text-gray-700 dark:text-gray-400">Succrent school chosen: </span>
                <span className="font-semibold text-gray-900 dark:text-white">{school || 'None'}</span>
            </p>
            <p>
                <span className="text-gray-700 dark:text-gray-400">Current status: </span>
                <span className="font-semibold text-gray-900 dark:text-white">{status || 'None'}</span>
            </p>
        </div>
    </div>
  )
}

export default EditProfileExtra
