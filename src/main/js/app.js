import React, {StrictMode} from "react";
const ReactDOM= require('react-dom');

/**
 * TODO
 * @returns {JSX.Element}
 * @constructor
 */
function App() {

    return (
        <StrictMode>
            <h1>Go Hire</h1>
        </StrictMode>
    )

}

ReactDOM.render(
    <App />,
    document.getElementById('react')
)