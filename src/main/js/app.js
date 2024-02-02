import React, {StrictMode, useState} from "react";
const ReactDOM= require('react-dom');
import {BrowserRouter, Route, Routes, useNavigate} from "react-router-dom";
import {HomePage} from "./presenter/homePage";
import {Login} from "./presenter/login";

/**
 * TODO
 * @returns {JSX.Element}
 * @constructor
 */
function App() {
    const navigate = useNavigate()
    const [user, setUser] = useState()

    React.useEffect(() => { // Runs when component is created
        if(window.location.pathname !== '/login' && !user)
            navigate('/login')
    }, [])

    return (
        <div>
            <h1>Go Hire</h1>
            <Routes>
                <Route path='/' element={user ? <HomePage /> : <div/>}/>
                <Route path='/login' element={<Login />}/>
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