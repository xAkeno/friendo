import { useState } from 'react';
import {BrowserRouter,Routes, Route} from 'react-router-dom';
import Index from './pages/index';
import About from './pages/about';
import Faq from './pages/faq';
import Home from './pages/home';
import Friend from './pages/friends'
import Verify from './pages/verify';
import Profile from './pages/profile';
import Chat from './pages/chat';
import Footer from './components/footer';
import './App.css'
import EditProfile from './pages/EditProfile';

function App() {
  return (
    <div className="flex flex-col min-h-screen bg-[#FFFFFF] dark:bg-gray-950 dark:text-white" id="light">
      {/* bg-[#D4A7A7] */}
      <BrowserRouter>
        <main className="flex-grow">
          <Routes>
            <Route index element={<Index/>}/>
            <Route path='/about' element={<About/>}/>
            <Route path='/faq' element={<Faq/>}/>
            <Route path='/Home' element={<Home/>}/>
            <Route path='/Friends' element={<Friend/>}/>
            <Route path='/Verify' element={<Verify/>}/>
            <Route path='Profile/EditProfile' element={<EditProfile/>}/>
            <Route path='/Profile/:username' element={<Profile/>}/>
            <Route path='/chat' element={<Chat/>}/>
          </Routes>
        </main>
        <Footer/>
      </BrowserRouter>
    </div>
  )
}

export default App
