import React, { useState } from 'react'

const ImageStucture = (props) => {

    const imgLength = props.data.length;
    const data = props.data;
    const [currentIndex,setCurrentIndex] = useState(0);
    const [modalStatus,setModalStatus] = useState(false);

    const openModal = (index) => {
        setModalStatus(true);
        setCurrentIndex(index);
    }
    const closeModal = () => {
        setModalStatus(false);
    }
    const nextModal = () => {
        setCurrentIndex((prev) => ((prev + 1) % imgLength));
    }
    const prevModal = () => {
        setCurrentIndex((prev) => ((prev - 1 + imgLength) % imgLength));
    }

    if (imgLength === 0) return null;
  return (
    <div className={imgLength >1 ? "h-[30rem]" : "h-auto flex justify-center"}>
        {data.length === 1 && (
            <div >
            {data.map((item, index) => (
                <img
                loading="eager"
                key={index}
                src={item.imageUrl}
                alt={`Image ${index}`}
                onClick={() => openModal(index)}
                className="h-full object-cover cursor-pointer rounded-xl"
                />
            ))}
            </div>
        )}
        {data.length == 2 && (
            <div className="flex h-full">
            {data.map((item, index) => (
                <img
                loading="eager"
                key={index}
                src={item.imageUrl}
                alt={`Image ${index}`}
                onClick={() => openModal(index)}
                className="w-1/2 h-full object-cover cursor-pointer rounded-xl"
                />
            ))}
            </div>
        )}

        {data.length >= 3 && (
            <div className="flex h-full">
            {/* Left side - first image */}
            <img
                loading="eager"
                src={data[0].imageUrl}
                alt="Image 0"
                onClick={() => openModal(0)}
                className="w-1/2 h-full object-cover cursor-pointer rounded-l-xl"
            />
            {/* Right side - two images stacked vertically */}
            <div className="w-1/2 flex flex-col">
                <img
                loading="eager"
                src={data[1].imageUrl}
                alt="Image 1"
                onClick={() => openModal(1)}
                className="h-1/2 w-full object-cover cursor-pointer rounded-tr-xl"
                />
                <img
                loading="eager"
                src={data[2].imageUrl}
                alt="Image 2"
                onClick={() => openModal(2)}
                className="h-1/2 w-full object-cover cursor-pointer rounded-br-xl"
                />
            </div>
        </div>
        )}
        {modalStatus && <div id="animation-carousel" class="fixed inset-0 bg-[#0000008f] flex items-center justify-center z-50" data-carousel="static">
            <div class="relative" >
                <div onClick={() => closeModal()} class=" cursor-pointer absolute flex text-white text-4xl font-bold px-1 py-1 z-50 border-2 border-gray-600 bg-gray-100 dark:border-gray-400 dark:bg-gray-600 dark:hover:bg-gray-800 hover:bg-gray-300 rounded-full">
                    <button class="cursor-pointer text-black dark:text-gray-300">
                        <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-x-icon lucide-x"><path d="M18 6 6 18"/><path d="m6 6 12 12"/></svg>
                    </button>
                </div>
                <img id="imgModalContent" src={data[currentIndex].imageUrl} class="max-w-full max-h-screen rounded shadow-lg"/>
            </div>
            <button type="button" className={`absolute top-0 start-0 z-30 flex items-center justify-center h-full px-4 group focus:outline-none ${imgLength === 1 ? "hidden" : "flex"}`} data-carousel-prev>
                <span onClick={() => prevModal()} class="cursor-pointer inline-flex items-center justify-center w-10 h-10 rounded-full bg-white/30 dark:bg-gray-800/30 group-hover:bg-white/50 dark:group-hover:bg-gray-800/60 group-focus:ring-4 group-focus:ring-white dark:group-focus:ring-gray-800/70 group-focus:outline-none">
                    <svg class="w-4 h-4 text-white dark:text-gray-800 rtl:rotate-180" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 1 1 5l4 4"/>
                    </svg>
                    <span class="sr-only">Previous</span>
                </span>
            </button>
            <button type="button" className={`absolute top-0 end-0 z-30 flex items-center justify-center h-full px-4 group focus:outline-none ${imgLength === 1 ? "hidden" : "flex"}`} data-carousel-next>
                <span onClick={() => nextModal()} class="cursor-pointer inline-flex items-center justify-center w-10 h-10 rounded-full bg-white/30 dark:bg-gray-800/30 group-hover:bg-white/50 dark:group-hover:bg-gray-800/60 group-focus:ring-4 group-focus:ring-white dark:group-focus:ring-gray-800/70 group-focus:outline-none">
                    <svg class="w-4 h-4 text-white dark:text-gray-800 rtl:rotate-180" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 9 4-4-4-4"/>
                    </svg>
                    <span class="sr-only">Next</span>
                </span>
            </button>
        </div>}
    </div>
  )
}

export default ImageStucture
