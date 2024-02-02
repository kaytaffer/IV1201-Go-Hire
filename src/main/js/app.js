import React, {StrictMode} from "react";
const ReactDOM= require('react-dom');
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {HomePage} from "./presenter/homePage";
import {Login} from "./presenter/login";

/**
 * TODO
 * @returns {JSX.Element}
 * @constructor
 */
function App() {
    return (
        <StrictMode>
            <h1>Go Hire</h1>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<HomePage />}/>
                    <Route path="/login" element={<Login />}/>
                </Routes>
            </BrowserRouter>
        </StrictMode>
    )
}

ReactDOM.render(
    <App />,
    document.getElementById('react')
)