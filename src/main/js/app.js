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
    const [user, setUser] = useState(null)

    function onLoggedIn(user){
        setUser(user)
        navigate('/')
    }

    function persistUserToLocalStorage() {
        if(user)
            localStorage.setItem('user', JSON.stringify(user));
    }

    function getUserFromLocalStorage() {
        const userFromStorage = JSON.parse(localStorage.getItem('user'));
        if(userFromStorage && userFromStorage !== user)
            setUser(user);
    }

    React.useEffect(persistUserToLocalStorage, [user]);

    React.useEffect(() => { // Runs when component is created
        getUserFromLocalStorage()
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