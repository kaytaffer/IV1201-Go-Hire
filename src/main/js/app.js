import React, {StrictMode, useState} from "react";
const ReactDOM= require('react-dom');
import {BrowserRouter, Route, Routes, useNavigate} from "react-router-dom";
import {HomePage} from "./presenter/homePage";
import {Login} from "./presenter/login";

/**
 * Root component for the application
 * @returns {JSX.Element} the rendered application
 * @constructor
 */
function App() {
    const navigate = useNavigate()
    const [user, setUser] = useState()

    function onLoggedIn(user){
        setUser(user)
        navigate('/')
    }

    React.useEffect(() => { // Runs when component is created
        if(window.location.pathname !== '/login' && !user)
            navigate('/login')
    }, [])

    return (
        <div>
            <h1>Go Hire</h1>
            <Routes>
                <Route path='/' element={user ? <HomePage user={user} /> : <div/>}/>
                <Route path='/login' element={<Login onLoggedIn={onLoggedIn}/>}/>
            </Routes>
        </div>
    )
}

ReactDOM.render(
    <StrictMode>
        <BrowserRouter>
            <App />
        </BrowserRouter>
    </StrictMode>,
    document.getElementById('react')
)