import React, {StrictMode, Suspense, useState} from "react";
const ReactDOM= require('react-dom');
import {BrowserRouter, Route, Routes, useNavigate} from "react-router-dom";
import {HomePage} from "./presenter/homePage";
import {Login} from "./presenter/login";
import {MainContentView} from "./view/mainContentView";
import {FooterView} from "./view/footerView";
import {ContainerView} from "./view/containerView";
import {logout} from "./presenter/api/apiCallHandler";
import {i182} from "./presenter/i18n/i18nConfig";
import {TopBar} from "./presenter/topBar";
import {PAGE_DOES_NOT_EXIST} from "./presenter/api/errorMessages";

/**
 * Root component for the application.
 * @returns {JSX.Element} the rendered application.
 * @constructor
 */
function App() {
    const navigate = useNavigate()
    const [user, setUser] = useState(null)
    const [isLoading, setIsLoading] = useState(true)
    const [pathRequestedByUnauthorizedUser, setPathRequestedByUnauthorizedUser] = useState('')

    function onLoggedIn(user){
        setUser(user)
        navigate(pathRequestedByUnauthorizedUser || '/')
        setPathRequestedByUnauthorizedUser('')
    }

    function persistUserToLocalStorage() {
        if(user)
            localStorage.setItem('user', JSON.stringify(user));
    }

    function getUserFromLocalStorage() {
        const userFromStorage = JSON.parse(localStorage.getItem('user'));
        if(userFromStorage && userFromStorage !== user)
            setUser(userFromStorage);
        setIsLoading(false)
    }

    React.useEffect(() => {
        persistUserToLocalStorage()
        if(window.location.pathname !== '/login' && !user) {
            setPathRequestedByUnauthorizedUser(window.location.pathname)
            navigate('/login')
        }
        else if(window.location.pathname === '/login' && user)
            navigate('/')
    }, [user]);

    React.useEffect(() => { // Runs when component is created
        getUserFromLocalStorage()
    }, [])

    function onLogout() {
        function logoutInBrowser() {
            localStorage.clear()
            navigate('/login')
            setUser(null)
        }

        logout().then(logoutInBrowser).catch(error => {
            if(error.message === PAGE_DOES_NOT_EXIST.errorType)
                logoutInBrowser()
        })
    }

    return (
        <div>
            <Suspense fallback={<div>loading...</div>}>
                <ContainerView>
                    <TopBar user={user}
                            onLogout={onLogout}/>
                    <MainContentView>
                        {!isLoading &&
                            <Routes>
                                <Route path='/' element={<HomePage user={user}/>}/>
                                <Route path='/login' element={<Login onLoggedIn={onLoggedIn}/>}/>
                            </Routes>
                        }
                    </MainContentView>
                    <FooterView/>
                </ContainerView>
            </Suspense>
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