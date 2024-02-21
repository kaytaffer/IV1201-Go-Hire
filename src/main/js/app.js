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
    const [isLoading, setIsLoading] = useState(true)

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
        if(userFromStorage && userFromStorage !== user) {
            setUser(userFromStorage);
        }
        setIsLoading(false)
    }

    React.useEffect(() => {
        persistUserToLocalStorage()
        if(window.location.pathname !== '/login' && !user)
            navigate('/login')
        else if(window.location.pathname === '/login' && user)
            navigate('/')
    }, [user]);

    React.useEffect(() => { // Runs when component is created
        getUserFromLocalStorage()
    }, [])

    return (
        <div>
            <h1>Go Hire</h1>
            {!isLoading &&
                <Routes>
                    <Route path='/' element={<HomePage user={user} />}/>
                    <Route path='/login' element={<Login onLoggedIn={onLoggedIn}/>}/>
                </Routes>
            }
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